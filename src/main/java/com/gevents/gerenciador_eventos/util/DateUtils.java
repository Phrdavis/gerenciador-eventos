package com.gevents.gerenciador_eventos.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtils {

    // Regex para encontrar o padrão de lista de datas: "dd, dd, dd e dd/mm"
    private static final Pattern DATES_LIST_PATTERN = Pattern.compile("(.+?)\\s*(\\d{1,2}/\\d{4}|\\d{1,2}/\\d{2})$");

    // Formatadores para parsing de data
    private static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("dd.MM.yyyy"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("dd/MM"),
        DateTimeFormatter.ofPattern("dd-MM")
    );

    // Formatador para datas com nomes de meses em português
    private static final DateTimeFormatter TEXT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));

    /**
     * Função principal para analisar datas a partir de strings extraídas de documentos.
     * Ela lida com datas saltadas, intervalos de datas e datas únicas.
     * * @param dateStr A string de data de início (pode conter uma lista de datas).
     * @param endDateStr A string de data de finalização (opcional).
     * @return Uma lista de LocalDate.
     */
    public static List<LocalDate> parseDates(String dateStr, String endDateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String cleanedInput = dateStr.trim();

        // 1. Tentar parsear como uma lista de datas saltadas
        if (cleanedInput.contains(",")) {
            List<LocalDate> parsedDates = parseDateList(cleanedInput);
            if (!parsedDates.isEmpty()) {
                return parsedDates;
            }
        }
        
        // 2. Tentar parsear como um intervalo de datas
        if (endDateStr != null && !endDateStr.trim().isEmpty()) {
            LocalDate startDate = parseSingleDate(cleanedInput);
            LocalDate endDate = parseSingleDate(endDateStr.trim());
            if (startDate != null && endDate != null) {
                return createDateRange(startDate, endDate);
            }
        }

        // 3. Tentar parsear como um formato de texto (ex: "10 de outubro de 2025")
        try {
            LocalDate textDate = LocalDate.parse(cleanedInput, TEXT_DATE_FORMATTER);
            return List.of(textDate);
        } catch (DateTimeParseException e) {
            // Se falhar, tenta o próximo padrão
        }
        
        // 4. Tentar parsear como uma única data em formatos comuns (ex: "10/10/2025", "10/10")
        LocalDate singleDate = parseSingleDate(cleanedInput);
        if (singleDate != null) {
            return List.of(singleDate);
        }

        System.err.println("Nenhum padrão de data reconhecido para: " + dateStr);
        return new ArrayList<>();
    }

    // --- Funções Auxiliares ---
    
    // Função para analisar uma única data em múltiplos formatos
    public static LocalDate parseSingleDate(String dateStr) {
        String processedDateStr = dateStr;
        if (dateStr.matches("\\d{1,2}[/-]\\d{1,2}")) {
            processedDateStr = String.format("%s/%d", dateStr, LocalDate.now().getYear());
        }
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(processedDateStr, formatter);
            } catch (DateTimeParseException e) {
                // Tentar o próximo
            }
        }
        return null;
    }

    // Função para analisar strings com listas de datas
    private static List<LocalDate> parseDateList(String input) {
        List<LocalDate> dates = new ArrayList<>();
        Matcher matcher = DATES_LIST_PATTERN.matcher(input.replace("e", ""));
        
        if (matcher.find()) {
            try {
                String dayPart = matcher.group(1).trim();
                String monthAndYear = matcher.group(2).trim();
                
                String[] days = dayPart.split("[,\\s]+");
                
                for (String day : days) {
                    if (!day.trim().isEmpty()) {
                        String fullDateStr = String.format("%s/%s", day.trim(), monthAndYear);
                        LocalDate date = parseSingleDate(fullDateStr);
                        if (date != null) {
                            dates.add(date);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro ao analisar a lista de datas: " + input);
                return new ArrayList<>();
            }
        }
        return dates;
    }

    // Função para gerar um intervalo de datas (Útil para o seu caso de datas de início e fim)
    public static List<LocalDate> createDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return new ArrayList<>();
        }
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return Stream.iterate(startDate, date -> date.plusDays(1))
                     .limit(numOfDays)
                     .collect(Collectors.toList());
    }
}
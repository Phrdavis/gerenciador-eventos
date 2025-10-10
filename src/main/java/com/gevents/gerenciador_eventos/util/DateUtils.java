package com.gevents.gerenciador_eventos.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtils {

    // Regex para encontrar listas de datas com conectores "e", "E", "a" ou "A"
    private static final Pattern DATES_LIST_PATTERN = Pattern.compile("(.+?)\\s*(e|E|a|A)\\s*(\\d{1,2}\\/\\d{4}|\\d{1,2}\\/\\d{2})$");

    private static final Pattern RANGE_PATTERN = Pattern.compile("(\\d{1,2})\\s*(?:a|A|e|E|\\s)?\\s*(\\d{1,2})\\s*(\\d{1,2}\\/\\d{4}|\\d{1,2}\\/\\d{2})$");

    // Formatadores para parsing de data
    private static final List<DateTimeFormatter> DATE_FORMATTERS = Arrays.asList(
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("dd.MM.yyyy"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("dd/MM"),
        DateTimeFormatter.ofPattern("dd-MM")
    );

    // Formatador para datas com nomes de meses em português
    private static final DateTimeFormatter TEXT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", Locale.forLanguageTag("pt-BR"));
    
    /**
     * Função principal para analisar datas a partir de strings extraídas de documentos.
     * @param dateStr A string de data de início (pode conter uma lista de datas ou uma única data).
     * @return Uma lista de LocalDate.
     */
    public static List<LocalDate> parseDates(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty() || dateStr.trim().equals("-")) {
            return new ArrayList<>();
        }

        String cleanedInput = dateStr.trim().replaceAll("\\(.+\\)", "");

        Matcher rangeMatcher = RANGE_PATTERN.matcher(cleanedInput);
        if (rangeMatcher.find()) {
            try {
                LocalDate startDate = parseSingleDate(rangeMatcher.group(1).trim() + "/" + rangeMatcher.group(3).trim());
                LocalDate endDate = parseSingleDate(rangeMatcher.group(2).trim() + "/" + rangeMatcher.group(3).trim());
                return createDateRange(startDate, endDate);
            } catch (Exception e) {
                System.err.println("Erro ao analisar o intervalo de datas: " + cleanedInput);
                return new ArrayList<>();
            }
        }
        
        Matcher listMatcher = DATES_LIST_PATTERN.matcher(cleanedInput);
        if (listMatcher.find()) {
            try {
                String dayPart = listMatcher.group(1).trim();
                String connector = listMatcher.group(2).trim().toLowerCase(); 
                String monthAndYear = listMatcher.group(3).trim();

                if ("e".equals(connector)) {
                    String[] days = dayPart.split("[,\\s]+");
                    List<LocalDate> dates = new ArrayList<>();
                    for (String day : days) {
                        if (!day.trim().isEmpty()) {
                            LocalDate date = parseSingleDate(day.trim() + "/" + monthAndYear);
                            if (date != null) {
                                dates.add(date);
                            }
                        }
                    }
                    return dates;
                }
            } catch (Exception e) {
                System.err.println("Erro ao analisar a lista de datas: " + cleanedInput);
                return new ArrayList<>();
            }
        }

        // 2. Tentar parsear como um formato de texto (ex: "10 de outubro de 2025")
        try {
            LocalDate textDate = LocalDate.parse(cleanedInput, TEXT_DATE_FORMATTER);
            return new ArrayList<>(List.of(textDate));
        } catch (DateTimeParseException e) {
            // Se falhar, tenta o próximo padrão
        }
        
        // Se não for uma lista, tentar parsear como uma única data
        LocalDate singleDate = parseSingleDate(cleanedInput);
        if (singleDate != null) {
            return new ArrayList<>(List.of(singleDate));
        }

        System.err.println("Nenhum padrão de data reconhecido para: " + dateStr);
        return new ArrayList<>();
    }
    
    // --- Funções Auxiliares ---
    
    public static LocalDate parseSingleDate(String dateStr) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Erro ao analisar a data: " + dateStr);
                return null;
            }
        }
        return null;
    }
    
    // private static List<LocalDate> parseDateList(String input) {
    //     List<LocalDate> dates = new ArrayList<>();
    //     Matcher matcher = DATES_LIST_PATTERN.matcher(input);
        
    //     if (matcher.find()) {
    //         try {
    //             String dayPart = matcher.group(1).trim();
    //             String connector = matcher.group(2).trim().toLowerCase();
    //             String monthAndYear = matcher.group(3).trim();

    //             if ("a".equals(connector)) {
    //                 String[] dayRange = dayPart.split("(?i)\\s*a\\s*");
    //                 if (dayRange.length == 2) {
    //                     LocalDate startDate = parseSingleDate(dayRange[0].trim() + "/" + monthAndYear);
    //                     LocalDate endDate = parseSingleDate(dayRange[1].trim() + "/" + monthAndYear);
    //                     return createDateRange(startDate, endDate);
    //                 }
    //             } else if ("e".equals(connector)) {
    //                 String[] days = dayPart.split("[,\\s]+");
                    
    //                 for (String day : days) {
    //                     if (!day.trim().isEmpty()) {
    //                         LocalDate date = parseSingleDate(day.trim() + "/" + monthAndYear);
    //                         if (date != null) {
    //                             dates.add(date);
    //                         }
    //                     }
    //                 }
    //                 return dates;
    //             }
    //         } catch (Exception e) {
    //             System.err.println("Erro ao analisar a lista de datas: " + input);
    //             return new ArrayList<>();
    //         }
    //     }
    //     return dates;
    // }

    private static List<LocalDate> createDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return new ArrayList<>();
        }
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return Stream.iterate(startDate, date -> date.plusDays(1))
                     .limit(numOfDays)
                     .collect(Collectors.toList());
    }
}
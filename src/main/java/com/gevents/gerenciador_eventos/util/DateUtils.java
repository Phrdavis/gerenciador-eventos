package com.gevents.gerenciador_eventos.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtils {

    // Lista de formatadores de data comuns que podem ser encontrados nos PDFs
    private static final List<DateTimeFormatter> DATE_FORMATTERS = Arrays.asList(
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("dd.MM.yyyy"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("d MMMM yyyy"),
        DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy") // Ex: 10 de outubro de 2025
    );

    public static LocalDate stringToDate(String input) {
        if (input == null || input.trim().isEmpty()) {
            return LocalDate.now();
        }

        String cleanedInput = input.trim();

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                // Tenta analisar a string com cada um dos formatadores
                return LocalDate.parse(cleanedInput, formatter);
            } catch (DateTimeParseException e) {
                // Se a análise falhar, tenta o próximo formatador
                continue;
            }
        }

        // Se nenhum formatador funcionou, você pode lançar uma exceção ou retornar null/uma data padrão
        System.err.println("Não foi possível analisar a data: " + input);
        return null; // ou throw new IllegalArgumentException("Formato de data inválido: " + input);
    }

    public static List<LocalDate> stringToDates(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<LocalDate> dates = new ArrayList<>();
        
        // Padrão para encontrar os dias e a parte final com mês e ano
        Pattern pattern = Pattern.compile("(.+?)\\s*e\\s*([^\\s]+)$");
        Matcher matcher = pattern.matcher(input);
        
        String dayPart = input;
        String monthAndYear = "";
        
        if (matcher.find()) {
            // Se encontrar o padrão, separa os dias e a parte final
            dayPart = matcher.group(1).replace("e", "").trim();
            monthAndYear = "/" + matcher.group(2).trim();
        }

        // Divide a string de dias usando ',' como delimitador
        String[] days = dayPart.split(",\\s*");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (String day : days) {
            String fullDateString = day.trim() + monthAndYear;
            try {
                // Tenta analisar cada data e adiciona na lista
                dates.add(LocalDate.parse(fullDateString, formatter));
            } catch (Exception e) {
                // Tratar erro de parse, se necessário
                System.err.println("Erro ao analisar a data: " + fullDateString);
            }
        }

        return dates;
    }
    public static List<LocalDate> extractDatesFromRangeOrList(String startDateStr, String endDateStr) {
        // Se a data de início for uma lista de datas, use a lógica de datas saltadas
        if (startDateStr.contains(",")) {
            return stringToDates(startDateStr);
        }

        // Se a data de início e fim estiverem preenchidas, considere um intervalo
        if (startDateStr != null && !startDateStr.trim().isEmpty() &&
            endDateStr != null && !endDateStr.trim().isEmpty()) {

            LocalDate startDate = stringToDate(startDateStr);
            LocalDate endDate = stringToDate(endDateStr);

            // Se as datas são válidas, gere a lista de datas do intervalo
            if (startDate != null && endDate != null && !startDate.isAfter(endDate)) {
                long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
                return Stream.iterate(startDate, date -> date.plusDays(1))
                             .limit(numOfDaysBetween)
                             .collect(Collectors.toList());
            }
        }

        // Se nenhuma das condições acima for atendida, retorne uma lista vazia ou com uma única data
        LocalDate singleDate = stringToDate(startDateStr);
        if (singleDate != null) {
            return List.of(singleDate);
        }

        return new ArrayList<>();
    }
}
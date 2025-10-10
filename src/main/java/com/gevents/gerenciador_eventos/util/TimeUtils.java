package com.gevents.gerenciador_eventos.util;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {
    
    public static String getCurrentTimestamp() {
        return java.time.LocalDateTime.now().toString().replace("T", " ").substring(0, 19);
    }

    public static LocalTime stringToTime(String timeStr) {
        timeStr = timeStr.trim();

        Pattern pattern1 = Pattern.compile("(?is)^(\\d{1,2})\\s*h[r]?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(timeStr);
        if (matcher1.matches()) {
            int hour = Integer.parseInt(matcher1.group(1));
            return LocalTime.of(hour, 0);  // Retorna LocalTime com hora e 0 minutos
        }

        // Caso "23:45h" ou "23:45 h"
        Pattern pattern2 = Pattern.compile("(?is)^(\\d{1,2}):(\\d{2})\\s*h?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher2 = pattern2.matcher(timeStr);
        if (matcher2.matches()) {
            int hour = Integer.parseInt(matcher2.group(1));
            int minute = Integer.parseInt(matcher2.group(2));
            return LocalTime.of(hour, minute);  // Retorna LocalTime com hora e minuto
        }

        // Caso "23:45"
        Pattern pattern3 = Pattern.compile("(?is)^(\\d{1,2}):(\\d{2})$");
        Matcher matcher3 = pattern3.matcher(timeStr);
        if (matcher3.matches()) {
            int hour = Integer.parseInt(matcher3.group(1));
            int minute = Integer.parseInt(matcher3.group(2));
            return LocalTime.of(hour, minute);  // Retorna LocalTime com hora e minuto
        }

        // Caso "23:45:30"
        Pattern pattern4 = Pattern.compile("(?is)^(\\d{1,2}):(\\d{2}):(\\d{2})$");
        Matcher matcher4 = pattern4.matcher(timeStr);
        if (matcher4.matches()) {
            int hour = Integer.parseInt(matcher4.group(1));
            int minute = Integer.parseInt(matcher4.group(2));
            int second = Integer.parseInt(matcher4.group(3));
            return LocalTime.of(hour, minute, second);  // Retorna LocalTime com hora, minuto e segundo
        }

        // Caso "23h59"
        Pattern pattern5 = Pattern.compile("(?is)^(\\d{1,2})h(\\d{2})$");
        Matcher matcher5 = pattern5.matcher(timeStr);
        if (matcher5.matches()) {
            int hour = Integer.parseInt(matcher5.group(1));
            int minute = Integer.parseInt(matcher5.group(2));
            return LocalTime.of(hour, minute);  // Retorna LocalTime com hora e minuto
        }

        // Se não bater nenhum formato, retorna null (ou você pode lançar uma exceção, se preferir)
        return null;
    }
    
}

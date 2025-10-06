package com.gevents.gerenciador_eventos.util;

import java.util.regex.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.gevents.gerenciador_eventos.dto.EventoDTO;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ExtrairDados {

    // Método para buscar correspondência usando regex
    public static String getRegexMatch(String input, String pattern, int groupIndex) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        if (matcher.find()) {
            return matcher.group(groupIndex);
        }
        return "";
    }

    public static String captalizingString(String text){

        var arrayString = text.split(" ");
        String finalString = "";

        for(String word : arrayString){
            if(word.trim() != ""){
                finalString += word.trim().substring(0, 1).toUpperCase() + word.substring(1) + " ";
            }
        }

        return finalString.trim() == "" ? text.trim() : finalString.trim();
    }
    
    private static String getUltimaDescricao(String texto) {
        Pattern pattern = Pattern.compile("(?i)solicitação:\\s*\\n+\\s*(?:•\\s*)?(.*?)\\n*(?:$|\\Z)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(texto);

        String ultimaDescricao = null;
        while (matcher.find()) {
            ultimaDescricao = matcher.group(1).trim();
        }

        return ultimaDescricao != null ? ultimaDescricao : "";
    }

    public static LocalDate stringToDate(String input) {

        if (input == null || input.trim().isEmpty()) {
            return LocalDate.now();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(input.trim(), formatter);
    }

    public static LocalTime stringToTime(String timeStr) {
        // Remove espaços extras
        timeStr = timeStr.trim();

        // Caso "19hr", "19h", "19 h"
        Pattern pattern1 = Pattern.compile("^(\\d{1,2})\\s*h[r]?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(timeStr);
        if (matcher1.matches()) {
            int hour = Integer.parseInt(matcher1.group(1));
            return LocalTime.of(hour, 0);  // Retorna LocalTime com hora e 0 minutos
        }

        // Caso "23:45h" ou "23:45 h"
        Pattern pattern2 = Pattern.compile("^(\\d{1,2}):(\\d{2})\\s*h?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher2 = pattern2.matcher(timeStr);
        if (matcher2.matches()) {
            int hour = Integer.parseInt(matcher2.group(1));
            int minute = Integer.parseInt(matcher2.group(2));
            return LocalTime.of(hour, minute);  // Retorna LocalTime com hora e minuto
        }

        // Caso "23:45"
        Pattern pattern3 = Pattern.compile("^(\\d{1,2}):(\\d{2})$");
        Matcher matcher3 = pattern3.matcher(timeStr);
        if (matcher3.matches()) {
            int hour = Integer.parseInt(matcher3.group(1));
            int minute = Integer.parseInt(matcher3.group(2));
            return LocalTime.of(hour, minute);  // Retorna LocalTime com hora e minuto
        }

        // Caso "23:45:30"
        Pattern pattern4 = Pattern.compile("^(\\d{1,2}):(\\d{2}):(\\d{2})$");
        Matcher matcher4 = pattern4.matcher(timeStr);
        if (matcher4.matches()) {
            int hour = Integer.parseInt(matcher4.group(1));
            int minute = Integer.parseInt(matcher4.group(2));
            int second = Integer.parseInt(matcher4.group(3));
            return LocalTime.of(hour, minute, second);  // Retorna LocalTime com hora, minuto e segundo
        }

        // Caso "23h59"
        Pattern pattern5 = Pattern.compile("^(\\d{1,2})h(\\d{2})$");
        Matcher matcher5 = pattern5.matcher(timeStr);
        if (matcher5.matches()) {
            int hour = Integer.parseInt(matcher5.group(1));
            int minute = Integer.parseInt(matcher5.group(2));
            return LocalTime.of(hour, minute);  // Retorna LocalTime com hora e minuto
        }

        // Se não bater nenhum formato, retorna null (ou você pode lançar uma exceção, se preferir)
        return null;
    }

    public static String cleanPhone(String input) {
        return input.trim().replaceAll("[\\s\\-]", "");
    }

    public static String getCurrentTimestamp() {
        return java.time.LocalDateTime.now().toString().replace("T", " ").substring(0, 19);
    }

    public static EventoDTO extrairDadosFundacao(String conteudo) {

        EventoDTO evento = new EventoDTO();

        evento.setModelo("Fudass"); 
        evento.setUpload(getCurrentTimestamp());

        evento.setNumSolicitacao(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*(\\d+/\\d+)", 1));
        evento.setDestino(captalizingString(getRegexMatch(conteudo, "(?i)destino:\\s*(.+)", 1)));
        evento.setDescricao(captalizingString(getUltimaDescricao(conteudo)));
        evento.setNome(captalizingString(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*\\d+/\\d+\\s*–\\s*(.+)", 1)));
        evento.setData(stringToDate(getRegexMatch(conteudo, "(?i)data da solicitação:\\s*([^\\n]+)", 1)));
        evento.setInicio(stringToDate(getRegexMatch(conteudo, "(?i)data início do evento:\\s*([^\\n]+)", 1)));
        evento.setFim(stringToDate(getRegexMatch(conteudo, "(?i)data finalização do evento:\\s*([^\\n]+)", 1)));
        evento.setHoraInicio(stringToTime(getRegexMatch(conteudo, "(?i)horário de início:\\s*([^\\n]+)", 1)));
        evento.setHoraFim(stringToTime(getRegexMatch(conteudo, "(?i)horário de finalização:\\s*([^\\n]+)", 1)));
        evento.setLocal(captalizingString(getRegexMatch(conteudo, "(?is)local de montagem:\\s*(.*?)\\s*(data de montagem:|responsável por)", 1)));
        evento.setResponsavel(captalizingString(getRegexMatch(conteudo, "(?is)responsável por\\s*acompanhar montagem:\\s*(.*?)\\s*(\\d{2}\\s*\\d{4,5}\\-?\\d{4})", 1)));
        evento.setTelefoneResponsavel(cleanPhone(getRegexMatch(conteudo, "(?is)responsável por\\s*acompanhar montagem:\\s*(.*?)\\s*(\\d{2}\\s*\\d{4,5}\\-?\\d{4})", 2)));

        return evento;

    }

    public static EventoDTO extrairDadosPrefeitura(String conteudo) {

        EventoDTO evento = new EventoDTO();

        evento.setModelo("Prefeitura");
        evento.setUpload(getCurrentTimestamp());

        evento.setNumSolicitacao(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*(\\d+/\\d+)", 1));
        evento.setDestino(captalizingString(getRegexMatch(conteudo, "(?i)destino:\\s*(.+)", 1)));
        evento.setDescricao(captalizingString(getUltimaDescricao(conteudo)));
        evento.setNome(captalizingString(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*\\d+/\\d+\\s*–\\s*(.+)", 1)));
        evento.setData(stringToDate(getRegexMatch(conteudo, "(?i)data da solicitação:\\s*([^\\n]+)", 1)));
        evento.setInicio(stringToDate(getRegexMatch(conteudo, "(?i)datas:\\s*([^\\n]+)", 1)));
        evento.setFim(stringToDate(getRegexMatch(conteudo, "(?i)datas:\\s*([^\\n]+)", 1)));
        evento.setHoraInicio(stringToTime(getRegexMatch(conteudo, "(?i)horário de início:\\s*([^\\n]+)", 1)));
        evento.setHoraFim(stringToTime(getRegexMatch(conteudo, "(?i)horário de finalização:\\s*([^\\n]+)", 1)));
        evento.setLocal(captalizingString(getRegexMatch(conteudo, "(?i)local de montagem:\\s*(.*?)\\s*(data de montagem:|responsável por)", 1)));
        evento.setResponsavel(captalizingString(getRegexMatch(conteudo, "(?i)responsável por\\s*acompanhar montagem:\\s*([A-Za-z\\s\\.]+)\\s+([\\d\\s\\-]+)", 1)));
        evento.setTelefoneResponsavel(cleanPhone(getRegexMatch(conteudo, "(?i)responsável por\\s*acompanhar montagem:\\s*([A-Za-z\\s\\.]+)\\s+([\\d\\s\\-]+)", 2)));

        return evento;

    }
    
    public static String extrairTextoDePDF(String caminhoPDF) throws IOException {

        PDDocument document = PDDocument.load(new File(caminhoPDF));
        
        PDFTextStripper stripper = new PDFTextStripper();
        String texto = stripper.getText(document);
        
        document.close();
        
        return texto;
    }
}

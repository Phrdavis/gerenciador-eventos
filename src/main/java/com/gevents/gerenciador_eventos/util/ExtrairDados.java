package com.gevents.gerenciador_eventos.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.gevents.gerenciador_eventos.dto.EventoDTO;
import com.gevents.gerenciador_eventos.enums.StatusErro;

public class ExtrairDados {

    private static List<ErroExtracao> erros = new ArrayList<>();

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
        Pattern pattern = Pattern.compile("(?i)(?<!Nº)SOLICITAÇÃO:\\s*(.*?)\\s*(?:OBSERVAÇÕES:|\\Z)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(texto);

        String ultimaDescricao = null;
        while (matcher.find()) {
            ultimaDescricao = matcher.group(1).trim();
        }

        return ultimaDescricao != null ? ultimaDescricao : "";
    }

    public static String cleanPhone(String input) {
        return input.trim().replaceAll("[\\s\\-]", "");
    }

    public static EventoDTO extrairDadosFundacao(String conteudo, String nomeArquivo) {

        EventoDTO evento = new EventoDTO();

        evento.setModelo("Fudass"); 
        evento.setUpload(TimeUtils.getCurrentTimestamp());

        // String numSolicitacao = extrairComTratamento(() -> getRegexMatch(conteudo, "(?i)nº solicitação:\\s*(?:.*\\s)?(\\d+\\/\\d+)", 1),
        //     "FUDASS_SOLICITACAO_AUSENTE", "Número de solicitação não encontrado.", nomeArquivo, StatusErro.ERROR);
        String numSolicitacao = getRegexMatch(conteudo, "(?i)nº solicitação:\\s*(?:.*\\s)?(\\d+\\/\\d+)", 1);

        evento.setNumSolicitacao(numSolicitacao);

        String destino = extrairComTratamento(() -> getRegexMatch(conteudo, "(?i)destino:\\s*(.+)", 1),
            "FUDASS_DESTINO_AUSENTE", "Destino não encontrado.", nomeArquivo, StatusErro.WARN);

        evento.setDestino(destino);

        String descricao = extrairComTratamento(() -> captalizingString(getUltimaDescricao(conteudo)),
            "FUDASS_DESCRICAO_AUSENTE", "Descrição não encontrada.", nomeArquivo, StatusErro.WARN);

        evento.setDescricao(descricao);

        // String nome = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*\\d+/\\d+\\s*–\\s*(.+)", 1)),
        //     "FUDASS_NOME_AUSENTE", "Nome não encontrado.", nomeArquivo, StatusErro.ERROR);
        String nome = captalizingString(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*(?:.*\\s)?\\d+/\\d+\\s*–\\s*(.+)", 1));
        evento.setNome(nome);

        String dataSolicitacaoStr = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)data da solicitação:\\s*([^\\n]+)", 1)),
            "FUDASS_DATA_SOLICITACAO_AUSENTE", "Data Solicitação não encontrada.", nomeArquivo, StatusErro.WARN);

        if (dataSolicitacaoStr != null) {
            evento.setDataSolicitacao(DateUtils.parseSingleDate(dataSolicitacaoStr));
        };

        String dataInicioStr = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)data início do evento:\\s*([^\\n]+)", 1)),
            "FUDASS_DATA_INICIO_AUSENTE", "Data Início não encontrada.", nomeArquivo, StatusErro.WARN);

        String dataFimStr = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)data finalização do evento:\\s*([^\\n]+)", 1)),
            "FUDASS_DATA_FIM_AUSENTE", "Data Fim não encontrada.", nomeArquivo, StatusErro.WARN);

        if (dataInicioStr != null) {
            List<LocalDate> datasDoEvento = DateUtils.parseDates(dataInicioStr);
            if(!datasDoEvento.isEmpty()){
                evento.setDatas(datasDoEvento);
            }
        }
        if (dataFimStr != null) {
            List<LocalDate> datasDoEvento = DateUtils.parseDates(dataFimStr);
            if(!datasDoEvento.isEmpty()){
                evento.getDatas().addAll(datasDoEvento);
            }
        }

        String horaInicioStr = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)horário de início:\\s*([^\\n]+)", 1)),
            "FUDASS_HORA_INICIO_AUSENTE", "Hora de Início não encontrada.", nomeArquivo, StatusErro.WARN);

        if (horaInicioStr != null) {
            evento.setHoraInicio(TimeUtils.stringToTime(horaInicioStr));
        }

        String horaFimStr = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)horário de finalização:\\s*([^\\n]+)", 1)),
            "FUDASS_HORA_FIM_AUSENTE", "Hora de Fim não encontrada.", nomeArquivo, StatusErro.WARN);

        if (horaFimStr != null && !horaFimStr.equals("-")) {
            evento.setHoraFim(TimeUtils.stringToTime(horaFimStr));
        }else{
            evento.setHoraFim(TimeUtils.stringToTime(horaInicioStr));
        }

        String local = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?is)local de montagem:\\s*(.*?)\\s*(data de montagem:|responsável por)", 1)),
            "FUDASS_LOCAL_AUSENTE", "Local não encontrado.", nomeArquivo, StatusErro.WARN);

        evento.setLocal(local);

        String responsavel = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?is)Responsável por\\s*Acompanhar Montagem:\\s*(.*?)(?=\\s*solicitação:|\\s*OBSERVAÇÕES:|\\Z)", 1)),
            "FUDASS_RESPONSAVEL_AUSENTE", "Responsável não encontrado.", nomeArquivo, StatusErro.WARN);

        if (responsavel != null && !responsavel.isEmpty()) {
            // Regex para extrair apenas o nome, que é a primeira linha
            Pattern nomePattern = Pattern.compile("(?s)(.*?)(?:\\n|\\r|\\Z)");
            Matcher nomeMatcher = nomePattern.matcher(responsavel);

            if (nomeMatcher.find()) {
                String nomeRespon = nomeMatcher.group(1).trim();
                evento.setResponsavel(captalizingString(nomeRespon));
            }

            // Regex para extrair o telefone
            Pattern telefonePattern = Pattern.compile("(\\d{2,3})\\s*(\\d{4,5}\\-?\\d{4})");
            Matcher telefoneMatcher = telefonePattern.matcher(responsavel);

            if (telefoneMatcher.find()) {
                String telefone = telefoneMatcher.group(1).trim().replaceAll("\\s+", ""); // Remove espaços
                evento.setTelefoneResponsavel(telefone);
            }
        }

        // evento.setResponsavel(responsavel);

        // String telResponsavel = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(\\d{2,3})\\s*(\\d{4,5}\\-?\\d{4})", 2)),
        //     "FUDASS_TELEFONE_RESPONSAVEL_AUSENTE", "Telefone do Responsável não encontrado.", nomeArquivo, StatusErro.WARN);

        // evento.setTelefoneResponsavel(telResponsavel);

        // evento.setNumSolicitacao(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*(\\d+/\\d+)", 1));
        // evento.setDestino(captalizingString(getRegexMatch(conteudo, "(?i)destino:\\s*(.+)", 1)));
        // evento.setDescricao(captalizingString(getUltimaDescricao(conteudo)));
        // evento.setNome(captalizingString(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*\\d+/\\d+\\s*–\\s*(.+)", 1)));
        // evento.setDataSolicitacao(DateUtils.parseSingleDate(getRegexMatch(conteudo, "(?i)data da solicitação:\\s*([^\\n]+)", 1)));
        
        // String dataInicioStr = getRegexMatch(conteudo, "(?i)data início do evento:\\s*([^\\n]+)", 1);
        // String dataFimStr = getRegexMatch(conteudo, "(?i)data finalização do evento:\\s*([^\\n]+)", 1);

        // List<LocalDate> datasDoEvento = new ArrayList<>();

        // datasDoEvento = DateUtils.parseDates(dataInicioStr);

        // if (datasDoEvento.isEmpty() && dataFimStr != null && !dataFimStr.trim().isEmpty()) {
        //     LocalDate dataInicio = DateUtils.parseSingleDate(dataInicioStr);
        //     LocalDate dataFim = DateUtils.parseSingleDate(dataFimStr);
        //     datasDoEvento = DateUtils.createDateRange(dataInicio, dataFim);
        // }

        // evento.setDatas(datasDoEvento);

        // evento.setHoraInicio(TimeUtils.stringToTime(getRegexMatch(conteudo, "(?i)horário de início:\\s*([^\\n]+)", 1)));
        // evento.setHoraFim(TimeUtils.stringToTime(getRegexMatch(conteudo, "(?i)horário de finalização:\\s*([^\\n]+)", 1)));
        // evento.setLocal(captalizingString(getRegexMatch(conteudo, "(?is)local de montagem:\\s*(.*?)\\s*(data de montagem:|responsável por)", 1)));
        // evento.setResponsavel(captalizingString(getRegexMatch(conteudo, "(?is)responsável por\\s*acompanhar montagem:\\s*(.*?)\\s*(\\d{2}\\s*\\d{4,5}\\-?\\d{4})", 1)));
        // evento.setTelefoneResponsavel(cleanPhone(getRegexMatch(conteudo, "(?is)responsável por\\s*acompanhar montagem:\\s*(.*?)\\s*(\\d{2}\\s*\\d{4,5}\\-?\\d{4})", 2)));

        return evento;

    }

    public static EventoDTO extrairDadosPrefeitura(String conteudo, String nomeArquivo) {

        EventoDTO evento = new EventoDTO();

        evento.setModelo("Prefeitura");
        evento.setUpload(TimeUtils.getCurrentTimestamp());

        String numSolicitacao = extrairComTratamento(() -> getRegexMatch(conteudo, "(?i)nº solicitação:\\s*(\\d+/\\d+)", 1),
            "FUDASS_SOLICITACAO_AUSENTE", "Número de solicitação não encontrado.", nomeArquivo, StatusErro.ERROR);

        evento.setNumSolicitacao(numSolicitacao);

        String destino = extrairComTratamento(() -> getRegexMatch(conteudo, "(?i)destino:\\s*(.+)", 1),
            "FUDASS_DESTINO_AUSENTE", "Destino não encontrado.", nomeArquivo, StatusErro.WARN);

        evento.setDestino(destino);

        String descricao = extrairComTratamento(() -> captalizingString(getUltimaDescricao(conteudo)),
            "FUDASS_DESCRICAO_AUSENTE", "Descrição não encontrada.", nomeArquivo, StatusErro.WARN);

        evento.setDescricao(descricao);

        String nome = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*\\d+/\\d+\\s*–\\s*(.+)", 1)),
            "FUDASS_NOME_AUSENTE", "Nome não encontrado.", nomeArquivo, StatusErro.ERROR);

        evento.setNome(nome);

        String dataSolicitacaoStr = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)data da solicitação:\\s*([^\\n]+)", 1)),
            "FUDASS_DATA_SOLICITACAO_AUSENTE", "Data Solicitação não encontrada.", nomeArquivo, StatusErro.WARN);

        if (dataSolicitacaoStr != null) {
            evento.setDataSolicitacao(DateUtils.parseSingleDate(dataSolicitacaoStr));
        };

        String dataInicioStr = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)datas:\\s*([^\\n]+)", 1)),
            "FUDASS_DATA_INICIO_AUSENTE", "Data Início não encontrada.", nomeArquivo, StatusErro.WARN);

        String dataFimStr = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)datas:\\s*([^\\n]+)", 1)),
            "FUDASS_DATA_FIM_AUSENTE", "Data Fim não encontrada.", nomeArquivo, StatusErro.WARN);

        if (dataInicioStr != null) {
            List<LocalDate> datasDoEvento = DateUtils.parseDates(dataInicioStr);
            evento.setDatas(datasDoEvento);
        }
        if (dataFimStr != null) {
            List<LocalDate> datasDoEvento = DateUtils.parseDates(dataFimStr);
            evento.getDatas().addAll(datasDoEvento);
        }
        String horaInicioStr = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)horário de início:\\s*([^\\n]+)", 1)),
            "FUDASS_HORA_INICIO_AUSENTE", "Hora de Início não encontrada.", nomeArquivo, StatusErro.WARN);

        if (horaInicioStr != null) {
            evento.setHoraInicio(TimeUtils.stringToTime(horaInicioStr));
        }

        String horaFimStr = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)horário de finalização:\\s*([^\\n]+)", 1)),
            "FUDASS_HORA_FIM_AUSENTE", "Hora de Fim não encontrada.", nomeArquivo, StatusErro.WARN);

        if (horaFimStr != null) {
            evento.setHoraFim(TimeUtils.stringToTime(horaFimStr));
        }

        String local = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?i)local de montagem:\\s*(.*?)\\s*(data de montagem:|responsável por)", 1)),
            "FUDASS_LOCAL_AUSENTE", "Local não encontrado.", nomeArquivo, StatusErro.WARN);

        evento.setLocal(local);

        String responsavel = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?is)responsável por\\s*acompanhar montagem:\\s*(.*?)\\s*(\\d{2}\\s*\\d{4,5}\\-?\\d{4})(?is)responsável por\\s*acompanhar montagem:\\s*(.*?)\\s*(\\d{2}\\s*\\d{4,5}\\-?\\d{4})", 1)),
            "FUDASS_RESPONSAVEL_AUSENTE", "Responsável não encontrado.", nomeArquivo, StatusErro.WARN);

        evento.setResponsavel(responsavel);

        String telResponsavel = extrairComTratamento(() -> captalizingString(getRegexMatch(conteudo, "(?is)responsável por\\s*acompanhar montagem:\\s*(.*?)\\s*(\\d{2}\\s*\\d{4,5}\\-?\\d{4})(?is)responsável por\\s*acompanhar montagem:\\s*(.*?)\\s*(\\d{2}\\s*\\d{4,5}\\-?\\d{4})", 2)),
            "FUDASS_TELEFONE_RESPONSAVEL_AUSENTE", "Telefone do Responsável não encontrado.", nomeArquivo, StatusErro.WARN);

        evento.setTelefoneResponsavel(telResponsavel);

        // evento.setNumSolicitacao(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*(\\d+/\\d+)", 1));
        // evento.setDestino(captalizingString(getRegexMatch(conteudo, "(?i)destino:\\s*(.+)", 1)));
        // evento.setDescricao(captalizingString(getUltimaDescricao(conteudo)));
        // evento.setNome(captalizingString(getRegexMatch(conteudo, "(?i)nº solicitação:\\s*\\d+/\\d+\\s*–\\s*(.+)", 1)));
        // evento.setDataSolicitacao(DateUtils.parseSingleDate(getRegexMatch(conteudo, "(?i)data da solicitação:\\s*([^\\n]+)", 1)));
        
        // String dataInicioStr = getRegexMatch(conteudo, "(?i)datas:\\s*([^\\n]+)", 1);
        // String dataFimStr = getRegexMatch(conteudo, "(?i)datas:\\s*([^\\n]+)", 1);

        // List<LocalDate> datasDoEvento = new ArrayList<>();

        // datasDoEvento = DateUtils.parseDates(dataInicioStr);

        // if (datasDoEvento.isEmpty() && dataFimStr != null && !dataFimStr.trim().isEmpty()) {
        //     LocalDate dataInicio = DateUtils.parseSingleDate(dataInicioStr);
        //     LocalDate dataFim = DateUtils.parseSingleDate(dataFimStr);
        //     datasDoEvento = DateUtils.createDateRange(dataInicio, dataFim);
        // }

        // evento.setDatas(datasDoEvento);

        // evento.setHoraInicio(TimeUtils.stringToTime(getRegexMatch(conteudo, "(?i)horário de início:\\s*([^\\n]+)", 1)));
        // evento.setHoraFim(TimeUtils.stringToTime(getRegexMatch(conteudo, "(?i)horário de finalização:\\s*([^\\n]+)", 1)));
        // evento.setLocal(captalizingString(getRegexMatch(conteudo, "(?i)local de montagem:\\s*(.*?)\\s*(data de montagem:|responsável por)", 1)));
        // evento.setResponsavel(captalizingString(getRegexMatch(conteudo, "(?i)responsável por\\s*acompanhar montagem:\\s*([A-Za-z\\s\\.]+)\\s+([\\d\\s\\-]+)", 1)));
        // evento.setTelefoneResponsavel(cleanPhone(getRegexMatch(conteudo, "(?i)responsável por\\s*acompanhar montagem:\\s*([A-Za-z\\s\\.]+)\\s+([\\d\\s\\-]+)", 2)));

        return evento;

    }
    
    public static String extrairTextoDePDF(String caminhoPDF, String nomeArquivo) throws IOException {

        try{

            PDDocument document = PDDocument.load(new File(caminhoPDF));
            PDFTextStripper stripper = new PDFTextStripper();
            String texto = stripper.getText(document);
            document.close();
            return texto;

        } catch(IOException e){
            registrarErro("ERRO_PDF", "Não foi possível extrair texto do PDF: " + e.getMessage(), nomeArquivo, StatusErro.ERROR);
            return null; 
        }
    }

    public List<ErroExtracao> getErros() {
        return erros;
    }
    
    private static <T> T extrairComTratamento(Supplier<T> extracao, String codigoErro, String mensagem, String arquivo, StatusErro status) {
        try {
            T resultado = extracao.get();
            if (resultado == null || (resultado instanceof String && ((String) resultado).trim().isEmpty())) {
                registrarErro(codigoErro, mensagem, arquivo, status);
                return null;
            }
            return resultado;
        } catch (Exception e) {
            registrarErro(codigoErro, mensagem + " - Erro: " + e.getMessage(), arquivo, status);
            return null;
        }
    }

    private static void registrarErro(String codigo, String mensagem, String arquivo, StatusErro status) {
        erros.add(new ErroExtracao(codigo, mensagem, arquivo, status));
    }
}

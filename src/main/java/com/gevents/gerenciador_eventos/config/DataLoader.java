package com.gevents.gerenciador_eventos.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gevents.gerenciador_eventos.model.Status;
import com.gevents.gerenciador_eventos.repository.StatusRepository;

@Configuration
public class DataLoader {
    
    @Bean
    CommandLineRunner initStatus(StatusRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                List<String> descricoes = List.of(
                    "Ativo",
                    "Cancelado",
                    "Concluído",
                    "Pendente",
                    "Em Andamento",
                    "Aprovado",
                    "Rejeitado",
                    "Suspenso",
                    "Finalizado",
                    "Em Revisão"
                );

                Status status = new Status();

                descricoes.forEach(desc -> {
                    status.setDescricao(desc);
                    repository.save(status);
                });
            }
        };
    }
    
}

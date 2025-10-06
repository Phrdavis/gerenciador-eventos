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
            System.out.println("🚀 Iniciando DataLoader...");

            List<String> descricoes = List.of(
                "Ativo", "Cancelado", "Concluído", "Pendente", "Em Andamento",
                "Aprovado", "Rejeitado", "Suspenso", "Finalizado", "Em Revisão"
            );

            descricoes.forEach(desc -> {
                // evita duplicação — busca antes de inserir
                if (repository.findByDescricao(desc).isEmpty()) {
                    Status status = new Status();
                    status.setDescricao(desc);
                    repository.save(status);
                    System.out.println("✅ Inserido: " + desc);
                } else {
                    System.out.println("⚠️ Já existe: " + desc);
                }
            });

        };
    }
    
}

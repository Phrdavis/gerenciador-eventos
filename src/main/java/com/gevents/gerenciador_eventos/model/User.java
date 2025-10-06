package com.gevents.gerenciador_eventos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "D_E_L_E_T_"}),
        @UniqueConstraint(columnNames = {"cpf", "D_E_L_E_T_"})
    }
)
@Getter @Setter @NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String tipo;

    private String nomeCompleto;

    private String cpf;

    private String telefone;
    
}

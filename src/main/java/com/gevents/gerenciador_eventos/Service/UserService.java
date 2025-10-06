package com.gevents.gerenciador_eventos.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gevents.gerenciador_eventos.dto.UserDTO;
import com.gevents.gerenciador_eventos.model.User;
import com.gevents.gerenciador_eventos.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public ResponseEntity<?> criar(UserDTO user) {

        User us = new User();
    
        us.setUsername(user.getUsername());
        us.setPassword(encoder.encode(user.getPassword()));
        us.setEmail(user.getEmail());
        us.setTipo(user.getTipo());
        us.setNomeCompleto(user.getNomeCompleto());
        us.setCpf(user.getCpf());
        us.setTelefone(user.getTelefone());

        User userSalvo = userRepository.save(us);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSalvo);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Usuario não encontrado"));
        }
        return ResponseEntity.ok(user);
    }
    
    public List<User> buscarTodos() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> atualizar(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Usuario não encontrado"));
        }

        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(encoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getTipo() != null) {
            user.setTipo(userDTO.getTipo());
        }
        if (userDTO.getNomeCompleto() != null) {
            user.setNomeCompleto(userDTO.getNomeCompleto());
        }
        if (userDTO.getCpf() != null) {
            user.setCpf(userDTO.getCpf());
        }
        if (userDTO.getTelefone() != null) {
            user.setTelefone(userDTO.getTelefone());
        }

        User menuAtualizado = userRepository.save(user);

        return ResponseEntity.ok(menuAtualizado);
    }
    public ResponseEntity<?> deletar(Long id) {
        if (!userRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "User não encontrado"));
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deletado com sucesso");
    }
    public ResponseEntity<?> criarMultiplos(List<UserDTO> users) {
        if (users == null || users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de usuarios vazia"));
        }

        List<User> usersSalvos = new ArrayList<>();
        for (UserDTO userDTO : users) {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(encoder.encode(userDTO.getPassword()));
            user.setEmail(userDTO.getEmail());
            user.setTipo(userDTO.getTipo());
            user.setNomeCompleto(userDTO.getNomeCompleto());
            user.setCpf(userDTO.getCpf());
            user.setTelefone(userDTO.getTelefone());
            usersSalvos.add(user);
        }
        List<User> salvos = userRepository.saveAll(usersSalvos);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

}

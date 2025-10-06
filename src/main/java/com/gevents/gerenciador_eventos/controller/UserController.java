package com.gevents.gerenciador_eventos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gevents.gerenciador_eventos.Service.UserService;
import com.gevents.gerenciador_eventos.dto.UserDTO;
import com.gevents.gerenciador_eventos.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UserDTO userDTO) {
        return userService.criar(userDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return userService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<User>> buscarTodos() {
        return ResponseEntity.ok(userService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.atualizar(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return userService.deletar(id);
    }

    @PostMapping("/multiplos")
    public ResponseEntity<?> criarMultiplos(@RequestBody List<UserDTO> userDTOs) {
        return userService.criarMultiplos(userDTOs);
    }
}
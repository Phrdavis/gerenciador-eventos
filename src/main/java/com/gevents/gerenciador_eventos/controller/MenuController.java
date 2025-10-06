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

import com.gevents.gerenciador_eventos.Service.MenuService;
import com.gevents.gerenciador_eventos.dto.MenuDTO;
import com.gevents.gerenciador_eventos.model.Menu;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody MenuDTO menuDTO) {
        return menuService.criar(menuDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return menuService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Menu>> buscarTodos() {
        return ResponseEntity.ok(menuService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
        return menuService.atualizar(id, menuDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return menuService.deletar(id);
    }

    @PostMapping("/multiplos")
    public ResponseEntity<?> criarMultiplos(@RequestBody List<MenuDTO> menuDTOs) {
        return menuService.criarMultiplos(menuDTOs);
    }
}

package com.gevents.gerenciador_eventos.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gevents.gerenciador_eventos.dto.MenuDTO;
import com.gevents.gerenciador_eventos.model.Menu;
import com.gevents.gerenciador_eventos.repository.MenuRepository;

@Service
public class MenuService {

    private MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }
    public ResponseEntity<?> criar(MenuDTO menu) {

        Menu men = new Menu();
        men.setLabel(menu.getLabel());
        men.setLink(menu.getLink());
        men.setIcon(menu.getIcon());
        men.setOrderIndex(menu.getOrderIndex());

        Menu statusSalvo = menuRepository.save(men);
        return ResponseEntity.status(HttpStatus.CREATED).body(statusSalvo);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Menu não encontrado"));
        }
        return ResponseEntity.ok(menu);
    }
    
    public List<Menu> buscarTodos() {
        return menuRepository.findByDeleted("");
    }

    public ResponseEntity<?> atualizar(Long id, MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Menu não encontrado"));
        }
        if(menuDTO.getLabel() != null) {
            menu.setLabel(menuDTO.getLabel());
        }
        if(menuDTO.getLink() != null) {
            menu.setLink(menuDTO.getLink());
        }
        if(menuDTO.getIcon() != null) {
            menu.setIcon(menuDTO.getIcon());
        }
        Menu menuAtualizado = menuRepository.save(menu);

        return ResponseEntity.ok(menuAtualizado);
    }
    
    public ResponseEntity<?> deletar(Long id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Menu não encontrado"));
        }
        menu.setDeleted("*"); // Marca como deletado
        menuRepository.save(menu);
        return ResponseEntity.ok(java.util.Collections.singletonMap("mensagem", "Menu deletado com sucesso"));
    }
    public ResponseEntity<?> criarMultiplos(List<MenuDTO> menus) {
        if (menus == null || menus.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de menus vazia"));
        }

        List<Menu> menusSalvos = new ArrayList<>();
        for (MenuDTO menuDTO : menus) {
            Menu menu = new Menu();
            menu.setLabel(menuDTO.getLabel());
            menu.setLink(menuDTO.getLink());
            menu.setIcon(menuDTO.getIcon());
            menu.setOrderIndex(menuDTO.getOrderIndex());
            menusSalvos.add(menu);
        }
        List<Menu> salvos = menuRepository.saveAll(menusSalvos);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

}

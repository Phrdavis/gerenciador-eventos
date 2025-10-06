package com.gevents.gerenciador_eventos.Service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.gevents.gerenciador_eventos.repository.UserRepository;
import com.gevents.gerenciador_eventos.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // retorna UserDetails do Spring
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
    public User loadUserEntityByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
}


}

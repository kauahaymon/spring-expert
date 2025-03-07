package com.world.haymon.libraryapi.security;

import com.world.haymon.libraryapi.model.Usuario;
import com.world.haymon.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = service.obterPorLogin(login);

        if (usuario == null)
            throw new UsernameNotFoundException("Usuário não encontrado");

        String[] rolesArray = usuario.getRoles().toArray(new String[usuario.getRoles().size()]);

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(rolesArray)
                .build();
    }
}
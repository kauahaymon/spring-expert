package com.world.haymon.libraryapi.security;

import com.world.haymon.libraryapi.model.Usuario;
import com.world.haymon.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuarioBanco = usuarioService.obterPorLogin(login);

        if (usuarioBanco == null) {
            throw getUsernameNotFoundException();
        }

        String senhaCriptografada = usuarioBanco.getSenha();
        boolean matches = passwordEncoder.matches(senhaDigitada, senhaCriptografada);

        if (matches) {
            return new CustomAuthentication(usuarioBanco);
        }
        throw getUsernameNotFoundException();
    }

    private UsernameNotFoundException getUsernameNotFoundException() {
        return new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}

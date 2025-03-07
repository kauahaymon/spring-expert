package com.world.haymon.libraryapi.service;

import com.world.haymon.libraryapi.model.Usuario;
import com.world.haymon.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public Usuario salvar(Usuario usuario) {
        var senha = usuario.getSenha();
        var senhaHash = encoder.encode(senha);
        usuario.setSenha(senhaHash);
        return repository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return repository.findByLogin(login);
    }
}
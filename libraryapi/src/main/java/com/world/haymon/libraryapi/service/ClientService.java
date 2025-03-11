package com.world.haymon.libraryapi.service;

import com.world.haymon.libraryapi.model.Client;
import com.world.haymon.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client) {
        String hash = encoder.encode(client.getClientSecret());
        client.setClientSecret(hash);
        return repository.save(client);
    }

    public Client obterPorClientId(String clientId) {
        return repository.findByClientId(clientId);
    }
}

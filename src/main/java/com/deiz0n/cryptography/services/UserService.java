package com.deiz0n.cryptography.services;

import com.deiz0n.cryptography.domain.dtos.UserDTO;
import com.deiz0n.cryptography.domain.entities.User;
import com.deiz0n.cryptography.domain.exceptions.UserNotFoundException;
import com.deiz0n.cryptography.domain.mappers.UserMapper;
import com.deiz0n.cryptography.infrastructure.EncryptionComponent;
import com.deiz0n.cryptography.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    UserMapper mapper;
    @Autowired
    EncryptionComponent encryption;

    public final List<UserDTO> getAll() {
       return repository.findAll()
                           .stream()
                           .map(user -> UserDTO.builder()
                                   .id(user.getId())
                                   .userDocument(encryption.decrypt(user.getUserDocument()))
                                   .creditCardToken(encryption.decrypt(user.getCreditCardToken()))
                                   .value(user.getValue())
                                   .build()
                           )
                           .toList();
    }

    public UserDTO getById(Long id){
        return repository.findById(id)
                        .map(user -> UserDTO.builder()
                                .id(user.getId())
                                .userDocument(encryption.decrypt(user.getUserDocument()))
                                .creditCardToken(encryption.decrypt(user.getCreditCardToken()))
                                .value(user.getValue())
                                .build()
                        )
                        .orElseThrow(
                                () -> new UserNotFoundException("User not found")
                        );
    }

    public UserDTO create(UserDTO newData) {
        var user = User.builder()
                .userDocument(encryption.encrypt(newData.userDocument()))
                .creditCardToken(encryption.encrypt(newData.creditCardToken()))
                .value(newData.value())
                .build();

        repository.save(user);

        return UserDTO.builder()
                .id(user.getId())
                .userDocument(user.getUserDocument())
                .creditCardToken(user.getCreditCardToken())
                .value(user.getValue())
                .build();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

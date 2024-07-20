package com.deiz0n.cryptography.services;

import com.deiz0n.cryptography.domain.dtos.UserDTO;
import com.deiz0n.cryptography.domain.entities.User;
import com.deiz0n.cryptography.domain.events.*;
import com.deiz0n.cryptography.domain.exceptions.UserNotFoundException;
import com.deiz0n.cryptography.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    private UserDTO getUserEncoded;
    private List<UserDTO> getListOfUserEncoded;
    private UserDTO userEncoded;

    public List<UserDTO> getAll() {
       var event = new GetListOfDataEvent(this,
               repository.findAll()
                       .stream()
                       .map(user -> new UserDTO(
                               user.getId(),
                               user.getUserDocument(),
                               user.getCreditCardToken(),
                               user.getValue()))
                       .collect(Collectors.toList()));

       eventPublisher.publishEvent(event);
       return getListOfUserEncoded;
    }

    public UserDTO getById(Long id){
        var event = new GetDataEvent(this,
                repository.findById(id)
                        .map(user -> new UserDTO(
                                user.getId(),
                                user.getUserDocument(),
                                user.getCreditCardToken(),
                                user.getValue()
                        ))
                        .orElseThrow(() -> new UserNotFoundException("User not found"))
        );

        eventPublisher.publishEvent(event);
        return getUserEncoded;
    }

    public UserDTO create(UserDTO newData) {
        var event = new CreatedDataEvent(this, newData);
        eventPublisher.publishEvent(event);

        repository.save(new User(
                userEncoded.id(),
                userEncoded.userDocument(),
                userEncoded.creditCardToken(),
                userEncoded.value()
        ));
        return userEncoded;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @EventListener
    private void setGetListOfUserEncoded(EncodingListOfDataEvent event) {
        try {
            getListOfUserEncoded = new ArrayList<>(event.getUsers());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao capturar os dados", e);
        }
    }

    @EventListener(condition = "event.source == 'get'")
    private void setGetUserEncoded(EncodingDataEvent event) {
        getUserEncoded = new UserDTO(
                event.getUser().id(),
                event.getUser().userDocument(),
                event.getUser().creditCardToken(),
                event.getUser().value()
        );
    }

    @EventListener(condition = "event.source == 'post'")
    private void setUserEncoded(EncodingDataEvent event) {
        userEncoded = new UserDTO(
                event.getUser().id(),
                event.getUser().userDocument(),
                event.getUser().creditCardToken(),
                event.getUser().value()
        );
    }

}

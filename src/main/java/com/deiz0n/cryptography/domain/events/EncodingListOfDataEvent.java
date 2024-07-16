package com.deiz0n.cryptography.domain.events;

import com.deiz0n.cryptography.domain.dtos.UserDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class EncodingListOfDataEvent extends ApplicationEvent {

    private final List<UserDTO> users;

    public EncodingListOfDataEvent(Object source, List<UserDTO> users) {
        super(source);
        this.users = users;
    }

}

package com.deiz0n.cryptography.domain.events;

import com.deiz0n.cryptography.domain.dtos.UserDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class DecodingListOfDataEvent extends ApplicationEvent {

    private List<UserDTO> users;

    public DecodingListOfDataEvent(Object source, List<UserDTO> users) {
        super(source);
        this.users = users;
    }

}

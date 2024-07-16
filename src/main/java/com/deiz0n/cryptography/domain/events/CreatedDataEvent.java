package com.deiz0n.cryptography.domain.events;

import com.deiz0n.cryptography.domain.dtos.UserDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreatedDataEvent extends ApplicationEvent {

    private UserDTO user;

    public CreatedDataEvent(Object source, UserDTO user) {
        super(source);
        this.user = user;
    }
}

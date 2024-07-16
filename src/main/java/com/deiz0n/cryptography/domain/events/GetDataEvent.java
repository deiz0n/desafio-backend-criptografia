package com.deiz0n.cryptography.domain.events;

import org.springframework.context.ApplicationEvent;

public class GetDataEvent extends ApplicationEvent {

    public GetDataEvent(Object source) {
        super(source);
    }

}

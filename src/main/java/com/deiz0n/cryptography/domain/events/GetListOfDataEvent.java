package com.deiz0n.cryptography.domain.events;

import org.springframework.context.ApplicationEvent;

public class GetListOfDataEvent extends ApplicationEvent {

    public GetListOfDataEvent(Object source) {
        super(source);
    }

}

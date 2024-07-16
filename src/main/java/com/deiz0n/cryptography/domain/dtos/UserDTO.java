package com.deiz0n.cryptography.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long id,
        @JsonProperty(value = "user_document")
        String userDocument,
        @JsonProperty(value = "credit_card_token")
        String creditCardToken,
        Long value) {
}

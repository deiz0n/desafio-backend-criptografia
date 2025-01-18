package com.deiz0n.cryptography.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDTO(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long id,
        @JsonProperty(value = "user_document")
        @NotBlank(message = "The field \"User Document\" is required")
        String userDocument,
        @JsonProperty(value = "credit_card_token")
        @NotBlank(message = "The field \"Credit Card Token\" is required")
        String creditCardToken,
        @NotNull(message = "The field \"Value\" is required")
        Long value
) {}

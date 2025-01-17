package com.deiz0n.cryptography.domain.mappers;

import com.deiz0n.cryptography.domain.dtos.UserDTO;
import com.deiz0n.cryptography.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getUserDocument(),
                entity.getCreditCardToken(),
                entity.getValue()
        );
    }

    public User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.id())
                .userDocument(dto.userDocument())
                .creditCardToken(dto.creditCardToken())
                .value(dto.value())
                .build();
    }
}

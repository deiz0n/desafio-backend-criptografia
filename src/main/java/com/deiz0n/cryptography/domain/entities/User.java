package com.deiz0n.cryptography.domain.entities;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true)
    private String userDocument;
    @Column(unique = true)
    private String creditCardToken;
    private Long value;

}

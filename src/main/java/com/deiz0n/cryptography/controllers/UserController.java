package com.deiz0n.cryptography.controllers;

import com.deiz0n.cryptography.domain.dtos.UserDTO;
import com.deiz0n.cryptography.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        var users = service.getAll();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(15, TimeUnit.SECONDS))
                .body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUsers(@PathVariable Long id) {
        var user = service.getById(id);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(15, TimeUnit.SECONDS))
                .body(user);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO request) {
        var user = service.create(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(user.id())
                .toUri();
        return ResponseEntity.created(uri)
                .cacheControl(CacheControl.maxAge(15, TimeUnit.SECONDS))
                .body(user);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

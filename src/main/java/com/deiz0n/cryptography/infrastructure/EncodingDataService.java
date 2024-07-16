package com.deiz0n.cryptography.infrastructure;

import com.deiz0n.cryptography.domain.dtos.UserDTO;
import com.deiz0n.cryptography.domain.entities.User;
import com.deiz0n.cryptography.domain.events.*;
import com.deiz0n.cryptography.domain.exceptions.UserNotFound;
import com.deiz0n.cryptography.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncodingDataService {

    @Value("${api.algorithm.secret.key}")
    private String key;

    @Autowired
    UserRepository repository;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    public String encrypt(CharSequence data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedTextBytes = cipher.doFinal(data.toString().getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedTextBytes);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar", e);
        }
    }

    public String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedTextBytes = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(decryptedTextBytes);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar", e);
        }
    }

    @EventListener
    public void encodeList(GetListOfDataEvent event) {
        var encodingEvent = new EncodingListOfDataEvent(event.getSource(),
                repository.findAll()
                        .stream()
                        .map(user -> {
                            return new UserDTO(
                                    user.getId(),
                                    decrypt(user.getUserDocument()),
                                    decrypt(user.getCreditCardToken()),
                                    user.getValue()
                            );
                        })
                        .toList()
        );
        eventPublisher.publishEvent(encodingEvent);
    }

    @EventListener
    public void encode(GetDataEvent event) {
        var user = repository.findById((Long) event.getSource())
                .map(u -> {
                    return new UserDTO(
                            u.getId(),
                            decrypt(u.getUserDocument()),
                            decrypt(u.getCreditCardToken()),
                            u.getValue()
                    );
                })
                .orElseThrow(() -> new UserNotFound("User not found"));
        var encodingEvent = new EncodingDataEvent("get", user);
        eventPublisher.publishEvent(encodingEvent);
    }

    @EventListener
    public void createdEncode(CreatedDataEvent event) {
        var encodingData = new EncodingDataEvent("post",
                new UserDTO(
                        event.getUser().id(),
                        encrypt(event.getUser().userDocument()),
                        encrypt(event.getUser().creditCardToken()),
                        event.getUser().value()
                ));
        eventPublisher.publishEvent(encodingData);
    }

}

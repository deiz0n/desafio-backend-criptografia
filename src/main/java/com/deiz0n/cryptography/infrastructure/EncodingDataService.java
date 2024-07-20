package com.deiz0n.cryptography.infrastructure;

import com.deiz0n.cryptography.domain.dtos.UserDTO;
import com.deiz0n.cryptography.domain.events.*;
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
        var encodingEvent = new EncodingListOfDataEvent(this,
                event.getUsers()
                        .stream()
                        .map(user -> new UserDTO(
                                user.id(),
                                decrypt(user.userDocument()),
                                decrypt(user.creditCardToken()),
                                user.value()
                        ))
                        .toList()
        );
        eventPublisher.publishEvent(encodingEvent);
    }

    @EventListener
    public void encode(GetDataEvent event) {
        var encodingEvent = new EncodingDataEvent("get",
                new UserDTO(
                        event.getUser().id(),
                        decrypt(event.getUser().creditCardToken()),
                        decrypt(event.getUser().userDocument()),
                        event.getUser().value()
                ));
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

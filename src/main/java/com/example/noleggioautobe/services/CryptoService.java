package com.example.noleggioautobe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
public class CryptoService {
    static final String ENCRYPTION_KEY = "12345678901234567890123456789012";

    public String decrypt(String encryptedData) throws Exception {
        String[] parts = encryptedData.split(":");
        if (parts.length != 2)
            throw new IllegalArgumentException("Invalid encrypted data format");

        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] encryptedBytes = Base64.getDecoder().decode(parts[1]);

        SecretKeySpec keySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

        byte[] plainData = cipher.doFinal(encryptedBytes);
        return new String(plainData, StandardCharsets.UTF_8);
    }
}
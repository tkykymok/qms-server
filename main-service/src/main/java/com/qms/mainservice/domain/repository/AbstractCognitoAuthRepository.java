package com.qms.mainservice.domain.repository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public abstract class AbstractCognitoAuthRepository {

    private static final String ALGORITHM = "HmacSHA256";

    protected String generateSecretHash(String username, String clientId, String clientSecret) {
        try {
            String message = username + clientId;
            Mac hmacSha256 = Mac.getInstance(ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            hmacSha256.init(secretKeySpec);
            byte[] hash = hmacSha256.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generating secret hash", e);
        }
    }
}
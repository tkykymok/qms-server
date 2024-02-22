package com.qms.mainservice.domain.service;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

@Service
public class CognitoJwtVerifier {

    @Value("${aws.cognito.region}")
    private String region;

    @Value("${aws.cognito.staff-pool-id}")
    private String staffPoolId;
    @Value("${aws.cognito.staff-client-id}")
    private String staffClientId;

    @Value("${aws.cognito.customer-pool-id}")
    private String customerPoolId;
    @Value("${aws.cognito.customer-client-id}")
    private String customerClientId;

    // ClientIdをキーにしてissuerを取得するマップ
    Map<String, String> issuerMap = new HashMap<>();

    @PostConstruct
    public void init() {
        String issuerBase = "https://cognito-idp." + region + ".amazonaws.com/";
        issuerMap.put(staffClientId, issuerBase + staffPoolId);
        issuerMap.put(customerClientId, issuerBase + customerPoolId);
    }

    public boolean verifyToken(String token) {
        // トークンからキー ID (kid)とissuer(ユーザープールID)を取得
        DecodedJWT decodedJWT = JWT.decode(token);
        final String kid = decodedJWT.getKeyId();
        // トークンからissuerを取得
        final String issuer = decodedJWT.getIssuer();
        // トークンからclientIDを取得
        final String clientId = decodedJWT.getClaim("client_id").asString();

        try {
            // JWKS エンドポイントから公開鍵を取得する JwkProvider を作成
            JwkProvider provider = new JwkProviderBuilder(issuer).build();

            // キー ID に対応する公開鍵を取得
            RSAPublicKey publicKey = (RSAPublicKey) provider.get(kid).getPublicKey();

            // 公開鍵を使用してアルゴリズムを作成
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            // トークンの検証器を作成
            final String targetIssuer = issuerMap.get(clientId);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(targetIssuer) // issuerMapから取得したissuer
                    .withClaim("client_id", clientId)
                    .build();

            // トークンを検証
            verifier.verify(token);

            return true;
        } catch (Exception e) {
            // トークン検証失敗
            return false;
        }
    }
}

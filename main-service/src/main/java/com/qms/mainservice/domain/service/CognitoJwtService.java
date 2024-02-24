package com.qms.mainservice.domain.service;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.qms.mainservice.infrastructure.config.security.CustomUserDetails;
import com.qms.shared.domain.model.valueobject.UserType;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CognitoJwtService {

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
        // トークンからaud(clientID)を取得
        final String audience = decodedJWT.getAudience().getFirst();

        try {
            // JWKS エンドポイントから公開鍵を取得する JwkProvider を作成
            JwkProvider provider = new JwkProviderBuilder(issuer).build();

            // キー ID に対応する公開鍵を取得
            RSAPublicKey publicKey = (RSAPublicKey) provider.get(kid).getPublicKey();

            // 公開鍵を使用してアルゴリズムを作成
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            // トークンの検証器を作成
            final String targetIssuer = issuerMap.get(audience);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(targetIssuer) // issuerMapから取得したissuer
                    .withAudience(audience)
                    .build();

            // トークンを検証
            verifier.verify(token);

            return true;
        } catch (Exception e) {
            // トークン検証失敗
            return false;
        }
    }

    /**
     * ユーザー情報を取得
     * @param token トークン
     * @return ユーザー情報
     */
    public CustomUserDetails getUserDetails(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        // cognitoユーザー名
        String username = decodedJWT.getClaim("cognito:username").asString();
        // 所属企業ID
        String companyId = decodedJWT.getClaim("custom:companyId").asString();
        // 所属店舗ID
        String storeIds = decodedJWT.getClaim("custom:storeId").asString();
        // 氏名
        String name = decodedJWT.getClaim("name").asString();
        // メールアドレス
        String email = decodedJWT.getClaim("email").asString();
        // 顧客 or スタッフ
        UserType userType = getUserType(token);
        List<SimpleGrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority(userType.name()));

        return new CustomUserDetails(
                username,
                null,
                authorities,
                companyId,
                storeIds,
                name,
                email
        );
    }

    /**
     * ユーザータイプを取得
     * @param token トークン
     * @return ユーザータイプ
     */
    private UserType getUserType(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        final String audience = decodedJWT.getAudience().getFirst();

        if (staffClientId.equals(audience)) {
            return UserType.STAFF;
        } else if (customerClientId.equals(audience)) {
            return UserType.CUSTOMER;
        } else {
            throw new IllegalArgumentException("Invalid audience: " + audience);
        }
    }
}

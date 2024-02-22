package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.repository.AbstractCognitoAuthRepository;
import com.qms.mainservice.domain.repository.StaffAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CognitoStaffAuthRepository extends AbstractCognitoAuthRepository implements StaffAuthRepository {

    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${aws.cognito.staff-client-id}")
    private String clientId;

    @Value("${aws.cognito.staff-client-secret}")
    private String clientSecret;


    @Override
    public InitiateAuthResponse authenticate(String username, String password) {
        InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .clientId(clientId)
                .authParameters(
                        Map.of(
                                "USERNAME", username,
                                "PASSWORD", password,
                                "SECRET_HASH", generateSecretHash(username, clientId, clientSecret)
                        )
                )
                .build();

        return cognitoClient.initiateAuth(authRequest);
    }

}

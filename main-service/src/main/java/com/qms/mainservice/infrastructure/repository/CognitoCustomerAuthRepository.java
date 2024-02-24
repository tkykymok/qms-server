package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.valueobject.Password;
import com.qms.mainservice.domain.model.valueobject.Username;
import com.qms.mainservice.domain.repository.AbstractCognitoAuthRepository;
import com.qms.mainservice.domain.repository.CustomerAuthRepository;
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
public class CognitoCustomerAuthRepository extends AbstractCognitoAuthRepository implements CustomerAuthRepository {

    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${aws.cognito.customer-client-id}")
    private String clientId;

    @Value("${aws.cognito.customer-client-secret}")
    private String clientSecret;


    @Override
    public InitiateAuthResponse authenticate(Username username, Password password) {
        InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .clientId(clientId)
                .authParameters(
                        Map.of(
                                "USERNAME", username.value(),
                                "PASSWORD", password.value(),
                                "SECRET_HASH", generateSecretHash(username.value(), clientId, clientSecret)
                        )
                )
                .build();

        return cognitoClient.initiateAuth(authRequest);
    }

}

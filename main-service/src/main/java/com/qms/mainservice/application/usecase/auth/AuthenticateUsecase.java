package com.qms.mainservice.application.usecase.auth;

import com.qms.mainservice.domain.model.valueobject.AccessToken;
import com.qms.mainservice.domain.model.valueobject.IdToken;
import com.qms.mainservice.domain.model.valueobject.RefreshToken;
import com.qms.mainservice.domain.repository.CustomerAuthRepository;
import com.qms.mainservice.domain.repository.StaffAuthRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

@Service
@RequiredArgsConstructor
public class AuthenticateUsecase extends Usecase<AuthenticateInput, AuthenticateOutput> {

    private final StaffAuthRepository staffAuthRepository;
    private final CustomerAuthRepository customerAuthRepository;


    @Override
    public AuthenticateOutput execute(AuthenticateInput input) {

        InitiateAuthResponse authResponse = switch (input.userType()) {
            case STAFF -> staffAuthRepository.authenticate(input.username(), input.password());
            case CUSTOMER -> customerAuthRepository.authenticate(input.username(), input.password());
            default -> throw new IllegalArgumentException("Invalid user type");
        };

        if (authResponse != null) {
            return AuthenticateOutput.builder()
                    .accessToken(AccessToken.of(authResponse.authenticationResult().accessToken()))
                    .refreshToken(RefreshToken.of(authResponse.authenticationResult().refreshToken()))
                    .idToken(IdToken.of(authResponse.authenticationResult().idToken()))
                    .expiresIn(authResponse.authenticationResult().expiresIn())
                    .build();
        }

        return null;
    }
}

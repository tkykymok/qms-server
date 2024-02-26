package com.qms.mainservice.application.usecase.auth;

import com.qms.mainservice.domain.model.valueobject.AccessToken;
import com.qms.mainservice.domain.model.valueobject.IdToken;
import com.qms.mainservice.domain.model.valueobject.RefreshToken;
import com.qms.mainservice.domain.repository.CustomerAuthRepository;
import com.qms.mainservice.domain.repository.StaffAuthRepository;
import com.qms.mainservice.domain.service.CognitoJwtService;
import com.qms.mainservice.infrastructure.config.security.StaffUserDetails;
import com.qms.shared.application.usecase.Usecase;
import com.qms.shared.domain.model.valueobject.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

@Service
@RequiredArgsConstructor
public class AuthenticateUsecase extends Usecase<AuthenticateInput, AuthenticateOutput> {

    private final StaffAuthRepository staffAuthRepository;
    private final CustomerAuthRepository customerAuthRepository;

    private final CognitoJwtService cognitoJwtService;


    @Override
    public AuthenticateOutput execute(AuthenticateInput input) {

        InitiateAuthResponse authResponse = switch (input.userType()) {
            case STAFF -> staffAuthRepository.authenticate(input.username(), input.password());
            case CUSTOMER -> customerAuthRepository.authenticate(input.username(), input.password());
            default -> throw new IllegalArgumentException("Invalid user type");
        };


if (authResponse != null) {
    var authResult = authResponse.authenticationResult();
    var builder = AuthenticateOutput.builder()
            .accessToken(AccessToken.of(authResult.accessToken()))
            .refreshToken(RefreshToken.of(authResult.refreshToken()))
            .idToken(IdToken.of(authResult.idToken()))
            .expiresIn(authResult.expiresIn());

    if (input.userType() == UserType.STAFF) {
        var userDetails = (StaffUserDetails)cognitoJwtService.getUserDetails(authResult.idToken());
        builder.storeIds(userDetails.getStoreIds());
    }

    return builder.build();
}

        return null;
    }
}

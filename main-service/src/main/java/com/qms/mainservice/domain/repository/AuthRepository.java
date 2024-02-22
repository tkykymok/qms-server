package com.qms.mainservice.domain.repository;

import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

public interface AuthRepository {

    InitiateAuthResponse authenticate(String username, String password);

}

package com.qms.mainservice.domain.repository;

import com.qms.mainservice.domain.model.valueobject.Password;
import com.qms.mainservice.domain.model.valueobject.Username;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

public interface AuthRepository {

    InitiateAuthResponse authenticate(Username username, Password password);

}

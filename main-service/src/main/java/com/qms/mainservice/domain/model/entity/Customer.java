package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.SingleKeyBaseEntity;
import com.qms.shared.domain.model.valueobject.TrackingInfo;
import lombok.Getter;

@Getter
public class Customer extends SingleKeyBaseEntity<CustomerId> {

    CognitoUserId cognitoUserId;
    LastName lastName;
    FirstName firstName;
    Email email;
    Gender gender;
    Birthday birthday;

    private Customer() {
    }


    public static Customer reconstruct(
            CustomerId customerId,
            CognitoUserId cognitoUserId,
            LastName lastName,
            FirstName firstName,
            Email email,
            Birthday birthday,
            TrackingInfo trackingInfo
    ) {
        Customer customer = new Customer();
        customer.id = customerId;
        customer.cognitoUserId = cognitoUserId;
        customer.lastName = lastName;
        customer.firstName = firstName;
        customer.email = email;
        customer.birthday = birthday;
        customer.trackingInfo = trackingInfo;
        return customer;
    }
}

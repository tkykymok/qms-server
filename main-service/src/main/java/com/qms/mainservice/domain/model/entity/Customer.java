package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.SingleKeyBaseEntity;
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
            Birthday birthday
    ) {
        Customer customer = new Customer();
        customer.id = customerId;
        customer.cognitoUserId = cognitoUserId;
        customer.lastName = lastName;
        customer.firstName = firstName;
        customer.email = email;
        customer.birthday = birthday;
        return customer;
    }
}

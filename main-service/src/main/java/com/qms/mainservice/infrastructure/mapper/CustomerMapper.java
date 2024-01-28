package com.qms.mainservice.infrastructure.mapper;

import com.qms.mainservice.domain.model.entity.Customer;
import com.qms.mainservice.domain.model.valueobject.*;
import org.jooq.Record;

import static com.qms.mainservice.infrastructure.jooq.Tables.*;

public class CustomerMapper {
    public static Customer recordToCustomer(Record record) {
        return Customer.reconstruct(
                CustomerId.of(record.get(CUSTOMERS.ID)),
                CognitoUserId.of(record.get(CUSTOMERS.COGNITO_USER_ID)),
                LastName.of(record.get(CUSTOMERS.LAST_NAME)),
                FirstName.of(record.get(CUSTOMERS.FIRST_NAME)),
                Email.of(record.get(CUSTOMERS.EMAIL)),
                Birthday.of(record.get(CUSTOMERS.BIRTHDAY))
        );
    }
}

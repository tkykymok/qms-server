package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.AggregateRoot;

public class CompanyStoreAggregate extends AggregateRoot<StoreId> {
    private CompanyId companyId;
    private StoreName storeName;
    private PostalCode postalCode;
    private Address address;
    private Location location;
    private PhoneNumber phoneNumber;
    private HomePageUrl homePageUrl;

    private CompanyStoreAggregate() {}





}

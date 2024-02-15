package com.qms.mainservice.domain.repository;

import com.qms.mainservice.domain.model.aggregate.Sales;

public interface SalesRepository {
    void insert(Sales sales);
}

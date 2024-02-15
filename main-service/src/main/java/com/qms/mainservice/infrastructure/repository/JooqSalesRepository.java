package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.aggregate.Sales;
import com.qms.mainservice.domain.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.qms.mainservice.infrastructure.jooq.Tables.SALES;

@Repository
@RequiredArgsConstructor
public class JooqSalesRepository implements SalesRepository {

    private final DSLContext dsl;

    @Override
    public void insert(Sales sales) {
        // 売上を保存する
        dsl.insertInto(SALES)
                .set(SALES.STORE_ID, sales.getStoreId().value())
                .set(SALES.RESERVATION_ID, sales.getReservationId().value())
                .set(SALES.MENU_NAME, sales.getMenuName().value())
                .set(SALES.SALES_AMOUNT, sales.getSalesAmount().value())
                .execute();
    }

}

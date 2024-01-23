package com.qms.mainservice.infrastructure.query.store;

import com.qms.mainservice.domain.query.store.StoreDetailsResult;
import com.qms.mainservice.domain.query.store.StoreQuery;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

import static com.qms.mainservice.infrastructure.jooq.Tables.STORES;

@Repository
@RequiredArgsConstructor
public class JooqStoreQuery implements StoreQuery {

    private final DSLContext dsl;


    @Override
    public List<StoreDetailsResult> findStoreDetailsByLocation(Point point, int distance) {
        // クエリの構築
        Result<Record> result = dsl.select()
                .from(STORES)
                .where(String.format("ST_Distance_Sphere(POINT(%f, %f), location) <= %d",
                        point.getX(), point.getY(), distance))
                .fetch();

        return null;
    }
}


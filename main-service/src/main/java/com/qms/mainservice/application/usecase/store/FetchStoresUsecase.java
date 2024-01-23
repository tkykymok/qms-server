package com.qms.mainservice.application.usecase.store;

import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchStoresUsecase extends Usecase<FetchStoresInput, FetchStoresOutput> {



    @Override
    public FetchStoresOutput execute(FetchStoresInput input) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(input.longitude(), input.latitude()); // 経度、緯度の順番
        Point point = geometryFactory.createPoint(coordinate);

        System.out.println(point);

        System.out.println("Longitude: " + point.getX());
        System.out.println("Latitude: " + point.getY());


        return null;
    }
}

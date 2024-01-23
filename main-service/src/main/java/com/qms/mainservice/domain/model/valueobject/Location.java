package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;
import org.locationtech.jts.geom.Geometry;

public record Location(Geometry value) implements ValueObject {
    public static Location of(Geometry value) {
        return new Location(value);
    }
}
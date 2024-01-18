package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;
import org.locationtech.jts.geom.Point;

public record Location(Point value) implements ValueObject {
    public static Location of(Point value) {
        return new Location(value);
    }
}
package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import lombok.Getter;

@Getter
public class Menu extends CompositeKeyBaseEntity<MenuKey> {

    private MenuName menuName;
    private Price price;
    private Time time;
    private Flag disabled;

}

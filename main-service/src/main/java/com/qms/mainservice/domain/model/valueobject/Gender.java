package com.qms.mainservice.domain.model.valueobject;

public enum Gender {
    OTHER("その他", 0),
    MALE("男性", 1),
    FEMALE("女性", 2);

    private final String text;
    private final int value;

    private Gender(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

    public static Gender fromValue(int value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue() == value) {
                return gender;
            }
        }
        throw new IllegalArgumentException("no such enum object for the id: " + value);
    }
}


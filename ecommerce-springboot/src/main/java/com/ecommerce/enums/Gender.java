package com.ecommerce.enums;

public enum Gender {
    MALE,
    FEMALE,
    TRANSGENDER;

    public String getSalutation() {
        return switch (this) {
            case MALE -> "Mr.";
            case FEMALE -> "Ms.";
            default -> "";
        };
    }

}

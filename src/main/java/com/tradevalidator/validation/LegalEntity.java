package com.tradevalidator.validation;

public enum LegalEntity {
    CS_ZURICH ("CS Zurich");

    private final String description;

    LegalEntity(String description) {
        this.description = description;
    }

    public static LegalEntity fromString(String description) {
        for (LegalEntity le : LegalEntity.values()) {
            if (le.getDescription().equalsIgnoreCase(description)) {
                return le;
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }
}

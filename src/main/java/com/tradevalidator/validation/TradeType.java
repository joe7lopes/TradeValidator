package com.tradevalidator.validation;

public enum TradeType {
    SPOT("Spot"),
    FORWARD("Forward"),
    VANILLA_OPTION("VanillaOption");

    private final String description;

    TradeType(String description) {
        this.description = description;
    }

    public static TradeType fromString(String name) {
        for (TradeType type : TradeType.values()) {
            if (type.getDescription().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }


}

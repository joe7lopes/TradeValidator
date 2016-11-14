package com.tradevalidator.validation;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Error {

    VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE("Value date cannot be before trade date"),
    UNSUPPORTED_CUSTOMER("Unsupported customer"),
    UNSUPPORTED_LEGAL_ENTITY("Unsupported Legal Entity"),
    UNSUPPORTED_STYLE("Unsupported style"),
    INVALID_CURRENCY_PAIR("Invalid Currency pair"),
    INVALID_VALUE_DATE("Invalid value date"),
    NON_UNDERSTANDABLE_REQUIREMENT("The requirement is not clear for spot and forward validations"),
    EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE("Expiry date should be before trade date"),
    EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE("Excercise start date has to be after the trade date but before the expiry date"),
    PREMIUM_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE("Premium should be before delivery date"),
    VALUE_DATE_FALLS_ON_WEEKEND("Value date falls on weekend for currency"),
    VALUE_DATE_FALLS_ON_NON_WORKING_DAY("Value date falls on non-working day for currency");
    private final String description;

    Error(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

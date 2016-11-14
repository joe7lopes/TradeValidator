package com.tradevalidator.validation.validator;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;

import java.util.Collection;

public interface Validator {
    Collection<Error> validate(Trade trade);
}

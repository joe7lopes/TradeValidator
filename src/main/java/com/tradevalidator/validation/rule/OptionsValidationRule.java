package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;

import java.util.Collection;

public interface OptionsValidationRule {
    Collection<Error> validate(Trade trade);
}

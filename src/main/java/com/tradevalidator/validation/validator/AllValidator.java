package com.tradevalidator.validation.validator;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.rule.AllValidationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@Order(1)
public class AllValidator implements Validator {

    @Autowired
    private Collection<AllValidationRule> validationRules;

    @Override
    public Collection<Error> validate(Trade trade) {
        Collection<Error> errors = new ArrayList<>();
        for (AllValidationRule rule : validationRules) {
            errors.addAll(rule.validate(trade));
        }
        return errors;
    }

    void setValidationRules(Collection<AllValidationRule> validationRules) {
        this.validationRules = validationRules;
    }
}
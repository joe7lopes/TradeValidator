package com.tradevalidator.validation.validator;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.TradeType;
import com.tradevalidator.validation.rule.OptionsValidationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class OptionValidator implements Validator {

    @Autowired
    private Collection<OptionsValidationRule> rules;

    @Override
    public Collection<Error> validate(Trade trade) {
        Collection<Error> errors = new ArrayList<>();

        if (isOptionTrade(trade)) {
            for (OptionsValidationRule rule : rules) {
                errors.addAll(rule.validate(trade));
            }
        }
        return errors;
    }

    private boolean isOptionTrade(Trade trade) {
        return TradeType.VANILLA_OPTION == TradeType.fromString(trade.getType());
    }


    void setRules(Collection<OptionsValidationRule> rules) {
        this.rules = rules;
    }
}

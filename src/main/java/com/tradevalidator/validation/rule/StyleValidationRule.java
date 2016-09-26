package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.Style;

import java.util.ArrayList;
import java.util.Collection;

import static com.tradevalidator.validation.Error.UNSUPPORTED_STYLE;

public class StyleValidationRule implements OptionsValidationRule {


    @Override
    public Collection<Error> validate(Trade trade) {

        Collection<Error> errors = new ArrayList<>();

        if (!isSupportedStyle(trade)) {
            errors.add(UNSUPPORTED_STYLE);
        }


        return errors;
    }

    private boolean isSupportedStyle(Trade trade) {
        for (Style style : Style.values()) {
            if (style.name().equals(trade.getStyle())) {
                return true;
            }
        }
        return false;
    }
}

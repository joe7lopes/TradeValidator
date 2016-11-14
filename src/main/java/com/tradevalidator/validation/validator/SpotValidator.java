package com.tradevalidator.validation.validator;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.TradeType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;


/*
    The Requirements are not clear for this rule.
 */

@Component
public class SpotValidator implements Validator {
    private Collection<Error> errors;

    @Override
    public Collection<Error> validate(Trade trade) {
        errors = new ArrayList<>();
        if (isSpotTrade(trade)) {
            errors.add(Error.NON_UNDERSTANDABLE_REQUIREMENT);
        }
        return errors;
    }

    private boolean isSpotTrade(Trade trade) {
        return TradeType.SPOT == TradeType.fromString(trade.getType());
    }


}

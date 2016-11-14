package com.tradevalidator.validation.validator;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.TradeType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static com.tradevalidator.validation.Error.NON_UNDERSTANDABLE_REQUIREMENT;

/*
    The Requirements are not clear for this rule.
 */
@Component
public class ForwardValidator implements Validator {
    private Collection<Error> errors;

    @Override
    public Collection<Error> validate(Trade trade) {
        errors = new ArrayList<>();
        if (isTradeTypeForward(trade)) {
            errors.add(NON_UNDERSTANDABLE_REQUIREMENT);
        }

        return errors;
    }

    private boolean isTradeTypeForward(Trade trade) {
        return trade.getType()!=null && TradeType.FORWARD == TradeType.fromString(trade.getType());
    }

}

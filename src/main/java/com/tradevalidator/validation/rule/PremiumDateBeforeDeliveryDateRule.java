package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static com.tradevalidator.validation.Error.PREMIUM_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE;

@Component
public class PremiumDateBeforeDeliveryDateRule implements OptionsValidationRule {
    @Override
    public Collection<Error> validate(Trade trade) {
        Collection<Error> errors = new ArrayList<>();

        if (!isPremiumDateBeforeDeliveryDate(trade)) {
            errors.add(PREMIUM_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE);
        }

        return errors;
    }

    private boolean isPremiumDateBeforeDeliveryDate(Trade trade) {
        Date firstDate = trade.getPremiumDate();
        Date secondDate = trade.getDeliveryDate();
        return isDateSet(firstDate) && isDateSet(secondDate) && firstDate.before(secondDate);
    }

    private boolean isDateSet(Date date) {
        return date != null;
    }
}

package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static com.tradevalidator.validation.Error.EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE;

@Component
public class ExpiryDateBeforeDeliveryDateRule implements OptionsValidationRule {
    @Override
    public Collection<Error> validate(Trade trade) {
        Collection<Error> errors = new ArrayList<>();

        if (!isExpiryDateBeforeDeliveryDate(trade)) {
            errors.add(EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE);
        }

        return errors;
    }

    private boolean isExpiryDateBeforeDeliveryDate(Trade trade) {
        Date expiryDate = trade.getExpiryDate();
        Date deliveryDate = trade.getDeliveryDate();
        return isDateSet(expiryDate) && isDateSet(deliveryDate) && expiryDate.before(deliveryDate);
    }

    private boolean isDateSet(Date date) {
        return date != null;
    }
}

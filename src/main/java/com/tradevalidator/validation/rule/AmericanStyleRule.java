package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.Style;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static com.tradevalidator.validation.Error.EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE;

@Component
public class AmericanStyleRule implements OptionsValidationRule {

    @Override
    public Collection<Error> validate(Trade trade) {
        Collection<Error> errors = new ArrayList<>();

        if (Style.AMERICAN.name().equals(trade.getStyle())) {
            if (!isExerciseStarDateAfterTradeDateAndBeforeExpiryDate(trade)) {
                errors.add(EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE);
            }
        }

        return errors;
    }

    private boolean isExerciseStarDateAfterTradeDateAndBeforeExpiryDate(Trade trade) {
        Date exerciseStartDate = trade.getExcerciseStartDate();
        Date tradeDate = trade.getTradeDate();
        Date expiryDate = trade.getExpiryDate();

        return isDateSet(exerciseStartDate) && isDateSet(tradeDate) && isDateSet(expiryDate) && exerciseStartDate.after(tradeDate) && tradeDate.before(expiryDate);

    }

    private boolean isDateSet(Date date) {
        return date != null;
    }


}

package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static com.tradevalidator.validation.Error.*;

@Component
public class ValueDateValidationRule implements AllValidationRule {

    private static final String NON_WORKING_DAY = "2017-12-25";

    @Override
    public Collection<Error> validate(Trade trade) {
        Collection<Error> errors = new ArrayList<>();

        if (!isValidValueDate(trade)) {
            errors.add(VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE);
        }
        if (isCurrencyTrade(trade)) {
            if (isWeekend(trade.getValueDate())) {
                errors.add(VALUE_DATE_FALLS_ON_WEEKEND);
            }
            if (isNonWorkingDay(trade.getValueDate())) {
                errors.add(VALUE_DATE_FALLS_ON_NON_WORKING_DAY);
            }
        }
        return errors;
    }

    private boolean isCurrencyTrade(Trade trade) {
        //I assume that currency trades are distinguish if they have ccPair.
        return trade.getCcyPair() != null;
    }

    private boolean isNonWorkingDay(Date date) {
        //This implementation depends on Country location or specific days provided.
        //It is unclear in the requirements the definition of non-working days, therefore, lets put christmas.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date christmas;
        try {
            christmas = sdf.parse(NON_WORKING_DAY);
        } catch (ParseException e) {
            return false;
        }
        return date.compareTo(christmas) == 0;
    }

    private boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    private boolean isValidValueDate(Trade trade) {
        boolean isDatesSet = trade.getValueDate() != null && trade.getTradeDate() != null;
        return isDatesSet && trade.getValueDate().after(trade.getTradeDate());
    }
}

package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;

import static com.tradevalidator.validation.Error.INVALID_CURRENCY_PAIR;

@Component
public class CurrencyValidationRule implements AllValidationRule {

    @Override
    public Collection<Error> validate(Trade trade) {

        Collection<Error> errors = new ArrayList<>();

        if (!isValidCurrencyPair(trade)) {
            errors.add(INVALID_CURRENCY_PAIR);
        }

        return errors;
    }

    private boolean isValidCurrencyPair(Trade trade) {
        boolean result = false;
        String currency = trade.getCcyPair();
        if (currency != null) {
            try {
                Currency.getInstance(currency.substring(0, 3));
                Currency.getInstance(currency.substring(3));
                result = true;
            } catch (Exception e) {
                result = false;
            }
        }
        return result;
    }
}

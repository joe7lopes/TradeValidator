package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Customer;
import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static com.tradevalidator.validation.Error.UNSUPPORTED_CUSTOMER;

@Component
public class CustomerValidationRule implements ValidationRule {


    @Override
    public Collection<Error> validate(Trade trade) {
        Collection<Error> errors = new ArrayList<>();

        if (!isValidCustomer(trade)) {
            errors.add(UNSUPPORTED_CUSTOMER);
        }

        return errors;
    }

    private boolean isValidCustomer(Trade trade) {

        for (Customer customer : Customer.values()) {
            if (customer.name().equals(trade.getCustomer())) {
                return true;
            }
        }
        return false;
    }
}

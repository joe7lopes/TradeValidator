package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static com.tradevalidator.domain.Customer.PLUTO1;
import static com.tradevalidator.domain.Customer.PLUTO2;
import static com.tradevalidator.validation.Error.UNSUPPORTED_CUSTOMER;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomerValidationRuleTest {

    private CustomerValidationRule rule;

    private Trade trade;

    @Before
    public void setUp() {
        rule = new CustomerValidationRule();
        trade = new Trade();
    }

    @Test
    public void givenNullThenError() {
        trade.setCustomer(null);
        assertThat(rule.validate(trade), is(hasItem(UNSUPPORTED_CUSTOMER)));
    }

    @Test
    public void givenEmptyCustomerThenError() {
        trade.setCustomer("");
        assertThat(rule.validate(trade), is(hasItem(UNSUPPORTED_CUSTOMER)));
    }

    @Test
    public void givenDummyCustomerThenError() {
        trade.setCustomer("Dummy");
        assertThat(rule.validate(trade), is(hasItem(UNSUPPORTED_CUSTOMER)));
    }

    @Test
    public void givenPLUTO1ThenNoError() {
        assertValidCustomer(PLUTO1.name());
    }

    @Test
    public void givenPLUTO2ThenNoError() {
        assertValidCustomer(PLUTO2.name());
    }

    private void assertValidCustomer(String customer) {
        trade.setCustomer(customer);
        assertThat(rule.validate(trade), is(Collections.emptyList()));
    }

}
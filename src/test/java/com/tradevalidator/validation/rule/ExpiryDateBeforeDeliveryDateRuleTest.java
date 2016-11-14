package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.tradevalidator.validation.Error.EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

public class ExpiryDateBeforeDeliveryDateRuleTest {

    private ExpiryDateBeforeDeliveryDateRule rule;

    private Trade trade;

    @Before
    public void setUp() {
        rule = new ExpiryDateBeforeDeliveryDateRule();
        trade = new Trade();
    }

    @Test
    public void givenNullExpiryDateThenError() {
        trade.setExpiryDate(null);
        assertError(EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE);
    }

    @Test
    public void expiryDateShouldBeBeforeDeliveryDate() {
        trade.setExpiryDate(convertStringToDate("2016-09-22"));
        trade.setDeliveryDate(convertStringToDate("2016-09-21"));
        assertError(EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE);
    }

    private void assertError(Error error) {
        assertThat(rule.validate(trade), hasItem(error));
    }

    private Date convertStringToDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate;
        try {
            convertedDate = df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Unable to convert date");
        }
        return convertedDate;
    }
}
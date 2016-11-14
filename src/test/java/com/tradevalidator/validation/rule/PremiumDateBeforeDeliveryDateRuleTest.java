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
import static com.tradevalidator.validation.Error.PREMIUM_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

public class PremiumDateBeforeDeliveryDateRuleTest {

    private PremiumDateBeforeDeliveryDateRule rule;
    private Trade trade;


    @Before
    public void setUp() {
        rule = new PremiumDateBeforeDeliveryDateRule();
        trade = new Trade();
    }

    @Test
    public void givenNullPremiumDateThenError() {
        trade.setPremiumDate(null);
        trade.setDeliveryDate(convertStringToDate("2016-09-21"));
        assertError(PREMIUM_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE);
    }

    @Test
    public void givenNullDeliveryDateThenError() {
        trade.setDeliveryDate(null);
        trade.setPremiumDate(new Date());
        assertError(PREMIUM_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE);
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
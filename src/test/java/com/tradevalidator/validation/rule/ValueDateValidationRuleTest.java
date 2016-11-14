package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.tradevalidator.validation.Error.*;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValueDateValidationRuleTest {

    private ValueDateValidationRule rule;
    private Trade trade;

    @Before
    public void setUp() {
        rule = new ValueDateValidationRule();
        trade = new Trade();
    }

    @Test
    public void givenNullValueDateThenError() {
        trade.setValueDate(null);
        assertError(VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE);
    }

    @Test
    public void givenNullTradeDateThenError() {
        trade.setTradeDate(null);
        assertError(VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE);
    }

    @Test
    public void givenNullValueDateAndValidTradeDateThenError() {
        trade.setValueDate(null);
        trade.setTradeDate(new Date());
        assertError(VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE);
    }

    @Test
    public void shouldBeAfterTradeDate() {
        trade.setValueDate(convertStringToDate("2016-09-21"));
        trade.setTradeDate(convertStringToDate("2016-09-22"));
        assertError(VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE);
    }

    @Test
    public void givenWeekendThenError() {
        trade.setValueDate(convertStringToDate("2016-09-24"));
        trade.setCcyPair("EURUSD");
        assertError(VALUE_DATE_FALLS_ON_WEEKEND);
    }

    @Test
    public void givenNonWorkingDateThenError() {
        trade.setValueDate(convertStringToDate("2017-12-25"));
        trade.setCcyPair("EURUSD");
        assertError(VALUE_DATE_FALLS_ON_NON_WORKING_DAY);
    }

    @Test
    public void givenValidValueDateThenNoError() {
        trade.setValueDate(convertStringToDate("2016-09-23"));
        trade.setTradeDate(convertStringToDate("2016-09-22"));
        assertThat(rule.validate(trade), is(emptyList()));
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
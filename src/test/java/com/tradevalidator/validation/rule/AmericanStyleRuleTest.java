package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.tradevalidator.validation.Error.EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE;
import static com.tradevalidator.validation.Error.UNSUPPORTED_STYLE;
import static com.tradevalidator.validation.Style.AMERICAN;
import static com.tradevalidator.validation.Style.EUROPEAN;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class AmericanStyleRuleTest {

private AmericanStyleRule rule;

    private Trade trade;

    @Before
    public void setUp() {
        rule = new AmericanStyleRule();
        trade= new Trade();
    }

    @Test
    public void givenNullThenError() {
        trade.setStyle(null);
        assertNoError(UNSUPPORTED_STYLE);
    }

    @Test
    public void givenEuropeanStyleThenNoError() {
        trade.setStyle(EUROPEAN.name());
        assertNoError(UNSUPPORTED_STYLE);
    }

    @Test
    public void givenAmericanStyleThenExerciseStartDateShouldBeAfterTradeDate() {
        trade.setStyle(AMERICAN.name());
        trade.setTradeDate(convertStringToDate("2016-09-23"));
        trade.setExcerciseStartDate(convertStringToDate("2016-09-22"));
        assertError(EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE);
    }

    @Test
    public void givenAmericanStyleExerciseStartDateShouldBeBeforeExpiryDate() {
        trade.setStyle(AMERICAN.name());
        trade.setExcerciseStartDate(convertStringToDate("2016-09-22"));
        trade.setTradeDate(convertStringToDate("2016-09-21"));
        trade.setExpiryDate(convertStringToDate("2016-09-21"));
        assertError(EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE);
    }

    @Test
    public void givenEuropeanStyleExerciseStartDateShouldBeBeforeExpiryDate() {
        trade.setStyle(EUROPEAN.name());
        trade.setExcerciseStartDate(convertStringToDate("2016-09-22"));
        trade.setTradeDate(convertStringToDate("2016-09-21"));
        trade.setExpiryDate(convertStringToDate("2016-09-21"));
        assertNoError(EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE);
    }

    @Test
    public void noErrorWhenExerciseStartDateAfterTradeDateAndBeforeExpiryDate() {
        trade.setStyle(AMERICAN.name());
        trade.setExcerciseStartDate(convertStringToDate("2016-09-22"));
        trade.setTradeDate(convertStringToDate("2016-09-21"));
        trade.setExpiryDate(convertStringToDate("2016-09-23"));
        assertNoError(EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE);
    }

    private void assertError(Error error) {
        assertThat(rule.validate(trade), hasItem(error));
    }

    private void assertNoError(Error error) {
        assertThat(rule.validate(trade), not(hasItem(error)));
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
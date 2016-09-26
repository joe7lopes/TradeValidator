package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.tradevalidator.validation.Error.INVALID_CURRENCY_PAIR;
import static com.tradevalidator.validation.Error.UNSUPPORTED_LEGAL_ENTITY;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CurrencyValidationRuleTest {

    private CurrencyValidationRule rule;

    private Trade trade;

    @Before
    public void setUp() {
        rule = new CurrencyValidationRule();
        trade = new Trade();
    }

    @Test
    public void givenNullThenError() {
        trade.setCcyPair(null);
        rule.validate(trade);
        assertThat(rule.validate(trade), hasItem(INVALID_CURRENCY_PAIR));
    }

    @Test
    public void givenEmptyThenError() {
        trade.setCcyPair("");
        rule.validate(trade);
        assertThat(rule.validate(trade), hasItem(INVALID_CURRENCY_PAIR));
    }

    @Test
    public void givenDummyThenError() {
        trade.setCcyPair("dummy");
        assertThat(rule.validate(trade), hasItem(INVALID_CURRENCY_PAIR));
    }

    @Test
    public void givenUSDPPTThenError() {
        trade.setCcyPair("USDPPT");
        assertThat(rule.validate(trade), hasItem(INVALID_CURRENCY_PAIR));
    }

    @Test
    public void givenValidCurrencyPairThenNoError() {
        trade.setCcyPair("EURUSD");
        assertThat(rule.validate(trade), is(emptyList()));
    }


}
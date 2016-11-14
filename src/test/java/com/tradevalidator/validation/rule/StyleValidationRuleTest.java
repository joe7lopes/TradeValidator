package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import org.junit.Before;
import org.junit.Test;

import static com.tradevalidator.validation.Error.UNSUPPORTED_STYLE;
import static com.tradevalidator.validation.Style.AMERICAN;
import static com.tradevalidator.validation.Style.EUROPEAN;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class StyleValidationRuleTest {

    private StyleValidationRule rule;

    private Trade trade;

    @Before
    public void setUp() {
        rule = new StyleValidationRule();
        trade = new Trade();
    }

    @Test
    public void givenNullThenError() {
        trade.setStyle(null);
        assertError(UNSUPPORTED_STYLE);
    }

    @Test
    public void givenEmptyThenError() {
        trade.setStyle("");
        assertError(UNSUPPORTED_STYLE);
    }

    @Test
    public void givenDummyStyleThenError() {
        trade.setStyle("dummy");
        assertError(UNSUPPORTED_STYLE);
    }

    @Test
    public void givenAmericanStyleThenNoError() {
        trade.setStyle(AMERICAN.name());
        assertNoError(UNSUPPORTED_STYLE);
    }

    @Test
    public void givenEuropeanStyleThenNoError() {
        trade.setStyle(EUROPEAN.name());
        assertNoError(UNSUPPORTED_STYLE);
    }


    private void assertError(Error error) {
        assertThat(rule.validate(trade), hasItem(error));
    }

    private void assertNoError(Error error) {
        assertThat(rule.validate(trade), not(hasItem(error)));
    }


}
package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.rule.LegalEntityValidationRule;
import org.junit.Before;
import org.junit.Test;

import static com.tradevalidator.validation.Error.UNSUPPORTED_LEGAL_ENTITY;
import static com.tradevalidator.validation.LegalEntity.CS_ZURICH;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LegalEntityValidationRuleTest {


    private LegalEntityValidationRule rule;
    private Trade trade;

    @Before
    public void setUp() {
        rule = new LegalEntityValidationRule();
        trade = new Trade();
    }

    @Test
    public void givenNullLegalEntityThenError() {
        trade.setLegalEntity(null);
        assertUnsupportedLegalEntity();
    }

    @Test
    public void givenInvalidLegalEntityThenError() {
        trade.setLegalEntity("dummy");
        assertUnsupportedLegalEntity();
    }

    @Test
    public void givenValidLegalEntityThenNoError() {
        trade.setLegalEntity(CS_ZURICH.getDescription());
        rule.validate(trade);
        assertThat(rule.validate(trade), is(emptyList()));
    }

    private void assertUnsupportedLegalEntity() {
        assertThat(rule.validate(trade), hasItem(UNSUPPORTED_LEGAL_ENTITY));
    }

}
package com.tradevalidator.validation.validator;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.rule.ExpiryDateBeforeDeliveryDateRule;
import com.tradevalidator.validation.rule.OptionsValidationRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static com.tradevalidator.validation.Error.EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE;
import static com.tradevalidator.validation.Error.EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE;
import static com.tradevalidator.validation.TradeType.VANILLA_OPTION;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OptionValidatorTest {


    private OptionValidator validator;
    private Trade trade;
    private Collection<Error> errors;

    @Mock
    private OptionsValidationRule rule;

    @Before
    public void setUp() {
        validator = new OptionValidator();
        validator.setRules(asList(rule));
        trade = new Trade();
        trade.setType(VANILLA_OPTION.getDescription());
    }

    @Test
    public void givenNoOptionTradeReturnNoErrors() {
        errors = validator.validate(trade);
        assertThat(errors, is(emptyList()));
    }

    @Test
    public void givenNoRulesThenNoError() {
        validator.setRules(emptyList());
        errors = validator.validate(trade);
        assertThat(errors, is(emptyList()));
    }

    @Test
    public void shouldCallRuleValidation() {
        when(rule.validate(trade)).thenReturn(anyCollection());
        validator.validate(trade);
        verify(rule, times(1)).validate(trade);
    }

    @Test
    public void whenRuleHasErrorsThenReturnAllErrors() {
        Collection<Error> errors;
        when(rule.validate(trade)).thenReturn(asList(EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE, EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE));
        errors = validator.validate(trade);
        assertThat(errors, hasItems(EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE, EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE));
    }

    @Test
    public void whenTwoRulesHasErrorsThenReturnAllErrors() {
        OptionsValidationRule currencyRule = mock(ExpiryDateBeforeDeliveryDateRule.class);
        validator.setRules(asList(rule, currencyRule));
        Collection<Error> errors;
        when(rule.validate(trade)).thenReturn(asList(EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE));
        when(currencyRule.validate(trade)).thenReturn(asList(EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE));
        errors = validator.validate(trade);
        assertThat(errors, hasItems(EXPIRY_DATE_SHOULD_BE_BEFORE_DELIVERY_DATE, EXCERCISE_START_DATE__HAS_TO_BE_AFTER_TRADE_DATE_AND_BEFORE_EXPIRY_DATE));
    }

}
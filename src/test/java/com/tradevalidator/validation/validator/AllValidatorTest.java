package com.tradevalidator.validation.validator;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.rule.AllValidationRule;
import com.tradevalidator.validation.rule.CurrencyValidationRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.tradevalidator.validation.Error.INVALID_VALUE_DATE;
import static com.tradevalidator.validation.Error.UNSUPPORTED_CUSTOMER;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AllValidatorTest {

    private AllValidator validator;
    private Trade trade;
    private Collection<Error> errors;

    @Mock
    private AllValidationRule rule;

    @Before
    public void setUp() {
        validator = new AllValidator();
        validator.setValidationRules(asList(rule));
        trade = new Trade();
    }

    @Test
    public void givenNoRulesThenNoError() {
        validator.setValidationRules(Collections.emptyList());
        errors = validator.validate(trade);
        assertThat(errors, is(Collections.emptyList()));
    }

    @Test
    public void shouldCallRuleValidation() {
        when(rule.validate(trade)).thenReturn(anyCollection());
        validator.validate(trade);
        verify(rule, Mockito.times(1)).validate(trade);
    }

    @Test
    public void whenRuleHasErrorsThenReturnAllErrors() {
        Collection<Error> errors;
        when(rule.validate(trade)).thenReturn(asList(INVALID_VALUE_DATE, UNSUPPORTED_CUSTOMER));
        errors = validator.validate(trade);
        assertThat(errors, hasItems(INVALID_VALUE_DATE, UNSUPPORTED_CUSTOMER));
    }

    @Test
    public void whenTwoRulesHasErrorsThenReturnAllErrors() {
        AllValidationRule currencyRule = mock(CurrencyValidationRule.class);
        validator.setValidationRules(Arrays.asList(rule, currencyRule));
        Collection<Error> errors;
        when(rule.validate(trade)).thenReturn(asList(INVALID_VALUE_DATE));
        when(currencyRule.validate(trade)).thenReturn(asList(UNSUPPORTED_CUSTOMER));
        errors = validator.validate(trade);
        assertThat(errors, hasItems(INVALID_VALUE_DATE, UNSUPPORTED_CUSTOMER));
    }

}
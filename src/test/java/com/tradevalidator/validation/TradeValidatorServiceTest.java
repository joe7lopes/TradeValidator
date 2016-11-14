package com.tradevalidator.validation;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.validator.Validator;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static com.tradevalidator.validation.Error.INVALID_CURRENCY_PAIR;
import static com.tradevalidator.validation.Error.INVALID_VALUE_DATE;
import static com.tradevalidator.validation.Error.UNSUPPORTED_LEGAL_ENTITY;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TradeValidatorServiceTest {

    private TradeValidatorService service;

    private Trade trade;
    @Mock
    private Validator validator;

    @Before
    public void setUp() {
        service = new TradeValidatorService();
        service.setValidators(asList(validator));
        trade = new Trade();
    }

    @Test
    public void givenOneTradeThenReturnErrors() {
        when(validator.validate(trade)).thenReturn(asList(INVALID_CURRENCY_PAIR));
        assertThat(service.validate(trade), hasItem(INVALID_CURRENCY_PAIR));
    }

    @Test
    public void givenOneTradeWithMultipleErrorsThenReturnAllErrors() {
        when(validator.validate(trade)).thenReturn(asList(INVALID_CURRENCY_PAIR,INVALID_VALUE_DATE));
        assertThat(service.validate(trade), hasItems(INVALID_CURRENCY_PAIR,INVALID_VALUE_DATE));
    }

    @Test
    public void givenTradeWithNoErrorsReturnEmpty() {
        when(validator.validate(trade)).thenReturn(Collections.emptyList());
        assertThat(service.validate(trade), is(Collections.emptyList()));
    }

    @Test
    public void givenTwoTrades_OneWithErrorsThenReturnErrorsForOnlyOne() {
        Trade validTrade= new Trade();
        validTrade.setStyle("someValue to not have equal trades");
        when(validator.validate(validTrade)).thenReturn(Collections.emptyList());
        when(validator.validate(trade)).thenReturn(asList(INVALID_VALUE_DATE));
        assertThat(service.validate(asList(validTrade,trade)).get(validTrade), is(Collections.emptyList()));
        assertThat(service.validate(asList(validTrade,trade)).get(trade), hasItem(INVALID_VALUE_DATE));
    }

    @Test
    public void givenOneTradeThenReturnAllErrors() {
        when(validator.validate(trade)).thenReturn(asList(INVALID_VALUE_DATE,UNSUPPORTED_LEGAL_ENTITY));
        assertThat(service.validate(asList(trade)).get(trade), hasItems(INVALID_VALUE_DATE,UNSUPPORTED_LEGAL_ENTITY));
    }
}
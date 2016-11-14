package com.tradevalidator.controller;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.TradeValidatorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.tradevalidator.validation.Error.INVALID_CURRENCY_PAIR;
import static com.tradevalidator.validation.Error.UNSUPPORTED_CUSTOMER;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TradeValidatorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private TradeValidatorService service;

    private Trade trade;

    private static final String SINGLE_TRADE_URL = "/api/trade";
    private static final String MULTIPLE_TRADE_URL = "/api/trades";

    @Before
    public void setUp() {
        trade = new Trade();
    }

    @Test
    public void givenNoTradeRequestReturn200OK() {
        when(service.validate(trade)).thenReturn(Collections.EMPTY_LIST);
        ResponseEntity<Error[]> entity = restTemplate.postForEntity(SINGLE_TRADE_URL, trade, Error[].class);
        assertThat(entity.getStatusCode(), is(OK));
    }


    @Test
    public void givenOneValidationErrorThenReturnErrorWithUnprocessableEntity() {
        Collection<Error> errors = Arrays.asList(UNSUPPORTED_CUSTOMER);
        when(service.validate(trade)).thenReturn(errors);
        assertSingleTradeError(1);
    }

    @Test
    public void givenTwoValidationErrorsThenReturnErrorWithUnprocessableEntity() {
        Collection<Error> errors = Arrays.asList(UNSUPPORTED_CUSTOMER, INVALID_CURRENCY_PAIR);
        when(service.validate(trade)).thenReturn(errors);
        assertSingleTradeError(2);
    }

    @Test
    public void givenEmptyTradesThen200Ok() {
        when(service.validate(anyCollection())).thenReturn(new HashMap<>());
        ResponseEntity<Error[]> entity = restTemplate.postForEntity(SINGLE_TRADE_URL, trade, Error[].class);
        assertThat(entity.getStatusCode(), is(OK));
    }

    @Test
    public void givenOneValidTradeThen200Ok() {
        when(service.validate(anyCollection())).thenReturn(new HashMap<>());
        ResponseEntity<Trade> entity = restTemplate.postForEntity(MULTIPLE_TRADE_URL, getTrades(), Trade.class);
        assertThat(entity.getStatusCode(), is(OK));
    }

    @Test
    public void givenOneTradeWithErrorsThenReturnErrorsWithUnprocessableEntity() {
        Map<Trade, Collection<Error>> errors = new HashMap<>();
        errors.put(trade, Arrays.asList(UNSUPPORTED_CUSTOMER));
        when(service.validate(anyCollection())).thenReturn(errors);
        assertMultipleTradeError();
    }


    @Test
    public void givenExceptionInApplicationThenReturnBadRequest() {
        doThrow(Exception.class).when(service).validate(trade);
        ResponseEntity<Error[]> entity = restTemplate.postForEntity(SINGLE_TRADE_URL, trade, Error[].class);
        assertThat(entity.getStatusCode(), is(BAD_REQUEST));
    }

    private void assertSingleTradeError(int numberOfErrors) {
        ResponseEntity<Object[]> entity = restTemplate.postForEntity(SINGLE_TRADE_URL, trade, Object[].class);
        assertThat(entity.getStatusCode(), is(UNPROCESSABLE_ENTITY));
        assertThat(entity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat(entity.getBody().length, is(numberOfErrors));
    }

    private void assertMultipleTradeError() {
        Collection<Trade> trades = getTrades();
        ResponseEntity<Trade> entity = restTemplate.postForEntity(MULTIPLE_TRADE_URL, trades, Trade.class);
        assertThat(entity.getStatusCode(), is(UNPROCESSABLE_ENTITY));
        assertThat(entity.getHeaders().getContentType(), is(APPLICATION_JSON));
        System.out.println(entity.toString());
    }

    private Collection<Trade> getTrades() {
        Collection<Trade> trades = new ArrayList<>();
        trades.add(trade);
        return trades;
    }


}
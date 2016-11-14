package com.tradevalidator.validation;

import org.junit.Test;

import static com.tradevalidator.validation.TradeType.SPOT;
import static com.tradevalidator.validation.TradeType.fromString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TradeTypeTest {

    @Test
    public void givenSpotAsStringInLowerCaseReturnSpotType() {
        assertThat(fromString("spot"), is(SPOT));
    }

    @Test
    public void givenSpotAsStringReturnSpotType() {
        assertThat(fromString("spoT"), is(SPOT));
    }

    @Test
    public void givenUnsupportedTypeAsStringReturnNull() {
        assertThat(fromString("sss"), is(nullValue()));
    }

}
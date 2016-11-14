package com.tradevalidator.validation;

import org.junit.Test;

import static com.tradevalidator.validation.LegalEntity.fromString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class LegalEntityTest {

    @Test
    public void givenCS_ZurichAsStringInLowerCaseReturnCS_ZurichType() {
        assertThat(fromString("CS Zurich"), is(LegalEntity.CS_ZURICH));
    }

    @Test
    public void givenUnsupportedLegalEntityAsStringReturnNull() {
        assertThat(fromString("sss"), is(nullValue()));
    }

}
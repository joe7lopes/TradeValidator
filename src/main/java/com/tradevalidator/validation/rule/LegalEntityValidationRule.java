package com.tradevalidator.validation.rule;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.LegalEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static com.tradevalidator.validation.Error.UNSUPPORTED_LEGAL_ENTITY;

@Component
public class LegalEntityValidationRule implements ValidationRule {
    @Override
    public Collection<Error> validate(Trade trade) {
        Collection<Error> errors = new ArrayList<>();

        if (!isValidLegalEntity(trade)) {
            errors.add(UNSUPPORTED_LEGAL_ENTITY);
        }

        return errors;
    }

    private boolean isValidLegalEntity(Trade trade) {

        for (LegalEntity entity : LegalEntity.values()) {
            if (entity.getDescription().equals(trade.getLegalEntity())) {
                return true;
            }
        }
        return false;
    }
}

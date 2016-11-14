package com.tradevalidator.validation;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class TradeValidatorService {
    private final Logger logger = LoggerFactory.getLogger(TradeValidatorService.class);

    @Autowired
    private Collection<Validator> validators;

    //I assume that there's no equal trades, otherwise Map is not the best option.
    public Map<Trade, Collection<Error>> validate(Collection<Trade> trades) {
        logger.info("validating trades:", trades);

        Map<Trade, Collection<Error>> errors = new HashMap<>();
        Collection<Error> validatorErrors;

        for (Trade trade : trades) {
            validatorErrors = validate(trade);
            errors.put(trade, validatorErrors);
        }

        logger.info("f errors found", errors);
        return errors;
    }

    public Collection<Error> validate(Trade trade) {

        Collection<Error> validatorErrors = new ArrayList<>();

        for (Validator validator : validators) {
            validatorErrors.addAll(validator.validate(trade));
        }
        return validatorErrors;
    }

    void setValidators(Collection<Validator> validators) {
        this.validators = validators;
    }
}
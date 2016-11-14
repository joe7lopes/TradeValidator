package com.tradevalidator.controller;

import com.tradevalidator.domain.Trade;
import com.tradevalidator.validation.Error;
import com.tradevalidator.validation.TradeValidatorService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class TradeValidatorController {

    private final Logger logger = LoggerFactory.getLogger(TradeValidatorController.class);

    @Autowired
    TradeValidatorService validator;

    @RequestMapping(value = "/trade", consumes = APPLICATION_JSON_VALUE, method = POST)
    public ResponseEntity<Collection<Error>> validate(@RequestBody Trade trade) {
        Collection<Error> errors = validator.validate(trade);

        if (errors.isEmpty()) {
            return new ResponseEntity<>(OK);
        } else {
            HttpHeaders headers = getHeaders();
            return new ResponseEntity<>(errors, headers, UNPROCESSABLE_ENTITY);
        }


    }

    @RequestMapping(value = "/trades", consumes = APPLICATION_JSON_VALUE, method = POST)
    public ResponseEntity<Map<Trade, Collection<Error>>> validate(@RequestBody Collection<Trade> trades) {
        Map<Trade, Collection<Error>> errors = validator.validate(trades);

        if (errors.isEmpty()) {
            return new ResponseEntity<>(OK);
        } else {
            HttpHeaders headers = getHeaders();
            return new ResponseEntity<>(errors, headers, UNPROCESSABLE_ENTITY);
        }

    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return headers;
    }

    @RequestMapping(value = "/trade", method = GET)
    public ResponseEntity<String> getSingleTradeExample() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(TEXT_PLAIN);
        String response = getJsonTradeExample();
        return new ResponseEntity<>(response, headers, OK);
    }

    @RequestMapping(value = "/trades", method = GET)
    public ResponseEntity<String> getMultipleTradeExample() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(TEXT_PLAIN);
        String response = "[ <OneTrade>,<anotherTrade> ]";
        return new ResponseEntity<>(response, headers, OK);
    }

    private String getJsonTradeExample() {
        return "{\n" +
                "  \"customer\": \"PLUTO1\",\n" +
                "  \"ccyPair\": \"EURUSpsd\",\n" +
                "  \"type\": \"Spot\",\n" +
                "  \"direction\": \"BUY\",\n" +
                "  \"tradeDate\": \"2016-08-11\",\n" +
                "  \"amount1\": \"1000000.00\",\n" +
                "  \"amount2\": \"1120000.00\",\n" +
                "  \"rate\": \"1.12\",\n" +
                "  \"valueDate\": \"2016-08-10\",\n" +
                "  \"legalEntity\": \"CS Zurich\",\n" +
                "  \"trader\": \"Johann Baunfiddler\"\n" +
                "}";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> exceptionHandler(HttpServletRequest req, Exception ex) {
        logger.error("Request:" + req.getRequestURL() + "raised" + ex);
        return new ResponseEntity<>(BAD_REQUEST);
    }

}

package com.tradevalidator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade {

    private Date valueDate;
    private Date tradeDate;
    private String ccyPair;
    private String customer;
    private String style;
    private Date expiryDate;
    private Date deliveryDate;
    private Date premiumDate;
    private String type;
    private String legalEntity;
    private Date excerciseStartDate;

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setCcyPair(String ccyPair) {
        this.ccyPair = ccyPair;
    }

    public String getCcyPair() {
        return ccyPair;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }

    public String getStyle() {
        return style;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Date getPremiumDate() {
        return premiumDate;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setPremiumDate(Date premium) {
        this.premiumDate = premium;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getType() {
        return type;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public Date getExcerciseStartDate() {
        return excerciseStartDate;
    }

    public void setExcerciseStartDate(Date excerciseStartDate) {
        this.excerciseStartDate = excerciseStartDate;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (valueDate != null ? !valueDate.equals(trade.valueDate) : trade.valueDate != null) return false;
        if (tradeDate != null ? !tradeDate.equals(trade.tradeDate) : trade.tradeDate != null) return false;
        if (ccyPair != null ? !ccyPair.equals(trade.ccyPair) : trade.ccyPair != null) return false;
        if (customer != null ? !customer.equals(trade.customer) : trade.customer != null) return false;
        if (style != null ? !style.equals(trade.style) : trade.style != null) return false;
        if (expiryDate != null ? !expiryDate.equals(trade.expiryDate) : trade.expiryDate != null) return false;
        if (deliveryDate != null ? !deliveryDate.equals(trade.deliveryDate) : trade.deliveryDate != null) return false;
        if (premiumDate != null ? !premiumDate.equals(trade.premiumDate) : trade.premiumDate != null) return false;
        if (type != null ? !type.equals(trade.type) : trade.type != null) return false;
        if (legalEntity != null ? !legalEntity.equals(trade.legalEntity) : trade.legalEntity != null) return false;
        return excerciseStartDate != null ? excerciseStartDate.equals(trade.excerciseStartDate) : trade.excerciseStartDate == null;

    }

    @Override
    public int hashCode() {
        int result = valueDate != null ? valueDate.hashCode() : 0;
        result = 31 * result + (tradeDate != null ? tradeDate.hashCode() : 0);
        result = 31 * result + (ccyPair != null ? ccyPair.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        result = 31 * result + (premiumDate != null ? premiumDate.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (legalEntity != null ? legalEntity.hashCode() : 0);
        result = 31 * result + (excerciseStartDate != null ? excerciseStartDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "valueDate=" + valueDate +
                ", tradeDate=" + tradeDate +
                ", ccyPair='" + ccyPair + '\'' +
                ", customer='" + customer + '\'' +
                ", style='" + style + '\'' +
                ", expiryDate=" + expiryDate +
                ", deliveryDate=" + deliveryDate +
                ", premiumDate=" + premiumDate +
                ", type='" + type + '\'' +
                ", legalEntity='" + legalEntity + '\'' +
                ", excerciseStartDate=" + excerciseStartDate +
                '}';
    }
}

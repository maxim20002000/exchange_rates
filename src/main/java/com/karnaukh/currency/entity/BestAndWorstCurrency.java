package com.karnaukh.currency.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;

@Document(collection = "bestAndWorstCurrency")
public class BestAndWorstCurrency implements Serializable {
    String bankName;
    Instant date;
    Double bestUsdPurchase;
    Double bestUsdSale;
    Double bestEurPurchase;
    Double bestEurSale;
    Double bestRubPurchase;
    Double bestRubSale;

    Double worstUsdPurchase;
    Double worstUsdSale;
    Double worstEurPurchase;
    Double worstEurSale;
    Double worstRubPurchase;
    Double worstRubSale;

    public BestAndWorstCurrency() {
    }

    public BestAndWorstCurrency(String bankName, Double bestUsdPurchase, Double bestUsdSale,
                                Double bestEurPurchase, Double bestEurSale, Double bestRubPurchase, Double bestRubSale,
                                Double worstUsdPurchase, Double worstUsdSale, Double worstEurPurchase,
                                Double worstEurSale, Double worstRubPurchase, Double worstRubSale) {
        this.bankName = bankName;
        this.date = Instant.now();
        this.bestUsdPurchase = bestUsdPurchase;
        this.bestUsdSale = bestUsdSale;
        this.bestEurPurchase = bestEurPurchase;
        this.bestEurSale = bestEurSale;
        this.bestRubPurchase = bestRubPurchase;
        this.bestRubSale = bestRubSale;
        this.worstUsdPurchase = worstUsdPurchase;
        this.worstUsdSale = worstUsdSale;
        this.worstEurPurchase = worstEurPurchase;
        this.worstEurSale = worstEurSale;
        this.worstRubPurchase = worstRubPurchase;
        this.worstRubSale = worstRubSale;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getBestUsdPurchase() {
        return bestUsdPurchase;
    }

    public void setBestUsdPurchase(Double bestUsdPurchase) {
        this.bestUsdPurchase = bestUsdPurchase;
    }

    public Double getBestUsdSale() {
        return bestUsdSale;
    }

    public void setBestUsdSale(Double bestUsdSale) {
        this.bestUsdSale = bestUsdSale;
    }

    public Double getBestEurPurchase() {
        return bestEurPurchase;
    }

    public void setBestEurPurchase(Double bestEurPurchase) {
        this.bestEurPurchase = bestEurPurchase;
    }

    public Double getBestEurSale() {
        return bestEurSale;
    }

    public void setBestEurSale(Double bestEurSale) {
        this.bestEurSale = bestEurSale;
    }

    public Double getBestRubPurchase() {
        return bestRubPurchase;
    }

    public void setBestRubPurchase(Double bestRubPurchase) {
        this.bestRubPurchase = bestRubPurchase;
    }

    public Double getBestRubSale() {
        return bestRubSale;
    }

    public void setBestRubSale(Double bestRubSale) {
        this.bestRubSale = bestRubSale;
    }

    public Double getWorstUsdPurchase() {
        return worstUsdPurchase;
    }

    public void setWorstUsdPurchase(Double worstUsdPurchase) {
        this.worstUsdPurchase = worstUsdPurchase;
    }

    public Double getWorstUsdSale() {
        return worstUsdSale;
    }

    public void setWorstUsdSale(Double worstUsdSale) {
        this.worstUsdSale = worstUsdSale;
    }

    public Double getWorstEurPurchase() {
        return worstEurPurchase;
    }

    public void setWorstEurPurchase(Double worstEurPurchase) {
        this.worstEurPurchase = worstEurPurchase;
    }

    public Double getWorstEurSale() {
        return worstEurSale;
    }

    public void setWorstEurSale(Double worstEurSale) {
        this.worstEurSale = worstEurSale;
    }

    public Double getWorstRubPurchase() {
        return worstRubPurchase;
    }

    public void setWorstRubPurchase(Double worstRubPurchase) {
        this.worstRubPurchase = worstRubPurchase;
    }

    public Double getWorstRubSale() {
        return worstRubSale;
    }

    public void setWorstRubSale(Double worstRubSale) {
        this.worstRubSale = worstRubSale;
    }
}

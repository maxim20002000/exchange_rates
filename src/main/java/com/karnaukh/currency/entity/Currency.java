package com.karnaukh.currency.entity;

import java.io.Serializable;

public class Currency implements Serializable {

    private String nameCurrency;

    private String nameCurrencyTo;

    private double purchasePrice;

    private double salePrice;

    public Currency() {
    }

    public Currency(String nameCurrency, String nameCurrencyTo, double purchasePrice, double salePrice) {
        this.nameCurrency = nameCurrency;
        this.nameCurrencyTo = nameCurrencyTo;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
    }

    public String getNameCurrency() {
        return nameCurrency;
    }

    public void setNameCurrency(String nameCurrency) {
        this.nameCurrency = nameCurrency;
    }

    public String getNameCurrencyTo() {
        return nameCurrencyTo;
    }

    public void setNameCurrencyTo(String nameCurrencyTo) {
        this.nameCurrencyTo = nameCurrencyTo;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "| " + nameCurrency + " -> " + nameCurrencyTo + " | Purchase: " + purchasePrice + " Sale: " + salePrice + " |";
    }
}

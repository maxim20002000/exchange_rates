package com.karnaukh.currency.entity;

public class Currency {
	private String nameCurrency;
	private String nameCurrencyTo;
	private double purchasePrice;
	private double salePrice;

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
}

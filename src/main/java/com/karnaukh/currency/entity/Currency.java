package com.karnaukh.currency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "currencies")
public class Currency implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_currency", nullable = false)
	private int idCurrency;

	@Column(name = "name_currency", nullable = false)
	private String nameCurrency;

	@Column(name = "name_currency_to", nullable = false)
	private String nameCurrencyTo;

	@Column(name = "purchase_price", nullable = false)
	private double purchasePrice;

	@Column(name = "sale_price", nullable = false)
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

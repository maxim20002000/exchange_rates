package com.karnaukh.currency.entity.secondary;

import com.google.gson.annotations.SerializedName;

public class Alfabank {
	@SerializedName("sellRate")
	private String sellRate;
	@SerializedName("sellIso")
	private String sellIso;
	@SerializedName("sellCode")
	private String sellCode;
	@SerializedName("buyRate")
	private String buyRate;
	@SerializedName("buyIso")
	private String buyIso;
	@SerializedName("buyCode")
	private String buyCode;
	@SerializedName("quantity")
	private String quantity;
	@SerializedName("name")
	private String name;
	@SerializedName("date")
	private String date;

	public Alfabank(String sellRate, String sellIso, String sellCode, String buyRate, String buyIso, String buyCode, String quantity, String name, String date) {
		this.sellRate = sellRate;
		this.sellIso = sellIso;
		this.sellCode = sellCode;
		this.buyRate = buyRate;
		this.buyIso = buyIso;
		this.buyCode = buyCode;
		this.quantity = quantity;
		this.name = name;
		this.date = date;
	}

	public Alfabank() {
	}

	public String getSellRate() {
		return sellRate;
	}

	public String getSellIso() {
		return sellIso;
	}

	public String getSellCode() {
		return sellCode;
	}

	public String getBuyRate() {
		return buyRate;
	}

	public String getBuyIso() {
		return buyIso;
	}

	public String getBuyCode() {
		return buyCode;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}
}

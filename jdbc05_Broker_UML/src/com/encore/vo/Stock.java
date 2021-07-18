package com.encore.vo;

public class Stock {
	private String stockCode;
	private String stockName;
	private double stockPrice;
	private String stockDate;
	private String industry;
	public Stock(String stockCode, String stockName, double stockPrice, String stockDate, String industry) {
		super();
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.stockPrice = stockPrice;
		this.stockDate = stockDate;
		this.industry = industry;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public double getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(double stockPrice) {
		this.stockPrice = stockPrice;
	}
	public String getStockDate() {
		return stockDate;
	}
	public void setStockDate(String stockDate) {
		this.stockDate = stockDate;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	@Override
	public String toString() {
		return "stock [stockCode=" + stockCode + ", stockName=" + stockName + ", stockPrice=" + stockPrice
				+ ", stockDate=" + stockDate + ", industry=" + industry + "]";
	}
	
	
}

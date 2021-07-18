package com.encore.vo;

public class Deal {
	private int dealCode;
	private double buyingPrice;
	private double sellingPrice;
	private String dealDate;
	public Deal(int dealCode, double buyingPrice, double sellingPrice, String dealDate) {
		super();
		this.dealCode = dealCode;
		this.buyingPrice = buyingPrice;
		this.sellingPrice = sellingPrice;
		this.dealDate = dealDate;
	}
	public int getDealCode() {
		return dealCode;
	}
	public void setDealCode(int dealCode) {
		this.dealCode = dealCode;
	}
	public double getBuyingPrice() {
		return buyingPrice;
	}
	public void setBuyingPrice(double buyingPrice) {
		this.buyingPrice = buyingPrice;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	@Override
	public String toString() {
		return "deal [dealCode=" + dealCode + ", buyingPrice=" + buyingPrice + ", sellingPrice=" + sellingPrice
				+ ", dealDate=" + dealDate + "]";
	}
	
	
}

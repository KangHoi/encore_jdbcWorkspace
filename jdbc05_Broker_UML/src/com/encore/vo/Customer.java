package com.encore.vo;

public class Customer {
	private int ssn;
	private String cusname;
	private String address;
	private int stocknum;
	public Customer(int ssn, String cusname, String address, int stocknum) {
		super();
		this.ssn = ssn;
		this.cusname = cusname;
		this.address = address;
		this.stocknum = stocknum;
	}
	public int getSsn() {
		return ssn;
	}
	public void setSsn(int ssn) {
		this.ssn = ssn;
	}
	public String getCusname() {
		return cusname;
	}
	public void setCusname(String cusname) {
		this.cusname = cusname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getStocknum() {
		return stocknum;
	}
	public void setStocknum(int stocknum) {
		this.stocknum = stocknum;
	}
	@Override
	public String toString() {
		return "customer [ssn=" + ssn + ", cusname=" + cusname + ", address=" + address + ", stocknum=" + stocknum
				+ "]";
	}

	
	
}

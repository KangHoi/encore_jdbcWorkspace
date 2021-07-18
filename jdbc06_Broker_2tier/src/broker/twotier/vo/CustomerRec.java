package broker.twotier.vo;

import java.util.Vector;

/**
 * @author Jiwon
 * 
 * 고개겡 대한 정보를 저장하는 Record Class..
 * 해당 고객은 주식을 사고파는데 연관이 있는 고객
 * 
 * 주식을 보유한 고객 | 주식을 보유하지 않은 고객일 수 있다.
 */
public class CustomerRec {
	public String ssn;
	public String name; //컬럼명은 cust_name
	public String address;
	
	//?
	private Vector<SharesRec> portfolio;

	public CustomerRec(String ssn, String name, String address, Vector<SharesRec> portfolio) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.address = address;
		this.portfolio = portfolio;
	}

	public CustomerRec(String ssn, String name, String address) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.address = address;
	}

	public CustomerRec() {
		super();
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Vector<SharesRec> getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Vector<SharesRec> portfolio) {
		this.portfolio = portfolio;
	}

	@Override
	public String toString() {
		return "CustomerRec [ssn=" + ssn + ", name=" + name + ", address=" + address + ", portfolio=" + portfolio + "]";
	}
	
	
}

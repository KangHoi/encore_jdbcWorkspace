package broker.three.shares;

import java.io.Serializable;

public class Command implements Serializable{
	//비지니스 로직이름 - - - 숫자로 매핑..
	public static final int BUYSHARES = 10;
	public static final int SELLSHARES = 20;
	public static final int GETALLSTOCK = 30;
	public static final int GETSTOCKPRICE = 40;
	public static final int GETALLCUSTOMER = 50;
	public static final int GETCUSTOMER = 60;
	public static final int ADDCUSTOMER = 70;
	public static final int DELETECUSTOMER = 80;
	public static final int UPDATECUSTOMER = 90;
	
	private int commandValue;
	private String[ ] args;
	
	// 추가
	private Result results;

	public Command(int commandValue) {		
		this.commandValue = commandValue;
		results = new Result(); //생성... command에 빈통 하나 주입...
	}
	
	public int getCommandValue() {
		return commandValue;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public void setCommandValue(int commandValue) {
		this.commandValue = commandValue;
	}	
	
	public Result getResults() {
		return results;
	}

	public void setResults(Result results) {
		this.results = results;
	}
	
}







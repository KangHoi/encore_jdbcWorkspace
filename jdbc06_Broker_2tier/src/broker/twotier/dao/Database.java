package broker.twotier.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;


import broker.twotier.exception.DuplicateSSNException;
import broker.twotier.exception.InvalidTransactionException;
import broker.twotier.exception.RecordNotFoundException;
import broker.twotier.vo.CustomerRec;
import broker.twotier.vo.SharesRec;
import broker.twotier.vo.StockRec;
import config.ServerInfo;

public  class Database implements DatabaseTemplate{
	public Database(String serverIp) throws ClassNotFoundException{
		Class.forName(ServerInfo.DRIVER);
		System.out.println("드라이버 로딩 성공...");
	}
	
	@Override
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("Database Connection...");
		return conn;
	}

	@Override
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if(ps != null) ps.close();
		if(conn != null) conn.close();
	}

	@Override
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if(rs != null) rs.close();
		closeAll(ps,conn);
		
	}


	
	//ssn이 있는지 없는지 확인하는 기능.. 하나 추가.. isIsbn()과 동일한 기능
	public boolean isExist(String ssn, Connection conn) throws SQLException{
		String sql = "SELECT SSN FROM CUSTOMER WHERE SSN=?";
		 PreparedStatement ps = conn.prepareStatement(sql);
		 ps.setNString(1,ssn);
		 ResultSet rs = ps.executeQuery();
		 return rs.next();	 
	}
	
	@Override
	public void addCustomer(CustomerRec cust) throws SQLException, DuplicateSSNException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			if(!isExist(cust.getSsn(), conn)) {// 추가하려는 고객의 ssn이 없다면
				String query = "INSERT INTO CUSTOMER(SSN, CUST_NAME, ADDRESS) VALUES(?,?,?)";
				ps = conn.prepareStatement(query);
				
				ps.setString(1, cust.getSsn());
				ps.setString(2, cust.getName());
				ps.setString(3, cust.getAddress());
				
				System.out.println(ps.executeUpdate() + "명 INSERT OK...addCustomer");
			}else {
				throw new DuplicateSSNException();
			}
		}finally {
			closeAll(ps,conn);
		}
		
	}

	@Override
	public void deleteCustomer(String ssn) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		//왜래키를 지정을 제약조건에 추가.. customer테이블에서 삭제 하게되면 연결된 ssn이 shares테이블에서도 자동 삭제될 것이다
		try {
			conn = getConnect();
			if(isExist(ssn, conn)) {
				String query = "DELETE FROM CUSTOMER WHERE SSN=?";
				ps = conn.prepareStatement(query);
		
				ps.setString(1, ssn);

				
				System.out.println(ps.executeUpdate() + "명 DELETE OK... deleteCustomer()...");
				
			}
		}finally {
			closeAll(ps,conn);
		}
		
	}

	@Override
	public void updateCustomer(CustomerRec cust) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "UPDATE CUSTOMER SET CUST_NAME=?, ADDRESS=? WHERE SSN=?";
			
			ps = conn.prepareStatement(query);
			ps.setString(1, cust.getName());
			ps.setString(2, cust.getAddress());
			ps.setString(3, cust.getSsn());
			
			int row = ps.executeUpdate();
			if(row==1) System.out.println(row + "명 UPDATE OK... updateCustomer()...");
		}finally {
			closeAll(ps,conn);
		}
		
	}
	@Override
	public Vector<SharesRec> getPortfolio(String ssn) throws SQLException {
		Vector<SharesRec> v= new Vector<SharesRec>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
		
			String query = "SELECT SSN, SYMBOL, QUANTITY FROM SHARES WHERE SSN=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, ssn);
			rs = ps.executeQuery();
			while(rs.next()) {
				v.add(new SharesRec(ssn,
						rs.getString("symbol"),
						rs.getInt("quantity")));
			}

		}
		finally{
			closeAll(ps,conn);
		}
		return v;
	}

	@Override
	public CustomerRec getCustomer(String ssn) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CustomerRec cust = null;
		
		try {
			conn = getConnect();
			
			String query = "SELECT SSN, CUST_NAME, ADDRESS FROM CUSTOMER WHERE SSN=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, ssn);
			
			rs = ps.executeQuery();
			if(rs.next()) { //ssn에 해당하는 고객이 있다면
				cust = new CustomerRec(ssn, 
						rs.getString("cust_name"),
						rs.getString("address"));
			}//if
			cust.setPortfolio(getPortfolio(ssn));
			
		}finally {
			closeAll(rs,ps,conn);
			
		}
		return cust;
	}

	@Override
	public ArrayList<CustomerRec> getAllCustomers() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CustomerRec> list = new ArrayList<CustomerRec>();
		try {
			conn = getConnect();
			
			String query = "SELECT SSN, CUST_NAME, ADDRESS FROM CUSTOMER";
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new CustomerRec(rs.getString("ssn"),
						rs.getString("cust_name"), 
						rs.getString("address"), 
						getPortfolio(rs.getString("ssn"))));
				} 
				
			}finally{
				closeAll(rs,ps,conn);
			}
		
		return list;
	}


	@Override
	public ArrayList<StockRec> getAllStocks() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<StockRec> list = new ArrayList<StockRec>();
		try {
			conn = getConnect();
			
			String query = "SELECT SYMBOL, PRICE FROM STOCK";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new StockRec(rs.getString(1),
						rs.getFloat(2)));
			}
			
		} finally {
			closeAll(rs,ps,conn);
		}
		return list;
	}
	
	
/*
 * 누가 어떤 주식을 몇 개 살지를 정의하는 기능...
 * 
 * 지금 가지고 있는 주식의 개수 (quantity)부터 확인해봐야 한다.
 * 
 * 내가 현재 주식을 가지고 있지 않다. 0, 100 --> insert into 100
 * 내가 현재 어느 정도의 주식을 가지고 있다 50, 100 --> update 150
 */
	@Override
	public void buyShares(String ssn, String symbol, int quantity) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			
			String query = "SELECT QUANTITY FROM SHARES WHERE SSN=? AND SYMBOL=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, ssn);
			ps.setString(2, symbol);
			
			rs = ps.executeQuery();//주식 보유량 가진 resultset리턴
			if(rs.next()) {
				int q = rs.getInt(1); //q는 현재 가지고 있는 주식의 수량
				int newQuantity = q + quantity; // | q(50) + quantity(100)
				
				//UPDATE
				String query1 = "UPDATE SHARES SET QUANTITY=? WHERE SSN=? AND SYMBOL=?";
				ps= conn.prepareStatement(query1);
				ps.setInt(1, newQuantity);
				ps.setString(2,ssn);
				ps.setString(3, symbol);
				
			}else {//q(0) + quantity(100)
				//INSERT
				String query2 = "INSET INTO SHARES(SSN, SYMBOL, QUANTITY) VALUES(?,?,?)";
				ps= conn.prepareStatement(query2);
				ps.setString(1, ssn);
				ps.setString(2,symbol);
				ps.setInt(3, quantity);
				
				System.out.println(ps.executeUpdate() + " row buyShares().. INSERT OK...");
			}
		} finally {
			closeAll(rs, ps,conn);
		}

	}

	/*
	 * 누가 어떤 주식을 몇개 팔 것인가에 대한 기능을 정의...
	 * 현재 가지고 잇는 주식의 수량을 먼저 알아야 한다... INT q
	 * 
	 * 1) 100개을 가지고 있다... 50개 sell ... update
	 * 2) 100개를 가지고 있다... 100개 sell ... delete
	 * 3) 100개를 가지고 있다... 200개 sell ... invalidTransactionException 펑
	 * 4) 판매하려는 주식이 없을 때 ............. RecordNotFoundException 펑
	 */
	@Override
	public void sellShares(String ssn, String symbol, int quantity) throws SQLException, InvalidTransactionException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			
			String query = "SELECT QUANTITY FROM  SHARES WHERE SSN=? AND SYMBOL=?";
			ps= conn.prepareStatement(query);
			ps.setString(1, ssn);
			ps.setString(2, symbol);
			
			rs = ps.executeQuery();
			rs.next(); //커서를 한 단계 아래로 내려 엘리먼트를 가리키게 하고 수량을 받아올 준비함
			
			
			int q = rs.getInt(1);// 현재 가지고 있는 수량 100
			int newQuantity = q - quantity;
			if(q !=0) {
				if(q == quantity) {
					String query1 = "DELETE FROM SHARES WHERE SSN=? AND SYMBOL=?";
					ps = conn.prepareStatement(query1);
					ps.setString(1, ssn);
					ps.setString(2, symbol);
					
					System.out.println(ps.executeUpdate() + " row SHARES DELETE ... sellshares()...");
				
				}else if(q>quantity) {
					String query2 = "UPDATE SHARES SET QUANTITY=? WHARE SSN =? AND SYMBOL=?";
					ps = conn.prepareStatement(query2);
					ps.setInt(1, newQuantity);
					ps.setString(2, ssn);
					ps.setString(3, symbol);
					
					System.out.println(ps.executeUpdate() + "row SHARES DELETE ... SELLSHARES()1...");
				}else{
					throw new InvalidTransactionException();
				}
		}else {
			throw new RecordNotFoundException();
		}
		
		}finally {
			closeAll(rs,ps,conn);
		}
		
	}
	

}

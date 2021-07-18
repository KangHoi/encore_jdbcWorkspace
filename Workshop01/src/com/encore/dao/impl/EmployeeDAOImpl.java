package com.encore.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.encore.dao.EmployeeDAO;
import com.encore.vo.Employee;

import config.ServerInfo;

public class EmployeeDAOImpl implements EmployeeDAO{

	private static Connection conn;
	public static Connection getConnect() throws SQLException{
		if(conn ==null) {
		conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("디비 서버 연결...");
		}
		return conn;
	}
	
	public static void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if(ps!=null) ps.close();
		if(conn!=null) conn.close(); //연 순서의 반대로 닫아주어야한다
	}
	
	public static void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if(rs!=null) rs.close();
		closeAll(ps,conn);
	}
	
	private static EmployeeDAOImpl dao = new EmployeeDAOImpl();
	private EmployeeDAOImpl() {
		
	}
	public static EmployeeDAOImpl getInstance() {
		return dao;
	}
	
	
	
	public static void insertEmp(Employee e) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = getConnect();
		
		String query = "insert into employee values(?,?,?,?)";
		
		ps = conn.prepareStatement(query);
		System.out.println("PreparedStatement 객체 생성...");
		
		ps.setInt(1, e.getNum());
		ps.setString(2, e.getName());
		ps.setDouble(3, e.getSalary());
		ps.setString(4, e.getAddress());
		
		System.out.println(ps.executeUpdate() + "명 추가되었습니다.");
		
		closeAll(ps, conn);
	}
	
	public static void removeEmp(int num) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
	
		conn = getConnect();
		
		String query = "delete from employee where num=?";
		ps = conn.prepareStatement(query);
		System.out.println("PreparedStatement 객체 삭제...");
		
		ps.setInt(1, num);
		System.out.println(ps.executeUpdate() + "명 삭제되었습니다.");
		
		closeAll(ps, conn);
	}
	
	public static void updateEmp(Employee e) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = getConnect();
		
		String query = "update employee set name=?, salary=?, address=? where num=?";
		ps = conn.prepareStatement(query);
		System.out.println("PreparedStatement 객체 변경...");
		
		ps.setString(1, e.getName());
		ps.setDouble(2, e.getSalary());
		ps.setString(3, e.getAddress());
		ps.setInt(4, e.getNum());
		
		System.out.println(ps.executeUpdate() + "명 변경되었습니다.");
		
		closeAll(ps, conn);
	}
	
	public static ArrayList<Employee> selectAll() throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Employee> list = new ArrayList<Employee>();
		
		conn = getConnect();
		
		String query = "select num, name, salary, address from employee";
		ps = conn.prepareStatement(query);
		System.out.println("PreparedStatement 객체 불러오기...");
		
		rs =ps. executeQuery();
		while(rs.next()) {
			list.add(new Employee(rs.getInt("num"),rs.getString("name"),rs.getDouble("salary"), rs.getString("address") ));
	}
		closeAll(rs,ps,conn);
		return list;
	}
}

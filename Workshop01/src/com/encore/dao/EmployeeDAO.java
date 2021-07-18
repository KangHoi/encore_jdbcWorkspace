package com.encore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.encore.vo.Employee;
import config.ServerInfo;

public interface EmployeeDAO {
	
	public static Connection getConnect() throws SQLException {
		return null;
	}
	
	public static void closeAll(PreparedStatement ps, Connection conn) throws SQLException{
	}
	
	public static void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {	
	}
	
	public static void insertEmp(Employee e) throws SQLException {	
	}
	
	public static void removeEmp(int num) throws SQLException{	
	}
	
	public static void updateEmp(Employee e) throws SQLException{
	}
	
	public static ArrayList<Employee> selectAll() throws SQLException{
		return null;
	}
}

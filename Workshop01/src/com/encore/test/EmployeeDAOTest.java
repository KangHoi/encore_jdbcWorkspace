package com.encore.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.encore.dao.EmployeeDAO;
import com.encore.dao.impl.EmployeeDAOImpl;
import com.encore.vo.Employee;

import config.ServerInfo;

public class EmployeeDAOTest {
	
	
	private Connection getConnection() {
		return null;
	}
	private void insertEmp(Employee emp) {}
	private void removeEmp(int num) {}
	private void updateEmp(Employee emp) {}
	ArrayList<Employee>list = new ArrayList<Employee>(); 



	public static void main(String[] args)  throws ClassNotFoundException, SQLException {

			Class.forName(ServerInfo.DRIVER);
			System.out.println("Driver Loading...");
			
			Connection conn = EmployeeDAOImpl.getConnect();
			EmployeeDAOTest jdbc = new EmployeeDAOTest();
	
//			EmployeeDAOImpl.insertEmp(new Employee(1,"James", 5500.0, "Texas"));
//			EmployeeDAOImpl.insertEmp(new Employee(2,"Jenny", 7500.0, "LA"));
//			EmployeeDAOImpl.updateEmp(new Employee(2, "John", 6000.0, "Otawa"));		
//			EmployeeDAOImpl.removeEmp(1);
			ArrayList<Employee> returnList = EmployeeDAOImpl.selectAll();
			for(Employee e : returnList) System.out.println(e);
	}




	



	}

	




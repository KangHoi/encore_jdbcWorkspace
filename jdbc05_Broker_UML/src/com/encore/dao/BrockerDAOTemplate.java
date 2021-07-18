package com.encore.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.encore.vo.Customer;
import com.encore.vo.Stock;

public interface BrockerDAOTemplate {
	void registerCustomer(Customer vo) throws SQLException;
	void buyingStock(String stockCode, String CusName) throws SQLException;
	void sellingStock(String stockCode, String CusName) throws SQLException;
	ArrayList<Stock> findbyStockCode(String stockCode) throws SQLException;
	ArrayList<Stock> printAllStock() throws SQLException;
}

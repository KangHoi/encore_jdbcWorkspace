package com.encore.dao.impl;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.encore.dao.BookDAO;
import com.encore.exception.BookNotFoundException;
import com.encore.exception.DuplicateISBNException;
import com.encore.exception.InvalidInputException;
import com.encore.vo.Book;

import config.ServerInfo;

public class BookDAOImpl implements BookDAO{
	
	private static BookDAOImpl dao = new BookDAOImpl();
	private BookDAOImpl() {
		
	}
	public static BookDAOImpl getInstance() {
		return dao;
	}

	//공통적 로직... Connection getConnect()
	//공통적 로직... closeAll()... 오버로딩 기법
	
	public static Connection getConnect() throws SQLException{
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("디비 서버 연결...");
		return conn;
	}
	public static void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();
	}
	
	public static void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if(rs!=null) rs.close();
		closeAll(ps,conn);
	}
	

	public boolean isIsbn(String isbn, Connection conn) throws SQLException{
		String query = "SELECT ISBN FROM BOOK WHERE ISBN=?";
		 PreparedStatement ps = conn.prepareStatement(query);
		 ps.setNString(1,isbn);
		 ResultSet rs = ps.executeQuery();
		 return rs.next();	 
	}
	
	@Override
	public void registerBook(Book vo) throws SQLException, DuplicateISBNException{
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getConnect();
			if(!isIsbn(vo.getIsbn(),conn)){
				System.out.println("true...");
				String query = "insert into book values(?,?,?,?,?)";
				ps = conn.prepareStatement(query);
				
				ps.setString(1, vo.getIsbn());
				ps.setString(2, vo.getTitle());
				ps.setString(3, vo.getWriter());
				ps.setString(4, vo.getPublisher());
				ps.setInt(5, vo.getPrice());
				
				System.out.println(ps.executeUpdate() + "row register!!!");
			} else { //isbn에 해당하는 책이 있다면...
				throw new DuplicateISBNException();
			}
			}finally {
				closeAll(ps,conn);
			}

	}

	@Override
	public void deleteBook(String isbn) throws SQLException, BookNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "DELETE FROM BOOK WHERE ISBN=?";
			ps = conn.prepareStatement(query);
			
			ps.setString(1,isbn);
			int row = ps.executeUpdate();
			if(row == 0) throw new BookNotFoundException();
			else System.out.println(row + "ROW delete!!!");
		} finally {
			closeAll(ps,conn);
		}
		
		
	}

	@Override
	public Book findByBook(String isbn, String title) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		Book book = null;
		ArrayList<Book> List = new ArrayList<Book>();
		
		conn = getConnect();
		String query = "select * from book where isbn=? and title=?";
		ps = conn.prepareStatement(query);
		System.out.println("PreparedStatement 객체 검색...");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			List.add(new Book(rs.getString("isbn"), rs.getString("title"), rs.getString("writer"), rs.getString("publisher"), rs.getInt("price")));
		}
		ps.setString(1, isbn);
		ps.setString(2, title);
		System.out.println(ps.executeUpdate() + "권 검색되었습니다..");
		
		closeAll(ps, conn);
		return book;
		
	}

	@Override
	public ArrayList<Book> findByWriter(String writer) throws SQLException {
		ArrayList<Book> list = new ArrayList<Book>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnect();
			String query = "SELECT *FROM BOOK WHERE AUTHOR=?";
			ps = conn.prepareStatement(query);
			
			ps.setString(1, writer);
			rs = ps.executeQuery();
			while(rs.next()) {
				Book vo = new Book(
						rs.getString(1), 
						rs.getString(2), 
						writer, 
						rs.getString(4), 
						rs.getInt(5));
		list.add(vo);
			}			
		}finally {
			closeAll(rs,ps,conn);
		}
		return list;
	}


	@Override
	public ArrayList<Book> findByPublisher(String publisher) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Book> list = new ArrayList<Book>();
		
		conn = getConnect();
		String query = "select * from book where publisher=?";
		ps = conn.prepareStatement(query);
		System.out.println("PreparedStatement 객체 검색...");
		
		ps.setString(1,publisher);
		rs = ps.executeQuery();
		while(rs.next()) {
			list.add(new Book(rs.getString("isbn"), rs.getString("title"), rs.getString("author"),rs.getString("publisher"), rs.getInt("price")));
		}
		closeAll(rs,ps,conn);
		return list;
	}

	@Override
	public ArrayList<Book> findByPrice(int min, int max) throws SQLException,InvalidInputException {
		if(min<=0 || min<0 || min>=max) throw new InvalidInputException();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Book> list = new ArrayList<Book>();
		
		try {
			conn = getConnect();
			String query = "SELECT * FROM BOOK WHERE PRICE >= ? AND PRICE <= ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, min);
			ps.setInt(2, max);
			rs = ps.executeQuery();
			while(rs.next()) {
				Book vo = new Book(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5));
				list.add(vo);
			}
		}finally {
			closeAll(rs,ps,conn);
		}
		return list;
	}


	@Override
	public void discountBook(int per, String publisher) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getConnect();
			String query = "UPDATE BOOK SET PRICE = (PRICE * (1-?/100)) WHERE PUBLISHER=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, per);
			ps.setString(2, publisher);
			System.out.println(ps.executeUpdate() + " row price update");
			
		}finally {
			closeAll(ps,conn);
		}
	}

	@Override
	public ArrayList<Book> printAllInfo() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Book> list = new ArrayList<Book>();
		
		conn = getConnect();
		
		String query = "select * from book";
		ps = conn.prepareStatement(query);
		System.out.println("PreparedStatement 객체 불러오기...");
		
		rs =ps. executeQuery();
		while(rs.next()) {
			list.add(new Book(rs.getString("isbn"),rs.getString("title"),rs.getString("writer"), rs.getString("publisher"), rs.getInt("price") ));
	}
		closeAll(rs,ps,conn);
		return list;
	
	}
		
	}


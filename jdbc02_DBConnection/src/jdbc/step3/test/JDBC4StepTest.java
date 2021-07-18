package jdbc.step3.test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import config.ServerInfo;

/*
 * DB Access하기 위한 4단계 작업을 작성하는 로직..
 * 1. DB서버에 대한 파편적인 정보들(서버 실제값)을 상수로 지정
 * ------->
 * 문제점
 * 소스코드에 서버정보가 그대로 노출되어져 있다.
 * 메타데이터화 시키자
 * ::
 * 2. 상수값과 추상메소드를 구성요소로 가지는 인터페이스를 별도의 모듈로 생성하고
 *    그 안에 서버 정보를 메타데이터화 시킨다.
 * ---->
 * 3. Java 진영에서 가장 많이 사용하는 Meta Data는 
 * 	  Properties 파일이다.
 * 	  key - value 갑슬 모두 스트링을 저장할 수 있다는 특징에 착안되어
 * 	  서버정보를 저장하는 메타데이터로 가장 많이 사용되어진다.
 * 	  config < db.properties
 * 
 */

public class JDBC4StepTest {
	static String driver;
	static String url;
	static String user;
	static String pass;
	static String query;
	
	public JDBC4StepTest() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		System.out.println("1. 드라이버 로딩 성공..");
		
		Connection conn = DriverManager.getConnection(url, user, pass); //Connection으로 꼭 잡아주기
		System.out.println("2.DB 연결 성공..");
		
		//mytable에 있는 모든 정보 다 가져와 출력
		PreparedStatement ps3 = conn.prepareStatement(query);
		
		ResultSet rs = ps3.executeQuery();
		System.out.println("\n=======================================================");
		while(rs.next()) {
			System.out.println(rs.getInt("num") + "\t"
								+rs.getString("name")+"\t"
								+rs.getString("address"));
		
		}
		System.out.println("=======================================================");
		
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		new JDBC4StepTest();

	}
	static {
		//1. properties파일의 내용을 로드해온다.
	try {
		Properties p = new Properties();
		p.load(new FileInputStream("src/config/db.properties")); //절대경로보다 상대경로가 portable하다
		
		driver = p.getProperty("jdbc.mysql.driver");
		url = p.getProperty("jdbc.mysql.url");
		user = p.getProperty("jdbc.mysql.user");
		pass = p.getProperty("jdbc.mysql.pass");
		query = p.getProperty("jdbc.sql.selectAll");
	
		
	} catch(Exception e) {
		
	}
}
}

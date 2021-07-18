package jdbc.step1.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * DB Access하기 위한 4단계 작업을 작성하는 로직..
 * 1. DB서버에 대한 파편적인 정보들(서버 실제값)을 상수로 지정
 */

public class JDBC4StepTest {
	public static String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static String URL = "jdbc:mysql://127.0.0.1:3306/scott?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
	public static String USER = "root";
	public static String PASSWORD = "1234";
	
	public JDBC4StepTest() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		System.out.println("1. 드라이버 로딩 성공..");
		
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); //Connection으로 꼭 잡아주기
		System.out.println("2.DB 연결 성공..");
		
		String query = "insert into mytable(num,name,address) values(?,?,?)";
		PreparedStatement ps = conn.prepareStatement(query);
		System.out.println("3. PreparedStatement 생성..");
		
		ps.setInt(1,333);
		ps.setString(2, "박나래");
		ps.setString(3, "여의도");//값이 물음표에 바인딩 되어진다. 들어가는 것 아님
	
		// 4단계
		int row = ps.executeUpdate();
		System.out.println(row + " row insert... ok");
		
		String querydel = "delete from mytable where num=?";
		PreparedStatement ps1 = conn.prepareStatement(querydel);
		System.out.println("3. PreparedStatement 생성..");
		
		ps1.setInt(1, 333);
		System.out.println(ps1.executeUpdate() + " row delete...ok");
		
		//update문 실행... 111인 사람의 이름과 주소 변경
		String queryUpdate = "update mytable set name=?, address=? where num=?";
		PreparedStatement ps2 = conn.prepareStatement(queryUpdate);
		
		ps2.setString(1,"정우재");
		ps2.setString(2,"방배동");
		ps2.setInt(3,111);
		
		System.out.println(ps2.executeUpdate() + " row update...ok");
		
		//mytable에 있는 모든 정보 다 가져와 출력
		String querySel = "select num, name, address from mytable";
		PreparedStatement ps3 = conn.prepareStatement(querySel);
		
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

}

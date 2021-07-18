package jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/*
 * DB 서버 정보에 해당하는 것들을 전부 상수값으로 세팅
 * 1. 드라이버 FQCN ---- 이 부분 먼저 메모리에 로딩된 다음 나머지 일들 일어나야 한다.
 * 2. 서버 주소, 계정 이름, 비번...
 * 
 */
public class DBConnectionTest2 {
	public static String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static String URL = "jdbc:mysql://127.0.0.1:3306/scott?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
	public static String USER = "root";
	public static String PASSWORD = "1234";
	public DBConnectionTest2() {
		//jdbc 기본 작업 진행... 2,3,4 단계 작업 진행
		try {
			//2.
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("DB server 연결 성공~");
			
			//3.
			String insertQuery = "INSERT INTO mytable (num, name, address) VALUES(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(insertQuery);
			System.out.println("PreparedStatement 생성~");
			
			//4. 값 바인딩 및 쿼리문 실행
			ps.setInt(1, 111);//첫번째 물음표에 111입력
			ps.setString(2, "박나래");//두번째 물음표에 박나래 입력
			ps.setString(3,"여의도");//세번째 물음표에 여의도 입력
			
			int row = ps.executeUpdate(); //업데이트된 (추가) row의 갯수 반환
			System.out.println(row + "명 추가됨!");
			
			String deleteQuery = "DELETE FROM mytable WHERE name=?";
			PreparedStatement ps2 = conn.prepareStatement(deleteQuery);
			ps2.setString(1,"박나래");
			System.out.println(ps2.executeUpdate() + "명 삭제됨!!");
		}catch(Exception e) {
			System.out.println("DB server 연결 실패~");
			
		}
	}

	public static void main(String[] args) {
		new DBConnectionTest2();

	}
	static {
		//1. 드라이버 로딩 작업
		try {
			Class.forName(DRIVER);
			System.out.println("Driver 로딩 성공~");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver 로딩 실패~");
		}

	}

}

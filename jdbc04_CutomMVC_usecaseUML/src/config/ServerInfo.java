package config;

public interface ServerInfo {
	String DRIVER = "com.mysql.cj.jdbc.Driver"; 
	String URL = "jdbc:mysql://127.0.0.1:3306/scott?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
	String USER = "root";
	String PASSWORD = "1234";
}

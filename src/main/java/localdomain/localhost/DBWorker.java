package localdomain.localhost;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBWorker {
	
	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://ec2-50-19-213-178.compute-1.amazonaws.com:3306/acronge";

	//  Database credentials
	static final String USER = "AcroNGE";
	static final String PASS = "dfdf9f2f4c6740fdb7d190c440ff8fe5";
	
	private ResultSet Execute(String query) throws SQLException, NamingException{return Execute(query, false);}
	
	private ResultSet Execute(String query, boolean noReturn) throws SQLException, NamingException{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/mydb");
		Connection conn = ds.getConnection();
		Statement stmt = conn.createStatement();
		if(noReturn){
			stmt.executeUpdate(query);
			return null;
		}
		else{
			ResultSet rst = stmt.executeQuery(query);
			return rst;	
		}
	}
	
	private String CreateQuery(String name){
		switch (name) {
		case "TryReg":
			return "CREATE TABLE TryReg (id int AUTO_INCREMENT PRIMARY KEY NOT NULL, email varchar(50) UNIQUE NOT NULL, regtime date NOT NULL, count int NOT NULL);";

		case "Users":
			return "CREATE TABLE Users (userid int AUTO_INCREMENT PRIMARY KEY NOT NULL, email varchar(50) UNIQUE NOT NULL, login varchar(25) UNIQUE NOT NULL, password varchar(16) NOT NULL);";

		case "Logon":
			return "CREATE TABLE Logon (userid int PRIMARY KEY UNIQUE, code varchar(25) NOT NULL, logtime date NOT NULL);";
		
		}
		return "";
	}
	
	public String DBcreate(String name){
		try {
			Execute(CreateQuery(name), true);
			System.out.println("database created successful");
		} catch (SQLException e) {
			e.printStackTrace();
			return "SQLException" + e.getMessage();
		} catch (NamingException e) {
			e.printStackTrace();
			return "NamingException" + e.getMessage();
		}
		return "DB " + name + " created!";
	}
	
	/*public boolean ExistEmail(String email) throws SQLException, NamingException{
		ResultSet rs = Execute("SELECT userid FROM Users WHERE email=" + email);
		rs.beforeFirst();
		return rs.next();
	}
	
	public boolean ExistTryEmail(String email) throws SQLException, NamingException{
		ResultSet rs = Execute("SELECT * FROM TryReg WHERE email=" + email);
		if(rs){
			
		}
		else{
			return false;
		}
	}*/

}

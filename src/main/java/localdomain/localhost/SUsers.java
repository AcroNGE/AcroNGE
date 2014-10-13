package localdomain.localhost;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SUsers extends TableDBS {
	
	protected String login;
	protected String password;
	protected String newpass;

	public SUsers(DBSAction dbSaction) {
		super(dbSaction);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getAttribute(String att) {
		switch (att) {
		case "email":
			return email;
			
		case "login":
			return login;

		case "password":
			return password;

		case "newpass":
			return newpass;

		default:
			return "";
		}
	}
	
	@Override
	public boolean setAttribute(String att, String value) {
		switch (att) {
		case "email":
			email = value;
			return true;

		case "login":
			login = value;
			return true;

		case "password":
			password = value;
			return true;

		case "newpass":
			newpass = value;
			return true;

		default:
			return false;
		}
	}
	
	private int firstNunber(int a, int b){
		return a * 3 + b + 1;
	}
	
	@SuppressWarnings("unused")
	private String enCodingPass(String pass){
		String cp = "";
		if(pass.length() % 2 == 1) cp += pass.charAt(pass.length() - 1);
		int k = pass.length() - 2;
		char a, b;
		for(int i = 0; i < k; i+=2){
			a = pass.charAt(i);
			b = pass.charAt(i + 1);
			cp += firstNunber(a / 100, b / 100) + (a % 100) + (b % 100);
		}
		return cp;
	}
	
	@Override
	public boolean Done(Statement statement) throws SQLException {
		switch (action) {
		case EE:			
			return ExistEmail(statement);

		case EU:			
			return ExistUser(statement);

		case AU:			
			return AddUser(statement);

		case CU:			
			return CheckUser(statement);

		case CP:			
			return ChangePass(statement);

		default:
			return false;
		}
	}
	
	private boolean ExistEmail(Statement statement) throws SQLException{
		ResultSet rs = statement.executeQuery("SELECT userid FROM Users WHERE email='" + email + "';");
		rs.beforeFirst();
		return rs.next();
	}
	
	private boolean ExistUser(Statement statement) throws SQLException{
		ResultSet rs = statement.executeQuery("SELECT userid FROM Users WHERE login='" + login + "';");
		rs.beforeFirst();
		return rs.next();
	}
	
	private boolean AddUser(Statement statement) throws SQLException{
		if(ExistEmail(statement)) return false;
		if(ExistUser(statement)) return false;
		return statement.executeUpdate("INSERT INTO Users (email, login, password) VALUES('" + email + "', '" + login + "', '" + password + ");") == 1;
	}
	
	private boolean CheckUser(Statement statement) throws SQLException{
		ResultSet rs = statement.executeQuery("SELECT email, password FROM Users WHERE email='" + email + "';");
		rs.beforeFirst();
		while (rs.next()) {
			return rs.getString("password") == password;
		}
		return false;
	}
	
	private boolean ChangePass(Statement statement) throws SQLException{
		if(CheckUser(statement)){
			return statement.executeUpdate("UPDATE TABLE Users VALUES(password='" + password + "') WHERE email='" + email + "';") == 1;
		}
		else{
			return false;
		}
	}

}

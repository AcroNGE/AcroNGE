package localdomain.localhost;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class SLogOn extends TableDBS {
	
	protected String password;
	protected String uid;
	protected String code;

	public SLogOn(DBSAction dbSaction) {
		super(dbSaction);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getAttribute(String att) {
		switch (att) {
		case "email":
			return email;

		case "password":
			return password;

		case "id":
			return uid;

		case "code":
			return code;

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

		case "password":
			password = value;
			return true;

		case "id":
			uid = value;
			return true;

		case "code":
			code = value;
			return true;

		default:
			return false;
		}
	}
	
	@Override
	public boolean Done(Statement statement) throws SQLException {
		switch (action) {
		case LN:
			return LogingOn(statement);

		case LF:
			return LogingOff(statement);

		case CL:
			return CheckLoging(statement);

		default:
			return false;
		}
	}
	
	private boolean LogingOn(Statement statement) throws SQLException{
		ResultSet rs = statement.executeQuery("SELECT email, userid, password FROM Users WHERE email='" + email + "';");
		rs.beforeFirst();
		while (rs.next()) {
			if(rs.getString("password") == password){
				uid = Integer.toString(rs.getInt("userid"));
				rs.close();
				rs = statement.executeQuery("SELECT userid FROM Logon WHERE userid=" + uid + ";");
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(System.currentTimeMillis());
				code = generatecode(25);
				rs.beforeFirst();
				while(rs.next()){
					if(Integer.toString(rs.getInt("userid")) == uid){
						statement.executeUpdate("UPDATE TABLE Logon VALUES (code='" + code + "', logtime=" + c.getTime() + ") WHERE userid=" +uid + ";");
						return true;
					}
				}
				statement.executeUpdate("INSERT INTO Logon (userid, code, logtime) VALUES (" + uid + ", " + code + ", " + c.getTime() + ");");
				return true;
			}
		}
		return false;
	}
	
	private boolean LogingOff(Statement statement) throws SQLException {
		statement.executeUpdate("DELETE FROM Logon WHERE userid=" + uid + ";");
		return true;
	}
	
	private boolean CheckLoging(Statement statement) throws SQLException{
		ResultSet rs = statement.executeQuery("SELECT userid, code FROM Logon WHERE userid=" + uid + ";");
		rs.beforeFirst();
		while (rs.next()) {
			if(rs.getInt("userid") == Integer.parseInt(uid)){
				return rs.getString("code") == code;
			}
		}
		return false;
	}

}

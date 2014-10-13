package localdomain.localhost;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class STryReg extends TableDBS {

	protected String code;
	
	public STryReg(DBSAction dbSaction) {
		super(dbSaction);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getAttribute(String att) {
		switch (att) {
		case "email":
			return email;
			
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
		case EE:
			return ExistTryEmail(statement);

		case AE:
			return AddTryEmail(statement);

		case CE:
			return CheckTryEmail(statement);

		case RE:
			return RemoveTryEmail(statement);

		default:
			return false;
		}
	}
	
	private boolean ExistTryEmail(Statement statement) throws SQLException{
		ResultSet rs = statement.executeQuery("SELECT * FROM TryReg WHERE email='" + email + "';");
		rs.beforeFirst();
		while(rs.next()){
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			c.add(Calendar.DATE, rs.getInt("count"));
			if(rs.getDate("regtime").before(c.getTime())){
				return true;
			}
		}
		return false;
	}
	
	private boolean AddTryEmail(Statement statement) throws SQLException{
		if(ExistTryEmail(statement)) return false;
		ResultSet rs = statement.executeQuery("SELECT * FROM TryReg WHERE email='" + email + "';");
		rs.beforeFirst();
		//String code = generatecode(30);
		code = generatecode(30);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		if(rs.next()){
			statement.executeUpdate("UPDATE TryReg SET (count=" + (rs.getInt("count") + 1) + ", regtime=" + c.getTime() + ", code='" + code + "') WHERE email='" + email + "';");
		}
		else{
			statement.executeUpdate("INSERT INTO TryReg (email, regtime, code, count) VALUES('" + email + "', " + c.getTime() + ", '" + code + "', 0);");
		}
		//send message with code
		return SendMessage("registration@AcroNGE", email, "Registration", "Your registration code is: " + code);
	}
	
	private boolean SendMessage(String FROM, String TO, String SUBJECT, Object CONTENT){
		try{
			Context initCtx = new InitialContext();
			Session session = (Session) initCtx.lookup("java:comp/env/mail/SendGrid");
			
			Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            InternetAddress to[] = new InternetAddress[1];
            to[0] = new InternetAddress(TO);
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(SUBJECT);
            message.setContent(CONTENT, "text/plain");
            Transport.send(message);
			
			return true;
		}
		catch (Exception e){
			return false;
		}
	}

	private boolean CheckTryEmail(Statement statement) throws SQLException{
		ResultSet rs = statement.executeQuery("SELECT code FROM TryReg WHERE email='" + email + "';");
		return code == rs.getString("code");
	}
	
	private boolean RemoveTryEmail(Statement statement) throws SQLException{
		statement.executeUpdate("DELETE FROM TryReg WHERE email='" + email + "';");
		return true;
	}
	
}

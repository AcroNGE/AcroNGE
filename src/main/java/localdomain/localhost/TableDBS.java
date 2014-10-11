package localdomain.localhost;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


public class TableDBS {
	
	public enum DBSAction {EE, AE, CE, RE, EU, AU, CU, CP, LN, LF, CL};
	
	protected DBSAction action;
	protected String email;
	
	public TableDBS(DBSAction dbSaction){
		action = dbSaction;
	}
	
	protected String generatecode(int length){
        Random rnd = new Random();
        String s = "";
        for (int i = 0; i < 5; ++i)
        {
            s += (char)(rnd.nextInt(26) + 59);
        }
        s += System.currentTimeMillis();
        length -= s.length();
        for (; length > 0; --length)
        {
            s += (char)(rnd.nextInt(26) + 59);
        }
        return s;
    }
	
	public String getAttribute(String att){
		return "";
	}
	
	public boolean setAttribute(String att, String value){
		return false;
	}
	
	public boolean Done(Statement statement) throws SQLException{
		return false;
	}

}

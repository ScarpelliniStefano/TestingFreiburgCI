package libFST.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


public class FSTdatabaseAIMO {
	
	static Connection conn;

	public void IniziaConn() {
		conn = getConnection();

	}

	public Connection getConn() {
		if (conn==null){
			return null;
		}else {
			return conn;
		}
	}
	
	private Boolean connOpers() {
		if (conn!=null) 
			return true;
		else
			return false;
	}
	/**
	 * Connect to the database if it exists
	 * 
	 * @return Connection: a connection to the URL
	 */
	private static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			return DriverManager.getConnection(Constant.urlMed, Constant.userMed, Constant.passwordMed);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public Boolean checkAuthorization(String user,String psw) {
		this.IniziaConn();
		try {
			psw = Constant.toSha256(psw);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			
		}
		try {
			Statement s= conn.createStatement();
			ResultSet r=s.executeQuery("SELECT * FROM dataAIMO where (mail="+user+" AND psw="+psw+")");
			if(r.next())
				return true;
		} catch (SQLException e) {
			return false;
		}
		
		return null;
		
	}
	
	public boolean isAvailable() {
		return connOpers();
	}
	Doctor doc;
	public boolean insertDoc(Doctor d) {
		Statement s;
		doc=d;
		int ok=0;
		try {
			s = conn.createStatement();
			String update="INSERT INTO Doctor (code, mail, psw) VALUES (\""+d.getCode()+"\", \""+
								d.getMail()+"\"\""+d.getPswHashed()+"\")";
			ok = s.executeUpdate(update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ok>0) {
			
			return true;
		}else
			return false;
	}
	
	public boolean assignPatDoc(Patient p, Doctor d) {
		return d.assignP(p);
	}
	
	public Doctor getDoc() {
		return doc;
	}
}
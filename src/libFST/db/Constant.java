package libFST.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** some constants for the connection to the DB*/
public class Constant {
	
	

	public static String urlMed = "jdbc:mysql://125.156.12.31:8636/AIMOmed?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";

	//DB user
	public static String userMed = "FRTest";

	//DB pw
	public static String passwordMed = "Ghdgsi748bnsh"; 
	//public static String password = "CVnXeGxr"; //Server: CVnXeGxr
	
	protected static String toSha256(String password) throws NoSuchAlgorithmException
	{
		 MessageDigest md = MessageDigest.getInstance("SHA-256");
	     md.update(password.getBytes());
	     StringBuffer result = new StringBuffer();
	     for (byte byt : md.digest()) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
	     return result.toString();
	}
}

package libFST.db;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
	private String ID;
	private String mail;
	private String psw;
	private List<Patient> listaPat;
	public String getCode() {
		return ID;
	}
	public void setCode(String iD) {
		ID = iD;
	}
	public void Doctor() {
		ID="";
		mail="";
		psw="";
		listaPat=new ArrayList<Patient>();
	}
	public boolean setMail(String mail) {
		if (checkMail(mail)) {
			this.mail = mail;
			return true;
		}
		else return false;
	}
	private boolean checkMail(String mail2) {
		return true;
		
	}
	public String getMail() {
		return mail;
	}
	public void changePsw(String psw) {
		try {
			this.psw=Constant.toSha256(psw);
		} catch (NoSuchAlgorithmException e) {
			//
		}
	}
	public String getPswHashed() {
		return psw;
	}
	public Boolean checkPSW(String pswIgnote) {
		if (this.psw==pswIgnote){
			return true;
		}
		return false;
	}
	public boolean assignP(Patient p) {
		if(checkPatient(p.getCode())==null) {
			listaPat.add(p);
			return true;
		}
		return false;
	}
	public Patient checkPatient(int codeP) {
		for(Patient p:listaPat) {
			if(p.getCode()==codeP) {
				return p;
			}
		}
		return null;
		
	}
}

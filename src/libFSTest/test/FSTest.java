package libFSTest.test;

import java.awt.image.BufferedImage;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Random;

import javax.imageio.ImageIO;

import libFST.db.Doctor;
import libFST.db.FSTdatabaseAIMO;
import libFST.db.Patient;
import libFSTest.session.PestBase.CertifierStatus;
import libFSTest.draw.AngleCalculus;
import libFSTest.draw.GeneraImg;
import libFSTest.session.Test_session;
import libFSTest.session.Test_session.Result;
import libFSTest.test.*;

public class FSTest extends Observable {
	
	/** The result. */
	private static DatiGenerazione risultato=new DatiGenerazione();

	/** the tester. */
	public static Test_session testSession=null;
	
	static ByteArrayOutputStream imagebuffer = null;
	
	
	/**
	 * The Enum Scelta
	 */
	public enum Scelta {
		/** Passa */
		PASSA,
		/** Corretto */
		CORRETTO,
		/** Sbagliato */
		SBAGLIATO,
		/** Finisci */
		FINISCI,
	}
	
	public FSTest() {
		
		//
	}
	
		
			/**
		* funzione di generazione immagine del test di freiburg dati i parametri di generazione
		*
		* @param result    dati di generazione strutturati come DatiGenerazione
		* 
		* @return      Stream immagine
		*/		
	public static InputStream IniziaTest(DatiGenerazione result) {
		if(risultato.getAngolo()==-1) {
			risultato=result;
		}
		else {
			risultato.setXBar(risultato.getLivMax());
			risultato.setPos(false);
		}
		
		testSession = new Test_session();
		Scelta inizio=Test_session.IniziaTest(risultato);
		if(inizio==Scelta.CORRETTO) {
			BufferedImage image=null;
			if(risultato.getDimensione().getWidth()==1) {
		        image=GeneraImg.GeneraImmagine(risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), risultato.getXBar(), risultato.getC1(), risultato.getC2());		
			}else {
				image=GeneraImg.GeneraImmagine(risultato.getDimensione().width,risultato.getDimensione().height,risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), risultato.getXBar(), risultato.getC1(), risultato.getC2());	
			}
			//prova di inserimento immagine nello stream
				try {
					// Write the image to a buffer
							imagebuffer = new ByteArrayOutputStream();
							ImageIO.write(image, "png", imagebuffer);
							// Return a stream from the buffer
							return new ByteArrayInputStream(imagebuffer.toByteArray());
				}catch(IOException e) {
					return null;
				}
		}else		return null;
		
				
	}
	
	/**
	 * Return the current depth (as established by the certifier), be careful the
	 * certifier may have decided to stop
	 *
	 * @return the current depth
	 */
	public int getCurrentDepth() {
		return testSession.getProfonditaCorrente();
	}

	/**
	 * Gets the current status
	 *
	 * @return the current status
	 */
	public static CertifierStatus getCurrentStatus() {
		return testSession.getStatoCorrente();
	}
	
	
	
	public static Scelta ControlloRisposta(String rispostaData) {
		Scelta scelta;
		scelta=testSession.ControlloRisposta(rispostaData); // Initialized before computeNextDepth()
		if(testSession.getStatoCorrente().currentResult!=Result.CONTINUA) {
			if(testSession.getStatoCorrente().currentResult==Result.FINE_NON_CERTIFICATA) {
				risultato.setAngolo(0);
				risultato.setLivello(0);
			}else {
				risultato.setAngolo(AngleCalculus.calcolaAngolo(risultato));
			    risultato.setLivello(1000* (AngleCalculus.MonitorWidthMM(risultato.getMonitorSize(),(int)risultato.getDimensione().getWidth(),(int)risultato.getDimensione().getHeight())*risultato.getXBar())/(int)risultato.getDimensione().getWidth());
		    }
		}
		return scelta;
		
	}
	
	private static void changePos() {
		risultato.setPos(new Random().nextBoolean());
	}
	
	public static InputStream settaNuovaImg() {
		
		assert testSession.getStatoCorrente().currentResult == Test_session.Result.CONTINUA;
		BufferedImage image = null;
		
		changePos();
		image=GeneraImg.modificaM(risultato.getXBar(), risultato);
						
		if(image!=null)	{
		//prova di inserimento immagine nello stream
		try {
			// Write the image to a buffer
					imagebuffer = new ByteArrayOutputStream();
					ImageIO.write(image, "png", imagebuffer);
					// Return a stream from the buffer
					return new ByteArrayInputStream(imagebuffer.toByteArray());
		}catch(IOException e) {
			return null;
		}
			}else return null;
		
	}
	
	private FSTdatabaseAIMO db;
	public void setDB(FSTdatabaseAIMO db) {
		this.db=db;
	}
	
	public Boolean checkAuthorization(String user,String psw) {
		return db.checkAuthorization(user, psw);
	}
	
	public Boolean addDoctor(Doctor d) {
		return db.insertDoc(d);
	
	}
	
	public Boolean assignP(Patient p) {
		return db.assignPatDoc(p, db.getDoc());
	}
	
}

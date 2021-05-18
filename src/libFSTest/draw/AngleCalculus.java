package libFSTest.draw;

import java.awt.Dimension;

import libFSTest.test.DatiGenerazione;

public class AngleCalculus {
	/**
	* funzione di calcolo angolo
	*
	* @param data    dati di generazione
	* 
	* @return      angolo (secondi d'arco)
	*/	
	public static int calcolaAngolo(DatiGenerazione data) {
				
				int angle=calcolaAngolo(data.getMonitorSize(),data.getDimensione(),data.getXBar(),data.getDistSchermo()/10);
				data.setAngolo(angle);
				return angle;
	}
	
	/**
	* funzione di calcolo angolo
	*
	* @param data    dati di generazione
	* 
	* @return      angolo (secondi d'arco)
	*/	
	public static int calcolaAngolo(int monitorSize,Dimension dimenSchermo, int XBar,int distSchermo) {
		distSchermo*=10;       
		//calcolo angolo
		double distanzaBarreMM=(MonitorWidthMM(monitorSize,(int)dimenSchermo.getWidth(),(int)dimenSchermo.getHeight())*XBar)/(int)dimenSchermo.getWidth();
		double angRad=Math.atan((distanzaBarreMM/2)/distSchermo);
				
		int angle=(int)(angRad*206265);
		return angle;
	}
	
	/**
	* restituisce la misura in millimetri della larghezza dello schermo
	*
	* @param diagonale  la diagonale del monitor in decimi di pollice
	 * @return      misura in mm larghezza schermo
	 */	
	public static double MonitorWidthMM(double diagonale,int w,int h) {
	double dPixel=Math.sqrt(Math.pow(w, 2)+Math.pow(h, 2)); //diagonale in pixel
	double wPollici=w*((diagonale/10)/dPixel); //larghezza in pollici
	double wMM=wPollici*25.4; //larghezza in mm
	return wMM;
	}
}

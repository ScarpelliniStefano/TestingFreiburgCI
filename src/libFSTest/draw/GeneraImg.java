package libFSTest.draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Random;

import libFSTest.test.DatiGenerazione;


public class GeneraImg extends Observable{
	
	/**
	 * funzione di modifica della distanza delle barre
	 *    
	 * @param m la distanza delle barre
	 * @param risultato i dati di generazione per generare la nuova immagine
	 * @return immagine da visualizzare
	 */
	public static BufferedImage modificaM(int m,DatiGenerazione risultato) {
		return GeneraImmagine(risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), m, risultato.getC1(), risultato.getC2());
		
	}
	
	/**
	 * funzione di modifica della distanza delle barre
	 *    
	 * @param m la distanza delle barre
	 * @param risultato i dati di generazione per generare la nuova immagine
	 * @return immagine da visualizzare
	 */
	public static BufferedImage modificaMWeb(int m,DatiGenerazione risultato) {
		return GeneraImmagine(risultato.getDimensione().width-80,risultato.getDimensione().height-80,risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), m, risultato.getC1(), risultato.getC2());
		
	}
		/**
		* funzione di generazione immagine del test di freiburg dati i parametri di generazione
		*
		* @param wRec               larghezza rettangolo (pixel)
		* @param hRec               altezza rettangolo (pixel)
		* @param hBar               altezza barre (pixel)
		* @param xBar               distanza barre (pixel)
		* @param c1                 colore barra sinistra (pixel)
		* @param c2                 colore barra destra (pixel)
		* @param diagonale          diagonale del monitor (in decimi di pollice)
		* 
		* @return      Stream immagine
		*/	
		public static BufferedImage GeneraImmagine(int wRec, int hRec,int hBar,int xBar,Color c1,Color c2) {
			Dimension ScreenSize=Toolkit.getDefaultToolkit().getScreenSize(); //dimensione monitor
			//larghezza e altezza immagine
			int wFin=(int) ScreenSize.getWidth();
			int hFin=(int) ScreenSize.getHeight();
			return GeneraImmagine(wFin, hFin, wRec, hRec, hBar, xBar, c1, c2);

	    }
		
		/**
		* funzione di generazione immagine del test di freiburg dati i parametri di generazione
		*
		* @param width              larghezza immagine
		* @param height             altezza immagine
		* @param wRec               larghezza rettangolo (pixel)
		* @param hRec               altezza rettangolo (pixel)
		* @param hBar               altezza barre (pixel)
		* @param xBar               distanza barre (pixel)
		* @param c1                 colore barra sinistra 
		* @param c2                 colore barra destra 
		* @param diagonale          diagonale del monitor (in decimi di pollice)
		* 
		* @return      Stream immagine
		*/	
		public static BufferedImage GeneraImmagine(int width,int height,int wRec, int hRec,int hBar,int xBar,Color c1,Color c2) {
			//larghezza e altezza immagine
			int wFin=width;
			int hFin=height;
		
		
		//settaggio parametri interni per la generazione
		BufferedImage image=new BufferedImage(wFin,hFin,BufferedImage.TYPE_INT_RGB);
		Graphics2D drawable=image.createGraphics();
		int dimBordoRect=5;
		int dimBarre=5;
		int centroImgx=(wFin/2);
	    int centroImgy=(hFin/2);
	    
	    //disegno fondo nero
		drawable.setColor(Color.BLACK);
		drawable.drawRect(1, 1, wFin, hFin);

		//disegno quadrati randomici bianchi per miglioramento della profondit�
		Shape[][] Figure= new Shape[50][50]; 
		Random rnd=new Random();
		for (int i=0; i<50; i++){ 
			for (int j=0; j<50; j++){ 
			drawable.setColor( ( rnd.nextFloat() > 0.75) ? Color.WHITE : Color.BLACK); 
			Figure [i][j] = new Rectangle2D.Double(i*(wFin/50),j*(hFin/50) ,(wFin/50),(hFin/50)); 
			drawable.fill(Figure [i][j]); 
			} 
		} 
		
		//disegno rettangolo bianco contenente le barre
		for(int x=(centroImgx-(wRec/2));x<=(centroImgx+(wRec/2));x++) {
			for(int y=(centroImgy-(hRec/2));y<=(centroImgy+(hRec/2));y++) {
				
					drawable.setColor(Color.BLACK);
					drawable.drawLine(x, y, x, y);
			}
		}
		
		//disegno bordo del rettangolo contenente le barre (con uso di colere visibile da entrambi gli occhi)
		for(int y=(centroImgy-(hRec/2));y<=((centroImgy-(hRec/2))+dimBordoRect);y++) {
				drawable.setColor(new Color((c1.getRed()+c2.getRed())/2,0,(c1.getBlue()+c2.getBlue())/2));
				drawable.drawLine((centroImgx-(wRec/2)), y, (centroImgx+(wRec/2)), y);
		}
		for(int y=((centroImgy+(hRec/2))-dimBordoRect);y<=(centroImgy+(hRec/2));y++) {
			drawable.setColor(new Color((c1.getRed()+c2.getRed())/2,0,(c1.getBlue()+c2.getBlue())/2));
			drawable.drawLine((centroImgx-(wRec/2)), y, (centroImgx+(wRec/2)), y);
	    }
		for(int x=(centroImgx+(wRec/2)-dimBordoRect);x<=(centroImgx+(wRec/2));x++) {
			drawable.setColor(new Color((c1.getRed()+c2.getRed())/2,0,(c1.getBlue()+c2.getBlue())/2));
			drawable.drawLine(x,(centroImgy-(hRec/2)), x, (centroImgy+(hRec/2)));
	    }
		for(int x=(centroImgx-(wRec/2));x<=((centroImgx-(wRec/2))+dimBordoRect);x++) {
			drawable.setColor(new Color((c1.getRed()+c2.getRed())/2,0,(c1.getBlue()+c2.getBlue())/2));
			drawable.drawLine(x,(centroImgy-(hRec/2)), x, (centroImgy+(hRec/2)));
	    }
		
		//disegno barre con controllo della distanza tra esse
		for(int y=(centroImgy-(hBar/2));y<=(centroImgy+(hBar/2));y++) {
			for(int x=(centroImgx-(xBar/2)-(dimBarre/2));x<=(centroImgx-(xBar/2)+(dimBarre/2));x++) {
			    drawable.setColor(c1);
			    drawable.drawLine(x, y, x, y);
			}
			for(int x=(centroImgx+(xBar/2)-(dimBarre/2));x<=(centroImgx+(xBar/2)+(dimBarre/2));x++) {
			    drawable.setColor(c2);
			    drawable.drawLine(x, y, x, y);
			}
			if((centroImgx-(xBar/2)+(dimBarre/2))>=(centroImgx+(xBar/2)-(dimBarre/2))) {
				for(int x=(centroImgx+(xBar/2)-(dimBarre/2));x<=(centroImgx-(xBar/2)+(dimBarre/2));x++) {
					drawable.setColor(new Color((c1.getRed()+c2.getRed())/2,0,(c1.getBlue()+c2.getBlue())/2));
				    drawable.drawLine(x, y, x, y);
				}
				
			}
		}
		//drawable.setColor(Color.WHITE);
		//drawable.fillRect((centroImgx-(dimBarre/2)), (centroImgy-(hBar/2)), dimBarre, hBar);
		return image;
		
		

	}

	
}

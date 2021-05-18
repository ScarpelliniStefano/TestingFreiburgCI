package libFSTest.test;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DatiGenerazione {
	private Dimension dimensione;
	private String Nome;
	private String Sesso;
	private Calendar dataNasc;
	private int MonitorSize;
	private int WRect;
	private int HRect;
	private int HBar;
	private int XBar;
	private int livMax;
	private int livMin;
	private int distSchermo;
	private Color c1;
	private Color c2;
	private boolean Pos;
	private double Livello;
	private double Angolo;
	private Calendar DataEsame;
	
	/**
	 * Costruttore
	 */
	public DatiGenerazione(){
		dimensione=new Dimension(1,1);
		Nome="Unnamed";
		Sesso="null";
		dataNasc=Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		MonitorSize=0;
		WRect=-1;
		HRect=-1;
		HBar=-1;
		XBar=-1;
		distSchermo=0;
		c1=Color.RED;
		c2=Color.BLUE;
		livMin=-1;
		livMax=-2;
		Pos=false;
		Livello=-1;
		Angolo=-1;
		DataEsame=Calendar.getInstance();
		
	}
	
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		
			return Nome;

		
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.Nome=nome;
		
	}
	/**
	 * @return the sesso
	 */
	public String getSesso() {
		return Sesso;
	}
	/**
	 * @param sesso the sesso to set
	 */
	public void setSesso(String sesso) {
		Sesso = sesso;
	}
	
	/**
	 * @return the dataNasc
	 */
	public Calendar getDataNasc() {
		return dataNasc;
	}
	/**
	 * @param dn the dataNasc to set
	 */
	public void setDataNasc(Calendar dn) {
		Calendar dateNow=Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		if(dateNow.after(dn)) {
		this.dataNasc = dn;}
	}
	/**
	 * @param dataNasc the dataNasc to set in dd/mm/yyyy
	 */
	public void setDataNasc(String dataNascString) {
		if(dataNascString.charAt(2)=='/' && dataNascString.charAt(5)=='/') {
			Calendar d = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
			GregorianCalendar data=new GregorianCalendar();
			DataEsame=dataNasc;
			DataEsame.setTime(data.getTime());
			char[] cD=dataNascString.toCharArray();
			String a=""+cD[6]+cD[7]+cD[8]+cD[9];
			String m=""+cD[3]+cD[4];
			String g=""+cD[0]+cD[1];
			if(Integer.parseInt(m)<=11&&Integer.parseInt(g)<=31) {
				data=new GregorianCalendar(Integer.parseInt(a),Integer.parseInt(m)-1, Integer.parseInt(g));
		
				if(DataEsame.getTime().after(data.getTime())) {
					this.dataNasc.setTime(data.getTime());
				}
			}
		}
	}
	/**
	 * @return the monitorSize
	 */
	public int getMonitorSize() {
		return MonitorSize;
	}
	/**
	 * @param d the monitorSize to set
	 */
	public void setMonitorSize(int d) {
		if(d>0)
			MonitorSize = d;
		else
			MonitorSize=0;
	}
	/**
	 * @return the wRect
	 */
	public int getWRect() {
		return WRect;
	}
	/**
	 * @param wRect the wRect to set
	 */
	public void setWRect(int wRect) {
		if(wRect<(dimensione.getWidth()-20)&&wRect>0) WRect = wRect;
		else WRect=(int) (dimensione.getWidth()-20);
	}
	/**
	 * @return the hRect
	 */
	public int getHRect() {
		return HRect;
	}
	/**
	 * @param hRect the hRect to set
	 */
	public void setHRect(int hRect) {
		if(hRect<(dimensione.getHeight()-20)&&hRect>0) HRect = hRect;
		else HRect=(int) (dimensione.getHeight()-20);
	}
	/**
	 * @return the hBar
	 */
	public int getHBar() {
		return HBar;
	}
	/**
	 * @param hBar the hBar to set
	 */
	public void setHBar(int hBar) {
		if(hBar<(HRect-10)&&hBar>0)	HBar = hBar;
		else HBar=HRect-10;
	}
	/**
	 * @return the xBar
	 */
	public int getXBar() {
		return XBar;
	}
	/**
	 * @param xBar the xBar to set
	 */
	public void setXBar(int xBar) throws ArithmeticException {
		if(livMin==-1&&livMax>=livMin)
			this.livMin=livMax;
		if(xBar<=livMax && xBar>=livMin)	this.XBar = xBar;
		else if(livMax==-2) 
				throw new ArithmeticException("Inserisci un livello massimo corretto");
			else
				this.XBar=livMax;
	}
	/**
	 * @return the livMax
	 */
	public int getLivMax() {
		return livMax;
	}


	/**
	 * @param livMax the livMax to set
	 */
	public void setLivMax(int livMax) {
		if(livMax<(WRect-10)&&livMax>0)	this.livMax = livMax;
		else this.livMax = WRect-10;
		this.XBar=this.livMax;
	}


	/**
	 * @return the livMin
	 */
	public int getLivMin() {
		return livMin;
	}


	/**
	 * @param livMin the livMin to set
	 */
	public void setLivMin(int livMin) {
		if(livMax==-2)
			livMax=livMin;
		if(livMin<(WRect-10)&&livMin>0&&livMin<=livMax)	this.livMin = livMin;
		else this.livMin=livMax;
	}


	/**
	 * @return the distSchermo
	 */
	public int getDistSchermo() {
		return distSchermo;
	}
	/**
	 * @param distSchermo the distSchermo to set
	 */
	public void setDistSchermo(int distSchermo) {
		this.distSchermo = distSchermo*10;
	}
	/**
	 * @return the c1
	 */
	public Color getC1() {
		return c1;
	}
	/**
	 * @param c1 the c1 to set
	 */
	public void setC1(Color c1) {
		this.c1 = c1;
	}
	/**
	 * @return the c2
	 */
	public Color getC2() {
		return c2;
	}
	/**
	 * @param c2 the c2 to set
	 */
	public void setC2(Color c2) {
		this.c2 = c2;
	}
	
	/**
	 * @return the pos
	 */
	public boolean getPos() {
		return Pos;
	}


	/**
	 * @param pos the position to set, false if behind or true if in front
	 */
	public void setPos(boolean pos) {
		if(pos!=Pos) {
			Color cTemp=getC1();
			this.setC1(getC2());
			this.setC2(cTemp);
		}
		Pos = pos;
		
	}


	
	/**
	 * @return the livello in µm
	 */
	public double getLivello() {
		return Livello;
	}
	/**
	 * @param i the livello to set (in µm)
	 */
	public void setLivello(double i) {
		Livello = i;
	}
	/**
	 * @return the angolo
	 */
	public double getAngolo() {
		return Angolo;
	}
	/**
	 * @param d the angolo to set
	 */
	public void setAngolo(double d) {
		this.Angolo = d;
	}
	/**
	 * @return the dataEsame
	 */
	public Calendar getDataEsame() {
		return DataEsame;
	}
	
	/**
	 * @param dimensione the dimension to set (Dimension format)
	 */
	public void setDimensione(Dimension dimensione) {
		this.dimensione = dimensione;
	}
	/**
	 * @return dimensione
	 */
	public Dimension getDimensione() {
		return dimensione;
	}
	/**
	 * @param dimensione the dimension to set (width and height in integer)
	 */
	public void setDimensione(int w,int h) {
		this.dimensione = new Dimension(w,h);
	}
	/**
	 * @param dimensione the dimension to set (width and height in double)
	 */
	public void setDimensione(double w,double h) {
		this.dimensione = new Dimension((int)w,(int)h);
	}


	
	
}

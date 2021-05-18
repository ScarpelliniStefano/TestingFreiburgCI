package libFSTest.test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import libFSTest.draw.AngleCalculus;
import libFSTest.test.DatiGenerazione;

public class DatiGenerazioneTest {
	
	private static DatiGenerazione dgen;
	
	@BeforeClass
	public static void SetupData() {
		dgen=new DatiGenerazione();
	}
	
	@Test
	public void testCostructor() {
		dgen=new DatiGenerazione();
		assertEquals(dgen.getC1(),Color.RED);
		assertEquals(dgen.getC2(),Color.BLUE);
		assertEquals(dgen.getNome(),"Unnamed");
		assertEquals(dgen.getSesso(),"null");
		assertNotNull(dgen.getDataNasc());
		assertEquals(dgen.getMonitorSize(),0);
		assertEquals(dgen.getDistSchermo(),0);
		assertEquals(dgen.getDimensione(),new Dimension(1,1));
		assertEquals(dgen.getWRect(),-1);
		assertEquals(dgen.getHRect(),-1);
		assertEquals(dgen.getHBar(),-1);
		assertNotNull(dgen.getXBar());
		assertNotNull(dgen.getLivMax());
		assertNotNull(dgen.getLivMin());
		assertFalse(dgen.getPos());
		assertNotNull(dgen.getLivello());
		assertNotNull(dgen.getAngolo());
		assertNotNull(dgen.getDataEsame());
		System.out.println("Test costruttore DatiGenerazione");
	}
	
	@Test
	public void testDataInsert() {
		dgen.setNome("TestUser");
		dgen.setSesso("Maschio");
		dgen.setDataNasc(Calendar.getInstance());
		dgen.setC1(Color.RED);
		dgen.setC2(Color.BLUE);
		dgen.setMonitorSize(173);
		dgen.setDistSchermo(40);
		
		dgen.setDimensione(1920.0d,1080.0d);
		dgen.setWRect(200);
		dgen.setHRect(400);
		dgen.setHBar(300);
		dgen.setLivMax(12);
		dgen.setLivMin(1);
		System.out.println("Dati inseriti. Test su inserimento parametri corretti.");
		assertEquals(dgen.getC1(),Color.red);
		assertEquals(dgen.getC2(),Color.BLUE);
		assertEquals(dgen.getNome(),"TestUser");
		assertEquals(dgen.getSesso(),"Maschio");
		assertNotNull(dgen.getDataNasc());
		assertEquals(dgen.getMonitorSize(),173);
		assertEquals(dgen.getDistSchermo(),400);
		assertEquals(dgen.getDimensione(),new Dimension(1920,1080));
		assertEquals(dgen.getWRect(),200);
		assertEquals(dgen.getHRect(),400);
		assertEquals(dgen.getHBar(),300);
		assertEquals(dgen.getXBar(),12);
		assertEquals(dgen.getLivMax(),12);
		assertEquals(dgen.getLivMin(),1);
	}
	
	@Test
	public void testDataNascCalendarType() {
		Calendar dn=Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		dn.set(2019, 0, 1); //1 gennaio 2019
		Calendar vData=dgen.getDataNasc();
		dgen.setDataNasc(dn);
		System.out.println("Test su inserimento data corretta.");
		assertEquals(dgen.getDataNasc(),dn);
		
		vData=dgen.getDataNasc();
		dn.set(2021, 10, 8);
		System.out.println("Test su inserimento data futura.");
		assertEquals(dgen.getDataNasc(),vData);
	}
	
	@Test
	public void testDataNascStringType() {
		System.out.println("Test su inserimento data corretta.");
		Calendar dn=Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		String data="20/11/2019";
		dgen.setDataNasc(data);
		dn.set(2019, 10, 20,0,0,0);
		assertEquals(dgen.getDataNasc().getTime().toString(),dn.getTime().toString());
		
		System.out.println("Test su inserimento date scorrette.");
		String[] fakeData= {"08/11/2021","32/11/2019","12/13/2020","2019/11/08","11/8/2021"};
		dn=Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		for(String dataf:fakeData) {
			dgen.setDataNasc(dataf);
			assertEquals(dgen.getDataNasc().getTime().toString(),dn.getTime().toString());
		}
	
	}
	
	@Test
	public void testWRect() {
		System.out.println("Test su inserimento wrect corretto.");
		dgen.setDimensione(Toolkit.getDefaultToolkit().getScreenSize());
		dgen.setWRect(200);
		assertEquals(dgen.getWRect(),200);
		
		System.out.println("Test su inserimento wrect scorretto.");
		int[] err= {Toolkit.getDefaultToolkit().getScreenSize().width-10,-3};
		for(int e:err) {
			dgen.setWRect(e);
			assertEquals(dgen.getWRect(),Toolkit.getDefaultToolkit().getScreenSize().width-20);
		}
	}
	
	@Test
	public void testHRect() {
		System.out.println("Test su inserimento hrect corretto.");
		dgen.setDimensione(Toolkit.getDefaultToolkit().getScreenSize());
		dgen.setHRect(300);
		assertEquals(dgen.getHRect(),300);
		
		System.out.println("Test su inserimento hrect scorretto.");
		int[] err= {Toolkit.getDefaultToolkit().getScreenSize().height-10,-5};
		for(int e:err) {
			dgen.setHRect(e);
			assertEquals(dgen.getHRect(),Toolkit.getDefaultToolkit().getScreenSize().height-20);
		}
	}
	
	@Test
	public void testHBar() {
		System.out.println("Test su inserimento hbar corretto.");
		dgen.setHRect(300);
		dgen.setHBar(200);
		assertEquals(dgen.getHBar(),200);
		
		System.out.println("Test su inserimento hbar scorretto.");
		int[] err= {295,-5};
		for(int e:err) {
			dgen.setHBar(e);
			assertEquals(dgen.getHBar(),290);
		}
	
	}
	
	@Test
	public void testXBarException() throws Exception {
		dgen=new DatiGenerazione();
		dgen.setDimensione(1920, 1080);
		try {
			dgen.setXBar(12);
			fail("Non è stata lanciata una eccezione giusta");
		}catch(ArithmeticException e) {
			//lanciata eccezione
		}
	}
	
	@Test
	public void testXBar() {
		System.out.println("Test su inserimento xbar corretto.");
		dgen.setLivMax(12);		
		dgen.setXBar(12);
		assertEquals(dgen.getXBar(),12);
		dgen.setLivMin(1);
		dgen.setXBar(5);
		assertEquals(dgen.getXBar(),5);
		
		System.out.println("Test su inserimento xbar scorretto.");
		dgen.setLivMax(3);
		dgen.setLivMin(10);
		dgen.setXBar(5);
		assertEquals(dgen.getXBar(),3);
		
		dgen.setLivMax(12);
		dgen.setLivMin(4);
		int[] err= {13,3,-5};
		for(int e:err) {
			dgen.setXBar(e);
			assertEquals(dgen.getXBar(),12);
		}
	
	}
	
	@Test
	public void testLivMax() {
		System.out.println("Test su inserimento livello massimo corretto.");
		dgen.setWRect(200);
		dgen.setLivMax(12);
		assertEquals(dgen.getLivMax(),12);
		
		System.out.println("Test su inserimento livello massimo scorretto.");
		int[] err= {195,-5};
		for(int e:err) {
			dgen.setLivMax(e);
			assertEquals(dgen.getLivMax(),190);
		}
	
	}
	
	@Test
	public void testLivMin() {
		dgen=new DatiGenerazione();
		dgen.setDimensione(1920, 1080);
		System.out.println("Test su inserimento livello minimo corretto.");
		dgen.setWRect(200);
		dgen.setLivMin(12);
		assertEquals(dgen.getLivMin(),12);
		
		dgen.setLivMax(12);
		dgen.setLivMin(1);
		assertEquals(dgen.getLivMin(),1);
		
		System.out.println("Test su inserimento livello minimo scorretto.");
		
		int[] err= {13,198,-5};
		for(int e:err) {
			dgen.setLivMin(e);
			assertEquals(dgen.getLivMax(),12);
		}
	
	}
	
	@Test
	public void testPos() {
		System.out.println("Test su inserimento posizione e colori.");
		dgen.setC1(Color.RED);
		dgen.setC2(Color.BLUE);
		dgen.setPos(false);
		assertEquals(dgen.getPos(),false);
		assertEquals(dgen.getC1(),Color.RED);
		assertEquals(dgen.getC2(),Color.BLUE);
		
		dgen.setPos(true);
		assertEquals(dgen.getPos(),true);
		assertEquals(dgen.getC1(),Color.BLUE);
		assertEquals(dgen.getC2(),Color.RED);
	
	}
	
	@Test
	public void testMonSize() {
		dgen.setMonitorSize(156);
		assertEquals(dgen.getMonitorSize(),156);
		dgen.setMonitorSize(0);
		assertNotEquals(dgen.getMonitorSize(),156);
	}
	
	@Test
	public void testAngleLevel() {
		dgen.setAngolo(AngleCalculus.calcolaAngolo(173, new Dimension(1920,1080), 12, 40));
		dgen.setLivello(12);
		assertEquals(dgen.getAngolo(),617,1);
		assertEquals(dgen.getLivello(),12,1);
	}
	
	
	
	
}

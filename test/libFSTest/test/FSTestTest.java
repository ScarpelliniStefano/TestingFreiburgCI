package libFSTest.test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import libFSTest.session.PestBase.CertifierStatus;
import libFSTest.session.Test_session.Result;
import libFSTest.test.FSTest.Scelta;


public class FSTestTest {

	private static DatiGenerazione dgen;
	static FSTest fst;
	@Before
	public void initialize() {
		dgen=new DatiGenerazione();
		dgen.setNome("TestUser");
		dgen.setSesso("Maschio");
		dgen.setDataNasc(Calendar.getInstance());
		dgen.setC1(Color.RED);
		dgen.setC2(Color.BLUE);
		dgen.setMonitorSize(173);
		dgen.setDistSchermo(40);
		dgen.setDimensione(new Dimension(1920,1080));
		dgen.setWRect(200);
		dgen.setHRect(400);
		dgen.setHBar(300);
		dgen.setLivMax(12);
		dgen.setLivMin(1);
		fst=new FSTest();
	}
	
	/*@Test(expected = IllegalArgumentException.class)
	public void testSceltaScorretta() {
		 try {
			 FSTest.Scelta s=FSTest.Scelta.valueOf("ciao");
			 fail("Expected exception of type java.lang.IllegalArgumentException");
		 }catch(java.lang.IllegalArgumentException e) {
			 //exception
		 }
	}*/
	
	
	@Test
	public void testFSTest() {
		System.out.println("Costruttore (vuoto) sotto test");
		fst=new FSTest();
		assertNotNull(fst);
	}

	@Test
	public void testIniziaTest() throws IOException {
		ByteArrayInputStream img=(ByteArrayInputStream) fst.IniziaTest(dgen);
		img.available();
		assertNotNull(img.available());
		
		img=(ByteArrayInputStream) fst.IniziaTest(dgen);
		img.available();
		assertNotNull(img.available());
	}

	@Test
	public void testGetCurrentDepthCorrect() {
		fst=new FSTest();
		fst.IniziaTest(dgen);
		int curr=fst.getCurrentDepth();
		assertEquals(curr,12);
		fst.ControlloRisposta("behind");
		curr=fst.getCurrentDepth();
		Result r=fst.getCurrentStatus().currentResult;
		//non possiamo proseguire poi con la verifica perchè è randomica
		assertEquals(fst.getCurrentDepth(),6);
		
	}
	
	@Test
	public void testGetCurrentDepthIncorrect() {
		fst.IniziaTest(dgen);
		int curr=fst.getCurrentDepth();
		assertEquals(curr,12);
		fst.ControlloRisposta("forward");
		curr=fst.getCurrentDepth();
		assertEquals(fst.getCurrentDepth(),12);
		Result stat=fst.getCurrentStatus().currentResult;
		assertEquals(stat,Result.CONTINUA);
		fst.settaNuovaImg();
		fst.ControlloRisposta("forward");
		stat=fst.getCurrentStatus().currentResult;
		curr=fst.getCurrentDepth();

		
	}


}

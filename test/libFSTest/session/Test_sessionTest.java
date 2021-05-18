package libFSTest.session;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import libFSTest.draw.AngleCalculus;
import libFSTest.session.Test_session.Result;
import libFSTest.session.Test_session.rispostaSingola;
import libFSTest.test.DatiGenerazione;
import libFSTest.test.FSTest.Scelta;

public class Test_sessionTest {

	private static libFSTest.test.DatiGenerazione dgen;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	}

	static Test_session session;
	
	@Before
	public void initialize() {
		session=null;
	}
	
	@Test
	public void testTest_session() {
		
		assertNull(session);
		session=new Test_session();
		assertNotNull(session);
		assertEquals(session.getStatoCorrente().currentDepth,12);
		assertEquals(session.getStatoCorrente().currentResult,Result.CONTINUA);
	}

	@Test
	public void testGetProfonditaCorrente() {
		session=new Test_session();
		session.IniziaTest(dgen);
		assertEquals(session.getProfonditaCorrente(),12);
		session.ControlloRisposta("behind");
		assertEquals(session.getProfonditaCorrente(),6);
	}

	@Test
	public void testGetStatoCorrente() {
		session=new Test_session();
		session.IniziaTest(dgen);
		assertEquals(session.getStatoCorrente().currentDepth,12);
		assertEquals(session.getStatoCorrente().currentResult,Result.CONTINUA);
		session.ControlloRisposta("behind");
		assertEquals(session.getStatoCorrente().currentDepth,6);
		assertEquals(session.getStatoCorrente().currentResult,Result.CONTINUA);
		session.ControlloRisposta("stop");
		assertEquals(session.getStatoCorrente().currentResult,Result.FINE_NON_CERTIFICATA);
	}

	@Test
	public void testIniziaTest() {
		session=new Test_session();
		Scelta choice=session.IniziaTest(dgen);
		assertEquals(choice,Scelta.CORRETTO);
		choice=Test_session.IniziaTest(dgen);//controllo in caso di richiamata
		assertEquals(choice,Scelta.CORRETTO);
	}

	@Test
	public void testControlloRisposta() {
		session=new Test_session();
		session.IniziaTest(dgen);
		Scelta choice=session.ControlloRisposta("behind");
		assertEquals(choice,Scelta.CORRETTO);
		choice=session.ControlloRisposta("stop");
		assertEquals(choice,Scelta.FINISCI);
		
	}

	@Test
	public void testGetSessionResults() {
		session=new Test_session();
		session.IniziaTest(dgen);
		session.ControlloRisposta("behind");
		session.ControlloRisposta("stop");
		List<String> listRisp=session.getSessionResults();
		List<String> listCorrect=new ArrayList<String>();
		listCorrect.add("behind,behind,12");
		listCorrect.add("skip");
		for(int i=0;i<listRisp.size();i++) {
			assertEquals(listRisp.get(i).substring(0, listCorrect.get(i).length()),listCorrect.get(i));
		}
	}

	@Test
	public void testGetSessionAnswers() {
		session=new Test_session();
		session.IniziaTest(dgen);
		session.ControlloRisposta("forward");
		session.ControlloRisposta("behind");
		session.ControlloRisposta("forward");
		session.ControlloRisposta("stop");
		List<rispostaSingola> listRisp=session.getSessionAnswers();
		assertNotNull(listRisp);
	}

}

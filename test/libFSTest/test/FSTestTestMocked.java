package libFSTest.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import libFST.db.Doctor;
import libFST.db.FSTdatabaseAIMO;
import libFST.db.Patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.awt.Color;
import java.awt.Dimension;
import java.io.InputStream;
import java.util.Calendar;

import libFSTest.session.Test_session;
import libFSTest.session.Test_session.Result;
import libFSTest.test.DatiGenerazione;
import libFSTest.test.FSTest;
import libFSTest.test.FSTest.Scelta;


public class FSTestTestMocked {

	FSTdatabaseAIMO db;
	Test_session session;
	DatiGenerazione dgen;
	
	@Before
	public void initialize() {
		dgen=new DatiGenerazione();
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
		dgen.setXBar(dgen.getLivMax());
	}
	
	@Test
	public void TestDB() {
		db=mock(FSTdatabaseAIMO.class);
		FSTest fst=spy(FSTest.class);
		
		fst.setDB(db);
		when(db.checkAuthorization(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		assertTrue(fst.checkAuthorization("pippo", "pluto"));
		verify(fst).checkAuthorization("pippo", "pluto");
	}
	
	@Test
	public void TestDBNewDoc() {
		db=mock(FSTdatabaseAIMO.class);
		FSTest fst=spy(FSTest.class);
		
		fst.setDB(db);
		when(db.insertDoc(Mockito.any(Doctor.class))).thenReturn(true);
		assertTrue(fst.addDoctor(null));
		verify(fst).addDoctor(null);
		fst.setDB(db);
		Doctor doc=new Doctor();
		doc.setCode("h432");
		doc.setMail("h.m@gmail.com");
		doc.changePsw("pieroRossi234");
		assertTrue(fst.addDoctor(doc));
		verify(fst).addDoctor(doc);
	}
	
	@Test
	public void TestDBPatientFalse() {
		db=mock(FSTdatabaseAIMO.class);
		FSTest fst=spy(FSTest.class);
		fst.setDB(db);
		Doctor doc=new Doctor();
		doc.setCode("h432");
		doc.setMail("h.m@gmail.com");
		doc.changePsw("pieroRossi234");
		when(db.insertDoc(Mockito.any(Doctor.class))).thenReturn(true);
		assertTrue(fst.addDoctor(doc));
		Patient pic=new Patient(123986);
		when(db.assignPatDoc(pic, doc)).thenReturn(false);
		//Boolean n=fst.assignP(pic);
		assertFalse(fst.assignP(pic));
		verify(fst).assignP(pic);
		
		
	}
	
	@Test
	public void TestMockSempreCorretta() {
		session=mock(Test_session.class);
		FSTest fst=spy(FSTest.class);
		fst=new FSTest();
		fst.testSession=session;
		InputStream is=fst.IniziaTest(dgen);
		assertNotNull(is);
		assertEquals(fst.getCurrentStatus().currentDepth,12);
		when(session.ControlloRisposta(Mockito.anyString())).thenReturn(Scelta.CORRETTO);
		Scelta s=fst.ControlloRisposta("behind");
		assertEquals(s,Scelta.CORRETTO);
		assertEquals(fst.getCurrentStatus().currentDepth,6);
		s=fst.ControlloRisposta("behind");
		assertEquals(s,Scelta.CORRETTO);
		assertEquals(fst.getCurrentStatus().currentDepth,3);
	}

	@Test
	public void TestMockErrors() {
		session=mock(Test_session.class);
		FSTest fst=spy(FSTest.class);
		fst=new FSTest();
		fst.testSession=session;
		InputStream is=fst.IniziaTest(dgen);
		assertNotNull(is);
		assertEquals(fst.getCurrentStatus().currentDepth,12);
		//when(session.ControlloRisposta(Mockito.anyString())).thenReturn(Scelta.CORRETTO);
		Scelta s=fst.ControlloRisposta("behind");
		assertEquals(s,Scelta.CORRETTO);
		assertEquals(fst.getCurrentStatus().currentDepth,6);
		when(session.ControlloRisposta(Mockito.anyString())).thenReturn(Scelta.SBAGLIATO);
		s=fst.ControlloRisposta("forward");
		assertEquals(s,Scelta.SBAGLIATO);
		assertEquals(fst.getCurrentStatus().currentDepth,9);
		s=fst.ControlloRisposta("forward");
		assertEquals(s,Scelta.SBAGLIATO);
		assertEquals(fst.getCurrentStatus().currentDepth,11);
		when(session.ControlloRisposta(Mockito.anyString())).thenReturn(Scelta.CORRETTO);
		s=fst.ControlloRisposta("behind");
		assertEquals(s,Scelta.CORRETTO);
		assertEquals(fst.getCurrentStatus().currentDepth,10);
		when(session.ControlloRisposta(Mockito.anyString())).thenReturn(Scelta.FINISCI);
		s=fst.ControlloRisposta("behind");
		assertEquals(s,Scelta.FINISCI);
		assertEquals(fst.getCurrentStatus().currentDepth,10);
		Result r=fst.getCurrentStatus().currentResult;
		assertEquals(r,Result.FINE_CERTIFICATA);
	}
}

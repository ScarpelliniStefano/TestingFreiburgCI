package libFSTest.session;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import libFSTest.draw.AngleCalculus;
import libFSTest.session.PestBase.CertifierStatus;
import libFSTest.session.PestBase.Soluzione;
import libFSTest.session.Test_session.Result;
@RunWith(Enclosed.class)
public class BestPestElabTest {
	BestPestElaboratorNew bpElab;
	 @RunWith(Parameterized.class)
	    public static class ParameterizedTest {
		 BestPestElaboratorNew bpElab;
		 	
		 	@Before
		 	public void initialize() {
		 		bpElab=new BestPestElaboratorNew(12,1);
		 	}
		 	List<Soluzione> listSol;
		 	int currentDepth;
		 	Result resFinale;
	
		 	public ParameterizedTest(List<Soluzione> listaSoluzioni,int currentDepth, Result resFinale) {
		 		super();
		 		this.listSol=listaSoluzioni;
		 		this.currentDepth=currentDepth;
		 		this.resFinale=resFinale;
		 	}
	
		 	@Parameterized.Parameters
		 	public static Collection<Object[]> input() {
		 		Collection<Object[]> array=Arrays.asList(new Object[][] {
													{Arrays.asList(Soluzione.GIUSTA,Soluzione.GIUSTA,Soluzione.GIUSTA,Soluzione.GIUSTA),2,Result.FINE_CERTIFICATA},
													{Arrays.asList(Soluzione.SBAGLIATA,Soluzione.SBAGLIATA),12,Result.FINE_NON_CERTIFICATA},
													{Arrays.asList(Soluzione.GIUSTA,Soluzione.SBAGLIATA,Soluzione.SBAGLIATA,Soluzione.SBAGLIATA),12,Result.FINE_CERTIFICATA},
													{Arrays.asList(Soluzione.GIUSTA,Soluzione.SBAGLIATA,Soluzione.STOP),9,Result.FINE_NON_CERTIFICATA},
													{Arrays.asList(Soluzione.SBAGLIATA,Soluzione.GIUSTA,Soluzione.SBAGLIATA),9,Result.CONTINUA},
													});
		 		return array;
		 	}
	
		 	@Test
			public void testComputeNextDepth() {
				//percorso tutto corretto:
				for(Soluzione sol:listSol)
					bpElab.computeNextDepth(sol); //6,3,2,2
				assertEquals(bpElab.getCurrentStatus().currentDepth,currentDepth);
				assertEquals(bpElab.getCurrentStatus().currentResult,resFinale);
			}
	 }
	 public static class NonParameterizedTest {
		 BestPestElaboratorNew bpElab;
		 @Test
		 public void testGetCurrentStatus() {
			 bpElab=new BestPestElaboratorNew(12,1);
			 CertifierStatus stat=bpElab.getCurrentStatus();
			 assertEquals(stat.currentDepth,12);
			 assertEquals(stat.currentResult,Result.CONTINUA);
		 }

		 @Test
		 public void testGetNextDepth() {
			 bpElab=new BestPestElaboratorNew(12,1);
			 assertEquals(bpElab.getNextDepth(),12);
			 bpElab.setNextDepth(11);
			 assertEquals(bpElab.getNextDepth(),11);
		 }

		 @Test
		 public void testSetNextDepth() throws IllegalArgumentException{
			 bpElab=new BestPestElaboratorNew(12,1);
			 bpElab.setNextDepth(12);
			 assertEquals(bpElab.getNextDepth(),12);
			 bpElab.setNextDepth(1);
			 assertEquals(bpElab.getNextDepth(),1);
			 try {
				 bpElab.setNextDepth(13);
				 fail("Eccezione non lanciata");
			 }catch(IllegalArgumentException e) {
				 //ECCEZIONE LANCIATA
			 }
			 try {
				 bpElab.setNextDepth(0);
				 fail("Eccezione non lanciata");
			 }catch(IllegalArgumentException e) {
				 //ECCEZIONE LANCIATA
			 }
		
		 }

		 @Test
		 public void testBestPestElaborator() {
			 assertNull(bpElab);
			 bpElab=new BestPestElaboratorNew(12,1);
			 assertEquals(bpElab.getCurrentStatus().currentResult,Result.CONTINUA);
		
			 int[][] maxMinErr= {{1,0},{1,2}};
			 for(int[] err:maxMinErr) {
				 bpElab=new BestPestElaboratorNew(err[0],err[1]);
				 assertEquals(bpElab.rightLimit,1);
			 }
		 }
	
		 @Test
		 public void testBestPestElaboratorExcept() throws IllegalArgumentException {
			 try {
				 bpElab=new BestPestElaboratorNew(0,1);
				 fail("Eccezione non lanciata");
			 }catch(IllegalArgumentException e) {
				 //ECCEZIONE LANCIATA
			 }
		
		 }
	 }

}

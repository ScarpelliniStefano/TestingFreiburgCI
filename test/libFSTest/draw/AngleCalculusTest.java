package libFSTest.draw;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import libFSTest.test.DatiGenerazione;

@RunWith(Enclosed.class)
public class AngleCalculusTest {
		private static AngleCalculus calculator;
	 @RunWith(Parameterized.class)
	    public static class ParameterizedTest {

		 	
		 	@Before
		 	public void initialize() {
		 		calculator=new AngleCalculus();
		 	}
		 	int monitorSize,XBar,distSchermo,angleExpected;
		 	Dimension dimenSchermo;
	
		 	public ParameterizedTest(int monitorSize,Dimension dimenSchermo, int XBar,int distSchermo,int angleExpected) {
		 		super();
		 		this.monitorSize=monitorSize;
		 		this.dimenSchermo=dimenSchermo;
		 		this.XBar=XBar;
		 		this.distSchermo=distSchermo;
		 		this.angleExpected=angleExpected;
		 	}
	
		 	@Parameterized.Parameters
		 	public static Collection<Object[]> input() {
		 		Dimension[] dim= {new Dimension(1920,1080),new Dimension(1366,768)};
		 		Collection<Object[]> array=Arrays.asList(new Object[][] {
													{173,dim[0],12,40,617},
													{173,dim[0],6,40,308},
													{156,dim[0],12,40,556},
													{156,dim[0],3,40,139},
													{173,dim[1],12,40,867},
													{173,dim[1],6,40,433},
													{156,dim[1],12,40,782},
													{156,dim[1],3,40,195},
													{156,dim[1],12,80,391},
													{156,dim[1],12,20,1564}});
		 		return array;
		 	}
	
		 	@Test
		 	public void calcolaAngoloTest() {
		 		int angle=calculator.calcolaAngolo(monitorSize,dimenSchermo,XBar,distSchermo);
		 		assertEquals(angle,angleExpected);
		 	}
	 }
	 
	    public static class NonParameterizedTest {
		 	private static DatiGenerazione dgen;
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
				calculator=new AngleCalculus();
			}
			
			@Test
			public void calcolaAngoloDatiGenTest() {
				int angle=calculator.calcolaAngolo(dgen);
				
				assertEquals(angle,617);
			}
			
			@Test
		 	public void calcolamillimetriWidthTest() {
		 		double widthmm=calculator.MonitorWidthMM(100,800,600);
		 		assertEquals(widthmm,203.2,0.1);
		 	}
			
	    }
	    
	   

}

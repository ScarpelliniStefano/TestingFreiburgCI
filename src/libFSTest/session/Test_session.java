package libFSTest.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import libFSTest.draw.AngleCalculus;
import libFSTest.session.PestBase.CertifierStatus;
import libFSTest.test.DatiGenerazione;
import libFSTest.test.FSTest.Scelta;

public class Test_session{
	
    private static DatiGenerazione risultato=new DatiGenerazione();
	/** the tester. */
	private static BestPestElaboratorNew testElab;
	
	// Single answer given by the user
		public static class rispostaSingola {
			String risposta;

			/**
			 * Costruzione della singola risposta della sessione
			 * 
			 * @param timeTaken istante nel quale è stata data la risposta risposta 
			 */
			public rispostaSingola(String sceltaFatta, String sceltaCorrente, int profonditaCorrente, double angolo, Date timeTaken) {
				String sceltaFattaS = (sceltaFatta == "stop") ? "skip" : sceltaFatta.toString();
				risposta = sceltaFattaS + "," + sceltaCorrente.toString() + "," + profonditaCorrente + "," + angolo + "," + timeTaken;
			}

			@Override
			public String toString() {
				return risposta;
			}
		}
	
	/**
	 * The Enum Result della sessione alla fine
	 */
	public enum Result {
		/** Continua test */
		CONTINUA,
		/** Fine certificata */
		FINE_CERTIFICATA,
		/** Fine non certificata */
		FINE_NON_CERTIFICATA // non può essere registrata profondità
	}
	
	static List<rispostaSingola> sessionStory = new ArrayList<rispostaSingola>();	
	
	/**
	 * costruttore
	 */
	public Test_session() {
		sessionStory.clear();
	}
	
		
	
	
	
	/**
	 * Ritorna la profondità corrente (come stabilito dal certificatore) - fare attenzione 
	 * che il certificatore potrebbe voler concluder il test
	 *
	 * @return profondità corrente
	 */
	public int getProfonditaCorrente() {
		return testElab.getCurrentDepth();
	}

	/**
	 * Ritorna lo stato corrente
	 *
	 * @return stato corrente
	 */
	public CertifierStatus getStatoCorrente() {
		return testElab.getCurrentStatus();
	}
	
	
		/**
		* funzione di inzio del test dati i dati di generazione
		*
		* @param result    dati di generazione strutturati come DatiGenerazione
		* 
		* @return      Stream immagine
		*/		
	public static Scelta IniziaTest(DatiGenerazione result) {
		
		
		if(risultato.getAngolo()==-1) {
			risultato=result;
		}
		else {
			risultato.setXBar(risultato.getLivMax());
		}
		
		testElab=new BestPestElaboratorNew(risultato.getLivMax(), risultato.getLivMin());
		risultato.setAngolo(AngleCalculus.calcolaAngolo(risultato));
		return Scelta.CORRETTO;
		
				
	}
	/**
	 * controllo della risposta e restituzione della scelta
	 * 
	 * @param rispostaData la risposta data dall'utente
	 * @return Scelta scelta decisa dal tester
	 */
	public Scelta ControlloRisposta(String rispostaData) {
		Scelta res;
		int currentDepth = testElab.getCurrentDepth(); // Initialized before computeNextDepth()
		if (rispostaData== "stop") {
			testElab.computeNextDepth(PestBase.Soluzione.STOP);
			res = Scelta.FINISCI;
		} else if (rispostaData==(risultato.getPos()==true ? "forward" : "behind")) {
			testElab.computeNextDepth(PestBase.Soluzione.GIUSTA);
			if(testElab.getCurrentStatus().currentResult!=Result.CONTINUA) {
				res=Scelta.FINISCI;
			}else {
				res = Scelta.CORRETTO;
			}
		} else {
			assert (rispostaData != (risultato.getPos()==true ? "forward" : "behind") && rispostaData != "stop");
			testElab.computeNextDepth(PestBase.Soluzione.SBAGLIATA);
			if(testElab.getCurrentStatus().currentResult!=Result.CONTINUA) {
				res=Scelta.FINISCI;
			}else {
				res = Scelta.SBAGLIATO;
			}


		}

		risultato.setXBar(getProfonditaCorrente());
		sessionStory.add(new rispostaSingola(rispostaData, risultato.getPos()==true ? "forward" : "behind", currentDepth, risultato.getAngolo(), new Date()));

		return res;
	}
	
	/**
	 * Ritorna la lista di stringhe rappresentanti la sessione
	 * 
	 * @return result
	 */
	public List<String> getSessionResults() {
		List<String> result = new ArrayList<String>();
		for (rispostaSingola sa : sessionStory) {
			result.add(sa.toString());
		}
		return result;
	}

	/**
	 * Ritorna le risposte della sessione
	 *
	 * @return risposte della sessione
	 */
	public List<rispostaSingola> getSessionAnswers() {
		return java.util.Collections.unmodifiableList(sessionStory);
	}
	
	
}

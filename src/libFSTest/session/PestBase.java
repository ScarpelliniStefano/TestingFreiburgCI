package libFSTest.session;

public abstract class PestBase {

	/**
	 * The Enum Solution
	 */
	public enum Soluzione {
		/** Corretta */
		GIUSTA,
		/** Sbagliata */
		SBAGLIATA,
		/** Fine */
		STOP
	}
	
	

	/** stato certificato */
	protected CertifierStatus certifierStatus;

	/**
	 * Classe CertifierStatus
	 */
	static public class CertifierStatus {

		/** Profondit� corrente */
		public int currentDepth;

		/** Risultato corrente */
		public Test_session.Result currentResult;

		@Override
		public String toString() {
			switch (currentResult) {
			case FINE_CERTIFICATA:
				return "CERTIFICATO a livello: " + currentDepth;
			case FINE_NON_CERTIFICATA:
				return "FINITO ma NON CERTIFICATO fino al livello: " + currentDepth;
			case CONTINUA:
				return "NON COMPLETATO (Testing fermato a " + currentDepth + ")";
			}
			return "";
		}
	}

	/**
	 * Get profondit� corrente
	 *
	 * @return la profondit� corrente
	 */
	public int getCurrentDepth() {
		return certifierStatus.currentDepth;
	}

	abstract void computeNextDepth(Soluzione sol);

	/**
	 * Get stato corrente
	 * 
	 * @return
	 */
	abstract public CertifierStatus getCurrentStatus();
}
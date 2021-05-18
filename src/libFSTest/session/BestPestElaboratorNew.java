package libFSTest.session;



import libFSTest.session.Test_session.Result;


/**
 * Algoritmo PEST 
 */

public class BestPestElaboratorNew extends PestBase {

	private int nextDepth=-1;
	public int getNextDepth() {
		return nextDepth;
	}

	public void setNextDepth(int nextDepth) {
		if(nextDepth<=maxDepth && nextDepth>=rightLimit)
			this.nextDepth = nextDepth;
		else
			throw new IllegalArgumentException();
	}

	private int maxDepth=-1;
	private int leftLimit=-1;
	protected int rightLimit=-1;
	private int chance=-1;
	private double value=-1;

	

	public BestPestElaboratorNew(int maxValue,int minValue) {
		certifierStatus = new CertifierStatus();
		if (maxValue >= 1)
			certifierStatus.currentDepth = maxValue;
		else
			throw new IllegalArgumentException();
		if(minValue<1 || minValue>maxValue) {
			minValue=maxValue;
		}
		maxDepth = maxValue;
		leftLimit = maxValue;
		rightLimit = minValue;
		nextDepth=maxValue;
		chance = 1;

		certifierStatus.currentResult = Result.CONTINUA;
	}

	
	@Override
	void computeNextDepth(PestBase.Soluzione solution) {
		

		if (solution == PestBase.Soluzione.SBAGLIATA && chance > 0 && certifierStatus.currentDepth == maxDepth) {
			chance--;
		} else if (solution == PestBase.Soluzione.SBAGLIATA && chance == 0
				&& certifierStatus.currentDepth == maxDepth) {
			certifierStatus.currentResult = Result.FINE_NON_CERTIFICATA;
		} else if (solution == PestBase.Soluzione.STOP) {
			// Nothing (end button)
			certifierStatus.currentResult=Result.FINE_NON_CERTIFICATA;
		} else if (solution == PestBase.Soluzione.GIUSTA) {
			leftLimit = certifierStatus.currentDepth;

			// Numerical rounding (Floor: round down)
			value = ((double) leftLimit + rightLimit) / 2;
			nextDepth = (int) (Math.floor(value));

			// Next depth
			certifierStatus.currentDepth = nextDepth;

			if ((leftLimit - rightLimit) <= 1) {
				certifierStatus.currentResult = Result.FINE_CERTIFICATA;
				certifierStatus.currentDepth = leftLimit;
			}
		} else {
			rightLimit = certifierStatus.currentDepth;

			// Numerical rounding (Ceil: round up)
			value = ((double) leftLimit + rightLimit) / 2;
			nextDepth = (int) (Math.ceil(value));

			// Next depth
			certifierStatus.currentDepth = nextDepth;

			if ((leftLimit - rightLimit) <= 1) {
				certifierStatus.currentResult = Result.FINE_CERTIFICATA;
				certifierStatus.currentDepth = leftLimit;
			}
		}
	}

	@Override
	public CertifierStatus getCurrentStatus() {
		assert maxDepth!=-1;
		return certifierStatus;
	}
}

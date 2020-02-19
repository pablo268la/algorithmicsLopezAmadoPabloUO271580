package session2;

/* This program can be used to sort n elements with 
 * a "bad" algorithm (quadratic). 
 * It is the SELECTION */
public class Selection extends Vector {
	public Selection(int nElements) {
		super(nElements);
	}

	@Override
	public void sort() {
		for (int j = 0; j < elements.length; j++) {
			int minElement = Integer.MAX_VALUE;
			int pos = -1;
			for (int i = j; i < elements.length; i++) {
				if (elements[i] < minElement) {
					minElement = elements[i];
					pos = i;
				}
			}
			if(pos != -1) {
				interchange(j, pos);
			}
		}
	}

	@Override
	public String getName() {
		return "Selection";
	}
}

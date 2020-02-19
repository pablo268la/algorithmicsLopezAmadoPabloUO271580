package session2;

/* This program can be used to sort n elements with 
 * a "bad" algorithm (quadratic). 
 * It is the DIRECT INSERTION */
public class Insertion extends Vector {

	public Insertion(int nElements) {
		super(nElements);
	}

	@Override
	public void sort() {
		int j;
		int pivot;
		for (int i = 1; i < elements.length; i++) {
			pivot = elements[i]; // element to sort
			j = i - 1; // last element sorted

			while (j >= 0 && pivot < elements[j]) {
				elements[j+1] = elements[j]; // [4] < [3] --> [4] = [3] && [3] = [4]
				j--;
			}
			elements[j+1] = pivot;
		}
	}

	@Override
	public String getName() {
		return "Insertion";
	}
}

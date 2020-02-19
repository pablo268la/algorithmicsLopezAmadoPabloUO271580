package session2;

/* This program can be used to sort n elements with 
 * the best algorithm of this lab. It is the QUICKSORT */
public class QuicksortCentralElement extends Vector {

	public QuicksortCentralElement(int nElements) {
		super(nElements);
	}

	private void quickSort(int left, int right) {
		int i = left;
		int j = right - 1;
		int pivot;

		if (left < right) {
			int pos = (left + right) / 2;
			pivot = elements[pos];
			interchange(pos, right);

			do {
				while (elements[i] <= pivot && i < right)
					i++; // first element > pivot
				while (elements[j] >= pivot && j > left)
					j--; // first element < pivot
				if (i < j)
					interchange(i, j);
			} while (i < j);

			interchange(i, right);
			quickSort(left, i - 1);
			quickSort(i + 1, right);
		}
	}

	@Override
	public void sort() {
		quickSort(0, elements.length - 1);
	}

	@Override
	public String getName() {
		return "Quicksort - Central element";
	}
}

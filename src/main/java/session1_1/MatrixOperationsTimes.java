package session1_1;

public class MatrixOperationsTimes {

	public static void main(String[] args) {
		int nTimes = Integer.parseInt(args[0]);
		System.out.println("SumDiagonal1");
		//sumDiagonal1Times(nTimes);
		System.out.println("----------------------");
		System.out.println("SumDiagonal2");
		sumDiagonal2Times(nTimes);
	}

	private static void sumDiagonal2Times(int nTimes) {
		for (int n = 10; n < Integer.MAX_VALUE; n *= 3) {
			MatrixOperations mo = new MatrixOperations(n);
			long t1, t2;
			t1 = System.currentTimeMillis();
			for (int rep = 1; rep < nTimes; rep++) {
				mo.sumDiagonal2();
			}
			t2 = System.currentTimeMillis();

			double totalTime = (double)(t2-t1)/nTimes;
			System.out.println(
					String.format("SIZE = %d ** TOTAL-TIME = %d ms **  TIMES = %d ** TIME/EXECUTION = %f ms", n, t2 - t1, nTimes, totalTime));
		}
	}

	private static void sumDiagonal1Times(int nTimes) {
		for (int n = 10; n < Integer.MAX_VALUE; n *= 3) {
			MatrixOperations mo = new MatrixOperations(n);
			long t1, t2;
			t1 = System.currentTimeMillis();
			for (int rep = 1; rep < nTimes; rep++) {
				mo.sumDiagonal1();
			}
			t2 = System.currentTimeMillis();

			double totalTime = (double)(t2-t1)/nTimes;
			System.out.println(
					String.format("SIZE = %d ** TOTAL-TIME = %d ms **  TIMES = %d ** TIME/EXECUTION = %f ms", n, t2 - t1, nTimes, totalTime));
		}
	}
}

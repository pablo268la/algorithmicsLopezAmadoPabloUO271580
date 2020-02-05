package session1_1;

public class Vector2 {

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int[] v = new int[n];
		Vector1.fillIn(v);

		long t1, t2;
		t1 = System.currentTimeMillis();
		int s = Vector1.sum(v);
		t2 = System.currentTimeMillis();
		
		System.out.println("SIZE = " + n + " ** TIME = " + (t2 - t1) + " ms");
		System.out.println(String.format("SIZE = %d ** TIME = %d ms", n, t2 - t1));
	}

}

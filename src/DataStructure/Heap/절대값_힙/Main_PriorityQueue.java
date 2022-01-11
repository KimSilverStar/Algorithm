package DataStructure.Heap.절대값_힙;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main_PriorityQueue {
	static int n;		// 수행할 연산 개수
	static int x;		// 정수 x
	static PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> {
		int absDiff = Math.abs(o1) - Math.abs(o2);
		if (absDiff != 0)
			return absDiff;
		else
			return o1 - o2;
	});

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());

		for (int i = 0; i < n; i++) {
			x = Integer.parseInt(br.readLine());

			if (x != 0)
				pq.add(x);
			else {		// x == 0
				if (!pq.isEmpty())
					System.out.println(pq.remove());
				else
					System.out.println(0);
			}
		}
	}
}

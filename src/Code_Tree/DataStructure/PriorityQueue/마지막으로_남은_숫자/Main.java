package Code_Tree.DataStructure.PriorityQueue.마지막으로_남은_숫자;
import java.io.*;
import java.util.*;

public class Main {
	static int n;				// 숫자 개수
	static int lastCount;		// 출력, 마지막으로 남은 숫자
	static PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < n; i++) {
			int num = Integer.parseInt(st.nextToken());
			pq.add(num);
		}

		while (pq.size() >= 2) {
			int num1 = pq.remove();
			int num2 = pq.remove();
			int diff = Math.abs(num1 - num2);

			if (diff != 0)
				pq.add(diff);
		}

		if (!pq.isEmpty())
			lastCount = pq.remove();
		else
			lastCount = -1;
		System.out.println(lastCount);
	}
}

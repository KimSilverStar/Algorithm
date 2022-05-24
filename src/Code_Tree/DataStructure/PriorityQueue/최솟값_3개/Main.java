package Code_Tree.DataStructure.PriorityQueue.최솟값_3개;
import java.io.*;
import java.util.*;

public class Main {
	static int n;			// 숫자 개수
	static PriorityQueue<Integer> pq = new PriorityQueue<>();
	static StringBuilder sb = new StringBuilder();

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

			if (pq.size() < 3) {
				sb.append(-1).append("\n");
				continue;
			}

			// 가장 작은 3개 숫자
			int min1 = pq.remove();
			int min2 = pq.remove();
			int min3 = pq.remove();
			long mul = (long)min1 * min2 * min3;
			sb.append(mul).append("\n");

			pq.add(min1);
			pq.add(min2);
			pq.add(min3);
		}

		System.out.println(sb);
	}
}

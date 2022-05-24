package Code_Tree.DataStructure.PriorityQueue.배열_추출;
import java.io.*;
import java.util.*;

public class Main {
	static int n;				// 연산 개수
	static int x;				// 입력 연산 정수
	static PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());

		for (int i = 0; i < n; i++) {
			x = Integer.parseInt(br.readLine());

			if (x > 0) {
				pq.add(x);
			}
			else {		// x == 0
				if (!pq.isEmpty())
					sb.append(pq.remove()).append("\n");
				else
					sb.append(0).append("\n");
			}
		}

		System.out.println(sb);
	}
}

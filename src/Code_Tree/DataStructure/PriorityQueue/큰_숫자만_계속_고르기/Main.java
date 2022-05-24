package Code_Tree.DataStructure.PriorityQueue.큰_숫자만_계속_고르기;
import java.io.*;
import java.util.*;

public class Main {
	static int n, m;			// n개 숫자, m개 쿼리
	static PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < n; i++) {
			int num = Integer.parseInt(st.nextToken());
			pq.add(num);
		}

		for (int i = 0; i < m; i++) {
			int maxNum = pq.remove();
			pq.add(maxNum - 1);
		}

		System.out.println(pq.peek());
	}
}

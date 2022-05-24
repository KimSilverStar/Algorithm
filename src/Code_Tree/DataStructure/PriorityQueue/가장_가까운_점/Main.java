package Code_Tree.DataStructure.PriorityQueue.가장_가까운_점;
import java.io.*;
import java.util.*;

class Pair implements Comparable<Pair> {
	public int x, y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// 큰 순: other - this
	// 작은 순: this - other
	public int compareTo(Pair p) {
		// 원점에서 거리 가까운 순(|x| + |y| 작은 순)으로 정렬
		int dist1 = Math.abs(x) + Math.abs(y);
		int dist2 = Math.abs(p.x) + Math.abs(p.y);

		if (dist1 != dist2)
			return dist1 - dist2;

		// 거리가 같은 경우 => x 좌표 작은 순
		if (this.x != p.x)
			return this.x - p.x;

		// 거리가 같고, x 좌표가 같은 경우 => y 좌표 작은 순
		return this.y - p.y;
	}
}

public class Main {
	static int n, m;			// n개 점, m개 쿼리
	static PriorityQueue<Pair> pq = new PriorityQueue<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			pq.add(new Pair(x, y));
		}

		for (int i = 0; i < m; i++) {
			Pair p = pq.remove();
			Pair newP = new Pair(p.x + 2, p.y + 2);
			pq.add(newP);
		}

		Pair p = pq.remove();
		System.out.println(p.x + " " + p.y);
	}
}

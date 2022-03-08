package Shortest_Path.Dijkstra.특정한_최단경로;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 1번 노드 -> v1, v2 노드 -> n번 노드로 가는 최단 경로

 case 1) 1번 노드 -> v1 노드 -> v2 노드 -> n번 노드
   - 1번 노드 -> v1 노드 최단경로 다익스트라
   - v1 노드 -> v2 노드 최단경로 다익스트라
   - v2 노드 -> n번 노드 최단경로 다익스트라
 case 2) 1번 노드 -> v2 노드 -> v1 노드 -> n번 노드
   - 1번 노드 -> v2 노드 최단경로 다익스트라
   - v2 노드 -> v1 노드 최단경로 다익스트라
   - v1 노드 -> n번 노드 최단경로 다익스트라
 => 1), 2) 중에서 최소 값

 * 다익스트라
 1) 시작 지점에 대한 비용 배열, 우선순위 큐 초기화
 2) 우선순위 큐가 empty 할 때까지, 다음을 반복
   - 우선순위 큐에서 가중치가 가장 낮은 노드를 꺼냄
   - 꺼낸 노드와 연결된 다음 노드들에 대해,
     다음을 비교하여 갱신 및 우선순위 큐에 추가
     ① 현재까지 갱신된 시작 노드 -> 다음 노드 경로의 비용 배열 값
     ② 시작 노드 -> 현재 노드 (경유) -> 다음 노드 경로의 비용


2. 자료구조
 - int[] dist: 각 최단경로 다익스트라에서 시작 지점 -> 목표 지점의 최단경로 길이
 - PriorityQueue<Node>: 경로 길이 작은 순으로 정렬
 - List<Node>[], ArrayList<Node>[]: 인접 리스트


3. 시간 복잡도
 - 다익스트라 시간 복잡도: O(E log_2 V)
   => E, V 최대값 대입: (2 x 10^5) x log_2 (2 x 20^2)
   = (2 x 10^5) x ( log_2 + log_2 20^2 )
   ~= (2 x 10^5) x 10 = 2 x 10^6 << 1억
*/

class Node implements Comparable<Node> {
	public int v;				// 정점
	public int w;				// 시작 정점 -> 정점 v 까지의 경로 길이

	public Node(int v, int w) {
		this.v = v;
		this.w = w;
	}

	public int compareTo(Node n) {
		return this.w - n.w;
	}
}

public class Main {
	static int n, e;				// 정점 개수 n, 간선 개수 e
	static int v1, v2;				// 반드시 거쳐야 하는 두 정점 v1, v2
	static List<Node>[] lists;		// 인접 리스트
	static int minCount;			// 출력, 최단경로 길이

	static final int INF = 200_000_000;		// 간선 최대 개수(2 x 10^5) x 최대 가중치(10^3)
	static int[] dist;						// 각 최단경로 길이
	static PriorityQueue<Node> pq = new PriorityQueue<>();

	static void solution() {
		// 1번 노드 -> v1 노드 -> v2 노드 -> n번 노드
		int case1 = dijkstra(1, v1) + dijkstra(v1, v2) + dijkstra(v2, n);
		// 1번 노드 -> v2 노드 -> v1 노드 -> n번 노드
		int case2 = dijkstra(1, v2) + dijkstra(v2, v1) + dijkstra(v1, n);

		if (case1 >= INF && case2 >= INF)		// 해당 경로로 갈 수 없는 경우
			minCount = -1;
		else
			minCount = Math.min(case1, case2);

//		// 1번 노드 -> v1 노드 -> v2 노드 -> n번 노드
//		int d1 = dijkstra(1, v1);
//		int d2 = dijkstra(v1, v2);
//		int d3 = dijkstra(v2, n);
//
//		// 1번 노드 -> v2 노드 -> v1 노드 -> n번 노드
//		int d4 = dijkstra(1, v2);
//		int d5 = dijkstra(v2, v1);
//		int d6 = dijkstra(v1, n);
//
//		// 해당 경로로 갈 수 없는 경우
//		if ((d1 == Integer.MAX_VALUE || d2 == Integer.MAX_VALUE || d3 == Integer.MAX_VALUE) &&
//			(d4 == Integer.MAX_VALUE || d5 == Integer.MAX_VALUE || d6 == Integer.MAX_VALUE)) {
//			minCount = -1;
//			return;
//		}
	}

	/* startV -> destV 로의 최단경로 길이 반환 */
	static int dijkstra(int startV, int destV) {
		// 1) 비용 배열, 우선순위 큐 초기값
//		Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(dist, INF);
		dist[startV] = 0;
		pq.add(new Node(startV, 0));

		// 2) 비용 배열 갱신 및 우선순위 큐에 추가
		while (!pq.isEmpty()) {
			Node current = pq.remove();

			// 이미 현재 지점까지로의 최단경로가 갱신된 경우는 제외
			if (dist[current.v] < current.w)
				continue;

			// 현재 노드 current.v 와 연결된 다음 노드 next
			for (Node next : lists[current.v]) {
				// dist[next.v]: 현재까지 갱신된 시작 노드 -> next.v 노드까지의 최단거리
				// dist[current.v] + next.w: 시작 노드 -> current.v 노드 -> next.v 노드 거리
				if (dist[next.v] > dist[current.v] + next.w) {
					dist[next.v] = dist[current.v] + next.w;
					pq.add(new Node(next.v, dist[next.v]));
				}
			}
		}

		return dist[destV];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());

		dist = new int[n + 1];				// [1] ~ [n] 사용
		lists = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			lists[i] = new ArrayList<>();

		for (int i = 0; i < e; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());		// 출발 v
			int b = Integer.parseInt(st.nextToken());		// 도착 v
			int c = Integer.parseInt(st.nextToken());		// 길이

			lists[a].add(new Node(b, c));
			lists[b].add(new Node(a, c));
		}

		st = new StringTokenizer(br.readLine());
		v1 = Integer.parseInt(st.nextToken());
		v2 = Integer.parseInt(st.nextToken());

		solution();
		System.out.println(minCount);
	}
}

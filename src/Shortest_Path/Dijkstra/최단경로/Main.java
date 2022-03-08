package Shortest_Path.Dijkstra.최단경로;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 한 노드 -> 다른 모든 노드로의 최단거리,
   음이 아닌 간선의 가중치
   => 다익스트라

   1) 비용 배열, 우선순위 큐 초기화
     - dist[]: 시작 노드 거리 0, 나머지 노드 무한대
     - pq: (시작 노드, 0) 추가
   2) pq 가 empty 할 때까지 다음을 반복
     - pq 에서 시작 노드와의 거리 dist[v] 가 가장 작은 노드 v 꺼냄
       => 이미 갱신된 최단거리 노드의 경우는 제외
     - pq 에서 뽑은 남은 노드 중 시작 노드와 가장 가까운 노드 v 에 대해,
       노드 v 와 연결된 노드 nv 를 확인
       => 시작 노드에서 노드 v 를 경유하여, 노드 nv 를 가는 것이 더 가까우면
          최단거리 값 갱신 및 pq 에 추가


2. 자료구조
 - int[] dist: 비용 배열
   => 정점 개수 V x 간선 가중치 최대값 w
   => (2 x 10^4) x 10 = 2x 10^5 << 21억 이므로, int 가능
 - ArrayList<Node>[] lists: 인접 리스트 (간선 저장)
   => Node: (연결 노드, 거리)
 - PriorityQueue<Node> pq
   : 아직 방문 안한 노드 중에서, 거리 dist[v] 값이 가장 작은 노드를 선택
   => Node 에서 거리 w 값이 작은 순으로 정렬


3. 시간 복잡도
 - 다익스트라 시간 복잡도: O(E log_2 V)
   => E, V 최대값 대입: (3 x 10^5) log_2 (2 x 10^4)
      = (6 x 10^5) log_2 10^4 ~= (6 x 10^5) log_2 2^13
      ~= (6 x 10^5) x 13 = 78 x 10^5 << 1억
*/

class Node implements Comparable<Node> {
	public int v;			// 연결된 노드
	public int w;			// 거리 (가중치)

	public Node(int v, int w) {
		this.v = v;
		this.w = w;
	}

	public int compareTo(Node n) {
		return this.w - n.w;		// PQ 에서 거리(가중치) w 작은 순으로 정렬
	}
}

public class Main {
	static int numOfVertex, numOfEdge;		// 정점 개수, 간선 개수
	static int startV;						// 시작 정점
	static List<Node>[] lists;				// 간선 인접 리스트

	static final int INF = 200_000;			// 최대 노드 개수 x 가중치 최대값
	static int[] dist;						// 최단거리 갱신 배열
	static PriorityQueue<Node> pq = new PriorityQueue<>();

	static void dijkstra() {
		// 거리 배열, 우선순위 큐 초기화 - 시작 노드
		Arrays.fill(dist, INF);
		dist[startV] = 0;
		pq.add(new Node(startV, 0));

		while (!pq.isEmpty()) {
			Node current = pq.remove();

			if (dist[current.v] < current.w)	// 이미 방문, 갱신된 최단거리 dist[] 는 제외
				continue;

			// 노드 v 와 연결된 다음 노드 next 확인
			for (Node next : lists[current.v]) {
				// 노드 v 를 경유하는 것이 더 빠른 경우 => 갱신, 우선순위 큐에 추가
				if (dist[next.v] > dist[current.v] + next.w) {
					dist[next.v] = dist[current.v] + next.w;
					pq.add(new Node(next.v, dist[next.v]));
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		numOfVertex = Integer.parseInt(st.nextToken());
		numOfEdge = Integer.parseInt(st.nextToken());
		startV = Integer.parseInt(br.readLine());

		dist = new int[numOfVertex + 1];			// [1] ~ [numOfVertex] 사용
		lists = new ArrayList[numOfVertex + 1];
		for (int i = 1; i <= numOfVertex; i++)
			lists[i] = new ArrayList<>();

		for (int i = 1; i <= numOfEdge; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());		// 출발 노드
			int v = Integer.parseInt(st.nextToken());		// 도착 노드
			int w = Integer.parseInt(st.nextToken());		// 거리

			lists[u].add(new Node(v, w));
		}

		dijkstra();

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= numOfVertex; i++) {
			if (dist[i] != INF)
				sb.append(dist[i]).append("\n");
			else
				sb.append("INF").append("\n");
		}
		System.out.println(sb);
	}
}

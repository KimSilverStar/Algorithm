package Shortest_Path.Floyd_Warshall.플로이드;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 모든 도시(노드) -> 다른 모든 도시(노드)로 갈 때, 최소 비용
   => 플로이드-와샬

 1) 비용 배열 초기화
   - cost[i][i] = 0, 나머지 cost[i][j] = INF
 2) 비용 배열에 간선의 가중치 저장
   - startV -> destV 노드로 가는 간선이 여러 개 존재할 경우, 가장 작은 가중치로 저장
     => cost[startV][destV] = min(cost[startV][destV], weight)
 3) 모든 노드에 대해 해당 노드를 거쳐서 갈 때, 비용이 더 작으면 갱신
   - 3중 for 문: 중간 경유 노드, 시작 노드, 끝 노드


2. 자료구조
 - int[][] cost
   ex) cost[i][j]: i번 노드 -> j번 노드로 가는 최소 비용
   ① 자료형: 최대값 INF = 노드 최대 개수(100) x 각 간선 최대 가중치 값(10^5)
     = 10^7 << 21억 이므로, int 가능
   ② 메모리: 4 x n x n byte
     => n 최대값 대입: 4 x 100 x 100 byte = 4 x 10^4 byte = 40 KB


3. 시간 복잡도
 - 플로이드-와샬 시간 복잡도: O(V^3)
   => V = n 최대값 대입: 100^3 = 10^6 << 1억
*/

public class Main {
	static int n, m;			// 도시(노드) 개수 n, 버스(간선) 개수 m

	static final int INF = 10_000_000;
	static int[][] cost;

	static void floyd() {
		for (int k = 1; k <= n; k++) {				// 경유 노드
			for (int i = 1; i <= n; i++) {			// 시작 노드
				for (int j = 1; j <= n; j++) {		// 끝 노드
					// 3) 모든 노드에 대해 해당 노드를 거쳐서 갈 때, 비용이 더 작으면 갱신
					if (cost[i][j] > cost[i][k] + cost[k][j])
						cost[i][j] = cost[i][k] + cost[k][j];
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());

		cost = new int[n + 1][n + 1];		// [1][1] ~ [n][n] 사용
		// 1) 비용 배열 초기화
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i == j)
					cost[i][j] = 0;
				else
					cost[i][j] = INF;
			}
		}

		// 2) 비용 배열에 간선의 가중치 저장
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int startV = Integer.parseInt(st.nextToken());
			int destV = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());	// startV -> destV 의 비용(간선 가중치)

			// startV -> destV 로 가는 간선이 여러 개일 경우, 더 작은 가중치로 저장
			cost[startV][destV] = Math.min(cost[startV][destV], weight);
		}

		floyd();

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (cost[i][j] != INF)
					sb.append(cost[i][j]).append(" ");
				else		// 해당 경로로 갈 수 없는 경우
					sb.append(0).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}

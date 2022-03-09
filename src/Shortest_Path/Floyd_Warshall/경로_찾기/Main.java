package Shortest_Path.Floyd_Warshall.경로_찾기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 모든 정점 -> 다른 모든 정점으로 탐색
   => 플로이드-와샬
 - 예외처리) 노드 본인에서 출발하여 다시 되돌아오는 싸이클 구성하는지 확인
   => 비용 배열 초기화 시
      노드 본인 -> 본인을 0 이 아닌, INF 로 초기화

 1) 비용 배열 초기화
   - dist[][] 를 모두 INF 로 초기화
     => dist[i][i] = 0 처리하지 않고, 모두 INF 로 초기화
     => 노드 본인으로 돌아오는 싸이클 구성 확인하기 위함
   - INF = 노드 최대 개수 100 x 간선 가중치 최대값 1 = 100
 2) 비용 배열에 간선 가중치 저장
 3) 3중 for 문
   - 중간 경유 노드, 시작 노드, 끝 노드
   - 중간 경유 노드를 거쳐서 갈 때 비용이 더 작은 경우, 비용 갱신


2. 자료구조
 - int[][] dist: 비용 배열
   => 비용 최대값 INF = 100 이므로, int 가능


3. 시간 복잡도
 - 플로이드-와샬 시간 복잡도: O(V^3)
   => V = n 최대값 대입: 100^3 = 10^6 << 1억
*/

public class Main {
	static int n;					// 노드 개수
	static int[][] dist;			// 비용 배열
	static final int INF = 100;

	static void floyd() {
		// 3) 3중 for 문 - 중간 경유 노드, 시작 노드, 끝 노드
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					// 중간 경유 노드를 거쳐서 갈 때 비용이 더 작은 경우, 비용 갱신
					if (dist[i][j] > dist[i][k] + dist[k][j])
						dist[i][j] = dist[i][k] + dist[k][j];
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

		// 1) 비용 배열 초기화
		dist = new int[n + 1][n + 1];		// [1][1] ~ [n][n] 사용
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				// 노드 본인 [i] -> [i] 도 INF 로 초기화
				dist[i][j] = INF;
//				if (i != j)
//					dist[i][j] = INF;
//				else
//					dist[i][i] = 0;
			}
		}

		// 2) 비용 배열에 간선 가중치(1) 저장
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 1; j <= n; j++) {
				int w = Integer.parseInt(st.nextToken());
				if (w == 1)				// 노드 [i] -> 노드 [j]
					dist[i][j] = 1;
			}
		}

		floyd();

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (dist[i][j] != INF)
					sb.append(1).append(" ");
				else		// 비용 배열 값이 갱신없이 INF 이면, 해당 경로 존재 X
					sb.append(0).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}

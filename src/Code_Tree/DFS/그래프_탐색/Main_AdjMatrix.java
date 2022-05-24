package Code_Tree.DFS.그래프_탐색;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 1번 정점에서 출발, 도달 가능한 정점 개수
   => DFS
 - 인접 행렬로 구현한 DFS 사용

2. 자료구조
 - boolean[][] : 인접 행렬
   => 최대 10^3 x 10^3 byte = 10^6 byte = 1 MB
 - boolean[] visited: 정점 방문 여부
  => 최대 10^3 byte = 1 KB

3. 시간 복잡도
 - O(V^2): 인접 행렬 구현 DFS 의 시간 복잡도
   => n 최대값 대입: (10^3)^2 = 10^6 << 1억
*/

public class Main_AdjMatrix {
	static int n, m;				// n개 정점, m개 간선
	static boolean[][] graph;		// 그래프 - 인접 행렬
	static boolean[] visited;
	static int count;				// 출력, 도달 가능한 정점 개수

	static void dfs(int node) {
		for (int nextNode = 1; nextNode <= n; nextNode++) {
			// 다음 노드를 아직 방문 안했고, 현재 노드와 연결된 경우 => 더 탐색
			if (!visited[nextNode] && graph[node][nextNode]) {
				count++;
				visited[nextNode] = true;
				dfs(nextNode);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		graph = new boolean[n + 1][n + 1];		// [1][1] ~ [n][n] 사용
		visited = new boolean[n + 1];			// [1] ~ [n] 사용
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int dest = Integer.parseInt(st.nextToken());

			graph[start][dest] = true;
			graph[dest][start] = true;
		}

		// 1번 정점에서 출발
		visited[1] = true;
		dfs(1);

		System.out.println(count);
	}
}

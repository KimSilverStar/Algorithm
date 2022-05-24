package Code_Tree.DFS.그래프_탐색;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 1번 정점에서 출발, 도달 가능한 정점 개수
   => DFS
 - 인접 리스트로 구현한 DFS 사용

2. 자료구조
 - List<Integer>[] lists: 인접 리스트
 - boolean[] visited: 정점 방문 여부
  => 최대 10^3 byte = 1 KB

3. 시간 복잡도
 - O(V + E): 인접 리스트 구현 DFS 의 시간 복잡도
   => n, m 최대값 대입: 10^3 + 10^4 = 11,000 << 1억
*/

public class Main_AdjList {
	static int n, m;				// n개 정점, m개 간선
	static List<Integer>[] lists;	// 인접 리스트
	static boolean[] visited;
	static int count;				// 출력, 도달 가능한 정점 개수

	static void dfs(int node) {
		List<Integer> list = lists[node];	// 현재 node 와 연결된 노드들
		for (int nextNode : list) {
			if (!visited[nextNode]) {		// 다음 노드를 방문 안한 경우 => 더 탐색
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

		visited = new boolean[n + 1];		// [1] ~ [n] 사용
		lists = new ArrayList[n + 1];		// [1] ~ [n] 사용
		for (int i = 1; i <= n; i++)
			lists[i] = new ArrayList<>();

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int dest = Integer.parseInt(st.nextToken());

			lists[start].add(dest);
			lists[dest].add(start);
		}

		// 1번 정점에서 출발
		visited[1] = true;
		dfs(1);

		System.out.println(count);
	}
}

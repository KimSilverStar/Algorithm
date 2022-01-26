package DataStructure.Tree.트리의_지름;
import java.io.*;
import java.util.*;

/*
- 가중치 그래프(트리)
  => 간선 edge 에 가중치(길이)가 존재
*/

/*
1. 아이디어
*** 트리에서 정점 간 거리 (간선 거리) 특징
   - 가장 거리가 먼 두 정점이 v1, v2 인 경우,
     임의의 다른 정점 v3 과 가장 먼 정점은 v1 또는 v2 이다.

*** 트리에서 정점 간의 거리가 가장 먼 두 정점 구하기
   1) 임의의 정점과 가장 먼 정점 1개 구하기 (구한 가장 먼 정점: v1)
     - 임의의 정점 v_any 에서 시작하여 DFS 수행
   2) 정점 v1 과 가장 먼 정점 구하기 (v1 과 가장 먼 정점: v2)
     - 정점 v1 에서 시작하여 DFS 수행
   => v1 과 v2 는 트리에서 정점 간 거리가 가장 먼 두 정점이다 !!
   => DFS 2번으로 가장 먼 두 정점 v1, v2 구할 수 있다 !!

 1) 첫 번째 DFS 로 정점 v1 구하기
 2) 두 번째 DFS 로 트리의 최대 지름 구하기

2. 자료구조
 - List<Pair>[], ArrayList<Pair>[]: 인접 리스트
   ex) lists[1]: 1번 정점과의 연결 정보 Pair (정점 번호, 간선 거리)들 저장
 - boolean[]: 방문 확인

3. 시간 복잡도
  - DFS 2번 수행
*/

public class Main {
	static int v;					// 트리 정점(노드) 개수, 정점 번호: 1 ~ v
	static int maxR = Integer.MIN_VALUE;
	// 출력 값: 트리의 최대 지름 (두 정점 사이의 거리 중, 가장 긴 것)
	static List<Pair>[] lists;		// 인접 리스트
	static boolean[] check;
	static int vertex;		// 임의의 정점과 가장 먼 정점 (가장 먼 두 정점 v1, v2 둘 중에 하나)

	/* vertexIdx: 현재 정점 번호, totalDistance: 누적 거리 */
	static void dfs(int vertexIdx, int totalDistance) {
		check[vertexIdx] = true;

		List<Pair> list = lists[vertexIdx];
		for (Pair p : list) {
			if (!check[p.vertex])
				dfs(p.vertex, totalDistance + p.distance);
		}

		if (totalDistance > maxR) {
			maxR = totalDistance;
			vertex = vertexIdx;			// 첫 번째 DFS 로 가장 먼 두 정점 중, v1 구함
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		v = Integer.parseInt(br.readLine());
		lists = new ArrayList[v + 1];			// [1 ~ v] 사용
		for (int i = 1; i <= v; i++)
			lists[i] = new ArrayList<>();

		for (int i = 1; i <= v; i++) {
			st = new StringTokenizer(br.readLine());

			int startNode = Integer.parseInt(st.nextToken());
			while (st.hasMoreTokens()) {
				int destNode = Integer.parseInt(st.nextToken());
				if (destNode == -1)
					break;
				int distance = Integer.parseInt(st.nextToken());

				lists[startNode].add(new Pair(destNode, distance));
			}
		}

		// 임의의 정점에서 가장 먼 정점 v1 구하기 (vertex 에 저장)
		check = new boolean[v + 1];
		dfs(1, 0);

		// 정점 v1 과 가장 먼 정점 v2 구하기 => v1 과 v2 는 트리에서 가장 먼 두 정점
		// 트리의 최대 지름 계산
		check = new boolean[v + 1];
		dfs(vertex, 0);

		System.out.println(maxR);
	}
}

package DataStructure.Tree.트리의_지름;
import java.io.*;
import java.util.*;

/*
- 가중치 그래프(트리)
  => 간선 edge 에 가중치(길이)가 존재
*/

/*
1. 아이디어
 1) 정점 연결 정보가 "인접" 형태로 주어짐
   ex) 1번 정점과 3번 정점 연결 => 3번 정점과 1번 정점 연결
   - 자료구조로 인접 행렬 or 인접 리스트 사용
   - 인접 행렬 사용 시, 메모리 초과
     => 인접 리스트 List<>[] 사용

 2) 1 ~ v 의 각 정점들에서 DFS 탐색 시작
   - DFS 로 최대한 깊이 내려가면서 거리 합 누적해나감
     => 재귀 종료 조건: 연결된 정점이 더 이상 없는 경우 (더 이상 이동 X)
   - 마지막 v 번 정점까지 DFS 완료해가면서 max 지름 값 갱신

2. 자료구조
 - List<Pair>[], ArrayList<Pair>[]: 인접 리스트
   ex) lists[1]: 1번 정점과의 연결 정보 Pair (정점 번호, 간선 거리)들 저장
 - boolean[]: 방문 확인

3. 시간 복잡도
 - DFS 1번 수행: O(V + E)
   => V: 10^5, E: 대충 10^5 => 2 x 10^5
 => DFS 총 10^5 번(정점 개수만큼) 수행
 => 총 시간 복잡도: 2 x 10^5 x 10^5 = 2 x 10^10 >> 2억 (2초) => 시간 초과 !!!
*/

public class Main_Time_Over {
	static int v;					// 트리 정점(노드) 개수, 정점 번호: 1 ~ v
	static int maxR = Integer.MIN_VALUE;
	// 출력 값: 트리의 최대 지름 (두 정점 사이의 거리 중, 가장 긴 것)
	static List<Pair>[] lists;		// 인접 리스트
	static boolean[] check;

	/* vertex: 현재 정점 번호, totalDistance: 누적 거리 */
	static void dfs(int vertex, int totalDistance) {
		check[vertex] = true;

		List<Pair> list = lists[vertex];
		for (Pair p : list) {
			if (!check[p.vertex])
				dfs(p.vertex, totalDistance + p.distance);
		}

		maxR = Math.max(maxR, totalDistance);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		v = Integer.parseInt(br.readLine());
		check = new boolean[v + 1];
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

		for (int i = 1; i <= v; i++) {
			check = new boolean[v + 1];

			if (!check[i])
				dfs(i, 0);		// i 번 정점에서 탐색 시작
		}

		System.out.println(maxR);
	}
}

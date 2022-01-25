package DataStructure.Tree.트리의_부모_찾기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 *** 트리를 직접 구현 ??
    - 노드의 자식 개수에 제한이 없음
    - left, right child 만 존재하는 이진 트리 형태가 아닐 수 있음
    => 그래프 탐색 수행: DFS / BFS
    => n x n 의 인접 행렬 int[][] 로 구현하면 메모리 초과 발생하므로,
      인접 리스트 List<>[] 로 구현

 - 1번 루트 노드와 연결된 자식 노드들을 차례로 DFS 탐색하여
   배열에 각 노드들의 부모 노드를 저장해나감
 - 주의: 부모 - 자식 or 자식 - 부모 순서로 입력되지 않음. 그냥 연결 노드 정보만 순서없이 주어짐

2. 자료구조
 - List<Integer>[], ArrayList<Integer>[]: 인접 리스트
   e.g. list[1] = { 2, 4 } => 1번 루트 노드와 2, 4번 노드 연결됨
 - boolean[]: 부모 노드 방문 확인
   => 부모 노드의 방문을 확인하는 이유: 부모 노드는 항상 자식 노드보다 먼저 방문

3. 시간 복잡도
 - O(V + E)
   => V: n (최대 10^5)
*/

public class Main_DFS {
	static int n;			// 노드의 개수
	static List<Integer>[] lists;		// 인접 리스트
	static boolean[] checkParent;		// 부모 노드 방문 확인
	static int[] parents;				// 출력 값: 각 노드들의 부모 노드 index

	static void dfs(int idx) {
		checkParent[idx] = true;

		List<Integer> list = lists[idx];		// idx 번 노드와 연결된 노드들
		for (int nextIdx : list) {
			// nextIdx 를 아직 방문 안한 경우 => nextIdx 가 자식 노드
			// => 부모 노드 idx 는 자식 노드 nextIdx 보다 먼저 방문됨
			if (!checkParent[nextIdx]) {
				parents[nextIdx] = idx;
				dfs(nextIdx);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		checkParent = new boolean[n + 1];
		parents = new int[n + 1];
		lists = new ArrayList[n + 1];			// [1 ~ n] 사용
		for (int i = 1; i <= n; i++)
			lists[i] = new ArrayList<>();

		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int idx1 = Integer.parseInt(st.nextToken());
			int idx2 = Integer.parseInt(st.nextToken());

			lists[idx1].add(idx2);
			lists[idx2].add(idx1);
		}

		dfs(1);			// 루트 노드에서 탐색 시작

		// 2 ~ n 번 노드의 부모 노드 차례로 출력
		StringBuilder sb = new StringBuilder();
		for (int i = 2; i <= n; i++)
			sb.append(parents[i]).append("\n");
		System.out.println(sb);
	}
}

package DataStructure.Tree.가장_가까운_공통_조상;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 루트 노드 찾기
   => 입력 트리 노드 정보가 "부모 노드 - 자식 노드" 형태로 주어짐
   => 트리 노드 정보 입력할 때, 자식 노드들을 방문 처리
   => 입력 끝난 후, 체크되지 않은 노드가 루트 노드

 *** 최소 공통 조상 (Lowest Common Ancestor, LCA) 알고리즘 ***
 1) 모든 노드의 깊이, 2^0 번째 부모 (직계 부모) 저장
   - DFS 수행: void dfs(int parentNode, int depth)
 2) 모든 노드의 2^i 번째 부모 저장
   - DP 수행: void setParent()
   - DP 배열 (Sparse Table): int[][] parent = new int[n][21];
     => n: 트리 노드 개수, 21: 트리 깊이 20이면 충분
     => parent[nodeIdx][i]: 노드의 2^i 번째 부모
  - DP 점화식: parent[nodeIdx][i] == parent[ parent[nodeIdx][i-1] ][i-1]
    => 노드의 2^(i+1) 번째 부모 == (노드의 2^i 번째 부모)의 2^i 번째 부모
    => 원리) 2^(i+1) = 2 x 2^i = 2^i + 2^i
 3) 두 노드 n1, n2 의 깊이를 동일하게 맞춤
   - 노드 n2 가 더 깊은 노드가 되도록, 필요시 n1 과 n2 를 swap
   - 노드 n2 의 depth 가 n1 과 같아지도록, 부모 방향으로 거슬러 올라감
 4) 두 노드 n1, n2 의 부모가 같아질 때까지,
    반복적으로 두 노드를 부모 방향으로 거슬러 올라감

 !!! 3), 4) 과정에서 노드를 부모 방향으로 이동 시
    - 2^k 칸씩 이동
    - 이동 가능한 최대 칸 부터 이동
      e.g. 15칸 위로 이동해야 하는 경우: 8칸 => 4칸 => 2칸 => 1칸 씩 이동

2. 자료구조
 - List<Integer>[], ArrayList<Integer>[]: 인접 리스트
 - boolean[]: 노드 방문 확인
   => DFS 로 각 노드의 depth, 직계 부모(2^0 번째 부모) 저장
 - int[] depth: 각 노드의 깊이
 - int[][] parent = new int[n][21];
   => Sparse Table (DP 배열): 각 노드의 2^i 번째 부모 저장
      ex) parent[node][2]: node 의 2^2 번째 부모
   => n: 트리의 노드 개수, 21: 깊이 21 이면 왠만한 트리 커버
   => 메모리: 4 x n x 21 byte
      n 최대값 대입: 840,000 byte = 0.84 MB

3. 시간 복잡도
 1) DFS 1번 수행: O(V + E) = O(n + n-1) ~= O(2n)
   - n 최대값 대입: 2 x 10^4
 2) DP 수행 (노드의 2^i 번째 부모 값 채우기): O(Max Node Level x n)
   - n, MAX_LEVEL 최대값 대입: 20 x 10^4 = 2 x 10^5
 3) LCA 수행: O(log n)	(n: 트리의 노드 개수)
   - n 최대값 대입: 대충 log 2^13 = 13		=> 무시 될 정도
 => 최소 공통 조상을 출력하는 1개의 Query (테스트 케이스)에 대해,
    시간 복잡도 = (2 x 10^4) + (2 x 10^5)
*/

public class Main {
	static int t;						// 테스트케이스 개수
	static int n;						// 트리의 노드 개수 (노드: 1 ~ n)
	static int node1, node2;			// 입력 노드 2개

	static int root;
	static boolean[] checkChild;		// 루트 노드를 찾기 위한 체크 배열 => 자식 노드 체크
	static List<Integer>[] lists;

	static boolean[] check;				// DFS 노드 방문 확인 (depth 계산한 노드 방문 처리)
	static final int MAX_LEVEL = 21;
	static int[] depth;					// 각 노드의 depth
	static int[][] parent;				// DP 배열, parent[nodeIdx][i]: 노드의 2^i 번째 부모

	/* 각 노드의 깊이 저장, 직계 부모(2^0 번째 부모) 저장 */
	static void dfs(int parentNode, int d) {
		check[parentNode] = true;
		depth[parentNode] = d;

		List<Integer> list = lists[parentNode];
		for (int node : list) {
			if (!check[node]) {
				parent[node][0] = parentNode;		// node 의 직계 부모(2^0 번째 부모)
				dfs(node, d + 1);
			}
		}
	}

	/* DP 배열 parent[][] 에 각 노드의 2^i 번째 부모 저장 */
	static void setParent() {
		// parent[node][i]: node 의 2^i 번째 부모
		for (int i = 1; i < MAX_LEVEL; i++) {
			for (int node = 1; node <= n; node++)
				parent[node][i] = parent[ parent[node][i-1] ][i-1];
				// 2^(i+1) = 2^i + 2^i
		}
	}

	/* 두 노드 n1, n2 의 LCA 탐색 */
	static int LCA(int n1, int n2) {
		// 노드 n2 가 더 깊은 노드가 되도록 swap
		if (depth[n1] > depth[n2]) {
			int temp = n1;
			n1 = n2;
			n2 = temp;
		}

		// 두 노드 n1, n2 의 깊이가 같아지도록, n2 를 위로 올림
		for (int i = MAX_LEVEL - 1; i >= 0; i--) {
			// 큰 거리의 부모부터 확인 => 2^20, 2^19, ..., 2^2, 2^1, 2^0
			int jump = 1 << i;

			// e.g. n1, n2 의 깊이 13 차이인 경우: 13 >= 2^3	=> n2 의 2^3 번째 부모로 올라감
			if (depth[n2] - depth[n1] >= jump)
				n2 = parent[n2][i];				// n2 의 2^i 번째 부모로 올라감
		}

		if (n1 == n2)			// 두 노드가 같은 경우 => 두 노드 자체가 LCA
			return n1;

		for (int i = MAX_LEVEL - 1; i >= 0; i--) {
			// 두 노드의 부모가 같아질 때까지 반복
			if (parent[n1][i] == parent[n2][i])
				continue;

			// 두 노드의 2^i 번째 부모가 서로 다르면, 2^i 칸씩 위로 올림
			n1 = parent[n1][i];
			n2 = parent[n2][i];
		}

		return parent[n1][0];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		t = Integer.parseInt(br.readLine());
		for (int tc = 0; tc < t; tc++) {
			n = Integer.parseInt(br.readLine());
			lists = new ArrayList[n + 1];			// [1] ~ [n] 사용
			checkChild = new boolean[n + 1];
			check = new boolean[n + 1];
			depth = new int[n + 1];
			parent = new int[n + 1][MAX_LEVEL];		// [1][0] ~ [n][MAX_LEVEL - 1] 사용
			for (int i = 1; i <= n; i++)
				lists[i] = new ArrayList<>();

			// 인접 리스트 저장
			for (int i = 1; i < n; i++) {
				st = new StringTokenizer(br.readLine());
				int parentIdx = Integer.parseInt(st.nextToken());
				int childIdx = Integer.parseInt(st.nextToken());

				checkChild[childIdx] = true;
				lists[parentIdx].add(childIdx);
				lists[childIdx].add(parentIdx);
			}

			st = new StringTokenizer(br.readLine());
			node1 = Integer.parseInt(st.nextToken());
			node2 = Integer.parseInt(st.nextToken());

			// root 노드 찾기
			for (int i = 1; i <= n; i++) {
				if (!checkChild[i]) {
					root = i;
					break;
				}
			}

			dfs(root, 0);
			setParent();
			sb.append(LCA(node1, node2)).append("\n");
		}

		System.out.println(sb);
	}
}

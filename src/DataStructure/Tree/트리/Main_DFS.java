package DataStructure.Tree.트리;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 인접 리스트에 노드들 입력
 - 기존 트리의 Leaf 노드 개수를 구함
 - DFS 로 노드를 삭제해가면서, Leaf 노드가 삭제되면 기존 Leaf 노드 개수에서 빼줌
 - 예외 처리
   1) 삭제 노드가 루트 노드인 경우 => 전체 삭제 후, 남은 Leaf 노드 개수는 0
   2) 삭제 후, 삭제된 노드의 부모 노드가 Leaf 노드가 되는지 여부
     e.g. 일직선으로 뻗친 트리에서 중간 노드 삭제할 경우
         - 삭제 전, 기존 Leaf 노드 1개 -> 삭제 후, 남은 Leaf 노드 1개

2. 자료구조
 - List<Integer>[], ArrayList<Integer>[]: 인접 리스트
   => i 번 노드의 자식 노드들을 lists[i] 에 저장
   e.g. 예제 입력 1) lists[0] = { 1, 2 }, lits[1] = { 3, 4 }

3. 시간 복잡도
 - DFS 1번 수행
   => O(V + E) = O(50 + E)
*/

public class Main_DFS {
	static int n;					// 노드의 개수, 노드 번호: [0] ~ [n-1]
	static List<Integer>[] lists;	// 인접 리스트
	static int deleteNode;			// 지울 노드 번호
	static int leafCount;			// 출력 값: 노드 삭제 후, 남은 Leaf 노드 개수
	static int rootNode;			// 루트 노드 번호

	/* deleteNode: 삭제할 노드 */
	static void dfs(int delteNode) {
		List<Integer> list = lists[delteNode];		// 삭제할 노드의 자식 노드들
		if (list.isEmpty())			// 삭제할 노드가 Leaf 노드인 경우
			leafCount--;

		for (int child : list)
			dfs(child);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		lists = new ArrayList[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			lists[i] = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int parent = Integer.parseInt(st.nextToken());
			if (parent == -1) {
				rootNode = i;
				continue;
			}
			lists[parent].add(i);		// parent 노드의 자식 노드들 저장
		}
		deleteNode = Integer.parseInt(br.readLine());

		// 예외 처리 1) deleteNode 가 루트 노드인 경우 => 삭제 후, 남은 Leaf 노드 개수는 0
		if (deleteNode == rootNode) {
			System.out.println(0);
			return;
		}

		// 기존 트리의 Leaf 노드 개수 계산
		for (int i = 0; i < n; i++) {
			if (lists[i].isEmpty())
				leafCount++;
		}

		int parentNode = -1;				// deleteNode 의 부모 노드
		for (int i = 0; i < n; i++) {
			for (int node : lists[i]) {
				if (node == deleteNode) {
					parentNode = i;
					break;
				}
			}
		}

		lists[parentNode].remove(Integer.valueOf(deleteNode));		// deleteNode 삭제
		dfs(deleteNode);

		// 예외 처리 2) deleteNode 의 부모 노드가 Leaf 노드가 되는지 확인
		if (lists[parentNode].isEmpty())
			leafCount++;

		System.out.println(leafCount);
	}
}

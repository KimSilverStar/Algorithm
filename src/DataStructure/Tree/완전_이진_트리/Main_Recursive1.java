package DataStructure.Tree.완전_이진_트리;
import java.io.*;
import java.util.StringTokenizer;

/*
- 완전 이진 트리: depth k 이면, 총 2^k - 1 개 노드
- 방문 순서: left child -> parent -> right child
  => 중위 순회 (Inorder Traversal)
- 중위 순회의 순서가 주어질 때, 트리를 출력 (depth 별로 출력)
*/

/*
1. 아이디어
 - 중위 순회 순서에서 루트 노드: 중간에 방문
   1) 루트 노드
     - 총 노드 개수 (2^k - 1) / 2 번째에 방문한 노드가 루트 노드
     - inorder[(2^k - 1) / 2] 가 tree[1] 이 됨
   2) 찾은 부모 노드를 기준으로, left / right subtree 각각에서 부모 노드찾음
     - 찾은 부모 노드가 [i]이면
       => left subtree 에서의 부모 노드는 [0] ~ [i-1] 의 중간 index
       => right subtree 에서의 부모 노드는 [i+1] ~ [끝] 의 중간 index
       !!! 이분 탐색 (Binary Search) 하듯이 중간 index (부모) 기준으로 2개 subtree
     => recursive(startIdx, endIdx) 로 중위 순회에서 부모 index 찾음
     => 재귀 종료 조건: Leaf 노드까지 내려간 경우
 - 트리에서 부모 index 가 [i] 이면		(루트 노드는 [1]에서 시작)
   => left child: [i * 2], right child: [i * 2 + 1]

2. 자료구조
 - int[]: 입력 값, 중위 순회 순서 저장
   => [0 ~ ] 사용
 - int[]: 출력 값, 트리 저장
   => [1 ~ ] 사용

3. 시간 복잡도
 - 1개 부모 노드에서 재귀 호출 2번 수행
   => 전체 (2^k - 1)개 노드에서, 대충 총 2(2^k - 1) 번 재귀 호출 발생
   => k 최대값 대입: 2(2^10 - 1) = 2(1,024 - 1) = 2,046 << 1억 (1초)
*/

public class Main_Recursive1 {
	static int k;				// 완전 이진 트리의 depth
	static int nodeCount;		// 총 노드 개수: 2^k - 1
	static int[] inorder;		// 입력: Inorder Traversal (중위 순회) 방문 순서
	static int[] tree;			// 출력: 트리 (루트 노드: tree[1])

	/* startIdx, endIdx: 중위 순회 순서 inorder[]에서의 index */
	/* treeIdx: 출력 배열 tree[]에 채울 index */
	static void recursive(int startIdx, int endIdx, int treeIdx) {
		// 주어진 범위 start ~ end 에서, 중위 순회 inorder[] 의 부모 노드를 찾음
		int parentIdx = (startIdx + endIdx) / 2;
		tree[treeIdx] = inorder[parentIdx];

		int leftChildIdx = treeIdx * 2;
		int rightChildIdx = treeIdx * 2 + 1;

		// 재귀 종료 조건: Leaf 노드까지 내려간 경우
//		if (leftChildIdx > nodeCount || rightChildIdx > nodeCount)
		if (startIdx == endIdx)
				return;

		recursive(startIdx, parentIdx - 1, leftChildIdx);	// left subtree
		recursive(parentIdx + 1, endIdx, rightChildIdx);		// right subtree
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		k = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		nodeCount = (int)Math.pow(2, k) - 1;
		inorder = new int[nodeCount];			// [0 ~ 노드 개수 - 1] 사용
		tree = new int[nodeCount + 1];			// [1 ~ 노드 개수] 사용
		for (int i = 0; i < inorder.length; i++)
			inorder[i] = Integer.parseInt(st.nextToken());

		// 루트 노드 tree[1] 에서부터 시작
		recursive(0, nodeCount - 1, 1);

		// 트리의 Depth (Level) 단위로 개행하여 출력
		StringBuilder sb = new StringBuilder();
		for (int level = 1; level <= k; level++) {
			// 현재 level 까지 속하는 노드 개수
			// e.g. level 2 까지 => 2^2 - 1 = 3개
			int currentNodeCount = (int)Math.pow(2, level) - 1;
			int prevNodeCount = (int)Math.pow(2, level - 1) - 1;

			for (int i = prevNodeCount + 1; i <= currentNodeCount; i++)
				sb.append(tree[i]).append(" ");
			sb.append("\n");
		}
		System.out.println(sb);
	}
}

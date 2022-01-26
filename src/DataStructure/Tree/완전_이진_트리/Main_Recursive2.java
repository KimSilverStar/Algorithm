package DataStructure.Tree.완전_이진_트리;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 기본적으로 부모 노드를 찾는 로직은 Main_Recursive1 과 동일
 - 차이점은 부모 노드를 찾아서 int[] tree 에 저장하는 게 아닌,
   부모 노드를 찾으면서 StringBuilder[]에 트리의 Level 단위로 저장해나감


 - 중위 순회 순서에서 루트 노드: 중간에 방문
   1) 루트 노드
     - 총 노드 개수 (2^k - 1) / 2 번째에 방문한 노드가 루트 노드
     - inorder[(2^k - 1) / 2] 가 루트 노드
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
 - StringBuilder[]: 출력 값, 트리 Level 단위로 노드들 저장

3. 시간 복잡도
 - 1개 부모 노드에서 재귀 호출 2번 수행
   => 전체 (2^k - 1)개 노드에서, 대충 총 2(2^k - 1) 번 재귀 호출 발생
   => k 최대값 대입: 2(2^10 - 1) = 2(1,024 - 1) = 2,046 << 1억 (1초)
*/

public class Main_Recursive2 {
	static int k;					// 완전 이진 트리의 depth
	static int nodeCount;			// 총 노드 개수: 2^k - 1
	static int[] inorder;			// 입력: Inorder Traversal (중위 순회) 방문 순서
	static StringBuilder[] sbArr;
	// 출력: 트리 Level 단위로 노드들 저장
	// e.g. sbArr[1] => Level 1 루트 노드

	/* startIdx, endIdx: 중위 순회 순서 inorder[]에서의 index */
	/* level: 트리의 level, 저장할 StringBuilder[] 의 index */
	static void recursive(int startIdx, int endIdx, int level) {
		// 재귀 종료 조건: Leaf 노드까지 내려간 경우
		if (level > k)
			return;

		int parentIdx = (startIdx + endIdx) / 2;
		sbArr[level].append(inorder[parentIdx]).append(" ");

		recursive(startIdx, parentIdx - 1, level + 1);		// left subtree
		recursive(parentIdx + 1, endIdx, level + 1);		// right subtree
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		k = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		nodeCount = (int)Math.pow(2, k) - 1;
		inorder = new int[nodeCount];					// [0 ~ 노드 개수 - 1] 사용
		sbArr = new StringBuilder[nodeCount + 1];		// [1 ~ 노드 개수] 사용
		for (int i = 1; i < sbArr.length; i++)
			sbArr[i] = new StringBuilder();
		for (int i = 0; i < inorder.length; i++)
			inorder[i] = Integer.parseInt(st.nextToken());

		// Level 1, 루트 노드에서부터 시작
		recursive(0, nodeCount - 1, 1);

		// 트리의 Depth (Level) 단위로 개행하여 출력
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < sbArr.length; i++)
			sb.append(sbArr[i]).append("\n");
		System.out.println(sb);
	}
}

package DataStructure.Tree.완전_이진_트리;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 기본적으로 부모 노드를 찾는 로직은 Main_Recursive1, 2 와 동일
 - 차이점은 부모 노드를 찾는 로직에서 재귀 함수가 아닌, Queue 를 사용
   => 트리의 Level 단위로 Queue 에 노드 추가


 - 중위 순회 순서에서 루트 노드: 중간에 방문
   1) 루트 노드
     - 총 노드 개수 (2^k - 1) / 2 번째에 방문한 노드가 루트 노드
     - inorder[(2^k - 1) / 2] 가 루트 노드
   2) 찾은 부모 노드를 기준으로, left / right subtree 각각에서 부모 노드찾음
     - 찾은 부모 노드가 [i]이면
       => left subtree 에서의 부모 노드는 [0] ~ [i-1] 의 중간 index
       => right subtree 에서의 부모 노드는 [i+1] ~ [끝] 의 중간 index
       !!! 이분 탐색 (Binary Search) 하듯이 중간 index (부모) 기준으로 2개 subtree
 - 트리에서 부모 index 가 [i] 이면		(루트 노드는 [1]에서 시작)
   => left child: [i * 2], right child: [i * 2 + 1]

2. 자료구조
 - int[]: 입력 값, 중위 순회 순서 저장
   => [0 ~ ] 사용
 - Queue<Pair>, LinkedList<Pair>: 트리 Level 단위로 inorder[]에서 탐색할 Pair (startIdx, endIdx) 저장

3. 시간 복잡도
*/

class Pair {
	public int startIdx, endIdx;

	public Pair(int startIdx, int endIdx) {
		this.startIdx = startIdx;
		this.endIdx = endIdx;
	}
}

public class Main_Queue {
	static int k;					// 완전 이진 트리의 depth
	static int nodeCount;			// 총 노드 개수: 2^k - 1
	static int[] inorder;			// 입력: Inorder Traversal (중위 순회) 방문 순서

	static StringBuilder sb = new StringBuilder();		// 출력 값: 트리의 Level 단위로 노드들 저장
	static Queue<Pair> queue = new LinkedList<>();		// 트리의 Level 단위로 노드들을 Queue 에 추가

	static void solution() {
//		int level = 1;			// Level 1 루트 노드에서부터 시작

		while (!queue.isEmpty()) {
//			if (level > k)
//				break;

			// 현재 트리 Level 의 노드 개수
			int currentNodeCount = queue.size();
			for (int i = 0; i < currentNodeCount; i++) {
				Pair p = queue.remove();

				int parentIdx = (p.startIdx + p.endIdx) / 2;
				sb.append(inorder[parentIdx]).append(" ");

				// p.startIdx == p.endIdx 인 경우, Leaf 노드까지 내려감
				if (p.startIdx != p.endIdx) {
					queue.add(new Pair(p.startIdx, parentIdx - 1));		// left subtree
					queue.add(new Pair(parentIdx + 1, p.endIdx));		// right subtree
				}
			}

			sb.append("\n");
//			level++;
		}
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
		for (int i = 0; i < inorder.length; i++)
			inorder[i] = Integer.parseInt(st.nextToken());

		// 루트 노드에서부터 시작
		queue.add(new Pair(0, nodeCount - 1));
		solution();

		System.out.println(sb);
	}
}

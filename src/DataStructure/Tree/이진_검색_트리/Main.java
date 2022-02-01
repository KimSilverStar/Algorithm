package DataStructure.Tree.이진_검색_트리;
import java.io.*;

/*
- 이진 탐색 트리 (Binary Search Tree)
- 입력: 전위 순회 (Prorder)
- 출력: 후위 순회 (Postorder)
*/

/*
1. 아이디어
 - BST: 부모 노드를 기준으로
   Left Subtree 는 모두 부모 노드보다 작고,
   Right Subtree 는 모두 부모 노드보다 큼

 - 후위 순회 (Postorder): Left Child -> Right Child -> Parent
 - 입력 preorder 배열에서 루트(부모) 노드를 정하고
   Left Subtree, Right Subtree 에 대해 재귀호출
   1) Left Subtree: postorder(startIdx + 1, 부모 노드보다 큰 노드의 idx - 1)
   2) Right Subtree: postorder(부모 노드보다 큰 노드의 idx + 1, endIdx)
   3) 부모 노드 preorder[startIdx] 출력

2. 자료구조
 - int[]: 입력, 전위 순회 순서
   => 최대 노드 개수 10^5 개
   => int[10^5]: 4 x 10^5 byte = 0.4 MB

3. 시간 복잡도
 - 부모 노드 1개에 대해 재귀 호출 2번 수행
 => 전체 최대 노드 수 10^5에 대해 대략 재귀 호출 2번 수행
 => 총 시간 복잡도: 2 x 10^5 << 1억 (1초)
*/

public class Main {
	static final int MAX_COUNT = 10000;
	static int[] preorder = new int[MAX_COUNT];			// 입력: 전위 순회
	static StringBuilder sb = new StringBuilder();		// 출력 값, 후위 순회 저장

	static void postorder(int startIdx, int endIdx) {
		if (startIdx > endIdx)
			return;

		int rightChildIdx;
		for (rightChildIdx = startIdx; rightChildIdx <= endIdx; rightChildIdx++) {
			if (preorder[startIdx] < preorder[rightChildIdx])		// Parent < Right Child
				break;
		}

		postorder(startIdx + 1, rightChildIdx - 1);			// Left Subtree
		postorder(rightChildIdx, endIdx);					// Right Subtree
		sb.append(preorder[startIdx]).append("\n");			// Parent 출력
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		int idx = 0;
		while (true) {
			String input = br.readLine();
			if (input == null || input.equals(""))
				break;
			preorder[idx++] = Integer.parseInt(input);
		}

		postorder(0, idx - 1);

		System.out.println(sb);
	}
}

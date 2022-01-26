package DataStructure.Tree.트리_순회;
import java.io.*;
import java.util.*;

/*
- 이진 트리, 노드 개수 n
- 각 노드와 left / right child 입력
- 루트 노드: 'A' 고정

1. 전위 순회: Parent -> Left Child -> Right Child
2. 중위 순회: Left Child -> Parent -> Right Child
3. 후위 순회: Left Child -> Right Child -> Parent
*/

/*
1. 아이디어
 - List<Character>[] 에 각 노드의 left, right child 저장
   ex) lists[0]: 루트 노드 'A'의 left child, right child 저장
       lists[1]: 루트 노드 'B'의 ~

 !!! char[] tree 에 전체 트리(노드들)를 저장하지 않은 이유
    - 입력 트리의 형태가 "완전 이진 트리"가 아닌, 그냥 이진 트리임
    - 트리가 최악으로 Unbalance 할 수 있음 (e.g. 오른쪽으로 길게 뻗친 트리 모양 등)
    - 최악의 경우로 트리가 오른쪽으로만 길게 뻗친 형태 (Right Child 만 존재)인 경우,
      char[]의 index => 루트 [1], [3], [7], [15], ..., [2^26 - 1] (n 최대 노드 개수 26)
      (부모 노드가 [i]이면, Left Child 는 [i * 2], Right Child 는 [i * 2 + 1])
    => char[] 의 메모리: 67,108,863 x 2 byte = 134,217,726 byte ~= 대략 134 MB >> 128 MB

 - 전위 / 중위 / 후위 순회
   => 재귀 함수

2. 자료구조
 - List<Character>[], ArrayList<Character>[]: 각 노드의 left, right child 저장

3. 시간 복잡도
 - 1개 부모 노드에서 재귀 호출 2번 수행
   => 대충 전체 n 개 노드에서, 재귀 호출 2번 모두 수행
   => 트리 순회 1번 당, O(2 x n)
 => 트리 순회 총 3번이므로, 총 시간 복잡도: O(6 x n)
 => n 최대값 대입: 6 x 26 = 156
*/

public class Main {
	static int n;						// 이진 트리의 노드 개수
	static List<Character>[] lists;		// 각 노드의 left, right child 저장
	static StringBuilder sb = new StringBuilder();		// 출력 값

	/* 전위 순회: Parent -> Left Child -> Right Child */
	static void preorder(int idx) {
		char parent = (char)('A' + idx);		// 1) Parent 방문
		sb.append(parent);

		List<Character> list = lists[idx];
		char leftChild = list.get(0);
		char rightChild = list.get(1);

		if (leftChild != '.')
			preorder(leftChild - 'A');		// 2) Left Child 방문

		if (rightChild != '.')
			preorder(rightChild - 'A');		// 3) Right Child 방문
	}

	/* 중위 순회: Left Child -> Parent -> Right Child */
	static void inorder(int idx) {
		List<Character> list = lists[idx];
		char leftChild = list.get(0);
		char rightChild = list.get(1);

		if (leftChild != '.')
			inorder(leftChild - 'A');		// 1) Left Child 방문

		char parent = (char)('A' + idx);		// 2) Parent 방문
		sb.append(parent);

		if (rightChild != '.')
			inorder(rightChild - 'A');		// 3) Right Child 방문
	}

	/* 후위 순회: Left Child -> Right Child -> Parent */
	static void postorder(int idx) {
		List<Character> list = lists[idx];
		char leftChild = list.get(0);
		char rightChild = list.get(1);

		if (leftChild != '.')
			postorder(leftChild - 'A');		// 1) Left Child 방문

		if (rightChild != '.')
			postorder(rightChild - 'A');	// 2) Right Child 방문

		char parent = (char)('A' + idx);		// 3) Parent 방문
		sb.append(parent);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		lists = new ArrayList[n];				// [0 ~ n - 1]
		for (int i = 0; i < n; i++)
			lists[i] = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			char parent = st.nextToken().charAt(0);
			lists[parent - 'A'].add(st.nextToken().charAt(0));		// left child
			lists[parent - 'A'].add(st.nextToken().charAt(0));		// right child
		}

		// 루트 노드 'A' 에서 시작
		preorder(0);
		sb.append("\n");

		inorder(0);
		sb.append("\n");

		postorder(0);
		sb.append("\n");

		System.out.println(sb);
	}
}

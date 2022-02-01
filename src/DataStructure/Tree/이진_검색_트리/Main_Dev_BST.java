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

 - 이진 탐색 트리 (Binary Search Tree)를 직접 구현하여 트리 저장,
   후위 순회를 재귀 함수로 구현
 - 입력이 전위 순회 순서로 들어오므로, 부모 노드를 기준으로 트리 저장
   1) 새로 insert 하려는 노드 값 < 부모 노드인 경우
     case 1) 부모 노드의 Left Child 가 없는 경우
            - 새로 Left Child 를 생성하여 삽입
     case 2) 부모 노드의 Left Child 가 이미 있는 경우
            - 이미 존재하는 Left Child 로 내려가서 다시 비교 및 삽입 => 재귀 호출
   2) 새로 insert 하려는 노드 값 > 부모 노드인 경우
     case 1) 부모 노드의 Right Child 가 없는 경우
            - 새로 Right Child 를 생성하여 삽입
     case 2) 부모 노드의 Right Child 가 이미 있는 경우
            - 이미 존재하는 Right Child 로 내려가서 다시 비교 및 삽입 => 재귀 호출

 - 후위 순회 (Postorder): Left Child -> Right Child -> Parent

2. 자료구조
 - BinarySearchTree: 이진 탐색 트리 구현

3. 시간 복잡도
 1) 이진 탐색 트리 저장
   - BST 의 탐색 / 삽입 시간 복잡도: O(H)	(H: BST 의 높이)
   - BST 가 완전 이진 트리 (균형이 완벽하게 잡힌 이진 트리)인 경우,
     O(H) = O(log2 n)	(n: 노드 개수)
   - Worst) BST 가 균형이 잡히지 않은 경우 (BST 가 왼쪽 or 오른쪽으로만 길게 뻗은 경우),
     O(H) => H 최대값으로 노드의 개수 10^5 개 대입
     	  => BST 저장 시간 복잡도: 10^5
 2) 후위 순회 출력
   - 부모 노드 1개에 대해 재귀 호출 2번 수행
   => 전체 최대 노드 수 10^5에 대해 대략 재귀 호출 2번 수행
   => 후위 순회 출력 시간 복잡도: 2 x 10^5

 => 전체 시간 복잡도 = BST 저장 후, 후위 순회 출력
    = 10^5 + (2 x 10^5) = 3 x 10^5 << 1억 (1초)
*/

public class Main_Dev_BST {
	static BinarySearchTree bst;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		int rootValue = Integer.parseInt(br.readLine());
		bst = new BinarySearchTree(new Node(rootValue));
		while (true) {
			String input = br.readLine();
			if (input == null || input.equals(""))
				break;

			bst.insert(Integer.parseInt(input));
		}

		bst.postorder(bst.root);
		System.out.println(bst.sb);
	}
}

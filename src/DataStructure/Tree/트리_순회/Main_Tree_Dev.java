package DataStructure.Tree.트리_순회;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - Tree 를 직접 구현
   1) 트리 추가
     - Left Child 추가
     - Right Child 추가
   2) 전위, 중위, 후위 순회
     - 재귀 함수

   !!! 입력 트리가 이진 트리 형태이고, 입력 노드 연결 정보가
       Parent - Left Child - Right Child 의 순서로 주어지므로, Tree 직접 구현해서 풀이 가능

2. 자료구조
 - Tree: Node 들을 갖는 트리 구현 클래스

3. 시간 복잡도
 - 1개 부모 노드에서 재귀 호출 2번 수행
   => 대충 전체 n 개 노드에서, 재귀 호출 2번 모두 수행
   => 트리 순회 1번 당, O(2 x n)
 => 트리 순회 총 3번이므로, 총 시간 복잡도: O(6 x n)
 => n 최대값 대입: 6 x 26 = 156
*/

class Node {
	public char data;
	public Node leftChild;
	public Node rightChild;

	public Node(char data) {
		this.data = data;
	}
}

class Tree {
	public Node root;
	public int size;

	public Tree(Node root) {
		this.root = root;
		size++;
	}

	/* 트리에서 parentData 값을 갖는 parent 노드를 찾아서,
	   leftData 값을 갖는 leftChild 노드 추가 */
	public void addLeft(char parentData, char leftData) {
		if (root == null) {
			root = new Node(parentData);				// Root 추가
			root.leftChild = new Node(leftData);		// Left Child 추가
			size += 2;
		}
		else
			addLeft(root, parentData, leftData);
	}

	public void addLeft(Node node, char parentData, char leftData) {
		if (node == null)						// 못 찾은 경우
			return;
		else if (node.data == parentData) {		// 찾은 경우
			node.leftChild = new Node(leftData);
			size++;
		}
		else {
			addLeft(node.leftChild, parentData, leftData);
			addLeft(node.rightChild, parentData, leftData);
		}
	}

	/* 트리에서 parentData 값을 갖는 parent 노드를 찾아서,
	   rightData 값을 갖는 rightChild 노드 추가 */
	public void addRight(char parentData, char rightData) {
		if (root == null) {
			root = new Node(parentData);				// Root 추가
			root.rightChild = new Node(rightData);		// Right Child 추가
			size += 2;
		}
		else
			addRight(root, parentData, rightData);
	}

	public void addRight(Node node, char parentData, char rightData) {
		if (node == null)						// 못 찾은 경우
			return;
		else if (node.data == parentData) {		// 찾은 경우
			node.rightChild = new Node(rightData);
			size++;
		}
		else {
			addRight(node.leftChild, parentData, rightData);
			addRight(node.rightChild, parentData, rightData);
		}
	}

	public StringBuilder sb;

	/* 전위 순회: Parent -> Left Child -> Right Child */
	public void preorder(Node parent) {
		sb.append(parent.data);
		if (parent.leftChild != null)
			preorder(parent.leftChild);
		if (parent.rightChild != null)
			preorder(parent.rightChild);
	}

	/* 중위 순회: Left Child -> Parent -> Right Child */
	public void inorder(Node parent) {
		if (parent.leftChild != null)
			inorder(parent.leftChild);
		sb.append(parent.data);
		if (parent.rightChild != null)
			inorder(parent.rightChild);
	}

	/* 후위 순회: Left Child -> Right Child -> Parent */
	public void postorder(Node parent) {
		if (parent.leftChild != null)
			postorder(parent.leftChild);
		if (parent.rightChild != null)
			postorder(parent.rightChild);
		sb.append(parent.data);
	}
}

public class Main_Tree_Dev {
	static int n;						// 이진 트리의 노드 개수
	static Tree tree;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		tree = new Tree(new Node('A'));
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			char parentData = st.nextToken().charAt(0);
			char leftData = st.nextToken().charAt(0);
			char rightData = st.nextToken().charAt(0);

			if (leftData != '.')
				tree.addLeft(parentData, leftData);
			if (rightData != '.')
				tree.addRight(parentData, rightData);
		}

		tree.sb = new StringBuilder();
		tree.preorder(tree.root);
		tree.sb.append("\n");

		tree.inorder(tree.root);
		tree.sb.append("\n");

		tree.postorder(tree.root);
		tree.sb.append("\n");
		System.out.println(tree.sb);
	}
}

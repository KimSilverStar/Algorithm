package DataStructure.Tree.이진_검색_트리;

class Node {
	public int data;			// 부모 노드의 값
	public Node leftChild, rightChild;

	public Node(int data) {
		this.data = data;
		this.leftChild = null;
		this.rightChild = null;
	}
}

class BinarySearchTree {
	public Node root;
	public StringBuilder sb = new StringBuilder();

	public BinarySearchTree(Node root) {
		this.root = root;
	}

	public void insert(int data) {
		// 루트 노드에서부터 삽입할 위치를 재귀 호출로 찾아서, 새로운 노드 삽입
		root = insertBST(root, data);
	}

	private Node insertBST(Node temp, int data) {
		if (temp == null)
			temp = new Node(data);
		else if (temp.data > data)			// Left Subtree
			temp.leftChild = insertBST(temp.leftChild, data);
		else if (temp.data < data)			// Right Subtree
			temp.rightChild = insertBST(temp.rightChild, data);

		return temp;
	}

	public void postorder(Node node) {
		if (node == null)
			return;

		postorder(node.leftChild);				// Left Subtree
		postorder(node.rightChild);				// Right Subtree
		sb.append(node.data).append("\n");		// Parent 출력
	}
}

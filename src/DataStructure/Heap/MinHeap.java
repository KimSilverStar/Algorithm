package DataStructure.Heap;

/*
* Min Heap 구현
 - Min 노드가 루트 노드 arr[1]에 위치
   (쉬운 구현을 위해 arr[0]은 사용 X)
 - 부모, 자식 index 번호 관계
   => 왼쪽 자식의 index = 부모의 index * 2
   => 오른쪽 자식의 index = (부모의 index * 2) + 1
   => 부모의 index = 자식의 index / 2

1. void add(int item)
 1) 맨 뒤 arr[size+1]에 새로운 item 추가
 2) 새로운 노드 arr[size+1]을 부모 노드들과 비교해가면서 힙 정렬
   - ReHeapification Upward

2. int remove()
 1) 루트 노드 arr[1]을 삭제
 2) 빈 루트 노드 arr[1]를 마지막 노드 arr[size]로 채움
 2) 루트 노드 arr[1] ~ arr[size-1] 까지 내려가면서 부모, 자식 비교해가면서 힙 정렬
   - ReHeapification Downward
*/

public class MinHeap {
	private int[] arr;		// arr[1 ~ size] 사용
	private int size = 0;

	public MinHeap(int length) {
		arr = new int[length];
	}

	public void add(int item) {
		arr[++size] = item;		// 맨 뒤에 새로운 노드 추가

		// Upward
		for (int i = size; i > 1; i /= 2) {
			if (arr[i / 2] > arr[i])		// 부모 > 자식
				swap(i / 2, i);
			else
				break;
		}
	}

	public int remove() {
		if (size == 0)
			throw new IllegalStateException();

		int root = arr[1];		// 삭제 및 반환할 루트 노드
		arr[1] = arr[size--];	// 빈 루트 노드에 마지막 노드 채움

		// Downward
		for (int i = 1; i * 2 <= size; ) {
			if (arr[i] < arr[i * 2] &&
					arr[i] < arr[i * 2 + 1])	// 부모 < 왼쪽 자식, 오른쪽 자식 => 정렬된 경우
				break;
			else if (arr[i * 2] < arr[i * 2 + 1]) {		// 왼쪽 자식 < 오른쪽 자식
				swap(i, i * 2);		// 더 작은 왼쪽 자식이랑 부모 바꿈
				i = i * 2;
			}
			else {		// 왼쪽 자식 > 오른쪽 자식
				swap(i, i * 2 + 1);
				i = i * 2 + 1;
			}
		}

		return root;
	}

	public boolean isEmpty() { return size == 0; }
	public int size() { return size; }

	private void swap(int idx1, int idx2) {
		int temp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = temp;
	}
}

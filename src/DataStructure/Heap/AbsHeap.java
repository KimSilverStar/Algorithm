package DataStructure.Heap;

/* 절대 값 힙 구현
 - 최소 절대값이 루트 노드 arr[1]에 위치
   (쉬운 구현을 위해 arr[0]은 사용 X)
   *** 절대값이 같은 경우, 값이 더 작은 수가 루트 노드에 위치
 - 부모, 자식 index 번호 관계
   => 왼쪽 자식의 index = 부모 index * 2
   => 오른쪽 자식의 index = (부모 inedx * 2) + 1
   => 부모의 index = 자식의 index / 2

1. void add(int item)
 1) 맨뒤 arr[size+1]에 새로운 item 추가
 2) 새로운 노드 arr[size+1]을 부모 노드들과 비교해가면서 힙 정렬
   - ReHeapification Upward

2. int remove()
 1) 루트 노드 arr[1]을 삭제
 2) 빈 루트 노드 arr[1]를 마지막 노드 arr[size]로 채움
 3) 루트 노드 arr[1] ~ arr[size-1] 까지 내려가면서 부모, 자식 비교해가면서 힙 정렬
   - ReHeapification Downward
*/

public class AbsHeap {
	private int[] arr;
	private int size;

	public AbsHeap(int length) {
		arr = new int[length];
	}

	public void add(int item) {
		arr[++size] = item;		// 맨 뒤에 새로운 노드 추가

		// ReHeapification Upward
		for (int i = size; i > 1; i /= 2) {
			int absParent = Math.abs(arr[i / 2]);
			int absChild = Math.abs(arr[i]);

			if (absParent > absChild)			// |부모| > |자식|
				swap(i / 2, i);
			else if (absParent == absChild) {	// |부모| == |자식|
				if (arr[i / 2] > arr[i])		// 부모 > 자식
					swap(i / 2, i);
			}
			else
				break;
		}
	}

	public int remove() {
		if (size == 0)
			throw new IllegalStateException();

		int root = arr[1];		// 삭제 및 반환할 기존 루트 노드
		arr[1] = arr[size--];	// 빈 루트 노드에 마지막 노드 채움

		// ReHeapification Downward
		for (int i = 1; i * 2 <= size; ) {
			int parentAbs = Math.abs(arr[i]);
			int leftChildAbs = Math.abs(arr[i * 2]);
			int rightChildAbs = Math.abs(arr[i * 2 + 1]);

			// |부모| < |자식| 또는
			// |부모| == |자식| && 부모 < 자식
			boolean isLeftSorted = (parentAbs < leftChildAbs) ||
					(parentAbs == leftChildAbs && arr[i] < arr[i * 2]);
			boolean isRightSorted = (parentAbs < rightChildAbs) ||
					(parentAbs == rightChildAbs && arr[i] < arr[i * 2 + 1]);

			// 정렬된 경우
			if (isLeftSorted && isRightSorted)
				break;
			// 왼쪽 자식의 절대값 < 오른쪽 자식의 절대값
			else if (leftChildAbs < rightChildAbs) {
				swap(i, i * 2);			// 왼쪽 자식과 부모 교체
				i = i * 2;
			}
			// 왼쪽 자식의 절대값 > 오른쪽 자식의 절대값
			else if (leftChildAbs > rightChildAbs) {
				swap(i, i * 2 + 1);		// 오른쪽 자식과 부모 교체
				i = i * 2 + 1;
			}
			// 왼쪽 자식의 절대값 == 오른쪽 자식의 절대값
			else {
				// 값이 더 작은 자식과 부모 교체
				if (arr[i * 2] < arr[i * 2 + 1]) {
					swap(i, i * 2);
					i = i * 2;
				}
				else {
					swap(i, i * 2 + 1);
					i = i * 2 + 1;
				}
			}
		}

		return root;
	}

	public int size() { return size; }
	public boolean isEmpty() { return size == 0; }

	private void swap(int idx1, int idx2) {
		int temp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = temp;
	}
}

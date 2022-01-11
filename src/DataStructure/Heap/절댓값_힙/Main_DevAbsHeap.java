package DataStructure.Heap.절댓값_힙;
import DataStructure.Heap.AbsHeap;

import java.io.*;

/*
* 절대값 힙
 - absHeap.add(x): 배열에 정수 x (x != 0)를 추가
 - absHeap.remove(): 배열에서 절대값이 가장 작은 값 반환 및 제거
   (절대값이 같은 원소가 있으면, 그 중에서 값이 가장 작은 수 선택)

- 입력 정수 x
  1) x != 0 인 경우, AbsHeap 에 x 추가
  2) x == 0 인 경우, AbsHeap 에서 최소 절대값 출력 후 제거
     * 빈 배열이면 0 출력

1. 아이디어
 - AbsHeap 직접 구현
   => 배열로 구현, add(), remove()
 - 루트 노드에 최소 절대값 위치

2. 자료구조
 - AbsHeap: 구현한 절대값 힙

3. 시간 복잡도
 - Heap (Priority Queue)의 시간 복잡도
   => 삽입 / 삭제: O(log n)
*/

public class Main_DevAbsHeap {
	static int n;		// 수행할 연산 개수
	static int x;		// 정수 x
	static AbsHeap absHeap;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());
		absHeap = new AbsHeap(n + 1);

		for (int i = 0; i < n; i++) {
			x = Integer.parseInt(br.readLine());

			if (x != 0)
				absHeap.add(x);
			else {		// x == 0
				if (!absHeap.isEmpty())
					System.out.println(absHeap.remove());
				else
					System.out.println(0);
			}
		}
	}
}

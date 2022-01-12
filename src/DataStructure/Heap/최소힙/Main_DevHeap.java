package DataStructure.Heap.최소힙;
import DataStructure.Heap.MinHeap;

import java.io.*;

/*
- n: 수행해야 할 연산 개수
- x
  => x > 0 인 경우, 배열에 x 추가
  => x == 0 인 경우, 배열에서 최소값 제거 및 출력
     + 빈 배열이면 0 출력

1. 아이디어
 - Min Heap 직접 구현
   => 배열로 구현, add(), remove()
 - 루트 노드에 min 값 위치

2. 자료구조
 - MinHeap: 구현한 최소 힙

3. 시간 복잡도
 - PriorityQueue / Heap 의 시간 복잡도
   => 삽입 / 삭제: O(log n)		(n: 노드 개수)
 - 최대 총 n번 삽입 / 삭제 발생
   => 대충 최대 n log n
   => n 최대값 대입: 10^5 x log 10^5 = 5 x 10^5 << 1억 (1초)
*/

public class Main_DevHeap {
	static int n;			// 연산의 개수
	static int x;			// 자연수 또는 0 입력
	static MinHeap minHeap;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringBuilder sb = new StringBuilder();

		n = Integer.parseInt(br.readLine());
		minHeap = new MinHeap(n + 1);

		for (int i = 0; i < n; i++) {
			x = Integer.parseInt(br.readLine());

			if (x > 0)
				minHeap.add(x);
			else if (x == 0) {
				if (!minHeap.isEmpty())
					sb.append(minHeap.remove()).append("\n");
				else
					sb.append(0).append("\n");
			}
		}

		System.out.println(sb.toString());
	}
}

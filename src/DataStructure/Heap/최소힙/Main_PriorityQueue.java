package DataStructure.Heap.최소힙;
import java.io.*;
import java.util.PriorityQueue;

/*
- n: 수행해야 할 연산 개수
- x
  => x > 0 인 경우, 배열에 x 추가
  => x == 0 인 경우, 배열에서 최소값 제거 및 출력
     + 빈 배열이면 0 출력

1. 아이디어
 - PriorityQueue 에 입력 x 를 추가 또는 삭제

2. 자료구조
 - PriorityQueue<Integer>

3. 시간 복잡도
 - PriorityQueue 의 시간 복잡도
   => 삽입 / 삭제: O(log n)
*/

public class Main_PriorityQueue {
	static int n;			// 연산의 개수
	static int x;			// 자연수 또는 0 입력
	static PriorityQueue<Integer> pq = new PriorityQueue<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());

		for (int i = 0; i < n; i++) {
			x = Integer.parseInt(br.readLine());

			if (x > 0)
				pq.add(x);
			else if (x == 0) {
				if (!pq.isEmpty())
					System.out.println(pq.remove());
				else
					System.out.println(0);
			}
		}
	}
}

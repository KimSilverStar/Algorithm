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
 - PriorityQueue / Heap 의 시간 복잡도
   => 삽입 / 삭제: O(log n)		(n: 노드 개수)
 - 최대 총 n번 삽입 / 삭제 발생
   => 대충 최대 n log n
   => n 최대값 대입: 10^5 x log 10^5 = 5 x 10^5 << 1억 (1초)
*/

public class Main_PriorityQueue {
	static int n;			// 연산의 개수
	static int x;			// 자연수 또는 0 입력
	static PriorityQueue<Integer> pq = new PriorityQueue<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		StringBuilder sb = new StringBuilder();

		n = Integer.parseInt(br.readLine());
		for (int i = 0; i < n; i++) {
			x = Integer.parseInt(br.readLine());

			if (x > 0)
				pq.add(x);
			else if (x == 0) {
				if (!pq.isEmpty())
					sb.append(pq.remove()).append("\n");
				else
					sb.append(0).append("\n");
			}
		}

		System.out.println(sb.toString());
	}
}

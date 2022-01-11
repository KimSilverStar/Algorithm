package DataStructure.PriorityQueue.N번째_큰수;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
1. 아이디어
 - PriorityQueue 에 최대 n 개의 수만 저장되도록 유지
   => 최초 n개의 수들을 PriorityQueue 에 저장하고,
      이후 부터는 add(new item), remove() 반복
      !!! 반드시 add(new item), remove() 순서로 할 것
   => PriorityQueue 의 내부 Heap 크기를 최소로 유지하여 삽입 / 삭제 속도를 높임

2. 자료구조
 - int[][]: 행렬에 적힌 수 (20억이 안되므로 int 가능)
 - PriorityQueue<Integer>: 행렬에 적힌 수 입력하여 오름차순 정렬

3. 시간 복잡도
 - Priority Queue의 시간 복잡도
   => 삽입 / 삭제: O(log n)	(n: 노드 개수)

  1) `PriorityQueue`에 최초 n개 입력: log 1 + log 2 + ... + log n
      = log (1 x 2 x ... x n) = log(n!)
  2) 이후 `pq.add()`, `pq.remove()` 반복: n(n-1) x 2 log n
  => 총 log(n!) + n(n-1) x 2 log n
  => n 최대값 대입: log(1500!) + 1500(1500-1) x 2 log 1500
  ~= 4,115 + 2,248,500 x 2 (log 15 + log 10^2)
  ~= 4,115 + 2,248,500 x 2 (1 + 2)
  ~= 4,115 + 2,248,500 x 6
  ~= 4,115 + 2,248,500 x 6
  ~= 13,495,115 << 1억 (1초)
*/

public class Main_PriorityQueue_Upgrade {
	static int n;
	static int[][] numbers;
	static PriorityQueue<Integer> pq = new PriorityQueue<>();

	static int solution() {
		// 최초 입력 행렬에서 n개 수들을 pq에 저장
		for (int i = 0; i < n; i++)
			pq.add(numbers[0][i]);

		// 이후 부터는 add(new item), remove 반복
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < n; j++) {
				pq.add(numbers[i][j]);
				pq.remove();
			}
		}

		return pq.peek();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		numbers = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++)
				numbers[i][j] = Integer.parseInt(st.nextToken());
		}

		System.out.println(solution());
	}
}

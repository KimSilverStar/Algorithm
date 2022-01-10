package DataStructure.PriorityQueue.N번째_큰수;
import java.io.*;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.PriorityQueue;

/*
1. 아이디어
 - 입력 n x n 행렬을 마지막 행부터 PriorityQueue 에 입력
 - n 번재 큰 수 = 맨 아래 행에서부터 n 번째 행에 존재
 => PriorityQueue 에 맨 아래 행부터 입력하여 오름차순으로 정렬되도록 함

2. 자료구조
 - int[][]: 행렬에 적힌 수 (20억이 안되므로 int 가능)
 - PriorityQueue<Integer>: 행렬에 적힌 수 입력하여 오름차순 정렬

3. Priority Queue 의 시간 복잡도
 - 삽입 / 삭제: O(log n)	(n: 노드 개수)
*/

public class Main_PriorityQueue {
	static int n;
	static int[][] numbers;			// n x n 행렬에 입력된 n^2 개의 수
	static PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

	static int solution() {
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j < n; j++)
				pq.add(numbers[i][j]);
		}

		// 앞에서부터 n-1 개 버림
		for (int i = 1; i <= n - 1; i++)
			pq.remove();

		return pq.peek();		// n 번째 큰 수
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

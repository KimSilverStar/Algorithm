package DataStructure.PriorityQueue.N번째_큰수;
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 입력 행렬의 모든 수를 1차원 배열에 저장
 - 배열 정렬하여, n 번째 큰 수 [length - n] 출력

2. 자료구조
 - int[]: 행렬의 수들 저장

3. 시간 복잡도
 - 행렬 입력: O(n^2)
 - 배열 정렬: O(N log N)		N: 정렬 item 개수
   => N = n^n => n 최대값 입력: 1,500 x 1,500 = 2,250,000 개 item 정렬
   => 2,250,000 x log(2,250,000) = 2,250,000 x log(225 x 10^4)
      = 2,250,000 x [log(225) + log(10^4)]
      ~= 2,250,000 x (3 + 4)
      ~= 15,750,000 << 1억 (1초)
*/

public class Main_Sort {
	static int n;
	static int[] numbers;		// n x n 행렬의 총 n^2 개의 수를 담은 배열

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		numbers = new int[n * n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < n; j++)
				numbers[i * n + j] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(numbers);

		System.out.println(numbers[numbers.length - n]);		// n 번째 큰 수
	}
}

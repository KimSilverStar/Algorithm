package BinarySearch.수_찾기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 단순히 길이 n인 정수 배열에서 길이 m인 정수 배열의 target 원소를 1개씩 비교하는 완전 탐색
   => O(m x n)
   => m, n 최대값 대입: 10^5 x 10^5 = 10^10 >> 1억

 - 이진 탐색
   1) 이진 탐색을 위해 길이 n인 정수 배열 정렬
   2) 이진 탐색 수행

2. 자료구조
 - int[]: 입력 정수 배열
   => 자료형: -2^31 이상 ~ 2^31 미만 이므로, int 가능

3. 시간 복잡도
 - 배열 정렬: O(n log_2 n)
 - 이진 탐색: O(log_2 n)
   => 총 m번 수행하므로, O(m log_2 n)
 => 총 시간 복잡도: O(n log_2 n + m log_2 n)
 => n, m 최대값 대입: 10^5 log_2 10^5 + 10^5 log_2 10^5
    = (2 x 10^5) x log_2 10^5 = 10^6 x log_2 10		(log_2 10 ~= log_2 2^4 = 4)
    ~= 4 x 10^6 << 1억
*/

public class Main2 {
	static int n;					// n개의 정수
	static int[] numbers;
	static int m;					// m개의 정수
	static int[] targets;

	static boolean binarySearch(int start, int end, int target) {
		if (start > end)		// target 이 없는 경우
			return false;

		int mid = (start + end) / 2;
		if (numbers[mid] < target)
			return binarySearch(mid + 1, end, target);
		else if (numbers[mid] > target)
			return binarySearch(start, mid - 1, target);
		else				// target 찾은 경우
			return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		numbers = new int[n];
		for (int i = 0; i < n; i++)
			numbers[i] = Integer.parseInt(st.nextToken());

		m = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		targets = new int[m];
		for (int i = 0; i < m; i++)
			targets[i] = Integer.parseInt(st.nextToken());

		Arrays.sort(numbers);

		StringBuilder sb = new StringBuilder();
		for (int target : targets) {
			if (binarySearch(0, n - 1, target))
				sb.append(1).append("\n");
			else
				sb.append(0).append("\n");
		}
		System.out.println(sb);
	}
}

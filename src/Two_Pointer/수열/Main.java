package Two_Pointer.수열;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 *** 투 포인터
 1) 최초 k 개 정수들의 합을 구함
 2) for 문으로 맨 앞 index(ptr1) 의 값을 빼고, 마지막 다음 index(ptr2) 의 값을 더함

 *** 단순하게 for 문으로 각 숫자의 위치에서, 이후 k 개의 수를 더하는 알고리즘
    - O(n x k)
      => n, k 최대값 대입: 10^5 x 10^5 = 10^10 >> 1억 (시간 초과!!!)

2. 자료구조
 - int: 출력, k 개 값의 최대 합
   => 100 x 10^5 = 10^7 << 21억 이므로, int 가능

2. 시간 복잡도
 - O(n)
   => n 최대값 대입: 10^5 << 1억
*/

public class Main {
	static int n, k;				// 전체 날짜 수 n, 합을 구할 연속 날짜 수 k
	static int[] temperatures;		// 각 날짜 별 온도
	static int maxSum;				// 출력

	static void solution() {
		// 최초 k 개 합
		int temp = 0;
		for (int i = 0; i < k; i++)
			temp += temperatures[i];

		maxSum = temp;

		for (int i = k; i < n; i++) {
			temp -= temperatures[i - k];
			temp += temperatures[i];
			maxSum = Math.max(maxSum, temp);
		}

//		int ptr1 = 0;			// 합에서 뺄 위치
//		int ptr2 = k;			// 합에서 더할 위치
//
//		while (ptr2 <= n - 1) {
//			temp -= temperatures[ptr1++];
//			temp += temperatures[ptr2++];
//			maxSum = Math.max(maxSum, temp);
//		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		temperatures = new int[n];
		for (int i = 0; i < n; i++)
			temperatures[i] = Integer.parseInt(st.nextToken());

		solution();
		System.out.println(maxSum);
	}
}

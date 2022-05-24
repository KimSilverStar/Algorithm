package Code_Tree.DP.연속_부분_합의_최댓값_구하기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) DP 배열 정의: int[] dp
   - dp[i]: numbers[i] 까지의 범위에서, 부분 수열의 최대 합
   - 출력: dp[i] 값 중, 최대값

 2) 규칙 및 점화식
   - dp[i] = max(dp[i-1] + numbers[i], numbers[i])

2. 자료구조
 - int[] dp: DP 배열

3. 시간 복잡도
 - O(n)
*/

public class Main {
	static int n;				// 정수 개수
	static int[] numbers;
	static int[] dp;
	static int maxSum = Integer.MIN_VALUE;		// 출력, 연속 부분 수열의 최대 합

	static void solution() {
		for (int i = 1; i <= n; i++) {
			dp[i] = Math.max(dp[i-1] + numbers[i], numbers[i]);
			maxSum = Math.max(maxSum, dp[i]);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		numbers = new int[n + 1];			// [1] ~ [n] 사용
		dp = new int[n + 1];				// [0] 원소는 패딩
		for (int i = 1; i <= n; i++)
			numbers[i] = Integer.parseInt(st.nextToken());

		solution();
		System.out.println(maxSum);
	}
}

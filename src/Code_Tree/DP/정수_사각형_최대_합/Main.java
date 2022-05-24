package Code_Tree.DP.정수_사각형_최대_합;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) DP 배열 정의: long[][] dp
   - dp[i][j]: [i][j] 지점까지 갈 때, 거쳐가는 숫자 최대 합
   - 출력: dp[n][n]

 2) 규칙 및 점화식
   - dp[i][j] = max(dp[i-1][j], dp[i][j-1]) + map[i][j]


2. 자료구조
 - long[][] dp: DP 배열
   ① 자료형: 원소 최대값(맨 오른쪽 하단 원소)
      = 100 x 100 x 10^6 = 10^10 > 21억 이므로, int 불가능
   ② 메모리: 최대 8 x 100 x 100 byte = 8 x 10^4 byte = 80 KB


3. 시간 복잡도
 - O(n^2)
*/

public class Main {
	static int n;					// n x n 행렬
	static int[][] map;
	static long[][] dp;
	static long maxSum;				// 출력, 최대 합

	static void solution() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++)
				dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]) + map[i][j];
		}

		maxSum = dp[n][n];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		map = new int[n + 1][n + 1];		// [1][1] ~ [n][n] 사용
		dp = new long[n + 1][n + 1];		// [0]행, [0]열은 패딩
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		solution();
		System.out.println(maxSum);
	}
}

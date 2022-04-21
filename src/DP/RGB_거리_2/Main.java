package DP.RGB_거리_2;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 1번째 집과 마지막 n번째 집의 색이 달라야 함
   => 1번 집의 색을 고정하고, 다음 집의 색을 차례대로 정해나감
   ex) 1번 집을 R 색으로 칠하고, 다음 집들을 이전 집 색과 다른 2개 색 중 하나로 칠함 ...
       마지막 n번 집은 1번 집의 R 색 제외

 1) DP 배열 정의: int[][] dp
   - dp[i][j]: [1] ~ [i]번 집을 색칠, [i]번 집은 [j] 색으로 색칠했을 때 최소 비용
   - 집의 색 j: Red 0, Green 1, Blue 2

 2) 규칙 및 점화식
   - [1]번 집의 색을 R, G, B 로 고정하고, 나머지 집의 색을 차례로 지정
   - 초기식: [1]번 집 색칠
     => 고정 색깔 제외, 나머지 2개 색은 INF 로 초기화
     ex) [1]번 집 색이 R [0] 이면
     	 dp[1][0] = costs[1][0], 나머지 2개 색 dp[1][1] = INF, dp[1][2] = INF
   - 점화식: [i]번 집 색칠
     => 이전 집의 색과 다른 2개 색 중에서 최소값 선택

     ① [i]번 집을 R [0]으로 색칠하는 경우
       - dp[i][0] = min(dp[i-1][1], dp[i-1][2]) + costs[i][0]
     ② [i]번 집을 G [1]으로 색칠하는 경우
       - dp[i][1] = min(dp[i-1][0], dp[i-1][2]) + costs[i][1]
     ③ [i]번 집을 B [2]으로 색칠하는 경우
       - dp[i][2] = min(dp[i-1][0], dp[i-1][1]) + costs[i][2]
     ※ 마지막 [n]번 집에서 [1]번 집의 색은 제외
       - dp[n][1번 집의 색] = INF


2. 자료구조
 - int[][] dp: DP 배열
   ① 자료형: 최대값 10^3 x 10^3 = 10^6 << 21억 이므로, int 가능
   ② 메모리: 대략 최대 10^3 x 3 byte = 3 KB


3. 시간 복잡도
 - DP 배열 채우기: O(3 x n) = O(3 x n)
   => n 최대값 대입: 3 x 10^3 << 0.5억
*/

public class Main {
	static int n;						// 집 개수
	static int[][] costs;				// 각 집의 R, G, B 색칠 비용
	static int[][] dp;
	static final int INF = 1_000_000;	// (n 최대값 10^3) x (비용 최대값 10^3)
	static int minCost = INF;			// 모든 집을 칠하는 최소 비용

	static void solution() {
		// [1]번 집의 색 고정
		for (int firstColor = 0; firstColor < 3; firstColor++) {
			if (firstColor == 0) {
				dp[1][0] = costs[1][0];
				dp[1][1] = INF;
				dp[1][2] = INF;
			}
			else if (firstColor == 1) {
				dp[1][1] = costs[1][1];
				dp[1][0] = INF;
				dp[1][2] = INF;
			}
			else {		// firstColor == 2
				dp[1][2] = costs[1][2];
				dp[1][0] = INF;
				dp[1][1] = INF;
			}

			// [2] ~ [n]번째 집의 색
			for (int i = 2; i <= n; i++) {
				dp[i][0] = Math.min(dp[i-1][1], dp[i-1][2]) + costs[i][0];
				dp[i][1] = Math.min(dp[i-1][0], dp[i-1][2]) + costs[i][1];
				dp[i][2] = Math.min(dp[i-1][0], dp[i-1][1]) + costs[i][2];

				// 마지막 [n]번 집에서 [1]번 집의 색은 제외
				if (i == n)
					dp[i][firstColor] = INF;
			}

			for (int j = 0; j < 3; j++)
				minCost = Math.min(minCost, dp[n][j]);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		costs = new int[n + 1][3];			// [1][0] ~ [n][3] 사용
		dp = new int[n + 1][3];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < 3; j++)
				costs[i][j] = Integer.parseInt(st.nextToken());
		}

		solution();
		System.out.println(minCost);
	}
}

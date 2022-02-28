package DP.스티커;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 규칙: 어떤 위치의 스티커를 떼면,
   다음 오른쪽 1칸 대각선 or 오른쪽 2칸 대각선 위치의 스티커 뗄 수 있음
   => 거꾸로 생각하면, 어떤 위치의 스티커를 떼기 전에는
      이전 왼쪽 1칸 대각선 or 왼쪽 2칸 대각선 위치의 스티커를 떼었음

 - dp[i][j]: [0][0] ~ [i][j] 지점까지 스티커 뜯어갔을 경우, 최대 점수 합

 - 점화식
   1) dp[0][j] = max(dp[1][j-1], dp[1][j-2]) + map[0][j]
   2) dp[1][j] = max(dp[0][j-1], dp[0][j-2]) + map[1][j]
   => dp[0][j], dp[1][j] 를 나눈 이유 (0행, 1행 나눈 이유)
      : map[0][0]을 뜯고 시작하는 경우 or map[1][0]을 뜯고 시작하는 경우

2. 자료구조
 - int[][] dp
   => 100 x 10^5 = 10^7 << 21억 이므로, int 가능

3. 시간 복잡도
 - DP 배열을 for 문으로 한 열(윗 칸, 아래 칸)씩 채워나감
   => for 문 1번 반복
   => O(n)
*/

public class Main {
	static int t;				// 테스트 케이스 개수
	static int n;				// 2행 n열
	static int[][] map;
	static int[][] dp;

	static int solution() {
		// DP 배열 한 열(윗 칸, 아래 칸)씩 채워나감
		for (int j = 2; j <= n; j++) {
			dp[0][j] = Math.max(dp[1][j-1], dp[1][j-2]) + map[0][j];
			dp[1][j] = Math.max(dp[0][j-1], dp[0][j-2]) + map[1][j];
		}

		return Math.max(dp[0][n], dp[1][n]);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		t = Integer.parseInt(br.readLine());
		for (int tc = 0; tc < t; tc++) {
			n = Integer.parseInt(br.readLine());
			// [0][1] ~ [1][n] 에 실제 스티커 점수 채워지고, 그 이전 칸들은 0으로 채워짐
			map = new int[2][n + 1];
			dp = new int[2][n + 1];

			for (int i = 0; i < 2; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 1; j <= n; j++)
					map[i][j] = Integer.parseInt(st.nextToken());
			}

			dp[0][1] = map[0][1];
			dp[1][1] = map[1][1];

			sb.append(solution()).append("\n");
		}

		System.out.println(sb);
	}
}

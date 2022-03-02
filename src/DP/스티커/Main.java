package DP.스티커;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) 규칙: 어떤 위치의 스티커를 떼면,
   다음 오른쪽 1칸 대각선 or 오른쪽 2칸 대각선 위치의 스티커 뗄 수 있음
   ① 윗 행의 스티커 [0][j] 를 뗐을 경우
     - 그 다음 뗄 수 있는 스티커는 [1][j+1] or [1][j+2]
   ② 아래 행의 스티커 [1][j] 를 뗐을 경우
     - 그 다음 뗄 수 있는 스티커는 [0][j+1] or [0][j+2]
 => 거꾸로 생각하면, 어떤 위치의 스티커를 떼기 전에는
    이전 왼쪽 1칸 대각선 or 왼쪽 2칸 대각선 위치의 스티커를 떼었음

 !!! 규칙(점화식)을 이용하여 이전 값들을 채우고,
     채워진 이전 값들을 이용하여 다음 값을 계산하므로 DP

 2) DP 배열 정의: int[][] dp
   => dp[i][j]: [0][0] ~ [i][j] 까지 스티커를 떼었을 경우, 최대 점수 합
   => 구하려는 maxSum = max(dp[0][n-1], dp[1][n-1])
      (마지막 열의 윗 행 or 아래 행까지 뗀 경우)

 3) 점화식
   ① dp[0][j] = max(dp[1][j-1], dp[1][j-2]) + map[0][j]
   ② dp[1][j] = max(dp[0][j-1], dp[0][j-2]) + map[1][j]
   - 초기식: dp[0][0] = 0, dp[1][0] = 0,
   			 dp[0][1] = map[0][1], dp[1][1] = map[1][1]

2. 자료구조
 - int[][] dp
   => 100 x 10^5 = 10^7 << 21억 이므로, int 가능

3. 시간 복잡도
 - DP 배열을 for 문으로 한 열(윗 칸, 아래 칸)씩 채워나감
   => 대략 열 개수 n 만큼 for 문 반복
   => O(n)
*/

public class Main {
	static int t;				// 테스트 케이스 개수
	static int n;				// 2행 n열
	static int[][] map;
	static int[][] dp;
	static int maxSum;

	static void solution() {
		// 초기식
		dp[0][1] = map[0][1];
		dp[1][1] = map[1][1];

		// DP 배열 한 열(윗 칸, 아래 칸)씩 채워나감
		for (int j = 2; j <= n; j++) {
			dp[0][j] = Math.max(dp[1][j-1], dp[1][j-2]) + map[0][j];
			dp[1][j] = Math.max(dp[0][j-1], dp[0][j-2]) + map[1][j];
		}

		maxSum = Math.max(dp[0][n], dp[1][n]);
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

			map = new int[2][n + 1];		// [0][0] ~ [1][n] 사용
			dp = new int[2][n + 1];			// [0][0] ~ [1][n] 사용

			for (int i = 0; i < 2; i++) {
				st = new StringTokenizer(br.readLine());

				for (int j = 1; j <= n; j++)
					map[i][j] = Integer.parseInt(st.nextToken());
			}

			maxSum = 0;
			solution();
			sb.append(maxSum).append("\n");
		}

		System.out.println(sb);
	}
}

package DP.진우의_달_여행_Large;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 출발 지점 -> 각 지점으로의 최소 비용 값을 DP 배열에 채워나감
 - 3가지 이동 방향: 왼쪽 아래, 아래, 오른쪽 아래
   => 각 지점을 각 이동 방향으로 이동 했을 때, 최소값을 저장
   ex) (3, 5) 지점을 이전 지점으로부터 [왼쪽 아래] 방향으로 이동했을 때, 연료 최소값
 - 해당 지점을 도달하기 위해, 이전 지점으로부터 이동한 방향을 "구분"하여 저장

 1) DP 배열 정의: int[][][] dp
   - dp[i][j][k]: 시작 지점 -> [i][j] 지점까지
     이전 윗 행의 지점으로부터 k의 방향으로 이동한 최소 비용
   - k: 0, 1, 2 차례로 왼쪽 대각선, 아래, 오른쪽 대각선 방향
   - 출력: 마지막 행 원소 중, 최소값

 2) 규칙 및 점화식
   - 현재 지점으로 k 방향으로 도달하는 최소 비용 dp[i][j][k]
     = 이전 윗 행의 지점으로부터 k와 다른 방향으로 오는 최소 비용 + 현재 지점 칸의 연료
   ① dp[i][j][0] = min(dp[i-1][j+1][1], dp[i-1][j+1][2]) + map[i][j]
      => 윗 행 [i-1][j+1] 으로부터 왼쪽 대각선 아래로 이동
   ② dp[i][j][1] = min(dp[i-1][j][0], dp[i-1][j][2]) + map[i][j]
      => 윗 행 [i-1][j] 으로부터 아래로 이동
   ③ dp[i][j][2] = min(dp[i-1][j-1][0], dp[i-1][j-1][1]) + map[i][j]
      => 윗 행 [i-1][j-1] 으로부터 왼쪽 대각선 아래로 이동
   - 초기식
     ① 첫 행 dp[0][j][k] = map[0][j]
     ② 오른쪽 끝 칸 [i][m-1] 으로 못 오는 경우
       - dp[i][m-1][0] = INF
     ③ 왼쪽 끝 칸 [i][0] 으로 못 오는 경우
       - dp[i][0][2] = INF


2. 자료구조
 - int[][][] dp: DP 배열
   ① 자료형: 마지막 행에서 원소 최대값
   			  10^3 x 10^3 x 100 = 10^8 << 21억 이므로, int 가능
   ② 메모리: 최대 4 x 10^3 x 10^3 x 3 byte = 12 MB


3. 시간 복잡도
 - DP 배열 채우기: O(n x m x 3)
   => 최대: 10^3 x 10^3 = 10^6
 - 출력 값 최소 비용 찾기: O(3 x m)
   => 최대: 3 x 10^3
 => 총 시간 복잡도: 대략 10^6 << 1억
*/

public class Main {
	static int n, m;						// n행 m열
	static int[][] map;
	static int[][][] dp;
	static int minCost = Integer.MAX_VALUE;	// 출력, 최소 비용

	static void solution() {
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (isValid(i - 1, j + 1))
					dp[i][j][0] = Math.min(dp[i-1][j+1][1], dp[i-1][j+1][2]) + map[i][j];

				if (isValid(i - 1, j))
					dp[i][j][1] = Math.min(dp[i-1][j][0], dp[i-1][j][2]) + map[i][j];

				if (isValid(i - 1, j - 1))
					dp[i][j][2] = Math.min(dp[i-1][j-1][0], dp[i-1][j-1][1]) + map[i][j];
			}
		}

		// dp 배열의 마지막 행 원소들 중, minCost 찾기
		for (int j = 0; j < m; j++) {
			for (int k = 0; k < 3; k++) {
				if (minCost > dp[n-1][j][k])
					minCost = dp[n-1][j][k];
			}
		}
	}

	static boolean isValid(int y, int x) {
		return (0 <= y && y < n) &&
				(0 <= x && x < m);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new int[n][m];
		dp = new int[n][m][3];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());

				if (i == 0) {
					// 초기식 ①: 첫 행 dp[0][j][k] = map[0][j]
					dp[i][j][0] = map[i][j];
					dp[i][j][1] = map[i][j];
					dp[i][j][2] = map[i][j];
				}
				else if (j == m - 1) {
					// 초기식 ②: 오른쪽 끝 칸 [i][m-1] 으로 못 오는 경우
					dp[i][j][0] = Integer.MAX_VALUE;
				}
				else if (j == 0) {
					// 초기식 ③: 왼쪽 끝 칸 [i][0] 으로 못 오는 경우
					dp[i][j][2] = Integer.MAX_VALUE;
				}
			}
		}

		solution();
		System.out.println(minCost);
	}
}

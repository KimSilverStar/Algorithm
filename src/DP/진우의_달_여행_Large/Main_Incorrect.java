package DP.진우의_달_여행_Large;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 출발 지점 -> 각 지점으로의 최소 비용 값을 DP 배열에 채워나감
 - 해당 지점을 도달하기 위해, 이전 지점으로부터 이동한 방향을 저장
 - 출력 값 최소 비용: 마지막 행 원소들 중, 최소값

 1) DP 배열 정의: int[][] dp
   - dp[i][j]: 시작 지점 -> [i][j] 지점까지 이동 최소 비용

 2) 규칙 및 점화식
   - 3가지 이동 방향 중, 이전 이동 방향 제외하고 최소 누적 값
   - 현재 지점으로의 최소 비용 dp[i][j] 채우기 위해서,
     이전 3개 지점의 최소 비용 dp[i-1][j-1], dp[i-1][j], dp[i-1][j+1] 확인


***** 틀린 이유 *****
 - 탐색에서 중복 발생
 - [i][j] 지점을 최소 비용으로 도달하는 경우에서
   [i-1][j-1] or [i-1][j] or [i-1][j+1] 3가지로부터 중복 발생 가능
 => 3차원 배열 int[][][] dp 로 탐색 중복 방지


2. 자료구조
 - int[][] dp: DP 배열
   ① 자료형: 마지막 행에서 원소 최대값
   			  10^3 x 10^3 x 100 = 10^8 << 21억 이므로, int 가능
   ② 메모리: 최대 4 x 10^3 x 10^3 byte = 4 MB
 - int[][] directions: 이전 지점으로부터의 이동 방향 저장


3. 시간 복잡도
 - DP 배열 채우기: O(n x m x 3)
   => 최대: 10^3 x 10^3 x 3 = 3 x 10^6
 - 출력 값 최소 비용 찾기: O(m)
   => 최대: 10^3
 => 총 시간 복잡도: 대략 3 x 10^6 << 1억
*/

public class Main_Incorrect {
	static int n, m;						// n행 m열
	static int[][] map;
	static int[][] dp;
	static int minCost = Integer.MAX_VALUE;	// 출력, 최소 비용

	static int[][] directions;			// 이전 지점으로부터 이동 방향
	// 3가지 이동 방향: 왼쪽 대각선 아래, 아래, 오른쪽 대각선 아래
	static final int LEFT_DOWN = -1;
	static final int DOWN = 0;
	static final int RIGHT_DOWN = 1;
	static int[] dx = { LEFT_DOWN, DOWN, RIGHT_DOWN };

	static int INF;

	static void solution() {
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				// 이전 3개 지점의 최소 비용 dp[i-1][j-1], dp[i-1][j], dp[i-1][j+1]
				for (int k = 0; k < 3; k++) {
					if (!isValid(i - 1, j + dx[k]))
						continue;

					// 이전 위치 도달하는 데 이동 방향 == 현재 위치 도달하는 데 이동 방향
					if (directions[i - 1][j + dx[k]] == dx[k])	// 같은 방향으로 연속하여 이동 X
						continue;

					int prev = dp[i - 1][j + dx[k]];
					if (dp[i][j] > map[i][j] + prev) {
						dp[i][j] = map[i][j] + prev;
						directions[i][j] = dx[k];
					}
				}
			}
		}

		// dp 배열의 마지막 행 원소들 중, minCost 찾기
		for (int j = 0; j < m; j++) {
			if (minCost >= dp[n - 1][j])
				minCost = dp[n - 1][j];
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

		INF = n * m * 100;

		map = new int[n][m];
		dp = new int[n][m];
		directions = new int[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());

				if (i == 0) {
					dp[i][j] = map[i][j];
					directions[i][j] = 100;
					// [0]행으로 도달하기 위한 이동 방향 = 지구 위치의 아무데서나
				}
				else
					dp[i][j] = INF;
			}
		}

		solution();
		System.out.println(minCost);
	}
}

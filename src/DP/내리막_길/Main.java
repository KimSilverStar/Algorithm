package DP.내리막_길;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - DFS + DP + (메모이제이션)
 - 현재 지점 [y][x]가 끝 지점이면, DFS 탐색 종료
 - 메모이제이션으로 현재 보고있는 경로가
   이미 끝 지점까지 탐색 완료된 경우 (메모이제이션 값 != -1), 더 탐색하지 않고 종료

 1) DP 배열 정의: int[][] dp
   - dp[y][x]: [y][x] 지점 -> 끝 지점으로 내리막 길로 가는 경로 개수
     => Top-Down 방식
   - 출력 값 h = dp[0][0]

 2) 규칙 및 점화식
   - 현재 지점 [y][x]에 대해, 각 상하좌우 지점 [ny][nx]을 확인
   - 다음 지점 [ny][nx]로 내리막 길로 갈 수 있는 경우
   ① 다음 지점 [ny][nx]의 경로가 이미 탐색 완료한 경우 (메모이제이션 값 != -1)
     - dp[y][x] += dp[ny][nx]
   ② 다음 지점 [ny][nx]의 경로가 아직 탐색 완료하지 않은 경우 (메모이제이션 값 == -1)
     - DFS로 더 탐색(재귀호출) 후, dp[ny][nx] 갱신


 ※ 처음 생각한 DFS + DP 풀이 방식
  - dp[y][x]: 시작 지점 [0][0] -> [y][x] 지점으로 내리막 길로 가는 경로 개수
    => Bottom-UP 방식
  - dp[y][x] = 0 이면, 해당 [y][x] 로 내리막 길로 갈 수 없음
  - 출력 값 h = dp[m-1][n-1]
  - 문제점) 갱신된 이동 경로 ~ 도착 경로까지 경로 개수를 전부 갱신 시켜줘야 함


2. 자료구조
 - int[][] dp: DP 배열


3. 시간 복잡도
 - 인접 행렬로 구현한 단순 DFS의 시간 복잡도: O(V^2) = O((n x m)^2)
   => n, m 최대값 대입: (500 x 500)^2 = (5^2 x 10^4)^2
      = 5^4 x 10^8 = 62,500,000,000 >> 2억
   => 시간초과 !!!
 - DFS + DP + (메모이제이션)으로 시간 복잡도 줄임
*/

public class Main {
	static int m, n;				// m x n 행렬
	static int[][] map;
	static int[][] dp;
	static int h;					// 출력, 이동 가능한 경로의 수
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };
//	static boolean[][] finished;	// 끝 지점까지 탐색 완료 여부

	static void dfs(int y, int x) {
		if (y == m - 1 && x == n - 1)	// 끝 지점 도착한 경우
			return;

		if (dp[y][x] != -1)				// 이미 끝 지점까지 탐색을 한 경우 (메모이제이션)
			return;
//		if (finished[y][x])
//			return;

		dp[y][x] = 0;
//		finished[y][x] = true;

		// 현재 지점 [y][x]에서 상하좌우 [ny][nx]로 가는 경로 개수 구하기
		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (!isValid(ny, nx))
				continue;

			// [y][x] -> [ny][nx] 내리막 길로 갈 수 있는 경우
			if (map[y][x] > map[ny][nx]) {
				if (dp[ny][nx] != -1)			// 이미 끝 지점까지 탐색을 한 경우 (메모이제이션)
					dp[y][x] += dp[ny][nx];
				else {							// 아직 끝 지점까지 탐색을 하지 않은 경우
					dfs(ny, nx);				// => 더 탐색
					dp[y][x] += dp[ny][nx];		// 끝 지점까지 탐색 완료 후, 갱신
				}
			}
		}
	}

	static boolean isValid(int y, int x) {
		return (y >= 0 && y < m) &&
				(x >= 0 && x < n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		m = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());

		map = new int[m][n];
//		finished = new boolean[m][n];
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		dp = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				dp[i][j] = -1;		// 메모이제이션: 탐색 완료 X 표시
		}
		dp[m-1][n-1] = 1;			// 초기식: 끝 지점

		dfs(0, 0);
		h = dp[0][0];

		System.out.println(h);

		System.out.println("--------------------");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(dp[i][j] + " ");
			System.out.println();
		}
		System.out.println("--------------------");
	}
}

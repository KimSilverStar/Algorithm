package DP.내리막_길;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - DFS

2. 자료구조

3. 시간 복잡도
 - 인접 행렬로 구현한 DFS의 시간 복잡도: O(V^2) = O((n x m)^2)
   => n, m 최대값 대입: (500 x 500)^2 = (5^2 x 10^4)^2
      = 5^4 x 10^8 = 62,500,000,000 >> 2억
   => 시간초과 !!!
*/

public class Main_TimeOver {
	static int m, n;			// m x n 행렬
	static int[][] map;
	static int h;				// 출력, 이동 가능한 경로의 수
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void dfs(int y, int x) {
		// 목표 지점에 도착한 경우
		if (y == m - 1 && x == n - 1) {
			h++;
			return;
		}

		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (!isValid(ny ,nx))
				continue;

			// 내리막 길 이동
			if (map[y][x] > map[ny][nx])
				dfs(ny, nx);
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
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		dfs(0, 0);			// Init Call
		System.out.println(h);
	}
}

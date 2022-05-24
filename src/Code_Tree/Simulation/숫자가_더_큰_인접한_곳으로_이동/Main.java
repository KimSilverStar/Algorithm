package Code_Tree.Simulation.숫자가_더_큰_인접한_곳으로_이동;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어

2. 자료구조

3. 시간 복잡도
 - 최대 O(n^2)
*/

public class Main {
	static int n;					// n x n 행렬
	static int r, c;				// 시작, 현재 위치
	static int[][] map;
	static int[] dy = { -1, 1, 0, 0 };			// 상하좌우 순서
	static int[] dx = { 0, 0, -1, 1 };
	static StringBuilder sb = new StringBuilder();

	static void solution() {
		sb.append(map[r][c]).append(" ");		// 시작 위치

		while (true) {
			if (!step())
				break;
		}
	}

	/* 상하좌우 확인 후, 이동 가능한 곳으로 이동 */
	static boolean step() {
		for (int i = 0; i < 4; i++) {
			int ny = r + dy[i];
			int nx = c + dx[i];

			if (isValid(ny, nx) && map[ny][nx] > map[r][c]) {
				sb.append(map[ny][nx]).append(" ");
				r = ny;			// 현재 위치 갱신
				c = nx;

				return true;
			}
		}

		return false;
	}

	static boolean isValid(int y, int x) {
		return (1 <= y && y <= n) && (1 <= x && x <= n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		map = new int[n+1][n+1];			// [1][1] ~ [n][n] 사용
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		solution();
		System.out.println(sb);
	}
}

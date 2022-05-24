package Code_Tree.Test.Q2;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어

2. 자료구조

3. 시간 복잡도
 - O(n)
*/

class Pair {
	public int y, x;

	public Pair(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static int n, m;			// n x n 행렬, m번 색칠
	static boolean[][] map;		// 각 칸의 색칠 여부
	static Pair[] pairs;		// 색칠할 위치들
	static StringBuilder sb = new StringBuilder();
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void solution() {
		for (Pair p : pairs) {
			map[p.y][p.x] = true;		// 현재 칸 색칠

			if (isComfortable(p.y, p.x))
				sb.append("1\n");
			else
				sb.append("0\n");
		}
	}

	static boolean isComfortable(int y, int x) {
		int count = 0;			// 색칠된 인접 칸 개수

		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (isValid(ny, nx) && map[ny][nx])
				count++;
		}

		return count == 3;
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
		m = Integer.parseInt(st.nextToken());
		map = new boolean[n + 1][n + 1];		// [1][1] ~ [n][n] 사용

		pairs = new Pair[m];
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());

			pairs[i] = new Pair(y, x);
		}

		solution();
		System.out.println(sb);
	}
}

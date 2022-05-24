package Code_Tree.Simulation.최고의_33위치;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 브루트 포스: 2중 for문으로 n x n 전체 확인

2. 자료구조

3. 시간 복잡도
 - O(n^2)
*/

public class Main {
	static int n;				// n x n 행렬
	static int[][] map;
	static int maxCount;		// 출력, 동전 최대 개수

	static void solution() {
		for (int i = 0; i <= n - 3; i++) {
			for (int j = 0; j <= n - 3; j++) {
				// [i][j]: 3 x 3 격자의 왼쪽 모서리
				maxCount = Math.max(maxCount, getCount(i, j));
			}
		}
	}

	/* [y][x]가 왼쪽 모서리인 3 x 3 격자에 존재하는 동전 개수 반환  */
	static int getCount(int y, int x) {
		int count = 0;

		for (int i = y; i < y + 3; i++) {
			for (int j = x; j < x + 3; j++) {
				if (map[i][j] == 1)
					count++;
			}
		}

		return count;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		map = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		solution();
		System.out.println(maxCount);
	}
}

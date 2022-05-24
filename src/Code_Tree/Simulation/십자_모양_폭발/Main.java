package Code_Tree.Simulation.십자_모양_폭발;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) 폭탄 터뜨리기
   - 터진 위치 -1로 채움
 2) 중력 작용
   - 새로운 배열에 복사
   - 열 기준, 아랫 칸부터 복사

2. 자료구조
 - int[][] map
 - int[][] newMap
 => 2차원 배열 2개: 최대 2 x 4 x 200^2 byte = 32 x 10^4 byte = 320 KB

3. 시간 복잡도
 - O(n^2)
*/

public class Main {
	static int n;				// n x n 행렬
	static int[][] map;
	static int r, c;			// 폭탄 위치
	static int[][] newMap;		// 출력, 폭탄 터진 후 중력 작용

	static void solution() {
		// 1. 폭탄 터뜨리기 (터진 위치 -1로 채움)
		burstBomb();

		// 2. 중력 작용 - 새로운 행렬에 복사
		drop();
	}

	/* [r][c] 위치의 폭탄 터뜨림 => 터진 위치 -1로 채움 */
	static void burstBomb() {
		int lineCount = (map[r][c] * 2) - 1;	// 가로 or 세로 기준으로, 터지는 개수

		// 가로
		for (int j = c - lineCount / 2; j <= c + lineCount / 2; j++) {
			if (isValid(r, j))
				map[r][j] = -1;
		}

		// 세로
		for (int i = r - lineCount / 2; i <= r + lineCount / 2; i++) {
			if (isValid(i, c))
				map[i][c] = -1;
		}
	}

	/* 중력 작용 - 새로운 행렬 newMap[][]에 복사 */
	static void drop() {
		// 열 기준, 아랫 칸부터 newMap[][]에 복사
		for (int j = 1; j <= n; j++) {
			int idx = n;

			for (int i = n; i >= 1; i--) {
				if (map[i][j] != -1)
					newMap[idx--][j] = map[i][j];
			}
		}
	}

	static boolean isValid(int y, int x) {
		return (1 <= y && y <= n) && (1 <= x && x <= n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		map = new int[n+1][n+1];			// [1][1] ~ [n][n] 사용
		newMap = new int[n+1][n+1];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		solution();

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++)
				sb.append(newMap[i][j]).append(" ");
			sb.append("\n");
		}
		System.out.println(sb);
	}
}

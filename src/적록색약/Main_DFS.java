package 적록색약;
import java.io.*;

/*
- 적록색약: R == G 로 봄
- 같은 구역 => 같은 색
- 같은 색이 상하좌우로 인접한 경우, 두 글자는 같은 구역에 속함
=> 정상인이 본 구역 수, 적록색약이 본 구역 수 구하기
*/

/*
1. 아이디어
(1) DFS 풀이
  - for 문으로 [0, 0] ~ [n, n] 차례로 확인
    => 아직 방문 안했으면 탐색 시작 (Init Call)
    => 인자로 탐색 시작 위치, 색깔 전달
  - DFS
    => 정상인) 다음 지점이 현재 지점과 같은 색이고 아직 방문 안한 경우, 탐색 확장
    => 적록색약) 다음 지점이 현재 지점과 같은 색 or 구분 못하는 색이고 아직 방문 안한 경우, 탐색 확장

2. 자료구조
 - boolean[][]: 방문 확인

3. 시간 복잡도
 - 
*/

public class Main {
	static int n;				// n x n 그림
	static char[][] map;
	static int ans1, ans2;		// 정상인, 적록색약이 본 구역 수

	static boolean[][] check;
	static int[] dy = { -1, 1, 0, 0 };		// 상하좌우
	static int[] dx = { 0, 0, -1, 1 };

	/* 정상인이 본 구역 탐색, color: 탐색하고 있는 색깔 */
	static void dfs1(int row, int col, char color) {
		check[row][col] = true;

		for (int i = 0; i < 4; i++) {
			int nextRow = row + dy[i];
			int nextCol = col + dx[i];

			if (0 <= nextRow && nextRow < n &&
				0 <= nextCol && nextCol < n) {
				// 다음 지점이 현재 지점과 같은 색이고, 아직 방문 안한 경우
				if (color == map[nextRow][nextCol]
						&& !check[nextRow][nextCol])
					dfs1(nextRow, nextCol, map[nextRow][nextCol]);
			}
		}
	}

	/* 적록색약이 본 구역 탐색, color: 탐색하고 있는 색깔 */
	static void dfs2(int row, int col, char color) {
		check[row][col] = true;

		for (int i = 0; i < 4; i++) {
			int nextRow = row + dy[i];
			int nextCol = col + dx[i];

			if (0 <= nextRow && nextRow < n &&
					0 <= nextCol && nextCol < n) {
				boolean isSameColor = (color == map[nextRow][nextCol]);
				boolean isSimilarColor1 = (color == 'R' && map[nextRow][nextCol] == 'G');
				boolean isSimilarColor2 = (color == 'G' && map[nextRow][nextCol] == 'R');

				// 다음 지점이 현재 지점과 같은 색 or 구분 못하는 색이고, 아직 방문 안한 경우
				if (!check[nextRow][nextCol] && (isSameColor ||
						isSimilarColor1 || isSimilarColor2))
					dfs2(nextRow, nextCol, map[nextRow][nextCol]);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());
		map = new char[n][n];
		for (int i = 0; i < n; i++)
			map[i] = br.readLine().toCharArray();

		// 정상인
		check = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (!check[i][j]) {
					ans1++;
					dfs1(i, j, map[i][j]);		// map[i][j] 위치의 색깔 탐색 시작
				}
			}
		}

		// 적록색약
		check = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (!check[i][j]) {
					ans2++;
					dfs2(i, j, map[i][j]);		// map[i][j] 위치의 색깔 탐색 시작
				}
			}
		}

		System.out.println(ans1 + " " + ans2);
	}
}

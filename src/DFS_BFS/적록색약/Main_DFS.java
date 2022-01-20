package DFS_BFS.적록색약;
import java.io.*;

/*
- DFS_BFS.적록색약: R == G 로 봄
- 같은 구역 => 같은 색
- 같은 색이 상하좌우로 인접한 경우, 두 글자는 같은 구역에 속함
=> 정상인이 본 구역 수, 적록색약이 본 구역 수 구하기
*/

/*
1. 아이디어
 - for 문으로 [0, 0] ~ [n, n] 차례로 확인
   => 아직 방문 안했으면 DFS 탐색 시작 (Init Call)
   => 인자로 탐색 시작 위치 전달
 - DFS
   => 정상인) 다음 지점이 현재 지점과 같은 색이고 아직 방문 안한 경우, 탐색 확장
   => 적록색약) 다음 지점이 현재 지점과 같은 색 or 구분 못하는 색이고 아직 방문 안한 경우, 탐색 확장

2. 자료구조
 - boolean[][]: 방문 확인

3. 시간 복잡도
 - 인접 리스트 DFS / BFS 의 시간 복잡도: O(V + E)
   => V: n x n, E: 한 vertex 당 최대 4개 edge 연결 가정하면 E = 4V
   => O(V + E) = O(5V) = O(5 x n^2)
   => n 최대값 대입: 5 x 10^4
   => 정상인 or 적록색약 1명이 수행하는 DFS: 5 x 10^4
   => 총 시간 복잡도 = 2 x (5 x 10^4) = 10^5 << 1억 (1초)
*/

public class Main_DFS {
	static int n;				// n x n 그림
	static char[][] map;
	static int ans1, ans2;		// 정상인, 적록색약이 본 구역 수

	static boolean[][] check;
	static int[] dy = { -1, 1, 0, 0 };		// 상하좌우
	static int[] dx = { 0, 0, -1, 1 };

	/* 정상인이 본 구역 탐색, color: 탐색하고 있는 색깔 */
	static void dfs1(int row, int col) {
		check[row][col] = true;

		for (int i = 0; i < 4; i++) {
			int nextRow = row + dy[i];
			int nextCol = col + dx[i];

			if (0 <= nextRow && nextRow < n &&
				0 <= nextCol && nextCol < n) {
				boolean isSameColor = map[row][col] == map[nextRow][nextCol];
				// 다음 지점이 현재 지점과 같은 색이고, 아직 방문 안한 경우
				if (isSameColor && !check[nextRow][nextCol])
					dfs1(nextRow, nextCol);
			}
		}
	}

	/* 적록색약이 본 구역 탐색, color: 탐색하고 있는 색깔 */
	static void dfs2(int row, int col) {
		check[row][col] = true;

		for (int i = 0; i < 4; i++) {
			int nextRow = row + dy[i];
			int nextCol = col + dx[i];

			if (0 <= nextRow && nextRow < n &&
					0 <= nextCol && nextCol < n) {
				boolean isSameColor = map[row][col] == map[nextRow][nextCol];
				boolean isSimilarColor1 = (
						map[row][col] == 'R' && map[nextRow][nextCol] == 'G'
				);
				boolean isSimilarColor2 = (
						map[row][col] == 'G' && map[nextRow][nextCol] == 'R'
				);

				// 다음 지점이 현재 지점과 같은 색 or 구분 못하는 색이고, 아직 방문 안한 경우
				if (!check[nextRow][nextCol] && (isSameColor ||
						isSimilarColor1 || isSimilarColor2))
					dfs2(nextRow, nextCol);
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
					dfs1(i, j);			// map[i][j] 위치 탐색 시작
				}
			}
		}

		// DFS_BFS.적록색약
		check = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (!check[i][j]) {
					ans2++;
					dfs2(i, j);			// map[i][j] 위치 탐색 시작
				}
			}
		}

		System.out.println(ans1 + " " + ans2);
	}
}

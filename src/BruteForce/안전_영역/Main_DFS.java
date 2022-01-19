package BruteForce.안전_영역;
import java.io.*;
import java.util.StringTokenizer;

/*
- 비의 양에 따라 안전 영역의 개수가 달라짐
  => 비의 양에 따른 모든 경우를 확인하여, 안전 영역의 최대 개수 구하기
- 지역의 높이 (행렬 칸의 값): 1 ~ 100
*/

/*
1. 아이디어
 - 행렬 입력하면서, 최대 지역 높이 저장
 - 브루트 포스로 가능한 비의 양 모두 확인
   => DFS 반복
   => 0 <= 비의 양 < 최대 건물 높이
     (비의 양 == 0 인 경우, 모든 지역이 안전 영역 => 안전 영역 1개
      비의 양 == 최대 지역 높이인 경우, 모든 지역이 잠김 => 안전 영역 0개)
 - for 문에서 [0, 0] ~ [n, n] 확인
   => 해당 지점이 안전 영역이고 아직 방문 안한 경우, 탐색 시작

2. 자료구조
 - boolean[]: 방문 확인

3. 시간 복잡도
 - 인접 리스트 DFS / BFS 의 시간 복잡도: O(V + E)
   => V: n x n, E: 한 vertex 당 4개 연결 가정하면 4V
   => O(V + E) = O(5V) = O(5 x n^2)
   => n 최대값 대입: 5 x 10^4 = 5 x 10^4
 => DFS 를 비의 양 (0 ~ 건물 최대 높이 - 1) 만큼 반복
 => 지역 최대 높이 100 가정
 => 총 시간 복잡도: 100 x (5 x 10^4) = 5 x 10^6 << 1억 (1초)
*/

public class Main_DFS {
	static int n;					// n x n 행렬 (지역 높이 2차원 배열)
	static int[][] heights;
	static int maxSafeCount;		// 출력: 안전 영역의 최대 개수

	static int maxHeight;			// 지역 최대 높이
	static boolean[][] check;
	static int[] dy = { -1, 1, 0, 0 };		// 상하좌우
	static int[] dx = { 0, 0, -1, 1 };

	/* rain: 비의 양 */
	static void dfs(int row, int col, int rain) {
		check[row][col] = true;

		for (int i = 0; i < 4; i++) {
			int nextRow = row + dy[i];
			int nextCol = col + dx[i];

			if (0 <= nextRow && nextRow < n &&
				0 <= nextCol && nextCol < n) {
				// 다음 지점이 안전 영역이고, 아직 방문 안한 경우
				if (heights[nextRow][nextCol] > rain
						&& !check[nextRow][nextCol])
					dfs(nextRow, nextCol, rain);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		heights = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				heights[i][j] = Integer.parseInt(st.nextToken());
				maxHeight = Math.max(maxHeight, heights[i][j]);
			}
		}

		// 비의 양: 0 ~ 최대 지역 높이 - 1 로 탐색
		for (int rain = 0; rain < maxHeight; rain++) {
			check = new boolean[n][n];
			int safeCount = 0;
			// 비의 양 rain 으로 설정하여 탐색한 안전 영역 개수

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					// 해당 지역이 안전 영역이고, 아직 방문 안한 경우
					if (heights[i][j] > rain && !check[i][j]) {
						safeCount++;
						dfs(i, j, rain);		// 탐색 시작
					}
				}
			}

			maxSafeCount = Math.max(maxSafeCount, safeCount);
		}

		System.out.println(maxSafeCount);
	}
}

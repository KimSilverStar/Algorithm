package DFS_BFS.알파벳;
import java.io.*;
import java.util.StringTokenizer;

/*
- 새로 이동한 칸의 알파벳 != 지금까지 지나온 모든 칸의 알파벳
  => 같은 알파벳이 적힌 칸을 2번 이상 지날 수 없음
- [0, 0]에서 시작해서 이동 가능한 칸의 최대 개수 구하기

1. 아이디어
 - 시작 지점 [0, 0] 에서부터 DFS 시작
   => 다음 지점의 알파벳을 아직 방문 안한 경우, 탐색 확장
      + 탐색 확장하면서 이동 가능한 칸 최대 개수 갱신 (DFS 함수 파라미터로 전달)
   => 재귀 종료 조건: 다음 지점의 알파벳을 이미 방문한 경우 (이동 가능한 칸이 없는 경우)
 => DFS + Backtracking

2. 자료구조
 - boolean[]: 알파벳 방문 확인
   => 길이 26 (알파벳 A ~ Z 개수)
   e.g. 알파벳 'B' 방문 확인 => check[해당 알파벳 대문자 - 'A']

3. 시간 복잡도
 - 인접 행렬을 이용한 DFS / BFS 의 시간 복잡도: O(V^2)
   => V: 칸 수 (R X C)
   => O(V^2) = O( (R X C)^2 )
   => R, C 최대값 대입: (20 x 20)^2 = 400^2 = 160,000 << 2억 (2초)
*/

public class Main {
	static int r, c;				// 입력 행, 열 개수
	static char[][] board;			// 알파벳이 적힌 row행 col열 보드
	static int maxNum = 0;			// 출력 값, 이동 가능한 최대 칸 수

	static boolean[] check = new boolean[26];		// 알파벳 방문 확인 (A ~ Z 26개)
	static int[] dy = { -1, 1, 0, 0 };				// 상하좌우
	static int[] dx = { 0, 0, -1, 1 };

	/* count: 이동한 칸의 수 (지나온 알파벳 개수) */
	static void dfs(int row, int col, int count) {
//		char alpha = board[row][col];			// 현재 지점의 알파벳
//		check[alpha - 'A'] = true;				// 현재 지점의 알파벳 방문

		// 현재 지점 기준, 상하좌우 확인
		for (int i = 0; i < 4; i++) {
			int nextRow = row + dy[i];
			int nextCol = col + dx[i];

			if (0 <= nextRow && nextRow < r
					&& 0 <= nextCol && nextCol < c) {
				char nextAlpha = board[nextRow][nextCol];		// 다음 지점의 알파벳
				if (!check[nextAlpha - 'A']) {
					check[nextAlpha - 'A'] = true;
					dfs(nextRow, nextCol, count + 1);

					// 재귀 호출 복귀 시점: 방문 확인 배열 복구 => Backtracking
					check[nextAlpha - 'A'] = false;
				}
			}
		}

		// 이동 가능한 칸이 없는 경우
//		check[alpha - 'A'] = false;
		maxNum = Math.max(maxNum, count);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		board = new char[r][c];
		for (int i = 0; i < r; i++) {
			String str = br.readLine();
			for (int j = 0; j < c; j++)
				board[i][j] = str.charAt(j);
		}

		// [0, 0] 방문 처리하고 시작
		char start = board[0][0];
		check[start - 'A'] = true;
		dfs(0, 0, 1);

		System.out.println(maxNum);
	}
}

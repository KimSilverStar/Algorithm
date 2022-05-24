package Code_Tree.DFS.두_방향_탈출_가능_여부_판별하기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - [0][0] -> [n-1][m-1] 경로 존재 여부만 확인
   => DFS

2. 자료구조
 - int[][]: 입력 행렬
 - boolean[][]: 방문 여부

3. 시간 복잡도
 - O(V + E)
   => V = n x m, E = 2V => O(V + E) = O(V + 2V) = O(3V) = O(3 x n x m)
   => n, m 최대값 대입: 3 x 100 x 100 = 3 x 10^4 << 1억
*/

public class Main {
	static int n, m;				// n x m 행렬
	static int[][] map;
	static boolean canEscape;		// 출력, 탈출 가능 여부
	static boolean[][] visited;
	static int[] dy = { 1, 0 };		// 이동 방향: 하, 우
	static int[] dx = { 0, 1 };

	static void dfs(int y, int x) {
		if (y == n - 1 && x == m - 1) {
			canEscape = true;
			return;
		}

		for (int i = 0; i < 2; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (isValid(ny, nx) && !visited[ny][nx]
					&& map[ny][nx] == 1) {		// 뱀이 없는 길(1)인 경우
				visited[ny][nx] = true;
				dfs(ny, nx);
			}
		}
	}

	static boolean isValid(int y, int x) {
		return (0 <= y && y < n) && (0 <= x && x < m);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new int[n][m];
		visited = new boolean[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		visited[0][0] = true;
		dfs(0, 0);

		if (canEscape)
			System.out.println(1);
		else
			System.out.println(0);
	}
}

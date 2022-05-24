package Code_Tree.BFS.네_방향_탈출_가능_여부_판별하기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - BFS (솔직히, 단순히 경로 존재 여부는 DFS)

2. 자료구조
 - Queue<Pair>, LinkedList<Pair>: BFS 수행
 - int[][]: 입력 행렬
 - boolean[][]: 방문 확인

3. 시간 복잡도
 - O(V + E)
   => V = n x m, E = 4V => O(V + E) = O(V + 4V) = O(5V) = O(5 x n x m)
   => n, m 최대값 대입: 5 x 100 x 100 = 5 x 10^4 << 1억)
*/

class Pair {
	public int y, x;

	public Pair(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static int n, m;					// n x m 행렬
	static int[][] map;
	static boolean[][] visited;
	static boolean canEscape;			// 출력, 탈출 가능 여부
	static Queue<Pair> queue = new LinkedList<>();
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void bfs() {
		while (!queue.isEmpty()) {
			Pair current = queue.remove();

			if (current.y == n - 1 && current.x == m - 1) {
				canEscape = true;
				return;
			}

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (isValid(ny, nx) && !visited[ny][nx]
						&& map[ny][nx] == 1) {			// 뱀이 없는 길(1)인 경우
					visited[ny][nx] = true;
					queue.add(new Pair(ny, nx));
				}
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
		queue.add(new Pair(0, 0));
		bfs();

		if (canEscape)
			System.out.println(1);
		else
			System.out.println(0);
	}
}

package Code_Tree.BFS.갈_수_있는_곳들;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 시작 지점이 여러 개인 경우의 BFS 탐색
   => 모든 시작 지점을 Queue 에 넣은 후, BFS 수행
 - BFS 완료 후, 방문 처리 배열에서 방문한 지점 개수 count

2. 자료구조
 - int[][] map: 입력 행렬
 - boolean[][] visited: 방문 확인
 - Queue<Pair>, LinkedList<Pair>: BFS 수행

3. 시간 복잡도
 - O(V + E)
   => V = n^2, E = 4V => O(V + E) = O(5V) = O(5 x n^2) = O(n^2)
   => n 최대값 대입: 100^2 = 10^4 << 1억
*/

public class Main_Faster {
	static int n;					// n x n 행렬
	static int k;					// 시작점 개수
	static int[][] map;
	static boolean[][] visited;		// 출력, 전체 방문 표시
	static Queue<Pair> queue = new LinkedList<>();
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void bfs() {
		while (!queue.isEmpty()) {
			Pair current = queue.remove();

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (isValid(ny, nx) && !visited[ny][nx]
						&& map[ny][nx] == 0) {		// 갈 수 있는 길(0)인 경우
					visited[ny][nx] = true;			// 전체 누적 방문 처리
					queue.add(new Pair(ny, nx));
				}
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
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		map = new int[n + 1][n + 1];			// [1][1] ~ [n][n] 사용
		visited = new boolean[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());

			// 모든 시작 지점을 Queue 에 넣은 후, BFS 수행
			visited[y][x] = true;
			queue.add(new Pair(y, x));
		}

		bfs();

		int count = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (visited[i][j])
					count++;
			}
		}
		System.out.println(count);
	}
}

package Code_Tree.BFS.갈_수_있는_곳들;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 각 시작 지점에서 BFS 수행
   => 전체 누적 방문 표시 배열 boolean[][] visited 에 방문 표시
      (단순히 해당 시작 지점에서의 BFS 경로만 방문 처리하는 게 아닌,
       모든 시작 지점에 대해서 누적하여 방문 처리)
 - 전체 시작 지점에 대해 BFS 수행 완료 후,
   전체 누적 방문 표시 배열에서 방문한 지점 개수 count

2. 자료구조
 - int[][] map: 입력 행렬
 - boolean[][] visited: 전체 누적 방문 처리
 - Pair[] pairs: 입력 시작 지점
 - Queue<Pair>, LinkedList<Pair>: BFS 수행

3. 시간 복잡도
 - 한 시작 지점에 대해 BFS 1회 수행: O(V + E)
   => V = n^2, E = 4V => O(V + E) = O(5V) = O(5 x n^2) = O(n^2)
 - k개 시작 지점에 대해 각각 BFS 수행: O(k x n^2)
   => n, k 최대값 대입: n^2 x n^2 = n^4 = 100^4 = 10^8 (1억) <= 1억
*/

public class Main {
	static int n;					// n x n 행렬
	static int k;					// 시작점 개수
	static int[][] map;
	static boolean[][] visited;		// 출력, 전체 방문 표시
	static Pair[] pairs;			// 각 시작점 위치
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

		pairs = new Pair[k];
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());

			pairs[i] = new Pair(y, x);
		}

		// 각 시작 지점에 대해 각각 BFS 수행
		for (Pair start : pairs) {
			visited[start.y][start.x] = true;		// 전체 누적 방문 처리
			queue.add(start);
			bfs();
		}

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

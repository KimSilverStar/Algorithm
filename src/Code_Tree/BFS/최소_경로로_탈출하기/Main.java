package Code_Tree.BFS.최소_경로로_탈출하기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 모든 간선의 가중치가 같음
 - 여러 경로 중, 최소 비용의 경로
 => BFS

2. 자료구조
 - int[][] map: 입력 행렬
 - boolean[][] visited: 방문 확인
 - Queue<State>, LinkedList<State>: BFS 수행
   => State: 현재 위치 (y, x), 현재 위치까지 이동 거리 distance

3. 시간 복잡도
 - O(V + E)
   => V = n x m, E = 4V => O(V + E) = O(5V) = O(5 x n x m) = O(n x m)
   => n, m 최대값 대입: 100 x 100 = 10^4 << 1억
*/

class State {
	public int y, x;
	public int distance;	// 현재 지점 [y][x] 까지 이동 거리

	public State(int y, int x, int distance) {
		this.y = y;
		this.x = x;
		this.distance = distance;
	}
}

public class Main {
	static int n, m;				// n x m 행렬
	static int[][] map;
	static boolean[][] visited;
	static int minDistance = -1;	// 출력, 탈출 최단 거리 (-1 값 그대로 이면, 탈출 불가능)
	static Queue<State> queue = new LinkedList<>();
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void bfs() {
		while (!queue.isEmpty()) {
			State current = queue.remove();

			// 끝 지점 도착한 경우 => minDistance 출력, 탐색 종료
			if (current.y == n - 1 && current.x == m - 1) {
				minDistance = current.distance;
				return;
			}

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (isValid(ny, nx) && !visited[ny][nx]
						&& map[ny][nx] == 1) {		// 뱀이 없는 길(1)인 경우
					visited[ny][nx] = true;
					queue.add(new State(ny, nx, current.distance + 1));
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
		queue.add(new State(0, 0, 0));
		bfs();

		System.out.println(minDistance);
	}
}

package DFS_BFS.벽_부수고_이동하기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - [1][1] -> [n][m] 로의 최단 경로
   => BFS
 - 단, 벽 1회 부수기 가능
 - [y][x] 지점까지 [벽을 부수고 or 안 부수고] 이동한 거리
   => BFS 상태 값: (y, x), crashCount(0 or 1), distance

2. 자료구조
 - Queue<State>, LinkedList<State>: BFS
 - boolean[][][] visited: 방문 확인
   ex) visited[y][x][0]: [y][x] 지점을 벽을 안 부수고 방문 했는지

3. 시간 복잡도
 - BFS의 시간 복잡도: O(V + E)
   => 한 V당 이동 가능한 곳 대략 상하좌우 x 벽 부순 경우 or 안 부순 경우 고려
   => E: 4 x 2 V = 8V
   => O(V + E) = O(9V) = O(9 x n x m)
   => n, m 최대값 대입: 9 x 10^3 x 10^3 = 9 x 10^6 << 2억
*/

class State {
	public int y, x;
	public int crashCount;
	public int distance;

	public State(int y, int x, int crashCount, int distance) {
		this.y = y;
		this.x = x;
		this.crashCount = crashCount;
		this.distance = distance;
	}
}

public class Main2 {
	static int n, m;				// n x m 행렬
	static char[][] map;
	static int minDistance = Integer.MAX_VALUE;		// 출력, 최단 거리 (불가능하면 -1)

	static boolean[][][] visited;
	static Queue<State> queue = new LinkedList<>();
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void bfs() {
		while (!queue.isEmpty()) {
			State current = queue.remove();

			if (current.y == n && current.x == m) {
				minDistance = Math.min(minDistance, current.distance);
			}

			// 상하좌우 + 벽 부순 여부
			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (!isValid(ny, nx))
					continue;

				// 다음 상태를 이미 방문한 경우 => BFS 탐색 제외

				// 다음 지점이 길(0)인 경우 => 벽 부수지 않고 이동
				if (map[ny][nx] == '0') {
					if (!visited[ny][nx][current.crashCount]) {
						visited[ny][nx][current.crashCount] = true;
						queue.add(new State(ny, nx, current.crashCount, current.distance + 1));
					}
				}
				// 다음 지점이 벽(1)인 경우 => 벽 부수고 이동
				else {	// current.crashCount = 0 인 경우만 이동 가능
					if (current.crashCount == 0 && !visited[ny][nx][current.crashCount + 1]) {
						visited[ny][nx][current.crashCount + 1] = true;
						queue.add(new State(ny, nx, current.crashCount + 1, current.distance + 1));
					}
				}
			}
		}
	}

	static boolean isValid(int y, int x) {
		return (1 <= y && y <= n) && (1 <= x && x <= m);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new char[n + 1][m + 1];		// [1][1] ~ [n][m] 사용
		visited = new boolean[n + 1][m + 1][2];
		for (int i = 1; i <= n; i++) {
			String input = br.readLine();
			for (int j = 1; j <= m; j++)
				map[i][j] = input.charAt(j - 1);
		}

		visited[1][1][0] = true;
		queue.add(new State(1, 1, 0, 1));
		bfs();

		if (minDistance != Integer.MAX_VALUE)
			System.out.println(minDistance);
		else
			System.out.println(-1);
	}
}

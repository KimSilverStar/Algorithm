package 벽_부수고_이동하기;
import java.io.*;
import java.util.*;

/*
- 0: 이동 가능, 1: 이동 불가한 벽
- 처음 [1, 1] -> [n, m] 으로 최단 경로 이동 목표
  => 벽(1)을 최대 1개 부수기 가능
- 최단 경로로 이동할 때, 최단 경로의 거리 값 출력
  (목표 지점으로 이동이 아예 불가능한 경우, -1 출력)
*/

/*
1. 아이디어
 - 최단 거리 => BFS
 - 벽을 부수지 않고 이동하는 경우, 벽을 부수고 이동하는 경우의 2가지 경우가 존재
   => 벽을 부수지 않고 탐색하는 경우, 벽을 부수고 탐색하는 경우의
      방문 확인 배열을 따로 사용

 1) 다음 지점이 벽인 경우
   - 현재 지점까지 벽을 부순 적 없으면, 부수고 이동
 2) 다음 지점이 벽이 아닌 경우
   case 1) 현재 지점까지 벽을 부순 적 없고,
           다음 지점을 아직 방문 안한 경우
   case 2) 현재 지점까지 벽을 부순 적 있고,
     	   다음 지점을 아직 방문 안한 경우

2. 자료구조
 - Queue<Point>, LinkedList<Point>: BFS
   => Point: 해당 지점 좌표, 해당 지점까지 이동한 거리, 해당 지점까지 이동하면서 벽 부순 여부
 - boolean[][][]: 벽 부순 경우 / 안 부순 경우 방문 여부
   1) check[i][j][0]: [i, j] 지점까지 벽 부수지 않고 방문 여부
   2) check[i][j][1]: [i, j] 지점까지 벽 부수고 방문 여부
   (boolean[][] check1, boolean[][] check2 처럼 2차원 방문 확인 배열 2개 사용해도 가능)

3. 시간 복잡도
 - 총 시간 복잡도: BFS 1번 수행
   => O(V + E) = O(5V) (V: n x m)
   => n, m 최대값 대입: 5 x 10^6 << 2억 (2초)
*/

class Point {
	public int y, x;
	public int distance;			// 현재 지점까지 이동한 거리
	public boolean destroyed;		// 현재 지점까지 이동하면서, 벽을 부순 여부

	public Point(int y, int x, int distance, boolean destroyed) {
		this.y = y;
		this.x = x;
		this.distance = distance;
		this.destroyed = destroyed;
	}
}

public class Main {
	static int n, m;			// n x m 행렬
	static char[][] map;
	static int minDistance = Integer.MAX_VALUE;				// 최단 거리

	static Queue<Point> queue = new LinkedList<>();
	static boolean[][][] check;				// 벽 부순 경우 / 안 부순 경우 방문 여부
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void bfs() {
		while (!queue.isEmpty()) {
			Point current = queue.remove();

			if (current.y == n - 1 && current.x == m - 1) {			// 목표 지점
				minDistance = current.distance;
				return;
			}

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				// 다음 지점이 범위 밖인 경우
				if (ny < 0 || ny >= n || nx < 0 || nx >= m)
					continue;

				if (map[ny][nx] == '1') {		// 다음 지점이 벽인 경우
					// 현재 지점까지 벽을 부순 적 없고, 다음 지점을 아직 방문 안한 경우 => 부수고 이동
					if (!current.destroyed && !check[ny][nx][0]) {
						check[ny][nx][1] = true;
						queue.add(new Point(ny, nx, current.distance + 1, true));
					}
				}
				else {		// 다음 지점이 벽이 아닌 경우
					// 현재 지점까지 벽을 부순 적 없고, 다음 지점을 아직 방문 안한 경우
					if (!current.destroyed && !check[ny][nx][0]) {
						check[ny][nx][0] = true;
						queue.add(new Point(ny, nx, current.distance + 1, false));
					}
					// 현재 지점까지 벽을 부순 적 있고, 다음 지점을 아직 방문 안한 경우
					else if (current.destroyed && !check[ny][nx][1]) {
						check[ny][nx][1] = true;
						queue.add(new Point(ny, nx, current.distance + 1, true));
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		check = new boolean[n][m][2];
		map = new char[n][m];
		for (int i = 0 ; i < n; i++) {
			String input = br.readLine();
			for (int j = 0; j < m; j++)
				map[i][j] = input.charAt(j);
		}

		check[0][0][0] = true;
		queue.add(new Point(0 ,0, 1, false));
		bfs();

		if (minDistance != Integer.MAX_VALUE)
			System.out.println(minDistance);
		else
			System.out.println(-1);
	}
}

package Shortest_Path.Dijkstra.미로_만들기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 시작 지점 [0][0] -> 끝 지점 [n-1][n-1] 으로의 최단경로
   => BFS
 - 단, 탐색 시 방을 바꾼 개수를 탐색 상태에 포함하여 고려
   => 방을 바꾼 개수 count 를 작은 순으로 먼저 탐색해야 함

 Queue 가 empty 할 때까지 다음을 반복
 - 현재 지점까지 방을 바꾼 개수 < 다음 지점까지 방을 바꾼 개수
   => queue 에 다음 지점을 추가


2. 자료구조
 - int[][] visited: 방문 확인
   ex) visited[y][x]: (y, x) 지점까지 방을 몇 개 바꿔서 방문했는지
   => 메모리: 4 x n x n byte = 4 x n^2 byte
   => n 최대값 대입: 4 x 25 x 10^2 byte = 10^4 byte = 10 KB
 - Queue<Point>: BFS


3. 시간 복잡도
 - BFS 시간 복잡도: O(V + E)
   => V: 최대 n^2 개, E: 한 Vertex 당 Edge 4개 가정
   = V + E = V + 4V = 5V = 5 x n^2
   => n 최대값 대입: 5 x 25 x 10^2 = 125 x 10^2 << 1억
*/

class Point {
	public int y, x;

	public Point(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class Main_BFS2 {
	static int n;					// n x n 행렬
	static int[][] map;
	static int minCount;			// 출력, 바꾸어야 할 검은 방 최소 개수

	static Queue<Point> queue = new LinkedList<>();
	static int[][] visited;
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void bfs() {
		visited[0][0] = 0;
		queue.add(new Point(0, 0));

		while (!queue.isEmpty()) {
			Point current = queue.remove();

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (ny < 0 || ny >= n || nx < 0 || nx >= n)
					continue;

				// 그 다음 지점이 방을 바꾼 개수가 더 적거나 같은 경우는 탐색 제외
				if (visited[ny][nx] <= visited[current.y][current.x])
					continue;

				if (map[ny][nx] == 1) {		// 흰 방
					visited[ny][nx] = visited[current.y][current.x];
					queue.add(new Point(ny, nx));
				}
				else {						// 검은 방
					visited[ny][nx] = visited[current.y][current.x] + 1;
					queue.add(new Point(ny, nx));
				}
			}
		}

		minCount = visited[n - 1][n - 1];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		visited = new int[n][n];
		for (int i = 0; i < n; i++) {
			String input = br.readLine();
			for (int j = 0; j < n; j++) {
				map[i][j] = Character.getNumericValue(input.charAt(j));
				visited[i][j] = Integer.MAX_VALUE;		// 최대값으로 초기화
			}
		}

		bfs();
		System.out.println(minCount);
	}
}

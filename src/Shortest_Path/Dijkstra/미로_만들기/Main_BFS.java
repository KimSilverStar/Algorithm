package Shortest_Path.Dijkstra.미로_만들기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 시작 지점 [0][0] -> 끝 지점 [n-1][n-1] 으로의 최단경로
   => BFS
 - 단, 탐색 시 방을 바꾼 개수를 탐색 상태에 포함하여 고려
   => 방을 바꾼 개수 count 를 작은 순으로 먼저 탐색해야 함
   => Queue 대신 PriorityQueue 사용

 PriorityQueue 가 empty 할 때까지 다음을 반복
 - 다음 지점이 범위 안이고, 다음 지점을 최소 count 로 아직 방문 안한 경우
   => 다음 지점을 pq 에 추가


2. 자료구조
 - boolean[][][] visited: 방문 확인
   ex) visited[y][x][k]: (y, x) 지점까지 k개 방을 바꾸었을 때의 방문 여부
   => 메모리: 최대 n x n x n^2 byte = n^4 byte
   => n 최대값 대입: 125 x 10^3 byte = 125 KB
 - PriorityQueue<State>: count 작은 순으로 BFS 먼저 탐색
   => State: 지점 (y, x), 이때까지 방을 바꾼 개수 count


3. 시간 복잡도
 - BFS 시간 복잡도: O(V + E)
   => V: 최대 n^2 개, E: 한 Vertex 당 Edge 4개 가정
   = V + E = V + 4V = 5V = 5 x n^2
   => n 최대값 대입: 5 x 25 x 10^2 = 125 x 10^2 << 1억
*/

class State implements Comparable<State> {
	public int y, x;		// 위치
	public int count;		// 바꾼 방 개수

	public State(int y, int x, int count) {
		this.y = y;
		this.x = x;
		this.count = count;
	}

	// count 작은 순으로 BFS 탐색하기 위함
	public int compareTo(State s) {
		return this.count - s.count;
	}
}

public class Main_BFS {
	static int n;					// n x n 행렬
	static int[][] map;
	static int minCount;			// 출력, 바꾸어야 할 검은 방 최소 개수

	static PriorityQueue<State> pq = new PriorityQueue<>();
	static boolean[][][] visited;
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void bfs() {
		visited[0][0][0] = true;
		pq.add(new State(0, 0, 0));

		while (!pq.isEmpty()) {
			State current = pq.remove();

			if (current.y == n - 1 && current.x == n - 1) {
				minCount = current.count;
				return;
			}

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (ny < 0 || ny >= n || nx < 0 || nx >= n)
					continue;

				int nCount = (map[ny][nx] == 0) ?
						current.count + 1 : current.count;
				if (!visited[ny][nx][nCount]) {
					visited[ny][nx][nCount] = true;
					pq.add(new State(ny, nx, nCount));
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		visited = new boolean[n][n][n * n + 1];		// [][][0] ~ 최대 [][][n^2] 사용
		for (int i = 0; i < n; i++) {
			String input = br.readLine();
			for (int j = 0; j < n; j++)
				map[i][j] = Character.getNumericValue(input.charAt(j));
		}

		bfs();
		System.out.println(minCount);
	}
}

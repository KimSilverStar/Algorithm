package Code_Tree.BFS.나이트;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 간선의 가중치가 모두 같음
 - 여러 경로 중, 최소 비용의 경로
 => BFS

2. 자료구조
 - boolean[][]: 방문 확인
 - Queue<State>, LinkedList<State>: BFS 수행
   => Staet: 현재 지점 [y][x], 이동 횟수 count

3. 시간 복잡도
 - O(V + E)
   => V = n^2, E = 8V => O(V + E) = O(9V) = O(9 x n^2) = O(n^2)
   => n 최대값 대입: 100^2 = 10^4 << 1억
*/

class State {
	public int y, x;
	public int count;		// [y][x] 지점까지 이동 횟수

	public State(int y, int x, int count) {
		this.y = y;
		this.x = x;
		this.count = count;
	}
}

public class Main {
	static int n;								// n x n 행렬
	static int startY, startX, endY, endX;		// 출발, 도착 지점
	static int minCount = -1;					// 최소 이동 횟수 (불가능한 경우 -1)
	static boolean[][] visited;
	static Queue<State> queue = new LinkedList<>();
	// 나이트 이동 방향 8개
	static int[] dy = { -2, -1, +1, +2, +2, +1, -1, -2 };
	static int[] dx = { +1, +2, +2, +1, -1, -2, -2, -1 };

	static void bfs() {
		while (!queue.isEmpty()) {
			State current = queue.remove();

			if (current.y == endY && current.x == endX) {
				minCount = current.count;
				return;
			}

			for (int i = 0; i < 8; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (isValid(ny, nx) && !visited[ny][nx]) {
					visited[ny][nx] = true;
					queue.add(new State(ny, nx, current.count + 1));
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
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		visited = new boolean[n + 1][n + 1];		// [1][1] ~ [n][n] 사용

		st = new StringTokenizer(br.readLine());
		startY = Integer.parseInt(st.nextToken());
		startX = Integer.parseInt(st.nextToken());
		endY = Integer.parseInt(st.nextToken());
		endX = Integer.parseInt(st.nextToken());

		visited[startY][startX] = true;
		queue.add(new State(startY, startX, 0));
		bfs();

		System.out.println(minCount);
	}
}

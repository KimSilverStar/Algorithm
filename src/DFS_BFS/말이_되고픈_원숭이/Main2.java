package DFS_BFS.말이_되고픈_원숭이;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - [0][0] -> [h-1][w-1] 이동 최소 비용
   => BFS
 - 단, 최대 k번 만큼 말처럼 이동 가능
 - 상태 값: [y][x] 지점으로 [horseCount]번 만큼 말처럼 이동했을 때, 전체 동작 횟수 [count]

2. 자료구조
 - Queue<State>, LinkedList<State>: BFS
 - boolean[][][]: 방문 확인
   ex) [y][x] 지점을 [k]번 만큼 말처럼 동작하여 방문 했는지
   => 메모리: 최대 200 x 200 x 30 byte = 12 x 10^5 byte = 1.2 MB

3. 시간 복잡도
 - BFS 시간 복잡도: O(V + E)
   => O(V + E) = O(V + 12V) = O(13V) = O(13 x h x w x k)
   => h, w, k 최대값 대입: 13 x 200 x 200 x 30 = 156 x 10^5 << 2억
*/

class State {
	public int y, x;
	public int horseCount;		// 말처럼 동작한 횟수
	public int totalCount;		// 전체 동작 횟수 (원숭이 + 말)

	public State(int y, int x, int horseCount, int totalCount) {
		this.y = y;
		this.x = x;
		this.horseCount = horseCount;
		this.totalCount = totalCount;
	}
}

public class Main2 {
	static int k;				// 말 처럼 동작 가능한 최대 횟수
	static int w, h;			// h x w 행렬
	static int[][] map;
	static int minCount = Integer.MAX_VALUE;	// 출력, 동작 최소 횟수 (불가능하면 -1)

	static boolean[][][] visited;
	static Queue<State> queue = new LinkedList<>();

	static int[] monkeyDy = { -1, 1, 0, 0 };	// 원숭이 이동
	static int[] monkeyDx = { 0, 0, -1, 1 };
	static int[] horseDx = { -2, -1, +1, +2, +2, +1, -1, -2 };	// 말 이동
	static int[] horseDy = { +1, +2, +2, +1, -1, -2, -2, -1 };

	static void bfs() {
		while (!queue.isEmpty()) {
			State current = queue.remove();

			if (current.y == h - 1 && current.x == w - 1)
				minCount = Math.min(minCount, current.totalCount);

			// 원숭이 동작
			for (int i = 0; i < 4; i++) {
				int ny = current.y + monkeyDy[i];
				int nx = current.x + monkeyDx[i];

				if (!isValid(ny, nx))
					continue;

				// 다음 지점이 평지(0)이고, 다음 상태를 방문 안한 경우
				if (map[ny][nx] == 0 && !visited[ny][nx][current.horseCount]) {
					visited[ny][nx][current.horseCount] = true;
					queue.add(new State(ny, nx, current.horseCount, current.totalCount + 1));
				}
			}

			if (current.horseCount >= k)
				continue;

			// 말 동작 => current.horseCount < k 인 경우만 가능
			for (int i = 0; i < 8; i++) {
				int ny = current.y + horseDy[i];
				int nx = current.x + horseDx[i];

				if (!isValid(ny, nx))
					continue;

				// 다음 지점이 평지(0)이고, 다음 상태를 방문 안한 경우
				if (map[ny][nx] == 0 && !visited[ny][nx][current.horseCount + 1]) {
					visited[ny][nx][current.horseCount + 1] = true;
					queue.add(new State(ny, nx, current.horseCount + 1, current.totalCount + 1));
				}
			}
		}
	}

	static boolean isValid(int y, int x) {
		return (0 <= y && y < h) && (0 <= x && x < w);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		k = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		w = Integer.parseInt(st.nextToken());
		h = Integer.parseInt(st.nextToken());

		map = new int[h][w];
		visited = new boolean[h][w][k + 1];
		for (int i = 0; i < h; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < w; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		visited[0][0][0] = true;
		queue.add(new State(0, 0, 0, 0));
		bfs();

		if (minCount != Integer.MAX_VALUE)
			System.out.println(minCount);
		else
			System.out.println(-1);
	}
}

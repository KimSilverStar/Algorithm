package Samsung_Coding_Test.연구소;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 벽을 반드시 3개 세워서, 바이러스가 최소로 퍼지도록 함

 1) 전체 빈 칸에서 벽을 세울 빈 칸 3개 선택 => 조합(백트래킹 + 브루트포스)
   - 세울 벽 위치를 3개 선택 완료한 경우 2), 3) 과정 수행

 2) 벽을 세울 3개 빈 칸 선택 후, 맵에 바이러스 확산 표시
   - 각 바이러스 지점에 대해 BFS 수행

 3) 바이러스 확산 표시된 map에서 안전 영역 칸 count
   - 2중 for문


2. 자료구조
 - Queue<Point> queue: BFS 수행
 - boolean[][] visited


3. 시간 복잡도

*/

class Point {
	public int y, x;

	public Point(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static int n, m;				// n x m 행렬
	static int[][] originMap;		// 바이러스 확산 전 맵
	static int[][] virusMap;		// 바이러스 확산 맵
	static int maxCount;			// 출력, 안전 영역 최대 크기

	static Queue<Point> queue = new LinkedList<>();
	static boolean[][] visited;

	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };
	static final int EMPTY = 0, WALL = 1, VIRUS = 2;

	static void backtrack(int wallCnt) {
		// 세울 벽 위치 3개 선택 완료한 경우
		if (wallCnt == 3) {
			visited = new boolean[n][m];		// 초기화
			virusMap = new int[n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					virusMap[i][j] = originMap[i][j];
				}
			}

			// 바이러스 확산 표시 => BFS
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (!visited[i][j] && virusMap[i][j] == VIRUS) {
						visited[i][j] = true;
						queue.add(new Point(i, j));
						bfs();
					}
				}
			}

			// 안전 영역 칸 count
			int count = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (virusMap[i][j] == EMPTY) {
						count++;
					}
				}
			}
			maxCount = Math.max(maxCount, count);

			return;
		}

		// 세울 벽 위치 선택
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (originMap[i][j] == EMPTY) {
					originMap[i][j] = WALL;		// 벽 세움
					backtrack(wallCnt + 1);

					originMap[i][j] = EMPTY;		// 세운 벽 취소
				}
			}
		}
	}

	static void bfs() {
		while (!queue.isEmpty()) {
			Point current = queue.remove();

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (!isValid(ny, nx))
					continue;

				if (!visited[ny][nx] && virusMap[ny][nx] == EMPTY) {
					virusMap[ny][nx] = VIRUS;			// 바이러스 확산
					visited[ny][nx] = true;
					queue.add(new Point(ny, nx));
				}
			}
		}
	}

	static boolean isValid(int ny, int nx) {
		return (0 <= ny && ny < n) && (0 <= nx && nx < m);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		originMap = new int[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < m; j++) {
				originMap[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// Init Call
		backtrack(0);

		System.out.println(maxCount);
	}
}

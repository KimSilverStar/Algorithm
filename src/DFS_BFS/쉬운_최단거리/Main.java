package DFS_BFS.쉬운_최단거리;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

/*
- 0: 갈 수 없는 땅, 1: 갈 수 있는 땅, 2: 목표 지점
- 각 지점에서 목표 지점까지의 거리 구하기
  => 원래 갈 수 없는 땅 (0)은 그대로 0 출력
  => 원래 갈 수 있는 땅 (1)에서 도달 할 수 없으면 -1 출력

1. 아이디어
 - 목표 지점으로부터 모든 지점들과의 거리 계산 => BFS

2. 자료구조


3. 시간 복잡도
*/

class Coord {
	private int row;
	private int col;

	public Coord(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public int getRow() { return row; }
	public int getCol() { return col; }
}

public class Main {
	static int n, m;			// n행 m열 지도
	static int[][] map;
	static int[] goal = new int[2];			// 목표 지점 [row, col]
	static int[][] answer;					// 출력 결과 행렬

	static Queue<Coord> queue = new LinkedList<>();
	static boolean[][] check;				// 최단거리 계산 시, 방문 확인
	static int[] dy = { -1, 1, 0, 0 };		// 상하좌우
	static int[] dx = { 0, 0, -1, 1 };

	static void bfs() {
		check[goal[0]][goal[1]] = true;				// 목표 지점
		queue.add(new Coord(goal[0], goal[1]));

		while (!queue.isEmpty()) {
			Coord c = queue.remove();

			for (int i = 0; i < 4; i++) {
				int nextRow = c.getRow() + dy[i];
				int nextCol = c.getCol() + dx[i];

				if (0 <= nextRow && nextRow < n &&
					0 <= nextCol && nextCol < m) {
					// 다음 지점이 갈 수 있는 땅(1)이고, 아직 방문 안한 경우
					if (map[nextRow][nextCol] == 1 &&
							!check[nextRow][nextCol]) {
						check[nextRow][nextCol] = true;
						queue.add(new Coord(nextRow, nextCol));
						answer[nextRow][nextCol] = answer[c.getRow()][c.getCol()] + 1;
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
		map = new int[n][m];
		answer = new int[n][m];
		check = new boolean[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());

				if (map[i][j] == 2) {			// 목표 지점 (2)
					goal[0] = i;
					goal[1] = j;
					answer[i][j] = 0;
				}
				else if (map[i][j] == 0)		// 원래 갈 수 없는 땅 (0)
					answer[i][j] = 0;
				else							// 원래 갈 수 있는 땅 (1)
					answer[i][j] = -1;			// => 출력 행렬에 우선 -1 (갈 수 없는 경우)로 채움
			}
		}

		bfs();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++)
				sb.append(answer[i][j]).append(" ");
			sb.append("\n");
		}

		System.out.println(sb.toString());
	}
}

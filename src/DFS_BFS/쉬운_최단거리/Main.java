package DFS_BFS.쉬운_최단거리;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

/*
- 0: 갈 수 없는 땅, 1: 갈 수 있는 땅, 2: 목표 지점
- 원래 갈 수 없는 땅(0)은 그대로 0 출력
- 원래 갈 수 있는 땅(1) 중에서, 도달할 수 없으면 -1 출력

* 목표 지점으로부터 모든 지점으로의 거리 => BFS

1. 아이디어
 - 목표 지점으로부터 모든 지점을 향해 BFS 수행
 - 다음 지점이 갈 수 있는 땅(1)이고 아직 방문 안한 경우
   => 탐색 확장,
   => 각 지점으로의 거리를 저장한 2차원 배열 answer[][] 를 갱신해나감

2. 자료구조
 - int[][]: 목표 지점 -> 각 지점들로의 거리 저장
 - Queue<Coord>, LinkedList<Coord>: BFS 수행
 - boolean[][]: 방문 확인

3. 시간 복잡도
 - DFS / BFS 의 시간 복잡도: O(V + E)		(V: vertex 개수, E: edge 개수)
   => V: n x m, E: 한 vertex 당 4개 edge (상하좌우 연결) 가정 = 대충 4V
   => O(V + E) = O(5V) = O(5 x n x m)
   => n, m 최대값 대입: 5 x 10^ 3 x 10^3 = 5 x 10^6 << 1억 (1초)
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
	static int[][] answer;		// 출력 값, 목표 지점과 각 지점으로의 거리
	
	static int[] goal = new int[2];			// 목표 지점 좌표 [row, col]
	static Queue<Coord> queue = new LinkedList<>();
	static boolean[][] check;				// 방문 확인
	static int[] dy = { -1, 1, 0, 0 };		// 상하좌우
	static int[] dx = { 0, 0, -1, 1 };

	static void bfs() {
		while (!queue.isEmpty()) {
			Coord prev = queue.remove();			// 이전 좌표

			for (int i = 0; i < 4; i++) {
				int nextRow = prev.getRow() + dy[i];
				int nextCol = prev.getCol() + dx[i];

				if (0 <= nextRow && nextRow < n &&
					0 <= nextCol && nextCol < m) {
					// 다음 지점이 갈 수 있는 땅(1)이고, 아직 방문 안한 경우
					if (map[nextRow][nextCol] == 1 &&
							!check[nextRow][nextCol]) {
						check[nextRow][nextCol] = true;
						answer[nextRow][nextCol] = answer[prev.getRow()][prev.getCol()] + 1;
						queue.add(new Coord(nextRow, nextCol));
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
			
			for (int j = 0; j < m; j++)  {
				map[i][j] = Integer.parseInt(st.nextToken());
				
				if (map[i][j] == 2) {			// 목표 지점
					answer[i][j] = 0;
					goal[0] = i;
					goal[1] = j;
				}
				else if (map[i][j] == 1)		// 갈 수 있는 땅
					answer[i][j] = -1;			// 일단 모두 -1 로 채움
				else			// map[i][j] == 0 	갈 수 없는 땅
					answer[i][j] = 0;
			}
		}
		
		// 목표 지점 -> 나머지 모든 지점으로 BFS 수행
		queue.add(new Coord(goal[0], goal[1]));
		check[goal[0]][goal[1]] = true;
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

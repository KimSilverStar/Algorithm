package DFS_BFS.벽_부수고_이동하기;
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
 - 목표 지점에 도달하지 못하는 경우
   : 시작 지점 [0, 0] 과 목표 지점 [n-1, m-1] 이 모두 벽에 둘러쌓인 경우
   => [0, 1], [1, 0] 이 벽이고 [n-1, m-2], [n-2, m-1] 이 벽인 경우

 - 최단 거리 => BFS
 1) 전체 벽의 좌표들을 리스트에 저장
 2) 벽 좌표 리스트에서 부술 벽 1개 선택
   - k 개 벽이면, k 개 경우 존재
 3) 부술 벽 1개 선택 후, BFS 수행

2. 자료구조
 - List<Point>, ArrayList<Point>: 벽 좌표들 저장
 - Queue<Point>, LinkedList<Point>: BFS
 - boolean[][]: 방문 확인

3. 시간 복잡도
 - BFS 한 번 수행: O(V + E) = O(5V)	(V = n x m)
 - 전체 벽 개수가 k 이면, 총 시간 복잡도: O(5 x V x k)
   => 대충 k 가 n x m 이면, O(5 x n^2 x m^2)
   => n, m 최대값 대입: 5 x 10^6 x 10^6 >> 2억 (2초) => 시간 초과 !!!
 => 전체 벽에서 1개 벽을 선택하여 부수는
    모든 경우의 수에 대해 BFS 를 수행하는 방법은 시간 초과 !!!
*/

public class Main_Time_Over {
	static int n, m;			// n x m 행렬
	static int[][] map;
	static int minDistance = Integer.MAX_VALUE;				// 최단 거리
	static int distance;

	static List<Point> wallList = new ArrayList<>();		// 벽 좌표들 저장
	static Queue<Point> queue = new LinkedList<>();
	static boolean[][] check;
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static class Point {
		public int row;
		public int col;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	static void bfs() {
		while (!queue.isEmpty()) {
			int queueSize = queue.size();		// 이동 거리 계산을 위해 Level 단위로 Queue 에서 꺼냄
			for (int i = 0; i < queueSize; i++) {
				Point current = queue.remove();

				if (current.row == n - 1 &&
					current.col == m - 1) {			// 목표 지점
					minDistance = Math.min(minDistance, distance);
					return;
				}

				if (distance > minDistance)
					return;

				for (int d = 0; d < 4; d++) {
					int nextRow = current.row + dy[d];
					int nextCol = current.col + dx[d];

					if (0 <= nextRow && nextRow < n &&
						0 <= nextCol && nextCol < m) {
						// 다음 지점이 이동 가능한 칸(0)이고, 아직 방문 안한 경우
						if (map[nextRow][nextCol] == 0 &&
								!check[nextRow][nextCol]) {
							check[nextRow][nextCol] = true;
							queue.add(new Point(nextRow, nextCol));
						}
					}
				}
			}

			distance++;
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
		for (int i = 0 ; i < n; i++) {
			char[] input = br.readLine().toCharArray();
			for (int j = 0; j < m; j++) {
				map[i][j] = Character.getNumericValue(input[j]);
				if (map[i][j] == 1)
					wallList.add(new Point(i, j));
			}
		}

		for (Point wall : wallList) {
			// 벽 1개 부수고, BFS 탐색 시작
			map[wall.row][wall.col] = 0;
//			System.out.print("["+ wall.row + ", " + wall.col + "]: ");

			check = new boolean[n][m];			// 초기화
			check[0][0] = true;
			distance = 1;
			queue.add(new Point(0 ,0));
			bfs();

//			System.out.println(distance);
			map[wall.row][wall.col] = 1;		// 부순 벽 복구
		}

		if (minDistance != Integer.MAX_VALUE)
			System.out.println(minDistance);
		else
			System.out.println(-1);
	}
}

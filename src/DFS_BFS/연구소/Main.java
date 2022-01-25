package DFS_BFS.연구소;
import java.io.*;
import java.util.*;

/*
- 0: 빈 칸,		1: 벽,		2: 바이러스
- 바이러스가 인접한 빈 칸으로 퍼져나감
- 새로 세울 벽 개수 3개
  => 벽 3개 추가한 후, 바이러스가 퍼질 수 없는 곳: 안전 영역
  => 벽 3개 추가 후, 바이러스가 퍼진 후, 남은 빈 칸(0) 개수
=> 벽 3개 추가한 후, 안전 영역(0)의 최대 개수 구하기
*/

/*
* 3 <= n, m <= 8,	2 <= 바이러스(2)의 개수 <= 10
- Backtracking 으로 벽 3개 세울 위치 선택
- BFS 로 바이러스 영역 탐색하여 계산

1. 아이디어
 1) 추가할 벽 3개 위치 선택 => Backtracking
 2) 추가할 벽 3개 위치 선택 후,
    입력 행렬을 [0, 0] ~ [n, m] 차례로 확인
   - 바이러스 칸(2)이고 아직 방문 안한 경우, BFS 탐색 시작
   - 탐색으로 바이러스가 퍼져 나가는 영역 계산
 => 안전 영역의 칸 수가 최대가 되려면, 바이러스 퍼져나가는 영역이 최소

2. 자료구조
 - Queue<Coord>, LinkedList<Coord>: BFS
 - boolean[][]: 방문 확인

3. 시간 복잡도
 - Backtracking 으로 3개 벽을 추가하는 경우의 수
   => 최대 전체 8 x 8 칸에서 최소 바이러스 칸 2개 제외, 62 칸에서 3개 선택
   => 62 x 61 x 60 = 226,920 가지 경우의 수

   !!! 필요없는 경우의 수 존재
      - [0, 0], [0, 1], [0, 2] 조합과 [0, 2], [0, 1], [0, 0] 은 동일
        => 중복없이, 순서 상관없이 선택하는 조합으로 바꾸면, 시간 절약 가능
      - 하지만, 조합으로 안해도 시간 복잡도 이내이므로 그냥 했음

 - 3개 벽을 세우는 1가지 경우에서, BFS 로 전체 퍼진 바이러스 영역 계산
   => O(V + E)
   => V: 빈 칸 개수, E: 최대 4개 edge 가정
   => O(5V) => 5 x 62 = 310
 => 총 시간 복잡도: 226,920 x 310 = 70,345,200 << 1억 (1초)
*/

/* 오답 노트
- 벽을 세운 후, 바이러스가 퍼져나가는 영역 크기를 계산할 때
  BFS 가 아닌, DFS 사용하여 틀림
  => BFS: 탐색 시작 지점으로부터 이웃한 지점으로 넓게 퍼져나가는 경우 !!
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
	static int n, m;				// n x m 행렬
	static int[][] originMap;		// 기존 입력 행렬
	static int[][] virusMap;		// 바이러스 퍼져나가는 맵
	static int originWallCount;		// 벽 3개 추가 전, 원래 벽 개수
	static int maxSafeCount;		// 출력 값: 안전 영역 최대 개수

	static int virusCount;			// 탐색한 바이러스 퍼져나가는 영역
	static int minVirusCount = Integer.MAX_VALUE;		// 바이러스 퍼져나가는 최소 영역
	static Queue<Coord> queue = new LinkedList<>();
	static boolean[][] check;
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	/* Backtracking 으로 추가할 벽 3개 위치 선택 후, BFS 탐색 시작 */
	/* wallCount: 현재까지 선택된 벽 개수 */
	static void solution(int wallCount) {
		// 추가할 벽 3개 위치 선택 완료
		if (wallCount == 3) {
			check = new boolean[n][m];		// 초기화
			virusCount = 0;
			virusMap = new int[n][m];		// originMap 을 Deep Copy
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++)
					virusMap[i][j] = originMap[i][j];
			}

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					// 바이러스 칸(2)이고 아직 방문 안한 경우 => BFS 시작
					if (virusMap[i][j] == 2 && !check[i][j])
						bfs(i, j);
				}
			}

			minVirusCount = Math.min(minVirusCount, virusCount);
			return;
		}

		// 벽 3개 선택
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				// 빈 칸(0)인 경우
				if (originMap[i][j] == 0) {
					originMap[i][j] = 1;		// 벽 추가
					solution(wallCount + 1);

					originMap[i][j] = 0;		// Backtracking (벽 취소)
				}
			}
		}
	}

	/* BFS 로 map 전체의 바이러스(2)가 퍼져나가는 영역 virusCount 를 계산 */
	static void bfs(int row, int col) {
		check[row][col] = true;
		queue.add(new Coord(row, col));
		virusCount++;

		while (!queue.isEmpty()) {
			Coord current = queue.remove();

			for (int i = 0; i < 4; i++) {
				int nextRow = current.getRow() + dy[i];
				int nextCol = current.getCol() + dx[i];

				if (0 <= nextRow && nextRow < n &&
					0 <= nextCol && nextCol < m) {
					// 빈 칸(0)이 바이러스(2)로 퍼져나감
					if (virusMap[nextRow][nextCol] == 0 &&
							!check[nextRow][nextCol]) {
						check[nextRow][nextCol] = true;
						virusMap[nextRow][nextCol] = 2;
						queue.add(new Coord(nextRow, nextCol));
						virusCount++;
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
		originMap = new int[n][m];
		check = new boolean[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				originMap[i][j] = Integer.parseInt(st.nextToken());
				if (originMap[i][j] == 1)
					originWallCount++;
			}
		}

		// 추가할 벽 3개 위치 선택 후, 바이러스 영역 탐색
		solution(0);

		// 안전 영역 최대 개수 = 전체 칸 수 - (기존 벽 수 + 3) - 전체 바이러스 칸 수
		maxSafeCount = (n * m) - (originWallCount + 3) - minVirusCount;
		System.out.println(maxSafeCount);
	}
}

package 말이_되고픈_원숭이;
import java.io.*;
import java.util.*;

/*
* 말 이동 규칙
 - 가로 2칸 + 세로 1칸
 - 가로 1칸 + 세로 2칸
 => 최대 8가지 선택

* 원숭이
 - k 번만 말 규칙으로 이동 가능
 - k 번 이후로는 인접한 1칸 이동
 => 최대 4가지 선택 (상하좌우)

=> 최소한의 동작으로 [0, 0] 에서 [h, w] 끝으로 이동
=> 원숭이의 최소 동작 횟수 구하기
   (시작점 -> 도착점으로 아예 갈 수 없는 경우, -1)
*/

/*
1. 아이디어
 - 최소 횟수, 최단 거리 => BFS

 - 방문 확인 배열 2개 사용
   1) 말처럼 동작하여 방문
   2) 원숭이로 동작하여 방문

2. 자료구조
 - Queue<Point>, LinkedList<Point>: BFS
   => Point: 현재 지점, 현재 지점까지의 말처럼 동작한 횟수, 전체 동작 횟수
 - boolean[][] checkHorse: 말처럼 동작하여 방문 여부
 - boolean[][] checkMonkey: 원숭이로 동작하여 방문 여부

3. 시간 복잡도
 - BFS 1번 수행
*/

class Point_ {
	public int y, x;
	public int horseCount;		// 현재 지점까지 말처럼 동작한 횟수
	public int totalCount;		// 현재 지점까지 전체 동작 횟수 (말 + 원숭이)

	public Point_(int y, int x, int horseCount, int totalCount) {
		this.x = x;
		this.y = y;
		this.horseCount = horseCount;
		this.totalCount = totalCount;
	}
}

public class Main_Incorrect {
	static int k;				// 원숭이가 말 처럼 동작 가능한 최대 횟수
	static int h, w;			// h x w 행렬
	static int[][] map;
	static int minCount = Integer.MAX_VALUE;		// 원숭이의 최소 동작 횟수

	static Queue<Point_> queue = new LinkedList<>();
	static boolean[][] checkHorse;			// 말처럼 동작하여 방문 여부
	static boolean[][] checkMonkey;			// 원숭이로 동작하여 방문 여부
	static int[] dy = { -1, 1, 0, 0 };		// 원숭이 이동: 상하좌우
	static int[] dx = { 0, 0, -1, 1 };
	static int[] hdy = { -2, -2, -1, -1, +1, +1, +2, +2 };		// 말 이동
	static int[] hdx = { -1, +1, -2, +2, -2, +2, -1, +1 };

	static void bfs() {
		while (!queue.isEmpty()) {
			Point_ current = queue.remove();

			if (current.y == h - 1 && current.x == w - 1) {
				minCount = current.totalCount;
				return;
			}

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];		// [ny, nx] 칸으로 이동: 원숭이로 동작
				int nx = current.x + dx[i];

				if (!isValid(ny, nx))			// 다음 지점이 범위 밖인 경우
					continue;

				int horseCount = current.horseCount;
				int totalCount = current.totalCount;

				// 인접한 지점이 평지(원숭이로 이동 가능)이고, 아직 방문 안한 경우
				if (map[ny][nx] == 0 && !checkMonkey[ny][nx]) {
					checkMonkey[ny][nx] = true;
					queue.add(new Point_(ny, nx, horseCount, totalCount + 1));
				}
				else if (map[ny][nx] == 1) {	// 인접한 지점이 장애물인 경우 => 원숭이로 이동 불가능
					if (horseCount >= k)		// 말 처럼 더 동작 불가능한 경우
						continue;

					// 말 처럼 동작하여 이동한 다음 좌표들
					int[][] hPoints = horseNextPoints(current.y, current.x);
					for (int j = 0; j < 8; j++) {
						int hy = hPoints[j][0];
						int hx = hPoints[j][1];

						// 다음 지점이 평지이고, 아직 방문 안한 경우
						if (isValid(hy, hx) &&
								map[hy][hx] == 0 && !checkHorse[hy][hx]) {
							checkHorse[hy][hx] = true;
							queue.add(new Point_(
									hy, hx,
									horseCount + 1, totalCount + 1)
							);
						}
					}
				}
			}
		}
	}

	/* 좌표가 범위 내에 있는지 유효 검사 */
	static boolean isValid(int y, int x) {
		return (0 <= y && y < h && 0 <= x && x < w);
	}

	static int[][] horseNextPoints(int y, int x) {
		// [0][0], [0][1]: 말 처럼 동작해서 이동하는 첫 번째 방법의 (y, x) 좌표
		int[][] nextPoints = new int[8][2];		// 총 8개 좌표
		nextPoints[0][0] = y - 1;		nextPoints[0][1] = x - 2;
		nextPoints[1][0] = y - 2;		nextPoints[1][1] = x - 1;
		nextPoints[2][0] = y - 2;		nextPoints[2][1] = x + 1;
		nextPoints[3][0] = y - 1;		nextPoints[3][1] = x + 2;
		nextPoints[4][0] = y + 1;		nextPoints[4][1] = x - 2;
		nextPoints[5][0] = y + 2;		nextPoints[5][1] = x - 1;
		nextPoints[6][0] = y + 2;		nextPoints[6][1] = x + 1;
		nextPoints[7][0] = y + 1;		nextPoints[7][1] = x + 2;
		return nextPoints;
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
		checkHorse = new boolean[h][w];
		checkMonkey = new boolean[h][w];
		map = new int[h][w];
		for (int i = 0; i < h; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < w; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		checkMonkey[0][0] = true;
		queue.add(new Point_(0, 0, 0, 0));
		bfs();

		if (minCount != Integer.MAX_VALUE)
			System.out.println(minCount);
		else
			System.out.println(-1);
	}
}

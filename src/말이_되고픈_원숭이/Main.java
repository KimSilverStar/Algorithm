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

/* 오답 노트
 * BFS 의 Queue 에는 탐색의 진행 "상태"를 나타내는 원소를 담아야 함.
   그에 따라 방문 확인 배열 check 도 해당 모든 "상태"를 저장하도록 정의해야 함.
   => 여러 경로들이 서로의 탐색에 관여하지 않도록 방문 처리를 구분
   ex) 본 문제의 경우, 같은 위치를 방문하더라도
       원숭이로 이동하는 경우와 말 처럼 n(<= k)번 동작하여 이동하는 경우가 있음

 * 틀린 이유 (Main_Incorrect.java 코드)
  - boolean[][] checkHorse, boolean[][] checkMonkey
    2개의 2차원 boolean 배열 사용
  - 단순히 원숭이로 이동하여 방문한 경우, 말 처럼 동작하여 방문한 경우만을 처리함
  - 문제가 말 처럼 동작하는 횟수 k 가 1인 경우라면 정답이지만, k 가 1 보다 더 큰 횟수 가능
    [해당 유형의 문제: 백준 2206 - 벽 부수고 이동하기]

 * 수정한 방법
  - 탐색 상태(경로)의 구분: 해당 위치 좌표 + 말 처럼 동작한 횟수
    => [y, x] 좌표를 말 처럼 k 번 이동하여 방문
    => boolean[][][] check 선언, check[y][x][k]
*/

class Point {
	public int y, x;
	public int horseCount;		// 현재 지점까지 말처럼 동작한 횟수
	public int totalCount;		// 현재 지점까지 전체 동작 횟수 (말 + 원숭이)

	public Point(int y, int x, int horseCount, int totalCount) {
		this.x = x;
		this.y = y;
		this.horseCount = horseCount;
		this.totalCount = totalCount;
	}
}

public class Main {
	static int k;				// 원숭이가 말 처럼 동작 가능한 최대 횟수
	static int h, w;			// h x w 행렬
	static int[][] map;
	static int minCount = Integer.MAX_VALUE;		// 원숭이의 최소 동작 횟수

	static Queue<Point> queue = new LinkedList<>();
	// check[y][x][k]: [y, x] 위치를 말 처럼 k 번 동작하여 방문 여부 확인
	static boolean[][][] check;
	static int[] dy = { -1, 1, 0, 0 };		// 원숭이 이동: 상하좌우
	static int[] dx = { 0, 0, -1, 1 };
	static int[] hdy = { -2, -2, -1, -1, +1, +1, +2, +2 };		// 말 이동
	static int[] hdx = { -1, +1, -2, +2, -2, +2, -1, +1 };

	static void bfs() {
		while (!queue.isEmpty()) {
			Point current = queue.remove();
			int horseCount = current.horseCount;
			int totalCount = current.totalCount;

			if (current.y == h - 1 && current.x == w - 1) {
				minCount = current.totalCount;
				return;
			}

			// 원숭이로 이동하는 경우
			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (ny < 0 || ny >= h || nx < 0 || nx >= w)		// 다음 지점이 범위 밖인 경우
					continue;

				// 인접한 지점이 평지이고, 아직 방문 안한 경우
				if (map[ny][nx] == 0 && !check[ny][nx][horseCount]) {
					check[ny][nx][horseCount] = true;
					queue.add(new Point(
							ny, nx, horseCount, totalCount + 1
					));
				}
			}

			// 말 처럼 동작하여 이동하는 경우
			if (horseCount < k) {
				for (int i = 0; i < 8; i++) {
					int hy = current.y + hdy[i];
					int hx = current.x + hdx[i];

					if (hy < 0 || hy >= h || hx < 0 || hx >= w)		// 다음 지점이 범위 밖인 경우
						continue;

					// 인접한 지점이 평지이고, 아직 방문 안한 경우
					if (map[hy][hx] == 0 && !check[hy][hx][horseCount + 1]) {
						check[hy][hx][horseCount + 1] = true;
						queue.add(new Point(
								hy, hx, horseCount + 1, totalCount + 1
						));
					}
				}
			}
		}
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
		// check[][][0] ~ [][][k] 사용: 말 처럼 동작 횟수 0번 ~ k 번까지
		check = new boolean[h][w][k + 1];
		map = new int[h][w];
		for (int i = 0; i < h; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < w; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		check[0][0][0] = true;
		queue.add(new Point(0, 0, 0, 0));
		bfs();

		if (minCount != Integer.MAX_VALUE)
			System.out.println(minCount);
		else
			System.out.println(-1);
	}
}

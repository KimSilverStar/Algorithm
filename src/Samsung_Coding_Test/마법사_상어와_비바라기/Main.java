package Samsung_Coding_Test.마법사_상어와_비바라기;
import java.io.*;
import java.util.*;

/*
- [1]번 행과 [n]번 행 연결,
  [1]번 열과 [n]번 열 연결
- 비바리기 시전, 비구름 생: (n, 1), (n, 2), (n-1, 1), (n-1, 2)
  => 하단 오른쪽 정사각형 4칸

명령
1) 각 구름 칸이 d 방향으로 s칸 이동
2) 각 구름 칸에서 비가 내림 => 바구니의 물 양 +1
3) 모든 구름 소멸
4) 2에서 물이 증가한 칸 [r][c]에 물복사버그 마법
  - 대각선 방향 1칸에 존재하는 물이 든 바구니 개수만큼 [r][c] 바구니의 물 양 증가
5) 바구니에 저장된 물 양이 2 이상인 모든 칸에 구름 생성되고, 물 양 -2
  - 구름 생성 칸은 3에서 구름이 사라진 칸이 아니어야 함 (기존 구름 칸이 아니어야 함)
  => 기존 구름 칸 제외, 물 양이 2 이상인 곳이 새로운 구름 칸이 됨
*/

/*
1. 아이디어
 - 구현, 시뮬레이션
 - 구름 칸 이동 -> 이동한 구름 칸에서 비 내리기 -> 물 복사 버그 시전 (대각선 확인) -> 새로운 구름 칸 생성

1) 각 구름 칸이 d 방향으로 s칸 이동
  - 큐에서 구름 좌표들을 꺼내서 차례로 이동
  - y < 1이 되면 y = n 처리 (맨 아래 행으로 보냄)
  - y > n이 되면, y = 1 처리 (맨 윗 행으로 보냄)
  - x < 1이 되면, x = n 처리 (맨 오른쪽 열로 보냄)
  - x > n이 되면, x = 1 처리 (맨 왼쪽 열로 보냄)

2) 각 구름 칸에서 비가 내림 => 바구니의 물 양 +1
  - 이동한 구름 좌표 바구니에 map[i][j]++

3) 모든 구름 소멸

4) 2에서 물이 증가한 칸 [r][c] (이동한 구름 칸)에 물복사버그 마법
  : 대각선 방향 1칸에 존재하는 물이 든 바구니 개수만큼 [r][c] 바구니의 물 양 증가
  - 큐에서 구름 좌표들을 차례로 꺼내서 대각선 바구니들 확인
  - 큐의 구름 좌표들에 대해 visited 방문 처리 (새로운 구름 칸 생성 전, 현재 구름 칸 좌표 체크)

5) 바구니에 저장된 물 양이 2 이상인 모든 칸에 구름 생성되고, 물 양 -2
  : 구름 생성 칸은 3에서 구름이 사라진 칸이 아니어야 함 (기존 구름 칸이 아니어야 함)
    => 기존 구름 칸 제외, 물 양이 2 이상인 곳이 새로운 구름 칸이 됨
  - !visited[기존 구름 칸 y][기존 구름 칸 x] 이고, map[i][j] >= 2 인 칸


2. 자료구조
 - Queue<Point>, LinkedList<Point>: 구름 좌표 큐
 - boolean[][] visited: 방문 처리 (구름 이동 전의 구름 좌표 체크)

 ※ 문제 핵심
  ① Queue<Point>를 이용한 구름 칸 시뮬레이션 처리
    - 구름 칸 이동, 비 내리기, 새로운 구름 칸 생성(추가)
  ② 2차원 방문 처리 배열: boolean[][] visited
*/

class Operation {
	public int d, s;		// 명령: 방향, 거리

	public Operation(int d, int s) {
		this.d = d;
		this.s = s;
	}
}

class Point {
	public int y, x;

	public Point(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static int n;					// n x n 행렬
	static int m;					// 구름에 m번 명령(방향 d, 거리 s)
	static int[][] map;
	static Operation[] operations;	// m개 명령
	static int resultSum;			// 출력, 바구니에 든 물 양의 합

	// 대각선 방향 index: [2], [4], [6], [8]
	static int[] dy = { 0, 0, -1, -1, -1, 0, 1, 1, 1 };		// 이동 8개 방향, [1] ~ [8] 사용
	static int[] dx = { 0, -1, -1, 0, 1, 1, 1, 0, -1 };
	static boolean[][] visited;
	static Queue<Point> queue = new LinkedList<>();			// 구름 좌표 큐

	static void solution() {
		// 각 명령 수행
		for (Operation op : operations) {
			visited = new boolean[n + 1][n + 1];		// 방문 배열 초기화

			// 각 구름 칸을 명령에 따라 이동 시킨 후, 비를 내림
			int qSize = queue.size();
			for (int i = 0; i < qSize; i++) {
				Point cloudPoint = queue.remove();
				int ny = cloudPoint.y;
				int nx = cloudPoint.x;

				// 1) 구름 칸 이동: op.d 방향으로 op.s 칸 만큼 이동
				for (int j = 0; j < op.s; j++) {
					ny += dy[op.d];
					nx += dx[op.d];

					if (ny < 1) ny = n;
					else if (ny > n) ny = 1;

					if (nx < 1) nx = n;
					else if (nx > n) nx = 1;
				}

				queue.add(new Point(ny, nx));		// 큐에 이동한 구름 칸 추가 (구름 칸 이동 반영)

				// 2) 이동한 구름 칸에서 비 내리기 => 바구니의 물 양 +1
				map[ny][nx]++;
			}

			// 3) 모든 구름 소멸: while 문 실행하면, queue empty
			// 4) 물 복사 버그 시전
			while (!queue.isEmpty()) {
				Point cloudPoint = queue.remove();
				int waterBasketCount = 0;		// 구름 칸 기준, 대각선 방향의 물이 든 바구니 개수

				for (int i = 2; i <= 8; i += 2) {		// 대각선 4개 방향 확인
					int ny = cloudPoint.y + dy[i];
					int nx = cloudPoint.x + dx[i];

					if (isValid(ny, nx) && map[ny][nx] > 0) {
						waterBasketCount++;
					}
				}

				visited[cloudPoint.y][cloudPoint.x] = true;		// 구름 위치 표시
				map[cloudPoint.y][cloudPoint.x] += waterBasketCount;
			}

			// 5) 새로운 구름 칸 생성
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					// 현재 구름 칸이 아니고, 물이 2 이상 든 바구니 => 새로운 구름 칸
					if (!visited[i][j] && map[i][j] >= 2) {
						map[i][j] -= 2;
						queue.add(new Point(i, j));
					}
				}
			}
		}
	}

	static boolean isValid(int ny, int nx) {
		return (1 <= ny && ny <= n) && (1 <= nx && nx <= n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new int[n + 1][n + 1];			// [1][1] ~ [n][n] 사용
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		operations = new Operation[m];
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());

			operations[i] = new Operation(d, s);
		}

		// 큐에 초기 4개 구름 칸 추가
		queue.add(new Point(n, 1));
		queue.add(new Point(n, 2));
		queue.add(new Point(n - 1, 1));
		queue.add(new Point(n - 1, 2));

		solution();

		// 물 양 카운트
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (map[i][j] > 0)
					resultSum += map[i][j];
			}
		}
		System.out.println(resultSum);
	}
}

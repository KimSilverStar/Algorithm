package Samsung_Coding_Test.구슬_탈출_2;
import java.io.*;
import java.util.*;

/*
- 빨간 구슬, 파란 구슬을 1개씩 넣음
  => 목표: 파란 구슬이 구멍에 들어가지 않고, 빨간 구슬을 구멍으로 빼내는 것
- 가장 바깥 행, 열은 막혀있음
- 보드에 구멍 1개 존재
- 동작: 왼쪽 / 오른쪽 / 위쪽 / 아래쪽으로 기울이기
  => 최소한의 동작으로 빨간 구슬만 구멍으로 빼내도록
  => 동작 10번 이하로 불가능한 경우, -1 출력
*/

/*
1. 아이디어
 - 시뮬레이션 + BFS
   ※ 최단 거리(최소 동작 횟수) => BFS
 - 탐색 노드 상태: (빨간 구슬 위치), (파란 구슬 위치), 현재까지 기울이기 동작 횟수
   ※ 문제 핵심 ①) BFS 탐색 노드 상태 클래스 정의
 - 방문 확인 배열: visited[빨간 구슬 y][빨간 구슬 x][파란 구슬 y][파란 구슬 x]
   ※ 문제 핵심 ②) 4차원 방문 배열

 1) 현재까지 동작 횟수가 10번 "이상"인 경우, 탐색 종료 (실패)
   ※ 오답 이유) 현재까지 동작 횟수 "current.count > 10"인 경우, 실패 및 탐색 종료시킴
    - 만약 current.count = 10 까지 허용할 경우,
      아래 코드 로직을 실행하면 구슬을 한번 더 굴릴 수 있게 되어 동작 횟수 11번 까지 가능하게 됨
 2) 상하좌우 방향에 대해, 2개의 구슬을 각각 이동
   - 각 기울이기 방향으로 기울였을 때, 2개의 구슬을 벽 '#'을 만나기 전까지 각각 이동
   - 이동 중, 구멍에 빠지는지 체크
 3) 각 구슬 이동시킨 후, 각 구슬이 구멍에 빠져있는지 확인
   - 파란 구슬이 구멍에 빠진 경우, 다른 경로(방향) 확인
   - 빨간 구슬만 구멍에 빠진 경우, 성공 (출력 및 종료)
 4) 2개의 구슬이 서로 위치가 겹친 경우, 위치 조정
   - 구슬 이동 전 방향을 서로 비교하여 1개 구슬을 1칸 뒤로 이동
 5) 2개 구슬의 다음 이동 위치에 대해 이전에 방문 안한 경우, 탐색 확장


2. 자료구조
 - char[][] map: n x m 행렬
 - Queue<Node>, LinkedList<Node>: BFS 수행
   => Node: (빨간 구슬 위치), (파란 구슬 위치), 현재까지 기울이기 동작 횟수
 - boolean[][][][] visited
   => visited[빨간 구슬 y][빨간 구슬 x][파란 구슬 y][파란 구슬 x]
*/

class Node {
	public int ry, rx;			// 빨간 구슬 위치
	public int by, bx;			// 파란 구슬 위치
	public int count;			// 현재까지 동작 횟수

	public Node(int ry, int rx, int by, int bx, int count) {
		this.ry = ry;
		this.rx = rx;
		this.by = by;
		this.bx = bx;
		this.count = count;
	}
}

public class Main {
	static int n, m;				// 세로 크기 n, 가로 크기 m
	static char[][] map;			// n x m 행렬
	static int minCount;			// 출력, 최소 동작 횟수

	static int startRY, startRX;	// 초기 빨간 구슬 위치
	static int startBY, startBX;	// 초기 파란 구슬 위치
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };
	// 방문 확인: 빨간 구슬 위치 + 파란 구슬 위치 => [ry][rx][by][bx]
	static boolean[][][][] visited;
	static Queue<Node> queue = new LinkedList<>();

	static void bfs() {
		while (!queue.isEmpty()) {
			Node current = queue.remove();

			// 현재까지 동작 횟수가 10번 이상인 경우 => 실패
			// ※ 아래에서 기울이기 동작을 1번 더 수행하면 10번을 초과하므로, 실패
			if (current.count >= 10) {
				minCount = -1;
				return;
			}

			// 상하좌우 => 벽 '#' 닿을 때까지 빨간, 파란 구슬 이동
			for (int i = 0; i < 4; i++) {
				int nextRY = current.ry, nextRX = current.rx;
				int nextBY = current.by, nextBX = current.bx;

				boolean isRedInHole = false;
				boolean isBlueInHole = false;

				// 빨간 구슬 이동
				while (map[nextRY + dy[i]][nextRX + dx[i]] != '#') {
					nextRY += dy[i];
					nextRX += dx[i];

					// 이동 중, 구멍을 만난 경우
					if (map[nextRY][nextRX] == 'O') {
						isRedInHole = true;
						break;
					}
				}

				// 파란 구슬 이동
				while (map[nextBY + dy[i]][nextBX + dx[i]] != '#') {
					nextBY += dy[i];
					nextBX += dx[i];

					// 이동 중, 구멍을 만난 경우
					if (map[nextBY][nextBX] == 'O') {
						isBlueInHole = true;
						break;
					}
				}

				// 2개 구슬 이동한 후, 구슬이 구멍에 빠졌는지 확인
				if (isBlueInHole) {		// 파란 구슬이 구멍에 위치 => 다른 경로(방향) 마저 확인
//					count = -1;
					continue;
				}
				if (isRedInHole && !isBlueInHole) {		// 빨간 구슬만 구멍에 위치 => 성공
					minCount = current.count + 1;
					return;
				}

				// 2개 구슬이 서로 구멍에 위치하지 않음 (더 진행)
				// 2개 구슬이 서로 위치가 겹침 => 겹치지 않게 조정 (기울이기 전 위치 비교하여 조정)
				if (nextRY == nextBY && nextRX == nextBX) {
					if (i == 0) {
						// 위쪽으로 기울이기 전, 더 아래에 있던 구슬이 아래로 가도록 조정
						if (current.ry > current.by) nextRY++;
						else nextBY++;
					}
					else if (i == 1) {
						// 아래쪽으로 기울이기 전, 더 위에 있던 구슬이 위로 가도록 조정
						if (current.ry < current.by) nextRY--;
						else nextBY--;
					}
					else if (i == 2) {
						// 왼쪽으로 기울이기 전, 더 오른쪽에 있던 구슬이 오른쪽으로 가도록 조정
						if (current.rx > current.bx) nextRX++;
						else nextBX++;
					}
					else if (i == 3) {
						// 오른쪽으로 기울이기 전, 더 왼쪽에 있던 구슬이 왼쪽으로 가도록 조정
						if (current.rx < current.bx) nextRX--;
						else nextBX--;
					}
				}

				// 2개 구슬의 다음 이동 위치를 이전에 방문 안한 경우, 탐색 확장
				if (!visited[nextRY][nextRX][nextBY][nextBX]) {
					visited[nextRY][nextRX][nextBY][nextBX] = true;
					queue.add(new Node(nextRY, nextRX, nextBY, nextBX, current.count + 1));
				}
			}
		}

		minCount = -1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new char[n][m];
		visited = new boolean[n][m][n][m];
		for (int i = 0; i < n; i++) {
			String input = br.readLine();

			for (int j = 0; j < m; j++) {
				map[i][j] = input.charAt(j);

				if (map[i][j] == 'R') {
					startRY = i;
					startRX = j;
				}
				else if (map[i][j] == 'B') {
					startBY = i;
					startBX = j;
				}
			}
		}

		// 초기 빨간 구슬, 파란 구슬 위치에서 BFS 탐색 시작
		visited[startRY][startRX][startBY][startBX] = true;
		queue.add(new Node(startRY, startRX, startBY, startBX, 0));
		bfs();

		System.out.println(minCount);
	}
}

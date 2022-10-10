package Samsung_Coding_Test.주사위_굴리기_2;
import java.io.*;
import java.util.*;

/*
- n x m 맵에 주사위 1개
- 최초
  > [1][1]에 주사위가 윗 면이 1, 동쪽 3 방향으로 놓임
  > 처음 주사위 이동 방향: 동쪽

* 주사위 이동 로직
1) 주사위 1칸 이동
 - 이동 방향에 칸이 있는 경우, 해당 이동 방향으로 1칸 굴러감
 - 이동 방향에 칸이 없는 경우, 반대 방향으로 1칸 굴러감
2) 주사위 도착 칸 [y][x]에 대한 획득 점수 계산
 - 주사위 도착 칸의 값 = map[y][x]
 -
3) 다음 이동 방향 결정
 - 주사위 아랫 면의 값 A, 주사위가 위치한 칸의 값 B (map[y][x])
 - A > B인 경우, 이동 방향을 90도 시계 방향 회전
 - A < B인 경우, 이동 방향을 90도 반시계 방향 회전
 - A = B인 경우, 이동 방향 그대로 유지
*/

/*
1. 아이디어
 > 구현, 시뮬레이션: 주사위 1칸 이동, 주사위 이동 방향 결정
 > BFS: 주사위 1칸 이동 후 획득 점수 계산

1) 주사위 1칸 이동
 - 이동 방향에 칸이 있는 경우, 해당 이동 방향으로 1칸 굴러감
 - 이동 방향에 칸이 없는 경우, 방향을 바꾸어 반대 방향으로 1칸 굴러감
   ※ diceDir 반대 방향 값으로 갱신 !!
 - 주사위 굴리기 메소드: dice[], diceY, diceX 갱신
   => 상, 하, 좌, 우로 각각 굴리는 메소드

2) 주사위 도착 칸 [diceY][diceX]에 대한 획득 점수 계산
 - 주사위 도착 칸의 값 boardValue = map[diceY][diceX]
 - BFS 수행: int bfs()
   > [diceY][diceX]의 인접 칸 값이 boardValue와 같은 경우, 탐색 확장

3) 다음 이동 방향 결정
 - 주사위 아랫 면의 값 A, 주사위가 위치한 칸의 값 B
 - A = dice[5], B = map[diceY][diceX]
 - A > B인 경우, 이동 방향을 90도 시계 방향 회전
 - A < B인 경우, 이동 방향을 90도 반시계 방향 회전
 - A == B인 경우, 이동 방향 그대로 유지


2. 자료구조
 - int[] dice: 길이 6, 주사위 전개도
 - diceY, diceX, diceDir: 주사위 위치, 이동 방향
 - boolean[][] visited: BFS 방문 처리
 - Queue<Node>, LinkedList<Node>: BFS 수행
   ※ Node: 현재 탐색 위치 (y, x)
*/

class Node {
	public int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static int n, m;			// n x m 맵
	static int k;				// k번 이동
	static int[][] map;
	static int totalScore;		// 출력, 획득 점수

	static int[] dice = new int[6];		// 주사위 전개도 배열
	static int diceY, diceX;			// 주사위 위치
	static int diceDir;					// 주사위 이동 방향

	static boolean[][] visited;
	static Queue<Node> queue = new LinkedList<>();
	static int[] dy = { -1, 0, 1, 0 };	// 상우하좌 (북동남서)
	static int[] dx = { 0, 1, 0, -1 };

	static void solution() {
		for (int i = 0; i < k; i++) {
			// 1) 주사위 1칸 이동
			rollDice();

			// 2) 주사위 도착 칸 [diceY][diceX]에 대한 획득 점수 계산
			visited = new boolean[n + 1][m + 1];		// Init

			visited[diceY][diceX] = true;
			queue.add(new Node(diceY, diceX));
			totalScore += bfs();

			// 3) 다음 이동 방향 결정
			changeDiceDir();
		}
	}

	static void rollDice() {
		// 현재 방향으로 이동했을 시, 다음 칸
		int ny = diceY + dy[diceDir];
		int nx = diceX + dx[diceDir];

		// 이동 방향에 칸이 있는 경우, 해당 이동 방향으로 1칸 굴러감
		if (isValid(ny, nx)) {
			if (diceDir == 0) rollDiceUp();
			else if (diceDir == 1) rollDiceRight();
			else if (diceDir == 2) rollDiceDown();
			else if (diceDir == 3) rollDiceLeft();
		}
		// 이동 방향에 칸이 없는 경우, 방향을 바꾸어 반대 방향으로 1칸 굴러감
		else {
			if (diceDir == 0) {
				diceDir = 2;
				rollDiceDown();
			}
			else if (diceDir == 1) {
				diceDir = 3;
				rollDiceLeft();
			}
			else if (diceDir == 2) {
				diceDir = 0;
				rollDiceUp();
			}
			else if (diceDir == 3) {
				diceDir = 1;
				rollDiceRight();
			}
		}
	}

	static void rollDiceLeft() {
		int[] tempDice = new int[6];
		for (int i = 0; i < 6; i++) {
			tempDice[i] = dice[i];
		}

		dice[0] = tempDice[2];
		dice[2] = tempDice[5];
		dice[3] = tempDice[0];
		dice[5] = tempDice[3];

		diceX--;
	}

	static void rollDiceRight() {
		int[] tempDice = new int[6];
		for (int i = 0; i < 6; i++) {
			tempDice[i] = dice[i];
		}

		dice[0] = tempDice[3];
		dice[2] = tempDice[0];
		dice[3] = tempDice[5];
		dice[5] = tempDice[2];

		diceX++;
	}

	static void rollDiceUp() {
		int[] tempDice = new int[6];
		for (int i = 0; i < 6; i++) {
			tempDice[i] = dice[i];
		}

		dice[0] = tempDice[4];
		dice[1] = tempDice[0];
		dice[4] = tempDice[5];
		dice[5] = tempDice[1];

		diceY--;
	}

	static void rollDiceDown() {
		int[] tempDice = new int[6];
		for (int i = 0; i < 6; i++) {
			tempDice[i] = dice[i];
		}

		dice[0] = tempDice[1];
		dice[1] = tempDice[5];
		dice[4] = tempDice[0];
		dice[5] = tempDice[4];

		diceY++;
	}

	/* 주사위 도착 위치 dicePoint를 시작 지점으로 BFS 탐색
	 * - 주사위 도착 칸에 대한 획득 점수 계산 */
	static int bfs() {
		int boardValue = map[diceY][diceX];
		int cnt = 1;

		while (!queue.isEmpty()) {
			Node current = queue.remove();

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (!isValid(ny, nx))
					continue;

				// 인접 칸의 값 == boardValue인 경우, 탐색 확장
				if (!visited[ny][nx] && map[ny][nx] == boardValue) {
					visited[ny][nx] = true;
					queue.add(new Node(ny, nx));

					cnt++;
				}
			}
		}

		return boardValue * cnt;
	}

	/* 주사위 이동 방향 결정 */
	static void changeDiceDir() {
		int a = dice[5];				// 주사위 아랫 면의 값
		int b = map[diceY][diceX];		// 주사위가 위치한 칸의 값

		// dy, dx 순서: 상우하좌
		if (a > b) {		// A > B인 경우, 이동 방향을 90도 시계 방향 회전
			diceDir = (diceDir + 1) % 4;
		}
		else if (a < b) {	// A < B인 경우, 이동 방향을 90도 반시계 방향 회전
			diceDir = (diceDir + 3) % 4;
		}
	}

	static boolean isValid(int y, int x) {
		return (1 <= y && y <= n) && (1 <= x && x <= m);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		map = new int[n + 1][m + 1];		// [1][1] ~ [n][m] 사용
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 1; j <= m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 최초 주사위 전개도
		for (int i = 0; i < 6; i++) {
			dice[i] = i + 1;
		}

		// 최초 주사위 - 위치: [1][1], 이동 방향: 동쪽(오른쪽)
		diceY = 1;
		diceX = 1;
		diceDir = 1;

		solution();

		System.out.println(totalScore);
	}
}

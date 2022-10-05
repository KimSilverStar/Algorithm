package Samsung_Coding_Test.청소년_상어;
import java.io.*;
import java.util.*;

/*
- 4 x 4 격자 맵
- 물고기: 번호(1 ~ 16), 방향(1 ~ 8, 상하좌우 4개 + 대각선 4개) 가짐

1) 물고기 이동: 번호가 작은 물고기부터 순서대로 이동
 - 이동 가능: 빈 칸, 다른 물고기가 있는 칸 (서로의 위치를 바꿈)
 - 이동 불가능: 상어가 있는 칸, 격자를 벗어난 칸
 => 이동 가능한 칸을 향할 때까지, 방향을 45도 반시계 회전
    (이동 가능한 칸이 없으면, 이동 X)

- 물고기 이동이 모두 끝나면, 상어가 이동
- 최초: [0][0]에 있는 물고기를 먹고, 해당 칸에 상어가 위치

2) 상어 이동
 - 이동 가능: 물고기가 있는 칸, 해당 방향으로 한 번에 여러 칸 이동 가능
 - 이동 불가능: 물고기가 없는 칸
 - 상어가 물고기 칸으로 이동한 경우
   > 해당 칸에 있는 물고기를 먹고
   > 먹은 물고기의 방향을 가짐
 - 상어가 이동 가능한 칸이 없으면, 종료

- 출력: 상어가 먹을 수 있는 물고기 번호 합의 max 값
*/

/*
1. 아이디어
 > 구현, 시뮬레이션
 > 백트래킹, 완전 탐색
   : 각 분기에서 상어의 방향 일직선 상으로 이동 가능한 칸 개수 = 최대 3개


2. 자료구조
 - int[][] map
 - Fish[] fishes: 1 ~ 16번 물고기 정보
   ※ Fish: 물고기 위치 (y, x), 번호 idx, 방향 dir, 먹힘 여부 alive


3. 시간 복잡도
 - 각 분기에서 상어가 이동할 위치 선택: 최대 3개 경우의 수
 - 최대 3^15 = 14,348,907
   => 완전 탐색 가능
*/

class Fish {
	public int y, x;			// 물고기 위치
	public int idx, dir;		// 물고기 번호: 1 ~ 16, 방향: 1 ~ 8
	public boolean alive;		// 물고기가 먹혔는지 여부

	public Fish(int y, int x, int idx, int dir, boolean alive) {
		this.y = y;
		this.x = x;
		this.idx = idx;
		this.dir = dir;
		this.alive = alive;
	}
}

public class Main {
	static int[][] map;
	static Fish[] fishes;
	static int maxEatSum;				// 출력

	// 반시계 방향 순서, index [1] ~ [8] 사용: { 제자리, ↑, ↖, ←, ↙, ↓, ↘, →, ↗ }
	static int[] dy = { 0, -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dx = { 0, 0, -1, -1, -1, 0, 1, 1, 1 };
	static final int EMPTY = 0, SHARK = -1;

	static void backtrack(int sharkY, int sharkX, int shakrDir, int eatSum) {
		int[][] tempMap = new int[4][4];		// Copy - map을 되돌려놓기 위해 백업
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				tempMap[i][j] = map[i][j];
			}
		}

		Fish[] tempFishes = new Fish[17];		// Copy - fishes를 되돌려놓기 위해 백업
		for (int i = 1; i <= 16; i++) {
			Fish fish = fishes[i];
			tempFishes[i] = new Fish(fish.y, fish.x, fish.idx, fish.dir, fish.alive);
		}

		// 물고기 번호 순으로 차례로 이동
		moveAllFishes();

		// 상어의 방향 일직선 상에서, 물고기 칸을 선택
		for (int i = 1; i <= 3; i++) {				// 최대 3개 경우의 수
			int ny = sharkY + (dy[shakrDir] * i);
			int nx = sharkX + (dx[shakrDir] * i);

			if (!isValid(ny, nx))
				continue;

			if (1 <= map[ny][nx] && map[ny][nx] <= 16) {	// 이동 선택할 다음 칸이 물고기 칸인 경우
				int fishIdx = map[ny][nx];					// 잡아먹힐 물고기 번호
				int nDir = fishes[fishIdx].dir;				// 상어의 다음 방향 = 먹은 물고기의 방향

				// 상어가 물고기 칸으로 이동해서 물고기 먹음
				map[sharkY][sharkX] = EMPTY;		// 기존에 상어가 있던 칸
				map[ny][nx] = SHARK;
				fishes[fishIdx].alive = false;
				maxEatSum = Math.max(maxEatSum, eatSum + fishIdx);

				backtrack(ny, nx, nDir,eatSum + fishIdx);

				// backtrack
				map[ny][nx] = fishIdx;
				map[sharkY][sharkX] = SHARK;
				fishes[fishIdx].alive = true;
			}
		}

		map = tempMap;
		fishes = tempFishes;
	}

	static void moveAllFishes() {
		for (int i = 1; i <= 16; i++) {
			// 아직 상어에게 잡아먹히지 않은 남은 물고기인 경우
			if (fishes[i].alive) {
				moveFish(i);
			}
		}
	}

	static void moveFish(int fishIdx) {
		int y = fishes[fishIdx].y;
		int x = fishes[fishIdx].x;
		int dir = fishes[fishIdx].dir;

		// 현재 방향부터 반시계 방향으로
		for (int i = 0; i <= 8; i++) {
			int nDir = (dir + i) % 9;			// i = 0이면, nDir = 현재 방향 dir과 동일
			if (nDir == 0)						// 본인 제자리 칸은 제외
				continue;

			fishes[fishIdx].dir = nDir;			// 물고기 방향 회전 반영

			int ny = y + dy[nDir];
			int nx = x + dx[nDir];

			if (!isValid(ny, nx))
				continue;

			if (map[ny][nx] == EMPTY) {			// 인접 칸이 빈 칸인 경우
				map[y][x] = EMPTY;				// 기존 칸

				map[ny][nx] = fishIdx;			// 새로 이동하는 칸
				fishes[fishIdx].y = ny;
				fishes[fishIdx].x = nx;

				break;
			}
			else if (1 <= map[ny][nx] && map[ny][nx] <= 16) {	// 인접 칸이 물고기 칸인 경우
				// 물고기 칸끼리 서로 위치 swap
				int nFishIdx = map[ny][nx];

				map[ny][nx] = fishIdx;
				fishes[fishIdx].y = ny;
				fishes[fishIdx].x = nx;

				map[y][x] = nFishIdx;
				fishes[nFishIdx].y = y;
				fishes[nFishIdx].x = x;

				break;
			}
		}
	}

	static boolean isValid(int y, int x) {
		return (0 <= y && y < 4) && (0 <= x && x < 4);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		map = new int[4][4];
		fishes = new Fish[17];			// [1] ~ [16] 사용
		for (int i = 0; i < 4; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < 4; j++) {
				int idx = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());

				map[i][j] = idx;
				fishes[idx] = new Fish(i, j, idx, dir, true);
			}
		}

		// 최초: 상어가 [0][0] 위치의 물고기를 먹고, 먹은 물고기 방향을 가짐
		int fishIdx = map[0][0];
		int sharkDir = fishes[fishIdx].dir;		// 상어 방향 = 먹은 물고기 방향

		map[0][0] = SHARK;
		fishes[fishIdx].alive = false;
		fishes[fishIdx].y = -1;
		fishes[fishIdx].x = -1;
		fishes[fishIdx].dir = -1;
		maxEatSum += fishIdx;

		backtrack(0, 0, sharkDir, maxEatSum);		// Init Call

		System.out.println(maxEatSum);
	}
}

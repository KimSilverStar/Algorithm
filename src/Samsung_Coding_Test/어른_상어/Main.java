package Samsung_Coding_Test.어른_상어;
import java.io.*;
import java.util.*;

/*
- n x n 맵, 상어 m 마리(번호 1 ~ m)
- 1번 상어: 가장 강력해서 모든 상어를 쫓아낼 수 있음

- 초기: 모든 상어들이 본인 위치에 자신의 냄새를 뿌림
- 이후, 1초마다 모든 상어가 동시에 인접 칸으로 이동하고, 이동한 칸에 자신의 냄새를 뿌림
- 냄새: 상어가 k번 이동하고 나면 사라짐

- 상어 이동 방향 결정
  > 인접 칸 중, 냄새가 없는 방향
  > 인접 칸에 모두 냄새가 존재하는 경우, 자신의 냄새가 있는 칸의 방향
    => 각 상어의 우선순위를 따름

- 모든 상어가 이동한 후, 1칸에 여러 마리의 상어가 존재하는 경우,
  가장 작은 번호를 가진 상어만 남음
*/

/*
1. 아이디어
 > 구현, 시뮬레이션

0) 초기
 - 모든 상어들이 본인 시작 위치에서 자신의 냄새를 뿌림

다음을 상어가 1마리만 남을 때까지 반복
- 남은 1마리 상어 = 1번 상어
  => 가장 강한 상어

1) 각 상어 이동
 - 인접 칸 중, 냄새가 없는 칸이 존재하는 경우
   > 해당 냄새가 없는 칸으로 이동
   > 냄새가 없는 칸이 여러 개일 경우, 상어의 방향 우선순위를 따름
 - 인접 칸에 모두 냄새가 있는 경우
   > 자신의 냄새가 있는 칸으로 이동
   > 자신의 냄새 칸이 여러 개일 경우, 상어의 방향 우선순위를 따름

2) 같은 칸에 있는 상어들은 1마리만 남기기

3) 이전 냄새 감소 처리
 - 2중 for문으로 smellMap[][] 확인
 - smellMap[i][j].smellCnt > 0인 경우, smellMap[i][j].smellCnt - 1 처리
 - 감소한 smellMap[i][j].smellCnt == 0인 경우, smellMap[i][j].sharkIdx = 0 처리

4) 새로 이동한 위치에 냄새 뿌리기


2. 자료구조
 - int[][][] preferDirs: 각 상어의 보고있는 방향 별, 이동 방향 우선순위
   ex) preferDirs[상어 번호][현재 보고있는 방향][이동 방향 우선순위: 1 ~ 4]
 - Smell[][] smellMap: 각 위치 별, 각 상어가 남긴 냄새 양 표시
   > Smell: 상어 번호 sharkIdx, 상어가 남긴 냄새 양 smellCnt
 - Shark[] sharks: 각 상어의 위치 (y, x), 방향 dir
*/

class Smell {
	public int sharkIdx;		// 냄새를 남긴 상어의 번호
	public int smellCnt;		// 남은 냄새 양

	public Smell(int sharkIdx, int smellCnt) {
		this.sharkIdx = sharkIdx;
		this.smellCnt = smellCnt;
	}
}

class Shark {
	public int y, x;
	public int dir;

	public Shark(int y, int x, int dir) {
		this.y = y;
		this.x = x;
		this.dir = dir;
	}
}

public class Main {
	static int n;				// n x n 맵
	static int m;				// 1번 ~ m번 상어
	static int k;				// 상어가 k번 이동하면, 냄새 사라짐
	static int time;			// 출력, 1번 상어만 남는 데 걸리는 시간
	static boolean finished;	// 성공 및 종료 여부

	static Smell[][] smellMap;
	static int[][][] preferDirs;	// 각 상어의 보고있는 방향 별, 이동 방향 우선순위
	static Shark[] sharks;			// 각 상어의 위치, 방향
	static int numOfShark;			// 남은 상어 수

	// [1] ~ [4] 사용: 상하좌우
	static int[] dy = { 0, -1, 1, 0, 0 };
	static int[] dx = { 0, 0, 0, -1, 1 };

	static void solution() {
		// 초기: 모든 상어들이 본인 시작 위치에서 자신의 냄새를 뿌림
		for (int sharkIdx = 1; sharkIdx <= m; sharkIdx++) {
			spreadSmell(sharkIdx);
		}

		// 상어 이동, 냄새 처리 반복
		while (time < 1000) {
			// 1) 각 상어 이동
			for (int sharkIdx = 1; sharkIdx <= m; sharkIdx++) {
				// 쫓겨나지 않고 남은 상어인 경우, 상어 이동
				if (sharks[sharkIdx] != null) {
					moveShark(sharkIdx);
				}
			}

			// 같은 칸에 위치한 상어들은 1마리만 남기기
			removeDuplicateSharks();

			// 2) 이전 냄새 감소 처리
			decreaseSmell();

			// 3) 새로 이동한 위치에 냄새 뿌리기
			for (int sharkIdx = 1; sharkIdx <= m; sharkIdx++) {
				// 쫓겨나지 않고 남은 상어인 경우, 새로 이동한 자리에 냄새 뿌림
				if (sharks[sharkIdx] != null) {
					spreadSmell(sharkIdx);
				}
			}

			time++;

			if (numOfShark == 1) {
				finished = true;
				break;
			}
		}
	}

	/* Shark[] sharks에 상어 이동 위치, 방향 저장 */
	static void moveShark(int sharkIdx) {
		Shark s = sharks[sharkIdx];

		// 인접 칸 확인 - 우선순위에 따라 확인
		for (int i = 1; i <= 4; i++) {
			int preferDir = preferDirs[sharkIdx][s.dir][i];

			int ny = s.y + dy[preferDir];
			int nx = s.x + dx[preferDir];

			if (!isValid(ny, nx))
				continue;

			// 인접 방향에 냄새가 없는 경우 => 해당 방향으로 이동
			if (smellMap[ny][nx].smellCnt == 0) {
				sharks[sharkIdx].y = ny;
				sharks[sharkIdx].x = nx;
				sharks[sharkIdx].dir = preferDir;

				return;
			}
		}

		// 인접 방향에 모두 냄새가 있는 경우
		// 자신의 냄새가 있는 칸들 중, 방향 우선순위에 따라 이동
		for (int i = 1; i <= 4; i++) {
			int preferDir = preferDirs[sharkIdx][s.dir][i];

			int ny = s.y + dy[preferDir];
			int nx = s.x + dx[preferDir];

			if (!isValid(ny, nx))
				continue;

			// 우선순위에 따른 인접 방향에 자신의 냄새가 있는 경우 => 해당 방향으로 이동
			if (smellMap[ny][nx].sharkIdx == sharkIdx) {
				sharks[sharkIdx].y = ny;
				sharks[sharkIdx].x = nx;
				sharks[sharkIdx].dir = preferDir;

				break;
			}
		}
	}

	static void removeDuplicateSharks() {
		// 같은 칸에 위치한 상어들은 1마리만 남기기
		int[][] sharkMap = new int[n + 1][n + 1];

		// 가장 강한 1번 상어부터 확인
		for (int sharkIdx = 1; sharkIdx <= m; sharkIdx++) {
			Shark s = sharks[sharkIdx];
			if (s == null)		// 이미 쫓겨난 상어
				continue;

			if (sharkMap[s.y][s.x] == 0) {		// 빈 칸인 경우
				sharkMap[s.y][s.x] = sharkIdx;
			}
			else {		// 이미 sharkIdx번 상어보다 강한 상어가 존재하는 경우
				sharks[sharkIdx] = null;		// sharkIdx번 상어 내보냄
				numOfShark--;
			}
		}
	}

	static void decreaseSmell() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (smellMap[i][j].smellCnt > 0) {
					smellMap[i][j].smellCnt--;
					smellMap[i][j].sharkIdx = (smellMap[i][j].smellCnt > 0) ?
							smellMap[i][j].sharkIdx : 0;
				}
			}
		}
	}

	static void spreadSmell(int sharkIdx) {
		Shark s = sharks[sharkIdx];
		smellMap[s.y][s.x].sharkIdx = sharkIdx;
		smellMap[s.y][s.x].smellCnt = k;
	}

	static boolean isValid(int y, int x) {
		return (1 <= y && y <= n) && (1 <= x && x <= n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		numOfShark = m;

		smellMap = new Smell[n + 1][n + 1];
		sharks = new Shark[m + 1];		// [1] ~ [m] 사용
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 1; j <= n; j++) {
				int sharkIdx = Integer.parseInt(st.nextToken());
				smellMap[i][j] = new Smell(sharkIdx, 0);

				if (sharkIdx >= 1) {
					sharks[sharkIdx] = new Shark(i, j, 0);		// dir: 일단 0 초기화
				}
			}
		}

		// 각 상어의 초기 방향
		st = new StringTokenizer(br.readLine());
		for (int sharkIdx = 1; sharkIdx <= m; sharkIdx++) {
			sharks[sharkIdx].dir = Integer.parseInt(st.nextToken());
		}

		// 각 상어의 보고있는 방향 별, 이동 방향 우선순위: [1][1][1] ~ [m][4][4] 사용
		preferDirs = new int[m + 1][5][5];
		for (int sharkIdx = 1; sharkIdx <= m; sharkIdx++) {
			for (int watchDir = 1; watchDir <= 4; watchDir++) {
				st = new StringTokenizer(br.readLine());

				for (int preferRank = 1; preferRank <= 4; preferRank++) {
					preferDirs[sharkIdx][watchDir][preferRank] = Integer.parseInt(st.nextToken());
				}
			}
		}

		solution();

		if (finished) {
			System.out.println(time);
		}
		else {
			System.out.println(-1);
		}
	}
}

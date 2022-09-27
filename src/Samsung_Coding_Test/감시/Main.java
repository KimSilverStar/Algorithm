package Samsung_Coding_Test.감시;
import java.io.*;
import java.util.*;

/*
- n x m 격자 맵 사무실, 최대 8개의 CCTV가 설치됨
- 0: 빈 칸, 1 ~ 5: CCTV 번호, 6: 벽

- CCTV: 5개 종류(감시 방법)
  ① 1개 방향 감시
  ② 서로 반대 방향으로 2개 방향 감시 (양 옆 or 위 아래)
  ③ 서로 직각 방향으로 2개 방향 감시
  ④ 3개 방향 감시
  ⑤ 전체 4개 방향 감시
- CCTV 90도 방향으로 회전 가능

- 출력: 사각 지대의 최소 크기
*/

/*
1. 아이디어
 > 조합(백트래킹 + 브루트포스), 구현, 시뮬레이션

모든 조합에 대해 다음을 반복
- k개 CCTV의 방향을 모두 정하고, 감시 영역을 표시
- 감시하지 못하는 사각지대 칸 수 count


2. 자료구조
 - List<CCTV> inputCCTVList: 입력 CCTV들의 정보
 - List<CCTV> cctvList: 조합을 구성한 CCTV들의 정보

 ※ CCTV
  - CCTV 위치 (y, x)
  - CCTV가 바라보는 방향 dir (0 ~ 3, 상우하좌 순서)
  - CCTV 번호 idx (1 ~ 5)


3. 시긴 복잡도
 - O(4^k x n x m)	(k: CCTV 개수)

 - 1개 CCTV에 대해 바라보는 방향(회전시키는 방법) 4개
   => k개 CCTV에 대해, 4^k개 조합
 - k개 CCTV의 조합을 구성한 후, CCTV 감시 영역 표시 및 사각지대 칸 수 세기: O(n x m)
*/

class CCTV {
	public int y, x;		// CCTV 위치
	public int dir;			// CCTV가 바라보는 방향
	public int idx;			// CCTV 번호

	public CCTV(int y, int x, int dir, int idx) {
		this.y = y;
		this.x = x;
		this.dir = dir;
		this.idx = idx;
	}
}

public class Main {
	static int n, m;			// n x m 행렬
	static int[][] inputMap;	// 입력 맵
	static int[][] map;
	static int minCount;		// 출력, 최소 사각지대 칸 수

	static int numOfCCTV;		// CCTV 개수
	static List<CCTV> inputCCTVList = new ArrayList<>();	// 입력 CCTV의 위치, 번호 저장
	static List<CCTV> cctvList = new ArrayList<>();

	static final int EMPTY = 0;
	static final int WALL = 6;
	static final int CHECK = 100;

	static void backtrack(int depth) {
		// 전체 CCTV의 감시 방향을 정한 경우
		if (depth >= numOfCCTV) {
			// map 초기화
			map = new int[n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					map[i][j] = inputMap[i][j];
				}
			}

			// map에 CCTV 감시 방향 표시
			for (CCTV cctv : cctvList) {
				checkMonitoringArea(cctv);
			}

			// 사각지대(빈 칸 0) 칸 수 count
			int count = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (map[i][j] == EMPTY) {
						count++;
					}
				}
			}
			minCount = Math.min(minCount, count);

			return;
		}

		// 4개 회전 방향 (dir: 0 ~ 3)
		CCTV cctv = inputCCTVList.get(depth);
		for (int dir = 0; dir < 4; dir++) {
			cctvList.add(new CCTV(cctv.y, cctv.x, dir, cctv.idx));	// dir 0: 상
			backtrack(depth + 1);
			cctvList.remove(depth);
		}
	}

	/* map에 CCTV 감시 영역 표시 */
	static void checkMonitoringArea(CCTV cctv) {
		if (cctv.idx == 1) {
			checkMonitoringAreaCCTV1(cctv);
		}
		else if (cctv.idx == 2) {
			checkMonitoringAreaCCTV2(cctv);
		}
		else if (cctv.idx == 3) {
			checkMonitoringAreaCCTV3(cctv);
		}
		else if (cctv.idx == 4) {
			checkMonitoringAreaCCTV4(cctv);
		}
		else {		// cctv.idx == 5
			checkMonitoringAreaCCTV5(cctv);
		}
	}

	/* 1번 CCTV의 감시 영역 표시 */
	static void checkMonitoringAreaCCTV1(CCTV cctv) {
		if (cctv.dir == 0) {		// 1번 CCTV가 위를 바라보는 경우
			checkUpward(cctv.y, cctv.x);
		}
		else if (cctv.dir == 1) {		// 1번 CCTV가 오른쪽을 바라보는 경우
			checkRightward(cctv.y, cctv.x);
		}
		else if (cctv.dir == 2) {		// 1번 CCTV가 아래를 바라보는 경우
			checkDownward(cctv.y, cctv.x);
		}
		else {        // 1번 CCTV가 왼쪽을 바라보는 경우
			checkLeftward(cctv.y, cctv.x);
		}
	}

	/* 2번 CCTV의 감시 영역 표시 */
	static void checkMonitoringAreaCCTV2(CCTV cctv) {
		if (cctv.dir == 0 || cctv.dir == 2) {		// 2번 CCTV가 위 or 아래를 바라보는 경우
			// 양 옆 일직선으로 감시
			checkRightward(cctv.y, cctv.x);
			checkLeftward(cctv.y, cctv.x);
		}
		else {		// 2번 CCTV가 오른쪽 or 왼쪽을 바라보는 경우
			// 위 아래 일직선으로 감시
			checkUpward(cctv.y, cctv.x);
			checkDownward(cctv.y, cctv.x);
		}
	}

	/* 3번 CCTV의 감시 영역 표시 */
	static void checkMonitoringAreaCCTV3(CCTV cctv) {
		if (cctv.dir == 0) {		// 3번 CCTV가 위를 바라보는 경우
			checkUpward(cctv.y, cctv.x);
			checkRightward(cctv.y, cctv.x);
		}
		else if (cctv.dir == 1) {		// 3번 CCTV가 오른쪽을 바라보는 경우
			checkRightward(cctv.y, cctv.x);
			checkDownward(cctv.y, cctv.x);
		}
		else if (cctv.dir == 2) {		// 3번 CCTV가 아래를 바라보는 경우
			checkDownward(cctv.y, cctv.x);
			checkLeftward(cctv.y, cctv.x);
		}
		else {		// 3번 CCTV가 왼쪽을 바라보는 경우
			checkLeftward(cctv.y, cctv.x);
			checkUpward(cctv.y, cctv.x);
		}
	}

	/* 4번 CCTV의 감시 영역 표시 */
	static void checkMonitoringAreaCCTV4(CCTV cctv) {
		if (cctv.dir == 0) {		// 4번 CCTV가 위를 바라보는 경우
			checkUpward(cctv.y, cctv.x);
			checkLeftward(cctv.y, cctv.x);
			checkRightward(cctv.y, cctv.x);
		}
		else if (cctv.dir == 1) {		// 4번 CCTV가 오른쪽을 바라보는 경우
			checkRightward(cctv.y, cctv.x);
			checkUpward(cctv.y, cctv.x);
			checkDownward(cctv.y, cctv.x);
		}
		else if (cctv.dir == 2) {		// 4번 CCTV가 아래를 바라보는 경우
			checkDownward(cctv.y, cctv.x);
			checkLeftward(cctv.y, cctv.x);
			checkRightward(cctv.y, cctv.x);
		}
		else {		// 4번 CCTV가 왼쪽을 바라보는 경우
			checkLeftward(cctv.y, cctv.x);
			checkUpward(cctv.y, cctv.x);
			checkDownward(cctv.y, cctv.x);
		}
	}

	/* 5번 CCTV의 감시 영역 표시 */
	static void checkMonitoringAreaCCTV5(CCTV cctv) {
		// 5번 CCTV: 상하좌우 4방향 일직선 모두 감시
		// => 바라보는 방향 dir에 상관없이 감시 처리
		checkUpward(cctv.y, cctv.x);
		checkDownward(cctv.y, cctv.x);
		checkRightward(cctv.y, cctv.x);
		checkLeftward(cctv.y, cctv.x);
	}

	// 윗 방향 일직선 감시 표시
	static void checkUpward(int y, int x) {
		for (int ny = y - 1; ny >= 0; ny--) {
			if (!isValid(ny, x) || map[ny][x] == WALL)	// 벽은 통과 불가능
				break;

			if (1 <= map[ny][x] && map[ny][x] <= 5)		// CCTV는 통과
				continue;

			map[ny][x] = CHECK;
		}
	}

	// 아래 방향 일직선 감시 표시
	static void checkDownward(int y, int x) {
		for (int ny = y + 1; ny < n; ny++) {
			if (!isValid(ny, x) || map[ny][x] == WALL)	// 벽은 통과 불가능
				break;

			if (1 <= map[ny][x] && map[ny][x] <= 5)		// CCTV는 통과
				continue;

			map[ny][x] = CHECK;
		}
	}

	// 오른쪽 방향 일직선 감시 표시
	static void checkRightward(int y, int x) {
		for (int nx = x + 1; nx < m; nx++) {
			if (!isValid(y, nx) || map[y][nx] == WALL)	// 벽은 통과 불가능
				break;

			if (1 <= map[y][nx] && map[y][nx] <= 5)		// CCTV는 통과
				continue;

			map[y][nx] = CHECK;
		}
	}

	// 왼쪽 방향 일직선 감시 표시
	static void checkLeftward(int y, int x) {
		for (int nx = x - 1; nx >= 0; nx--) {
			if (!isValid(y, nx) || map[y][nx] == WALL)	// 벽은 통과 불가능
				break;

			if (1 <= map[y][nx] && map[y][nx] <= 5)		// CCTV는 통과
				continue;

			map[y][nx] = CHECK;
		}
	}

	static boolean isValid(int ny, int nx) {
		return (0 <= ny && ny < n) && (0 <= nx && nx < m);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		minCount = n * m;

		inputMap = new int[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < m; j++) {
				inputMap[i][j] = Integer.parseInt(st.nextToken());
				if (1 <= inputMap[i][j] && inputMap[i][j] <= 5) {
					numOfCCTV++;
					inputCCTVList.add(new CCTV(i, j, -1, inputMap[i][j]));
				}
			}
		}

		// Init Call
		backtrack(0);

		System.out.println(minCount);
	}
}

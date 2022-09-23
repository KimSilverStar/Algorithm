package Samsung_Coding_Test.로봇_청소기;
import java.io.*;
import java.util.StringTokenizer;

/*
- 빈 칸: 0, 벽: 1
- 로봇 청소기 방향: 북(상0), 동(우1), 남(하2), 서(좌3)
- 이미 청소된 칸을 다시 청소 X

1) 현재 위치 청소
2) 현재 위치, 현재 방향을 기준으로 왼쪽 방향부터 차례로 탐색
 ① 왼쪽 칸을 아직 청소 안한 경우, 왼쪽 방향으로 회전한 후 1칸 전진하고 1) 부터 다시 진행
 ② 왼쪽 칸이 이미 청소되거나 벽인 경우, 왼쪽 방향으로 회전한 후 2) 부터 다시 진행
 ③ 4방향 모두 청소되거나 벽인 경우, 현재 방향을 유지한 채로 1칸 후진 후 2) 부터 다시 진행
 ④ 4방향 모두 청소되거나 벽이고 뒤쪽 방향이 벽인 경우, 작동 종료
*/

/*
1. 아이디어
 - 구현, 시뮬레이션

1) 현재 위치 청소
  - map[i][j] = CLEAR;
  - resultClearCnt++;
2) 현재 위치, 현재 방향을 기준으로 왼쪽 방향부터 차례로 탐색
 ① 왼쪽 칸을 아직 청소 안한 경우(청소 안한 빈 칸 EMPTY인 경우),
    왼쪽 방향으로 회전한 후 1칸 전진하고 1) 부터 다시 진행
 ② 왼쪽 칸이 이미 청소(CLEAR)되거나 벽(WALL)인 경우,
    왼쪽 방향으로 회전한 후 2) 부터 다시 진행
 ③ 4방향 모두 청소되거나 벽인 경우,
    현재 방향을 유지한 채로 1칸 후진 후 2) 부터 다시 진행
 ④ 4방향 모두 청소되거나 벽이고 뒤쪽 방향이 벽인 경우, 작동 종료

  - int getLeftState(): 현재 위치 (y, x), 방향 d 기준으로 왼쪽 칸의 상태 반환 (EMPTY / WALL / CLEAR)
  - void rotateLeft(): 현재 위치 (y, x), 방향 d 기준으로 왼쪽 방향으로 회전
  - void moveForward(): 현재 위치 (y, x), 방향 d 기준으로 1칸 전진
  - void moveBackward(): 현재 위치 (y, x), 방향 d 기준으로 1칸 후진
  - boolean isFourDirectionClearedOrWall(): 현재 위치 (y, x) 기준으로 4방향 모두 청소되거나 벽인지 확인
  - boolean existBackWall(): 현재 위치 (y, x), 방향 d 기준으로 뒷 칸이 벽인지 확인
*/

public class Main {
	static int n, m;				// n x m 행렬
	static int y, x, d;				// 로봇 청소기의 현재 위치 (y, x), 방향 d
	static int[][] map;
	static int resultClearCnt;		// 출력, 청소하는 영역 칸 개수

	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static final int EMPTY = 0;		// 빈 칸
	static final int WALL = 1;		// 벽
	static final int CLEAR = 2;		// 청소된 칸

	// 로봇 청소기 방향: 북(상0), 동(우1), 남(하2), 서(좌3)
	static final int DIR_UP = 0;
	static final int DIR_RIGHT = 1;
	static final int DIR_DOWN = 2;
	static final int DIR_LEFT = 3;

	static void solution() {
		// 시작 지점 청소
		map[y][x] = CLEAR;
		resultClearCnt++;

		while (true) {
			// 현재 위치, 방향을 기준으로 왼쪽 칸의 상태 확인
			int leftState = getLeftState();

			if (isFourDirectionClearedOrWall()) {
				// ④ 4방향 모두 청소되거나 벽이고 뒤쪽 방향이 벽인 경우, 작동 종료
				if (existBackWall())
					break;

				// ③ 4방향 모두 청소되거나 벽인 경우(+ 로봇 청소기의 뒷 칸은 벽이 아님)
				moveBackward();
//				continue;
			}
			else if (leftState == EMPTY) {	// ① 왼쪽 칸을 아직 청소 안한 경우
				rotateLeft();
				moveForward();
				map[y][x] = CLEAR;
				resultClearCnt++;
			}
			else if (leftState == CLEAR || leftState == WALL) {		// ② 왼쪽 칸이 이미 청소되거나 벽인 경우
				rotateLeft();
//				continue;
			}
		}
	}

	/** 현재 위치 (y, x) 기준으로 4방향 모두 청소되거나 벽인지 확인
	 * - 4방향 중, 청소가 안된 빈 칸(EMPTY)이 존재하면, false 반환 */
	static boolean isFourDirectionClearedOrWall() {
		boolean existEmpty = false;

		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (isValid(ny, nx) && map[ny][nx] == EMPTY) {
				existEmpty = true;
				break;
			}
		}

		return !existEmpty;
	}

	/** 현재 위치 (y, x), 방향 d 기준으로 뒷 칸이 벽인지 확인 */
	static boolean existBackWall() {
		if (d == DIR_UP)
			return map[y + 1][x] == WALL;
		else if (d == DIR_RIGHT)
			return map[y][x - 1] == WALL;
		else if (d == DIR_DOWN)
			return map[y - 1][x] == WALL;
		else	// d == DIR__LEFT 인 경우
			return map[y][x + 1] == WALL;
	}

	/** 현재 위치 (y, x), 방향 d 기준으로 1킨 후진 */
	static void moveBackward() {
		if (d == DIR_UP)
			y++;
		else if (d == DIR_RIGHT)
			x--;
		else if (d == DIR_DOWN)
			y--;
		else 	// d == DIR__LEFT 인 경우
			x++;
	}

	/** 현재 위치 (y, x), 방향 d 기준으로 1칸 전진 */
	static void moveForward() {
		if (d == DIR_UP)
			y--;
		else if (d == DIR_RIGHT)
			x++;
		else if (d == DIR_DOWN)
			y++;
		else 	// d == DIR__LEFT 인 경우
			x--;
	}

	/** 현재 위치 (y, x), 방향 d 기준으로 왼쪽 칸의 상태 반환 (EMPTY / WALL / CLEAR) */
	static int getLeftState() {
		if (d == DIR_UP)
			return map[y][x - 1];
		else if (d == DIR_RIGHT)
			return map[y - 1][x];
		else if (d == DIR_DOWN)
			return map[y][x + 1];
		else	// d == DIR__LEFT 인 경우
			return map[y + 1][x];
	}

	/** 현재 위치 (y, x), 방향 d 기준으로 왼쪽 방향으로 회전 */
	static void rotateLeft() {
		if (d == DIR_UP)
			d= DIR_LEFT;
		else if (d == DIR_RIGHT)
			d = DIR_UP;
		else if (d == DIR_DOWN)
			d = DIR_RIGHT;
		else	// d == DIR__LEFT 인 경우
			d = DIR_DOWN;
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

		st = new StringTokenizer(br.readLine());
		y = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());

		map = new int[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		solution();

		System.out.println(resultClearCnt);
	}
}

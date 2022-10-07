package Samsung_Coding_Test.미세먼지_안녕;
import java.io.*;
import java.util.*;

/*
- 공기 청정기: [1]열에 설치, 행 2칸 차지

1초 동안 1), 2) 수행

1) 모든 미세먼지 칸에서 동시에 미세먼지 확산
 - 인접 4개 방향으로 확산
 - 인접 칸에 공기 청정기가 있거나 or 칸이 없으면 확산 X
 - 인접 칸에 확산되는 양 = map[r][c] / 5
 - [r][c]에 남은 미세먼지 양 = map[r][c] - (map[r][c] / 5) x 확산된 방향 개수
   => 기존 양에서 확산된 양 빼주기

2) 공기 청정기 작동
 - 위쪽 공기 청정기 바람: 반시계 방향으로 순환
 - 아래쪽 공기 청정기 바람: 시계 방향으로 순환
 - 바람이 불면, 미세먼지가 바람의 방향으로 모두 1칸씩 이동
 - 공기 청정기로 들어간 미세먼지는 모두 정화됨
*/

/*
1. 아이디어
 > 구현, 시뮬레이션

1) 모든 미세먼지 칸에서 동시에 미세먼지 확산
 - tempMap[][]에 map[][]을 copy
 - 2중 for문으로 tempMap[][] 확인
 - tempMap[i][j]에 미세먼지가 존재하는 경우, 인접 방향으로 미세먼지 확산시킴
   => map[][]에 확산 처리
   => map[i][j]에는 확산시킨 양을 빼줌

2) 공기 청정기 작동
 - 위쪽 공기 청정기 바람: 반시계 방향으로 순환
 - 아래쪽 공기 청정기 바람: 시계 방향으로 순환
 - 바람이 불면, 미세먼지가 바람의 방향으로 모두 1칸씩 이동
 - 공기 청정기로 들어간 미세먼지는 모두 정화됨

*/

public class Main {
	static int r, c;				// r x c 행렬
	static int t;					// t초 후
	static int[][] map;
	static int sum;					// 출력

	static int[] machineY = new int[2];		// 공기 청정기 위치
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };
	static final int MACHINE = -1;

	static void solution() {
		for (int i = 0; i < t; i++) {
			// 1) 모든 미세먼지 칸에서 동시에 미세먼지 확산
			spreadAllDusts();

			// 2) 공기 청정기 작동
			runMachine();
		}

		// 남은 미세먼지 양 계산
		for (int i = 1; i <= r; i++) {
			for (int j = 1; j <= c; j++) {
				if (map[i][j] > 0)
					sum += map[i][j];
			}
		}
	}

	static void spreadAllDusts() {
		int[][] tempMap = new int[r + 1][c + 1];		// map copy
		for (int i = 1; i <= r; i++) {
			for (int j = 1; j <= c; j++) {
				tempMap[i][j] = map[i][j];
			}
		}

		for (int y = 1; y <= r; y++) {
			for (int x = 1; x <= c; x++) {
				// tempMap[i][j]에 미세먼지 존재하는 경우
				if (tempMap[y][x] > 0) {
					// 인접 방향으로 미세먼지 확산시킴: map[][]에 확산 처리
					int amount = tempMap[y][x] / 5;		// 인접 1칸에 확산되는 양

					// 인접한 4개 칸 확인
					for (int i = 0; i < 4; i++) {
						int ny = y + dy[i];
						int nx = x + dx[i];

						// 인접 칸이 공기 청정기 칸 or 범위를 벗어난 경우
						if (!isValid(ny, nx) || tempMap[ny][nx] == MACHINE)
							continue;

						map[ny][nx] += amount;
						map[y][x] -= amount;
					}
				}
			}
		}
	}

	static void runMachine() {
		circulateUp();
		circulateDown();
	}

	/* 위쪽 공기 청정기 바람: 반시계 방향 순환 */
	static void circulateUp() {
		int mUpY = machineY[0];

		// 공기 청정기로 들어오는 바람 = 첫 열에서 아래로 내려가는 바람: 아래로 shift
		for (int y = mUpY - 1; y >= 2; y--) {
			map[y][1] = map[y-1][1];
		}

		// 맨 윗행에서 왼쪽으로 부는 바람: 왼쪽으로 shift
		for (int x = 1; x <= c - 1; x++) {
			map[1][x] = map[1][x+1];
		}

		// 끝 열에서 위로 올라가는 바람: 위로 shift
		for (int y = 1; y <= mUpY - 1; y++) {
			map[y][c] = map[y+1][c];
		}

		// 공기 청정기에서 나가는 바람 = 공기 청정기에서 오른쪽으로 부는 바람: 오른쪽으로 shift
		for (int x = c; x >= 3; x--) {
			map[mUpY][x] = map[mUpY][x-1];
		}
		map[mUpY][2] = 0;
	}

	/* 아래쪽 공기 청정기 바람: 시계 방향 순환 */
	static void circulateDown() {
		int mDownY = machineY[1];

		// 공기 청정기로 들어오는 바람 = 첫 열에서 위로 올가는 바람: 위로 shift
		for (int y = mDownY + 1; y <= r - 1; y++) {
			map[y][1] = map[y+1][1];
		}

		// 맨 아래 행에서 왼쪽으로 부는 바람: 왼쪽으로 shift
		for (int x = 1; x <= c - 1; x++) {
			map[r][x] = map[r][x+1];
		}

		// 끝 열에서 아래로 내려가는 바람: 아래로 shift
		for (int y = r; y >= mDownY + 1; y--) {
			map[y][c] = map[y-1][c];
		}

		// 공기 청정기에서 나가는 바람 = 공기 청정기에서 오른쪽으로 부는 바람: 오른쪽으로 shift
		for (int x = c; x >= 3; x--) {
			map[mDownY][x] = map[mDownY][x-1];
		}
		map[mDownY][2] = 0;
	}

	static boolean isValid(int y, int x) {
		return (1 <= y && y <= r) && (1 <= x && x <= c);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());

		int machineIdx = 0;
		map = new int[r + 1][c + 1];			// [1][1] ~ [r][c] 사용
		for (int i = 1; i <= r; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 1; j <= c; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());

				if (map[i][j] == MACHINE) {
					machineY[machineIdx++] = i;
				}
			}
		}

		solution();

		System.out.println(sum);
	}
}

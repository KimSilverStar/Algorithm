package Samsung_Coding_Test.사다리_조작;
import java.io.*;
import java.util.*;

/*
- 세로선: 왼쪽에서부터 1번부터 시작
- 점선 번호: 위에서부터 1번부터 시작

- 사다리에 가로선을 추가해서, 각 [i]번 세로선의 결과가 [i]번이 되도록 하려함
- 출력: 추가해야 하는 가로선 개수의 최소값
  > 정답 > 3인 경우, -1
  > 불가능한 경우, -1
*/

/*
1. 아이디어
 > 구현, 시뮬레이션
 > 조합(백트래킹, 브루트 포스)


2. 자료구조
 - int[][] map: 가로선 연결 정보 맵
   => map[i][j] = 1, map[i][j+1] = 2
 - boolean finished


3. 시간 복잡도
 - 추가할 수 있는 가로선 최대 개수 = (n-1) x h	(입력 가로선이 0개일 때)
   => n, h 최대값 대입: 최대 270개
 - 추가한 가로선이 3개 초과하면, 출력 -1로 더 확인할 필요 X
   => 추가할 가로선 0개, 1개, 2개, 3개로 제한하여, 각각 조합 구성 및 탐색
   => 최대 확인할 경우의 수 = C(270, 0) + C(270, 1) + C(270, 2) + C(270, 3)
   	  = 1 + 270 + 36,315 + 3,244,140 = 3,280,726
*/

public class Main {
	static int n, m;				// n개 세로선, m개 가로선
	static int h;					// 세로선마다 가로선을 놓을 수 있는 위치 h개
	static int minCnt = -1;			// 출력, 추가하는 가로선 개수 최소값

	static int[][] map;				// 가로선 연결 정보 맵
	static boolean finished;
	static final int EMPTY = 0, RIGHT = 1, LEFT = 2;

	/* addedCnt: 현재까지 추가한 가로선 개수, finishCnt: 미리 정한 추가할 가로선 개수 */
	static void backtrack(int y, int x, int addedCnt, int finishCnt) {
		// 이전 분기에서 목표 조건 만족한 경우, 다른 분기 확인 X
		if (finished)
			return;

		// 미리 정한 추가할 가로선 개수만큼 가로선 추가 완료한 경우
		if (addedCnt == finishCnt) {
			// 현재까지 추가한 가로선들로 목표 조건 만족하는지 확인
			if (checkAllVLines()) {
				finished = true;
			}

			return;
		}

		// 목표 조건 불만족한 경우, 가로선 더 추가
		for (int i = y; i <= h; i++) {
			for (int j = x; j < n; j++) {
				// 가로선이 없는 경우 (가로선 추가 가능한 경우)
				if (map[i][j] == EMPTY && map[i][j+1] == EMPTY) {
					int nx = (x + 1 < n) ? x + 1 : 1;
					int ny = (nx == 1) ? y + 1 : y;

					// 선택 O
					map[i][j] = RIGHT;
					map[i][j+1] = LEFT;
					backtrack(ny, nx, addedCnt + 1, finishCnt);

					// 선택 X - 복구
					map[i][j] = EMPTY;
					map[i][j+1] = EMPTY;
				}
			}
		}
	}

	static boolean checkAllVLines() {
		// 세로선 [1]번 ~ [n]번 차례로 확인
		for (int vLineIdx = 1; vLineIdx <= n; vLineIdx++) {
			// 세로선 1개라도 조건 만족 안되면, 실패
			if (!checkVLines(vLineIdx))
				return false;
		}

		return true;
	}

	static boolean checkVLines(int vLineIdx) {
		int vIdx = vLineIdx;	// 세로선 위치
		int hIdx = 1;			// 가로선 위치: 1칸씩 아래로 내려감

		while (hIdx <= h) {
			if (map[hIdx][vIdx] == RIGHT)
				vIdx++;
			else if (map[hIdx][vIdx] == LEFT)
				vIdx--;

			hIdx++;
		}

		return vIdx == vLineIdx;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		h = Integer.parseInt(st.nextToken());

		map = new int[h + 1][n + 1];			// [1][1] ~ [h][n] 사용
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			// map[a][b], map[a][b + 1] 연결한 가로선
			map[a][b] = RIGHT;
			map[a][b+1] = LEFT;
		}

		// 추가할 가로선 개수 0 ~ 3개 제한하여 탐색
		// => 추가할 가로선 개수를 미리 정하여 종료 조건으로 걸어두고 탐색
		for (int finishCnt = 0; finishCnt <= 3; finishCnt++) {
			backtrack(1, 1, 0, finishCnt);		// Init Call

			if (finished) {
				minCnt = finishCnt;
				break;
			}
		}

		System.out.println(minCnt);
	}
}

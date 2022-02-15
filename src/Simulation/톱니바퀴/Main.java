package Simulation.톱니바퀴;
import java.io.*;
import java.util.StringTokenizer;

/*
- 8개의 톱니를 갖는 톱니바퀴 4개 (1 ~ 4번 톱니바퀴)
- 톱니바퀴를 총 k 번(k 칸) 회전
  => 회전시킬 톱니바퀴, 회전 방향

- 회전시키는 톱니바퀴의 톱니 vs 맞닿은 톱니바퀴의 톱니
  1) 회전 전, 맞닿은 톱니가 서로 다른 극인 경우
    => 회전시키는 톱니바퀴와 반대 방향으로 맞닿은 톱니바퀴도 회전
  2) 회전 전, 맞닿은 톱니가 서로 같은 극인 경우
    => 맞닿은 톱니바퀴는 회전 X
*/

/*
1. 아이디어
 - 각 톱니바퀴 별, 톱니의 상태를 배열에 저장
   => int[][] gears
   => gears[톱니바퀴 번호][톱니 번호]: 해당 톱니바퀴의 톱니 상태

 - 각 톱니바퀴의 회전
   1) 시계 방향 회전
     - 해당 톱니바퀴 상태 배열에서 오른쪽으로 shift
   2) 반시계 방향 회전
     - 해당 톱니바퀴 상태 배열에서 왼쪽으로 shift

 - 회전시킬 톱니바퀴를 회전 시키기 전,
   연쇄적으로 맞닿은 톱니바퀴들의 회전 여부 및 회전 방향 결정
   1) i번 톱니바퀴를 회전시키려는 경우
     - 왼쪽 [i-1]번 톱니바퀴의 오른쪽 [3]번 톱니 vs [i]번 톱니바퀴의 왼쪽 [7]번 톱니
     - 오른쪽 [i+1]번 톱니바퀴의 왼쪽 [7]번 톱니 vs [i]번 톱니바퀴의 오른쪽 [3]번 톱니
     => 각각 재귀호출
   2) 2개의 톱니바퀴의 왼쪽 / 오른쪽 톱니의 극이 서로 다른 경우, 회전
   => setRotateDirection(톱니바퀴 번호, 해당 톱니바퀴의 회전 방향)

2. 자료구조
 - int[][] gears: 각 톱니바퀴의 톱니 상태
 - Pair[] pairs: 입력 회전 방법 순서
   => 회전 시킬 톱니바퀴 번호, 회전 방향
 - int[] rotateDirections: 각 회전 명령에 따라, 각 톱니바퀴의 회전 방향
 - boolean[] check: 각 회전 명령에 따라, 각 톱니바퀴의 회전 방향을 결정할 때
   톱니바퀴의 방문 여부 확인

3. 시간 복잡도
 1) 입력 회전 명령 1개에 대해, 각 톱니바퀴의 회전 방향 결정
   - 재귀호출 대충 4번
   => 회전 명령 k번: O(k x 4)
 2) 톱니바퀴 1개를 회전: 8
   - 톱니 8개를 왼쪽 / 오른쪽으로 shift
   => 톱니바퀴 4개 전부 회전: 4 x 8 = 32
 - 전체 시간 복잡도 = O(k x 4 x 32)
   => k 최대값 대입: 100 x 4 x 32 = 12,800 << 2억
*/

class Pair {
	public int gearIdx;				// 회전시킬 톱니바퀴 번호
	public int direction;			// 회전 방향

	public Pair(int gearIdx, int direction) {
		this.gearIdx = gearIdx;
		this.direction = direction;
	}
}

public class Main {
	static int[][] gears;			// 4개 톱니바퀴의 톱니 상태, [톱니바퀴 번호][톱니 번호]
	static int k;					// 회전 횟수
	static Pair[] pairs;			// 회전 방법 순서
	static int[] directions;		// 각 톱니바퀴의 회전 여부 및 방향 (1: 시계, -1: 반시계)
	static boolean[] check;
	static int sum;					// k번 회전 후, 4개 톱니바퀴의 점수 합

	static void solution() {
		// 입력 회전 순서에 따라 회전 수행
		for (int i = 0; i < k; i++) {
			Pair current = pairs[i];

			check = new boolean[5];				// [1] ~ [4] 사용
			directions = new int[5];

			check[current.gearIdx] = true;
			directions[current.gearIdx] = current.direction;
			setDirection(current.gearIdx, current.direction);

			for (int j = 1; j <= 4; j++) {
				if (directions[j] == 1)
					rotateClock(j);
				else if (directions[j] == -1)
					rotateCounterClock(j);
			}
		}

		// 결과 톱니바퀴 점수 계산
		if (gears[1][1] == 1) sum += 1;
		if (gears[2][1] == 1) sum += 2;
		if (gears[3][1] == 1) sum += 4;
		if (gears[4][1] == 1) sum += 8;
	}

	static void rotateClock(int gearIdx) {			// 톱니바퀴 시계 방향 회전
		// 오른쪽으로 shift
		int temp = gears[gearIdx][8];
		for (int i = 8; i >= 2; i--)
			gears[gearIdx][i] = gears[gearIdx][i - 1];
		gears[gearIdx][1] = temp;
	}

	static void rotateCounterClock(int gearIdx) {	// 톱니바퀴 반시계 방향 회전
		// 왼쪽으로 shift
		int temp = gears[gearIdx][1];
		for (int i = 1; i <= 7; i++)
			gears[gearIdx][i] = gears[gearIdx][i + 1];
		gears[gearIdx][8] = temp;
	}

	/* 인자로 받은 톱니바퀴의 왼쪽, 오른쪽 톱니바퀴가 회전 여부 및 방향 결정 */
	static void setDirection(int gearIdx, int direction) {
		int leftGearIdx = gearIdx - 1;
		if (leftGearIdx >= 1) {
			if (!check[leftGearIdx]) {
				check[leftGearIdx] = true;

				// 인자 톱니바퀴의 왼쪽 [7]번 톱니와 왼쪽 톱니바퀴의 오른쪽 [3]번 톱니 비교
				if (gears[gearIdx][7] != gears[leftGearIdx][3]) {
					int leftGearDirection = direction * (-1);
					directions[leftGearIdx] = leftGearDirection;
					setDirection(leftGearIdx, leftGearDirection);
				}
			}
		}

		int rightGearIdx = gearIdx + 1;
		if (rightGearIdx <= 4) {
			if (!check[rightGearIdx]) {
				check[rightGearIdx] = true;

				// 인자 톱니바퀴의 오른쪽 [3]번 톱니와 오른쪽 톱니바퀴의 왼쪽 [7]번 톱니 비교
				if (gears[gearIdx][3] != gears[rightGearIdx][7]) {
					int rightGearDirection = direction * (-1);
					directions[rightGearIdx] = rightGearDirection;
					setDirection(rightGearIdx, rightGearDirection);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		gears = new int[5][9];			// [1][1] ~ [4][8] 사용
		for (int i = 1; i <= 4; i++) {
			String str = br.readLine();
			for (int j = 1; j <= 8; j++)
				gears[i][j] = Character.getNumericValue(str.charAt(j - 1));
		}

		k = Integer.parseInt(br.readLine());
		pairs = new Pair[k];
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			int gearIdx = Integer.parseInt(st.nextToken());
			int direction = Integer.parseInt(st.nextToken());
			pairs[i] = new Pair(gearIdx, direction);
		}

		solution();
		System.out.println(sum);
	}
}

package Samsung_Coding_Test.톱니바퀴;
import java.io.*;
import java.util.*;

/*
- 4개 톱니바퀴: 1번 ~ 4번
- 각 톱니바퀴는 8개의 톱니를 가짐
- 각 톱니: N극 or S극
  => 톱니 상태: 12시 방향부터 시계 방향 순서대로 저장
  => N극은 0, S극은 1

- 톱니바퀴를 총 k번 회전시킴
- 회전 방향: 시계 or 반시계
- 톱니바퀴를 회전시키면,

- A 톱니바퀴를 회전시킴
  => A 옆에 있는 B 톱니바퀴: A와 맞닿은 톱니의 극이 다르면,
     B는 A 회전 반대 방향으로 회전함
*/

/*
1. 아이디어
 > 구현, 시뮬레이션

1) 각 톱니바퀴 회전 여부 및 회전 방향 결정
  - [i]번 톱니바퀴 회전
    ① [i-1]번 톱니바퀴 확인: gears[i][6]과 gears[i-1][2] 비교
    ② [i+1]번 톱니바퀴 확인: gears[i][2]과 gears[i+1][6] 비교
  - 각 톱니바퀴의 회전 여부, 회전 방향을 rotateDirs[]에 저장

2) 저장된 rotateDirs[]에 따라, 회전시킬 톱니바퀴 회전
  ① 시계 방향 회전
    - gears[gearIdx][]를 오른쪽으로 shift
  ② 반시계 방향 회전
    - gears[gearIdx][]를 왼쪽으로 shift


2. 자료구조
 - int[][] gears: 각 톱니바퀴의 톱니 상태
   => gears[i]: [i]번 톱니바퀴의 톱니 상태
 - Command[] commands: 입력 회전 명령
   ※ Command: 톱니바퀴 번호 gearIdx, 회전 방향 rotateDir
 - int[] rotateDirs: 각 톱니바퀴 회전 여부 및 회전 방향 저장
*/

class Command {
	public int gearIdx;			// 톱니바퀴 번호
	public int rotateDir;		// 회전 방향

	public Command(int gearIdx, int rotateDir) {
		this.gearIdx = gearIdx;
		this.rotateDir = rotateDir;
	}
}

public class Main {
	static int[][] gears;				// 각 톱니바퀴의 톱니 상태
	static int k;						// 톱니바퀴 회전 횟수
	static Command[] commands;			// 입력 회전 명령
	static int scores;

	static boolean[] visited;
	static int[] rotateDirs;
	static final int N = 0, S = 1;
	static final int CLOCK = 1, COUNTER_CLOCK = -1;		// 회전 방향

	static void solution() {
		for (Command command : commands) {
			// 1) 각 톱니바퀴 회전 여부 및 회전 방향 확인
			visited = new boolean[5];		// Init
			rotateDirs = new int[5];

			visited[command.gearIdx] = true;
			rotateDirs[command.gearIdx] = command.rotateDir;
			checkRotate(command.gearIdx, command.rotateDir);

			// 2) 체크한 rotateDirs[]에 따라, 회전시킬 톱니바퀴 회전
			for (int gearIdx = 1; gearIdx <= 4; gearIdx++) {
				if (rotateDirs[gearIdx] == CLOCK) {
					rotateClock(gearIdx);
				}
				else if (rotateDirs[gearIdx] == COUNTER_CLOCK) {
					rotateCounterClock(gearIdx);
				}
			}
		}

		// k번 회전 끝난 후, 점수 계산
		if (gears[1][0] == S) scores += 1;
		if (gears[2][0] == S) scores += 2;
		if (gears[3][0] == S) scores += 4;
		if (gears[4][0] == S) scores += 8;
	}

	/* gearIdx번 톱니바퀴가 rotateDir 방향으로 회전할 경우,
	양 옆 톱니바퀴 회전 여부 및 방향 체크 */
	static void checkRotate(int gearIdx, int rotateDir) {
		int leftGearIdx = gearIdx - 1;
		int rightGearIdx = gearIdx + 1;
		// 양 옆 톱니바퀴가 회전한다면, 회전 방향은 반대가 됨
		int otherRotateDir = (rotateDir == CLOCK) ? COUNTER_CLOCK : CLOCK;

		if (leftGearIdx >= 1 && !visited[leftGearIdx] &&
				gears[gearIdx][6] != gears[leftGearIdx][2]) {
			visited[leftGearIdx] = true;
			rotateDirs[leftGearIdx] = otherRotateDir;
			checkRotate(leftGearIdx, otherRotateDir);
		}

		if (rightGearIdx <= 4 && !visited[rightGearIdx] &&
				gears[gearIdx][2] != gears[rightGearIdx][6]) {
			visited[rightGearIdx] = true;
			rotateDirs[rightGearIdx] = otherRotateDir;
			checkRotate(rightGearIdx, otherRotateDir);
		}
	}

	/* gearIdx번 톱니바퀴를 시계 방향으로 회전 */
	static void rotateClock(int gearIdx) {
		// gears[gearIdx][]를 오른쪽으로 shift
		int temp = gears[gearIdx][7];
		for (int i = 7; i > 0; i--) {
			gears[gearIdx][i] = gears[gearIdx][i - 1];
		}
		gears[gearIdx][0] = temp;
	}

	/* gearIdx번 톱니바퀴를 반시계 방향으로 회전 */
	static void rotateCounterClock(int gearIdx) {
		// gears[gearIdx][]를 왼쪽으로 shift
		int temp = gears[gearIdx][0];
		for (int i = 0; i < 7; i++) {
			gears[gearIdx][i] = gears[gearIdx][i + 1];
		}
		gears[gearIdx][7] = temp;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		gears = new int[5][8];			// [1] ~ [4] 사용
		for (int i = 1; i <= 4; i++) {
			String input = br.readLine();

			for (int j = 0; j < 8; j++) {
				gears[i][j] = Character.getNumericValue(input.charAt(j));
			}
		}

		k = Integer.parseInt(br.readLine());

		commands = new Command[k];
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			int gearIdx = Integer.parseInt(st.nextToken());
			int rotateDir = Integer.parseInt(st.nextToken());

			commands[i] = new Command(gearIdx, rotateDir);
		}

		solution();

		System.out.println(scores);
	}
}

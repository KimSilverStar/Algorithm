package Samsung_Coding_Test.컨베이어_벨트_위의_로봇;
import java.io.*;
import java.util.*;

/*
- 길이 n인 컨베이어 벨트, 길이 2n 벨트
- 벨트가 1칸 회전 = 시계 방향으로 1칸씩 이동
- [i]번 칸의 내구도 = A_i
- [1]번 칸: "올리는 위치",
  [n]번 칸: "내리는 위치"

* 로봇
- [1]번 칸에 올림
  => [1]번 칸 내구도 -1
- [n]번 칸에 도달하면, 내림 (로봇 없앰)
- 로봇은 컨베이어 벨트 위에서 이동 가능
  => 이동 시, 해당 칸의 내구도 -1

* 다음 과정 반복
1) 벨트가 각 칸에 있는 로봇과 함께 1칸 회전
2) 가장 먼저 벨트에 올라간 로봇부터, 벨트 회전 방향으로 1칸 이동 가능한 경우 이동.
   이동 불가능한 경우, 가만히 있음
 - 로봇 이동 조건 = 이동하려는 칸에 로봇이 없고, 이동하려는 칸의 내구도 >= 1
3) [1]번 칸 내구도 > 0 인 경우, [1]번 칸에 로봇을 올림
4) 내구도가 0인 칸의 개수 >= k 인 경우, 과정 종료

* 출력: 종료되었을 때, 과정을 몇 번째 반복중이였는지
*/

/*
1. 아이디어
 > 구현, 시뮬레이션

- 로봇: 컨베이어 벨트 윗 부분에서만 위치
  => 벨트 칸 [1]번 ~ [n]번

* 다음 과정 반복
1) 벨트가 각 칸에 있는 로봇과 함께 1칸 회전
 - void rotate()
   : a[], existRobot[] 벨트 회전 시계 방향으로 1칸씩 이동
 - rotate() 수행 후, [n]번 칸에 로봇 있으면, 해당 로봇 삭제(로봇 내림)

2) 가장 먼저 벨트에 올라간 로봇부터, 벨트 회전 방향으로 1칸 이동 가능한 경우 이동.
   + 로봇 이동 조건 = 이동하려는 칸에 로봇이 없고, 이동하려는 칸의 내구도 >= 1
 - "가장 먼저 벨트에 올라간 로봇부터"
   => for문으로 [n-1] ~ [1] 순서로 확인
      > 로봇은 컨베이어 벨트 윗 부분에만 위치 + [n]번 칸의 로봇은 삭제
   => [i] 칸 기준: [i+1] 칸에 로봇이 없고 [i+1] 칸의 내구도 >= 1인 경우, [i+1] 칸으로 이동
 - for문으로 이동 수행 완료 후, [n]번 칸에 로봇 있으면, 해당 로봇 삭제(로봇 내림)

3) [1]번 칸 내구도 > 0 인 경우, [1]번 칸에 로봇을 올림
 - [1]번 칸에 로봇 올리고, 내구도 감소 처리

4) 내구도가 0인 칸의 개수 >= k 인 경우, 과정 종료
 - for문으로 전체 벨트 칸의 내구도 확인


2. 자료구조
 - int[] a: 각 벨트 칸의 내구도
 - boolean[] existRobot: 각 벨트 칸에 로봇이 존재하는지 표시
*/

public class Main {
	static int n;					// 길이 2 x n 벨트
	static int k;
	static int[] a;					// 각 벨트 칸의 내구도
	static boolean[] existRobot;
	static int cnt;					// 출력, 종료 시 과정을 몇 번째 반복 중이였는지

	static void solution() {
		cnt = 1;

		while (true) {
			// 1) 벨트가 각 칸에 있는 로봇과 함께 1칸 회전
			rotate();
			existRobot[n] = false;	// [n]번 칸에 로봇 있으면, 해당 로봇 삭제(로봇 내림)

			// 2) 가장 먼저 올라간 로봇부터, 이동 가능한 로봇들 이동
			moveAllRobots();
			existRobot[n] = false;	// [n]번 칸에 로봇 있으면, 해당 로봇 삭제(로봇 내림)

			// 3) [1]번 칸 내구도 > 0 인 경우, [1]번 칸에 로봇을 올림
			if (a[1] > 0) {
				// [1]번 칸에 로봇 올리고, 내구도 감소 처리
				existRobot[1] = true;
				a[1]--;
			}

			// 4) 내구도가 0인 벨트 칸의 개수 >= k 인 경우, 과정 종료
			if (countZeroDurability() >= k) {
				break;
			}

			cnt++;
		}
	}

	/* 벨트 회전: 각 벨트 칸, 로봇 1칸씩 시계 방향으로 이동 */
	static void rotate() {
		int tempA = a[2*n];
		for (int i = 2 * n; i >= 2; i--) {
			a[i] = a[i-1];
		}
		a[1] = tempA;

		for (int i = n; i >= 2; i--) {
			existRobot[i] = existRobot[i-1];
		}
		existRobot[1] = false;		// [1]번 칸에는 로봇이 없게됨
	}

	/* 이동 가능한 로봇들 이동 */
	static void moveAllRobots() {
		for (int i = n - 1; i >= 1; i--) {
			// [i]번 칸에 로봇 존재하는 경우: [i]번 칸의 로봇을 다음 칸으로 이동 가능한지 확인
			if (existRobot[i]) {
				// [i+1]번 칸에 로봇이 없고, [i+1]번 칸의 내구도가 1 이상인 경우
				if (!existRobot[i+1] && a[i+1] >= 1) {
					// [i]번 칸의 로봇을 [i+1]번 칸으로 이동
					existRobot[i] = false;
					existRobot[i+1] = true;
					a[i+1]--;
				}
			}
		}
	}

	/* 내구도 0인 벨트 칸 개수 반환 */
	static int countZeroDurability() {
		int cnt = 0;

		for (int i = 1; i <= 2 * n; i++) {
			if (a[i] == 0)
				cnt++;
		}

		return cnt;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());

		a = new int[2 * n + 1];					// [1] ~ [2*n] 사용
		existRobot = new boolean[n + 1];		// [1] ~ [n] 사용: 로봇은 컨베이어 벨트 윗 부분에만 존재
		for (int i = 1; i <= 2 * n; i++) {
			a[i] = Integer.parseInt(st.nextToken());
		}

		solution();

		System.out.println(cnt);
	}
}

package Simulation.로봇_시뮬레이션;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 입력 행렬(행이 밑에서부터 시작) 저장
   => map[b - row][col]
 - 로봇 명령 수행
   1) 방향 전환: L, R
     - L: E -> N -> W -> S -> E ...
     - R: E -> S -> W -> N -> E ...
   2) 전진: F

2. 자료구조
 - Robot[]: 각 로봇 번호 별 로봇의 위치, 방향 저장
 - Command[]: 입력 명령들

3. 시간 복잡도
 - 전체 명령 개수 M x 각 명령의 반복 횟수 만큼 반복
   => 최대 100 x 100 >> 2억
*/

class Robot {
	public int x, y;
	public char direct;			// 방향

	public Robot(int x, int y, char direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
}

class Command {
	public int robotIdx;		// 명령 수행할 로봇 번호
	public char kind;			// 명령 종류
	public int count;			// 반복 횟수

	public Command(int robotIdx, char kind, int count) {
		this.robotIdx = robotIdx;
		this.kind = kind;
		this.count = count;
	}
}

public class Main {
	static int a, b;				// 땅의 가로, 세로 크기
	static int n, m;				// 로봇 개수, 명령 개수
	static Robot[] robots;			// 각 로봇 번호 별 로봇 위치, 방향
	static Command[] commands;		// 입력 명령들

	static int crashedRobotIdx;		// 충돌한 로봇 번호 (예시 y번 로봇)
	static StringBuilder sb = new StringBuilder();

	static void solution() {
		for (int i = 0; i < m; i++) {
			Command current = commands[i];
			Robot robot = robots[current.robotIdx];		// 명령 수행할 로봇

			for (int j = 0; j < current.count; j++) {
				if (current.kind == 'L')
					turnLeft(robot);
				else if (current.kind == 'R')
					turnRight(robot);
				else if (current.kind == 'F') {
					goFront(robot);
					// 전진 후, 벽과 충돌 검사
					if (isCrashedWall(robot)) {
						sb.append("Robot ").append(current.robotIdx)
							.append(" crashes into the wall");
						return;
					}
					// 전진 후, 로봇과 충돌 검사
					if (isCrashedRobot(robot)) {
						sb.append("Robot ").append(current.robotIdx)
							.append(" crashes into robot ").append(crashedRobotIdx);
						return;
					}
				}
			}
		}

		sb.append("OK");
	}

	/* 로봇이 벽과 충돌했는지 검사 */
	static boolean isCrashedWall(Robot robot) {
		if (robot.y < 1 || robot.y > b ||
				robot.x < 1 || robot.x > a)
			return true;
		return false;
	}

	/* 로봇이 로봇과 충돌했는지 검사 */
	static boolean isCrashedRobot(Robot robot) {
		for (int i = 1; i <= n; i++) {
			if (robot == robots[i])			// 로봇 본인
				continue;

			if (robot.y == robots[i].y &&
					robot.x == robots[i].x) {
				crashedRobotIdx = i;
				return true;
			}
		}
		return false;
	}

	/* 로봇이 앞으로 전진하는 명령(F) 수행 */
	static void goFront(Robot robot) {
		if (robot.direct == 'E') robot.x++;
		else if (robot.direct == 'S') robot.y--;
		else if (robot.direct == 'W') robot.x--;
		else if (robot.direct == 'N') robot.y++;
	}

	/* 로봇이 왼쪽으로 방향 전환하는 명령(L) 수행 */
	static void turnLeft(Robot robot) {
		if (robot.direct == 'E') robot.direct = 'N';
		else if (robot.direct == 'N') robot.direct = 'W';
		else if (robot.direct == 'W') robot.direct = 'S';
		else if (robot.direct == 'S') robot.direct = 'E';
	}

	/* 로봇이 오른쪽으로 방향 전환하는 명령(R) 수행 */
	static void turnRight(Robot robot) {
		if (robot.direct == 'E') robot.direct = 'S';
		else if (robot.direct == 'S') robot.direct = 'W';
		else if (robot.direct == 'W') robot.direct = 'N';
		else if (robot.direct == 'N') robot.direct = 'E';
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		a = Integer.parseInt(st.nextToken());		// 땅 가로 크기
		b = Integer.parseInt(st.nextToken());		// 땅 세로 크기

		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());		// 로봇 개수
		m = Integer.parseInt(st.nextToken());		// 명령 개수

		robots = new Robot[n + 1];			// [1] ~ [n] 사용
		for (int i = 1; i <= n; i++) {
			// 각 로봇의 초기 위치 좌표, 방향
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			char direct = st.nextToken().charAt(0);
			robots[i] = new Robot(x, y, direct);
		}

		commands = new Command[m];
		for (int i = 0; i < m; i++) {
			// 각 명령의 로봇 번호, 명령 종류, 명령 반복 횟수
			st = new StringTokenizer(br.readLine());
			int robotIdx = Integer.parseInt(st.nextToken());
			char kind = st.nextToken().charAt(0);
			int count = Integer.parseInt(st.nextToken());
			commands[i] = new Command(robotIdx, kind, count);
		}

		solution();
		System.out.println(sb);
	}
}

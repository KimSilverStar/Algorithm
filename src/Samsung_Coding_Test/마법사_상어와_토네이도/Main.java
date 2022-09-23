package Samsung_Coding_Test.마법사_상어와_토네이도;
import java.io.*;
import java.util.*;

/*
토네이도 시전
- 격자 가운데 칸부터 토네이도 이동 시작
  => 나선형 형태로 이동
- 토네이도가 1칸 이동할 때마다, 모래가 특정 비율로 흩날림
- 출력: 토네이도가 최상단 왼쪽 칸에 도착할 때까지, 격자 밖으로 나간 모래 양
*/

/*
1. 아이디어
 - 구현, 시뮬레이션
 - 토네이도 시작 위치: 격자 중앙 칸
   => (n / 2, n / 2)
 - 토네이도 이동 규칙
   ① 이동 방향: 좌하우상 순서로 반복
   ② 이동 칸 수: 좌하우상 한 싸이클 기준,
      { 1칸, 1칸, 2칸, 2칸 }, { 3칸, 3칸, 4칸, 4칸 }, { 5칸, 5칸, 6칸, 6칸 }, ...
      => 4방향 한 싸이클 돌고, 2칸씩 늘어남


반복 조건: 토네이도가 맵을 벗어날 때까지 반복

 1) 토네이도 이동
   - 토네이도 다음 위치 (nTornadoY, nTornadoX) 계산
     ※ 토네이도 다음 위치가 맵을 벗어난 경우 (이동했을 때 맵을 벗어난 경우), 반복 종료 및 출력
   - 토네이도 다음 위치의 모래 흩날리기
     int nextPosSand = map[nTornadoY][nTornadoX];		// temp 저장 (흩날려서 확산된 모래 양을 빼준 값 계산 용도)
     map[nTornadoY][nTornadoX] = 0;

 2) 모래 흩날리기
   - 각 지점마다 해당 비율에 따른 모래를 더해줌.
     해당 지점에 더해준 흩날린 모래 양을 sumSpreadSand에 더해줌.
     ※ 흩날리는 모래가 더해지는 지점이 맵을 벗어난 경우, 흩날리는 모래 양을 출력 변수에 더해줌
   - 각 지점 비율에 따라 모래를 더해주고, 남은 모래를 알파 지점에 더해줌
     알파 지점에 더해줄 남은 모래 양 = nextPosSand - sumSpreadSand
     ※ 알파 지점이 맵을 벗어난 경우, 알파 지점에 더해지는 모래 양을 출력 변수에 더해줌

 - 현재 토네이도 위치 갱신 (다음 위치로 이동 반영)
 - 이동 칸 수 배열 dd[]의 각 원소 값 += 2


2. 자료구조
 1) 토네이도 이동 방향 4개: 좌하우상 순서
   - int[] dy = { 0, 1, 0, -1 }
   - int[] dx = { -1, 0, 1, 0 }
     => 토네이도 이동 한 싸이클: 좌하우상
     => 토네이도 다음 위치 y 좌표: nTornadoY = y + dy[i]

 2) 토네이도 이동 방향에 따른, 이동 칸 수
   - int[] dd = { 1, 1, 2, 2 }
     => 토네이도 이동 한 싸이클을 돌고난 후, dd[] 각 원소 += 2

 3) 모래 흩날리는 비율: 9개 지점에 대한 비율
   - double[] spreadRatio = { 0.01, 0.01, 0.07, 0.02, 0.07, 0.02, 0.1, 0.1, 0.05 }

 4) 토네이도 이동 방향에 따른, 모래 확산 방향 (그림 예시 y 지점 기준)
   - int[][] dsy = {
		{ 토네이도가 왼쪽으로 이동할 때, 모래 확산 y 방향 9개 },
		{ 토네이도가 아래쪽으로 이동할 때, 모래 확산 y 방향 9개 },
		{ 토네이도가 오른쪽으로 이동할 때, 모래 확산 y 방향 9개 },
		{ 토네이도가 위쪽으로 이동할 때, 모래 확산 y 방향 9개 }
     }
   - int[][] dsx = {
     	{ 토네이도가 왼쪽으로 이동할 때, 모래 확산 x 방향 9개 },
		{ 토네이도가 아래쪽으로 이동할 때, 모래 확산 x 방향 9개 },
		{ 토네이도가 오른쪽으로 이동할 때, 모래 확산 x 방향 9개 },
		{ 토네이도가 위쪽으로 이동할 때, 모래 확산 x 방향 9개 }
     }
     => dsy[토네이도 이동 방향: 0 ~ 3][0 ~ 8]
     => 토네이도 다음 위치 y 좌표 nTornadoY에 대해,
        흩날려서 확산되는 모래 지점 y 좌표: nTornadoY + dsy[토네이도 이동 방향][0 ~ 8]
*/

public class Main {
	static int n;				// n x n 행렬
	static int[][] map;			// 각 칸의 모래 양
	static int sumOutSand;		// 출력, 맵 밖으로 나가는 모래 양
	static int tornadoY, tornadoX;			// 현재 토네이도의 위치

	// 토네이도 이동 방향: 좌하우상
	static int[] dy = { 0, 1, 0, -1 };
	static int[] dx = { -1, 0, 1, 0 };

	// 토네이도 이동 방향에 따른, 이동 칸 수 (좌하우상 1 싸이클 돌고, 각각 2칸 씩 커짐)
	static int[] dd = { 1, 1, 2, 2 };		// { 1, 1, 2, 2 } => { 3, 3, 4, 4 } => { 5, 5, 6, 6 } => ...

	// 토네이도가 날리는 모래 비율
	static double[] spreadRatio = { 0.01, 0.01, 0.07, 0.02, 0.07, 0.02, 0.1, 0.1, 0.05 };

	// 토네이도 이동 방향에 따른, 모래 확산 방향 (예시 y 지점 기준)
	static int[][] dsy = {		// 좌하우상 순서
			{ -1, 1, -1, -2, 1, 2, -1, 1, 0 },		// 1%, 1%, 7%, 2%, 7%, 2%, 10%, 10%, 5%
			{ -1, -1, 0, 0, 0, 0, 1, 1, 2 },
			{ 1, -1, 1, 2, -1, -2, 1, -1, 0 },
			{ 1, 1, 0, 0, 0, 0, -1, -1, -2 }
	};
	static int[][] dsx = {		// 좌하우상 순서
			{ 1, 1, 0, 0, 0, 0, -1, -1, -2 },
			{ -1, 1, -1, -2, 1, 2, -1, 1, 0 },
			{ -1, -1, 0, 0, 0, 0, 1, 1, 2 },
			{ 1, -1, 1, 2, -1, -2, 1, -1, 0 }
	};

	static void solution() {
		while (true) {
			// 좌하우상 한 싸이클
			for (int moveDir = 0; moveDir < 4; moveDir++) {		// 4개 토네이도 이동 방향
				for (int moveCnt = 0; moveCnt < dd[moveDir]; moveCnt++) {		// 각 방향, 규칙에 따라 이동 칸 수
					// 1) 토네이도 이동
					int nTornadoY = tornadoY + dy[moveDir];
					int nTornadoX = tornadoX + dx[moveDir];

					// 토네이도 다음 이동 위치가 맵을 벗어나면([0][0] 왼쪽으로 벗어난 경우), 종료
					if (!isValid(nTornadoY, nTornadoX)) {
						return;
					}

					// 이동한 위치의 모래 날리기
					int nextPosSand = map[nTornadoY][nTornadoX];	// temp 저장
					map[nTornadoY][nTornadoX] = 0;

					int sumSpreadSand = 0;			// 흩날려서 분배된 모래 양
					for (int spreadDir = 0; spreadDir < 9; spreadDir++) {	// 9개 모래 확산 방향
						int spreadY = nTornadoY + dsy[moveDir][spreadDir];
						int spreadX = nTornadoX + dsx[moveDir][spreadDir];
						// 모래가 흩날려서 [spreadY][spreadX] 지점에 더해지는 양
						int spreadAmount = (int) (nextPosSand * spreadRatio[spreadDir]);

						if (!isValid(spreadY, spreadX)) {		// 흩날리는 모래가 맵을 벗어난 경우
							sumOutSand += spreadAmount;
						}
						else {
							map[spreadY][spreadX] += spreadAmount;
						}
						sumSpreadSand += spreadAmount;
					}

					// 알파 지점에 흩날리고 남은 모래 더해주기
					int alphaY = nTornadoY + dy[moveDir];
					int alphaX = nTornadoX + dx[moveDir];
					int alphaSandAmount = nextPosSand - sumSpreadSand;

					if (!isValid(alphaY, alphaX)) {
						sumOutSand += alphaSandAmount;
					}
					else {
						map[alphaY][alphaX] += alphaSandAmount;
					}

					// 현재 토네이도 위치 갱신 (다음 위치로 이동 반영)
					tornadoY = nTornadoY;
					tornadoX = nTornadoX;
				}
			}

			// 이동 칸 수 2칸씩 증가
			for (int i = 0; i < 4; i++) {
				dd[i] += 2;
			}
		}
	}

	static boolean isValid(int ny, int nx) {
		return (0 <= ny && ny < n) && (0 <= nx && nx < n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		map = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 토네이도 시작 위치: 격자 중간 지점
		tornadoY = n / 2;
		tornadoX = n / 2;

		solution();

		System.out.println(sumOutSand);
	}
}

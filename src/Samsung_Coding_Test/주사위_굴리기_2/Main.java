package Samsung_Coding_Test.주사위_굴리기_2;
import java.io.*;
import java.util.*;

/*
- 초기: 주사위 모든 면에 0 적혀짐
- 주사위를 굴림
  > 이동한 칸에 지도에 쓰여져있는 수 == 0 인 경우,
    주사위의 바닥면에 쓰여져있는 수가 칸에 복사됨
  > 이동한 칸에 지도에 쓰여져있는 수 !=0 인 경우,
    지도의 칸에 쓰여져있는 수가 주사위 바닥면에 복사됨 + 지도의 칸에 쓰여져있는 수는 0이 됨
- 주사위가 이동했을 때마다, 주사위 상단에 쓰여지는 값을 출력
*/

/*
1. 아이디어
 - 시뮬레이션, 구현
 - 주사위 각 칸의 값을 배열에 저장
   => 길이 6 int[]
      ex) 문제 주사위 전개도: { 1, 5, 6, 2, 4, 3 }
   ※ 다른 방법: int[4][3]에 주사위 칸 값 저장
     ex) { { 0, 2, 0 },
     	   { 4, 1, 3 },
     	   { 0, 5, 0 },
     	   { 0, 6, 0 } }
 - 주사위 굴리기
   > 구현: 위 / 아래 / 왼쪽 / 오른쪽으로 굴리기
   => 주사위 배열 값 조정

1) 주사위 굴리기: 주사위 배열 값 조정
  - 굴렸을 때 주사위가 map을 벗어나면, 주사위 안굴리고 출력도 안함
2) 주사위 굴린 후, 지도 칸의 값 확인
  - 지도 칸 값 == 0 인 경우, 주사위 바닥면에 쓰여있는 값이 지도 칸에 복사됨
  - 지도 칸 값 != 0 인 경우, 지도 칸 값이 주사위 바닥면으로 복사됨 + 지도 칸 값은 0이 됨
3) 주사위 윗면 값 출력


2. 자료구조
 - int[] diceValue: 길이 6 배열, 주사위 칸 값 저장
*/

public class Main {
	static int n, m;			// n x m 행렬
	static int[][] map;
	static int y, x;			// 주사위 위치
	static int k;				// 명령 개수
	static int[] directions;	// k개 명령 방향 (동서남북 => 1, 2, 3, 4)
	static StringBuilder sb = new StringBuilder();		// 출력

	// [1] ~ [4]: 동서북남(우좌상하), [0]은 사용 X
	static int[] dy = { 0, 0, 0, -1, 1 };
	static int[] dx = { 0, 1, -1, 0, 0 };
	static int[] diceValue = { 0, 0, 0, 0, 0, 0 };	// 주사위 각 칸의 값 (초기: 모든 칸 0)

	static void solution() {
		for (int dir : directions) {
			int ny = y + dy[dir];
			int nx = x + dx[dir];

			// 주사위 굴렸을 때 맵을 벗어나면, 명령 및 출력 수행 X
			if (!isValid(ny, nx))
				continue;

			// 1) 주사위 굴리기: 주사위 배열 값 조정
			if (dir == 1) roleRight();
			else if (dir == 2) roleLeft();
			else if (dir == 3) roleUp();
			else if (dir == 4) roleDown();

			y = ny;			// 주사위 위치 갱신
			x = nx;

			// 2) 주사위 굴린 후, 지도 칸의 값 확인
			if (map[ny][nx] == 0) {
				// 지도 칸 값 == 0 인 경우, 주사위 바닥면에 쓰여있는 값이 지도 칸에 복사됨
				map[ny][nx] = diceValue[2];
			}
			else {
				// 지도 칸 값 != 0 인 경우, 지도 칸 값이 주사위 바닥면으로 복사됨 + 지도 칸 값은 0이 됨
				diceValue[2] = map[ny][nx];
				map[ny][nx] = 0;
			}

			// 3) 주사위 윗면 값 출력
			sb.append(diceValue[0]).append("\n");
		}
	}

	static void roleUp() {
		// [0] ~ [3] left shift
		int temp = diceValue[0];
		diceValue[0] = diceValue[1];
		diceValue[1] = diceValue[2];
		diceValue[2] = diceValue[3];
		diceValue[3] = temp;
	}

	static void roleDown() {
		// [0] ~ [3] right shift
		int temp = diceValue[3];
		diceValue[3] = diceValue[2];
		diceValue[2] = diceValue[1];
		diceValue[1] = diceValue[0];
		diceValue[0] = temp;
	}

	static void roleLeft() {
		// [1], [3] 그대로
		int tempIdx0 = diceValue[0];
		int tempIdx2 = diceValue[2];
		int tempIdx4 = diceValue[4];
		int tempIdx5 = diceValue[5];

		diceValue[0] = tempIdx5;
		diceValue[2] = tempIdx4;
		diceValue[4] = tempIdx0;
		diceValue[5] = tempIdx2;
	}

	static void roleRight() {
		// [1], [3] 그대로
		int tempIdx0 = diceValue[0];
		int tempIdx2 = diceValue[2];
		int tempIdx4 = diceValue[4];
		int tempIdx5 = diceValue[5];

		diceValue[0] = tempIdx4;
		diceValue[2] = tempIdx5;
		diceValue[4] = tempIdx2;
		diceValue[5] = tempIdx0;
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

		y = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		map = new int[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine());
		directions = new int[k];
		for (int i = 0; i < k; i++) {
			directions[i] = Integer.parseInt(st.nextToken());
		}

		solution();

		System.out.println(sb);
	}
}

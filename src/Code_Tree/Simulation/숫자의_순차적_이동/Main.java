package Code_Tree.Simulation.숫자의_순차적_이동;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 1 ~ n^2 숫자들을 차례로 확인
   => 인접한 8개 방향 중, 가장 큰 숫자와 교환 (swap)

2. 자료구조
 - Pair[] pairs: 각 숫자들의 위치
   ex) pairs[num]: 숫자 num의 위치 (y, x)
   => 메모리: 최대 대략 20 x 20 x 8 byte = 32 x 10^2 byte = 3.2 MB

3. 시간 복잡도
 - O(m x n^2)
*/

class Pair {
	public int y, x;

	public Pair(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static int n;				// n x n 행렬, 1 ~ n^2 숫자들
	static int m;				// 턴 수
	static int[][] map;
	static Pair[] pairs;		// pairs[num]: 숫자 num의 위치 (y, x)
	static int[] dy = { -1, 1, 0, 0, -1, 1, 1, -1 };		// 상하좌우 + 대각선 4방향
	static int[] dx = { 0, 0, -1, 1 , 1, 1, -1, -1 };

	static void solution() {
		for (int i = 0; i < m; i++) {
			changeAll();
		}
	}

	/* 1회 턴에서 1 ~ n^2 숫자들을 차례로 위치 교환 */
	static void changeAll() {
		// 1 ~ n^2 숫자들 - num 의 위치 교환
		for (int num = 1; num <= n * n; num++) {
			Pair pNum = pairs[num];		// 숫자 num의 위치
			int maxNum = 0;				// num과 인접한 8방향 위치 중, 가장 큰 위치의 숫자

			for (int i = 0; i < 8; i++) {
				int ny = pNum.y + dy[i];
				int nx = pNum.x + dx[i];

				if (isValid(ny, nx))
					maxNum = Math.max(maxNum, map[ny][nx]);
			}

			// num과 maxNum의 위치 swap
			swap(num, maxNum);
		}
	}

	/* num1과 num2의 위치 swap */
	static void swap(int num1, int num2) {
		Pair pNum1 = pairs[num1];		// 숫자 num1, num2의 위치
		Pair pNum2 = pairs[num2];

		// map[][] swap
		int temp = map[pNum1.y][pNum1.x];
		map[pNum1.y][pNum1.x] = map[pNum2.y][pNum2.x];
		map[pNum2.y][pNum2.x] = temp;

		// pairs[] swap
		Pair pTemp = pairs[num1];
		pairs[num1] = pairs[num2];
		pairs[num2] = pTemp;
	}

	static boolean isValid(int y, int x) {
		return (0 <= y && y < n) && (0 <= x && x < n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new int[n][n];
		pairs = new Pair[n * n + 1];		// [1] ~ [n x n] 사용 (1 ~ n^2 숫자들의 위치)
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				pairs[map[i][j]] = new Pair(i, j);
			}
		}

		solution();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				sb.append(map[i][j]).append(" ");
			sb.append("\n");
		}
		System.out.println(sb);
	}
}

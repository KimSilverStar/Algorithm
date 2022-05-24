package Code_Tree.Test.Q5;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어

2. 자료구조

3. 시간 복잡도
 - O()
*/

public class Main {
	static int n;						// n x n 행렬
	static char[][] map;
	static int startY, startX;			// 시작 위치
	static int time;					// 출력, 탈출 시간 (불가능한 경우 -1)
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void solution() {

	}

	static boolean isValid(int y, int x) {
		return (1 <= y && y <= n) && (1 <= x && x <= n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		startX = Integer.parseInt(st.nextToken());
		startY = Integer.parseInt(st.nextToken());

		map = new char[n + 1][n + 1];		// [1][1] ~ [n][n] 사용
		for (int i = 1; i <= n; i++) {
			String input = br.readLine();
			for (int j = 1; j <= n; j++) {
				map[i][j] = input.charAt(j-1);
			}
		}

		solution();
		System.out.println(time);
	}
}

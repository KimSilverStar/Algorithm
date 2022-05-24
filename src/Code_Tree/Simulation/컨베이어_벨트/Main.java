package Code_Tree.Simulation.컨베이어_벨트;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 2 x n 행렬을 t회 시계 방향으로 shift 수행

2. 자료구조

3. 시간 복잡도
 - O(t x 2 x n) = O(t x n)
*/

public class Main {
	static int n;				// 2 x n 행렬
	static int t;				// t초 후
	static int[][] map;

	static void solution() {
		// t번 회전
		for (int i = 0; i < t; i++)
			rotate();
	}

	static void rotate() {
		int temp1 = map[0][n-1];
		int temp2 = map[1][0];

		// 윗 행
		for (int j = n - 1; j > 0; j--)
			map[0][j] = map[0][j-1];
		map[0][0] = temp2;

		// 아랫 행
		for (int j = 0; j < n - 1; j++)
			map[1][j] = map[1][j + 1];
		map[1][n-1] = temp1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());

		map = new int[2][n];

		st = new StringTokenizer(br.readLine());
		for (int j = 0; j < n; j++)
			map[0][j] = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		for (int j = n - 1; j >= 0; j--)
			map[1][j] = Integer.parseInt(st.nextToken());

		solution();
		StringBuilder sb = new StringBuilder();
		// 윗 행
		for (int j = 0; j < n; j++)
			sb.append(map[0][j]).append(" ");
		sb.append("\n");
		// 아랫 행
		for (int j = n - 1; j >= 0; j--)
			sb.append(map[1][j]).append(" ");
		System.out.println(sb);
	}
}

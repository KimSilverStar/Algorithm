package Code_Tree.Simulation.삼각형_컨베이어_벨트;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 배열 한 행(컨베이어 벨트 1개)씩 오른쪽으로 shift

2. 자료구조
 - int[][] belts: 3개의 컨베이어 벨트
   => belts[0][], belts[1][], belts[2][] 각각 왼쪽, 오른쪽, 아래 컨베이어 벨트

3. 시간 복잡도
 - O(n)
*/

public class Main {
	static int n;				// 길이 n 컨베이어 벨트
	static int t;				// t초 후
	static int[][] belts;		// 3개의 컨베이어 벨트

	static void solution() {
		for (int i = 0; i < t; i++)
			rotate();
	}

	/* 회전 1회 */
	static void rotate() {
		int temp1 = belts[0][n-1];
		int temp2 = belts[1][n-1];
		int temp3 = belts[2][n-1];

		for (int i = 0; i < 3; i++) {
			for (int j = n - 1; j > 0; j--)
				belts[i][j] = belts[i][j-1];
		}

		belts[0][0] = temp3;
		belts[1][0] = temp1;
		belts[2][0] = temp2;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());

		belts = new int[3][n];
		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++)
				belts[i][j] = Integer.parseInt(st.nextToken());
		}

		solution();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < n; j++)
				sb.append(belts[i][j]).append(" ");
			sb.append("\n");
		}
		System.out.println(sb);
	}
}

package Code_Tree.Simulation.떨어지는_1자_블록;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 윗 행부터 아래 행으로 내려가면서, 블록 차지 범위에서 충돌(겹침) 여부 확인
   1) 충돌 안하는 경우
     - 다음 아래 행에서 다시 충돌 여부 확인
   2) 충돌 하는 경우
     - 이전 윗 행(충돌 X)에 블록 배치

2. 자료구조

3. 시간 복잡도
 - O(n x m)
*/

public class Main {
	static int n;			// n x n 행렬
	static int m;			// 1 x m 크기 블록
	static int k;			// 블록 떨어질 열 위치
	static int[][] map;
	static int maxRow;		// 블록 배치할 행 위치 => 윗 행부터 확인

	static void solution() {
		// 윗 행부터 블록 범위 내 충돌 여부 확인
		for (int i = 1; i <= n; i++) {
			if (!isCrashed(i))
				maxRow = Math.max(maxRow, i);
			else
				break;
		}

		// maxRow 행에 블록 배치 => [maxRow][k] ~ [maxRow][k + m - 1] 범위에 배치
		for (int j = k; j <= k + m - 1; j++)
			map[maxRow][j] = 1;
	}

	/* 떨어지는 블록과 [r]행의 블록들의 충돌(겹침) 여부 */
	static boolean isCrashed(int r) {
		// 블록 차지 범위: [k]열 ~ [k + m - 1]열
		for (int j = k; j <= k + m - 1; j++) {
			if (map[r][j] == 1)
				return true;
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		map = new int[n+1][n+1];			// [1][1] ~ [n][n] 확인
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		solution();

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++)
				sb.append(map[i][j]).append(" ");
			sb.append("\n");
		}
		System.out.println(sb);
	}
}

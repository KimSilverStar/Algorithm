package Code_Tree.Simulation.행복한_수열의_개수;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 각 행 / 열을 기준으로, 길이 n인 수열(배열)을 담은 후
   해당 수열에 대해 행복한 수열인지 확인

2. 자료구조

3. 시간 복잡도
 - O(n^2)
*/

public class Main_Faster {
	static int n;				// n x n 행렬 (각 행 or 열 기준), 2n 개 수열
	static int m;				// 연속하여 동일한 숫자 개수
	static int[][] map;
	static int happyCount;		// 출력
	static int[] sequence;		// 길이 n인 수열 => 행복한 수열인지 확인

	static void solution() {
		// 행 기준으로 수열 확인
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				sequence[j] = map[i][j];

			if (isHappySequence())
				happyCount++;
		}

		// 열 기준으로 수열 확인
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++)
				sequence[i] = map[i][j];

			if (isHappySequence())
				happyCount++;
		}
	}

	/* 길이 n인 수열 sequence[]가 행복한 수열인지 확인 */
	static boolean isHappySequence() {
		int sameCount = 1;			// 연속하여 동일한 개수
		int maxSameCount = 1;

		for (int i = 0; i < n - 1; i++) {
			if (sequence[i] == sequence[i+1])
				sameCount++;
			else
				sameCount = 1;

			maxSameCount = Math.max(maxSameCount, sameCount);
		}

		return maxSameCount >= m;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		sequence = new int[n];			// 길이 n 수열
		solution();
		System.out.println(happyCount);
	}
}

package Code_Tree.Simulation.행복한_수열의_개수;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 2중 for문으로 [0][0] ~ [n-1][n-1] 확인
 - 각 [i][j]에 대해 해당 원소부터 시작하는 수열이 행복한 수열인지 확인
 1) 각 행 기준으로 수열 확인
 2) 각 열 기준으로 수열 확인

2. 자료구조

3. 시간 복잡도
 - O(n^3)
*/

public class Main {
	static int n;				// n x n 행렬 (각 행 or 열 기준), 2n 개 수열
	static int m;				// 연속하여 동일한 숫자 개수
	static int[][] map;
	static int happyCount;		// 출력

	static void solution() {
		// 행 기준으로 수열 확인
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (isHappyRow(i, j)) {
					happyCount++;
					break;
				}
			}
		}

		// 열 기준으로 수열 확인
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				if (isHappyCol(i, j)) {
					happyCount++;
					break;
				}
			}
		}
	}

	/* 행복한 수열인지 판단 - 행 기준, [y][x] 지점부터 원소들이 m개 이상 연속한지 확인 */
	static boolean isHappyRow(int y, int x) {
		int sameCount = 1;

		for (int j = x; j < n - 1; j++) {
			if (map[y][j] == map[y][j+1])
				sameCount++;
			else
				break;
		}

		return sameCount >= m;
	}

	/* 행복한 수열인지 판단 - 열 기준, [y][x] 지점부터 원소들이 m개 이상 연속한지 확인 */
	static boolean isHappyCol(int y, int x) {
		int sameCount = 1;

		for (int i = y; i < n - 1; i++) {
			if (map[i][x] == map[i+1][x])
				sameCount++;
			else
				break;
		}

		return sameCount >= m;
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

		solution();
		System.out.println(happyCount);
	}
}

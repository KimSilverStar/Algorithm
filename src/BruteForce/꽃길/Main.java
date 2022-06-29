package BruteForce.꽃길;
import java.io.*;
import java.util.*;

/*
 - n x n 행렬의 [1][1] ~ [n][n] 지점에 3개의 씨앗을 심음
 - 심은 씨앗에서 꽃이 피면, 씨앗 중심으로부터 상하좌우로 꽃잎이 만개함
 - 만개한 꽃잎이 다른 꽃잎과 닿거나, 행렬 범위를 벗어나면 꽃이 죽음
 - 입력 행렬 원소 값 = 화단 대여 비용
 - 3개의 꽃을 심기 위한 최소 비용 구하기
*/

/*
1. 아이디어
 - [1][1] ~ [n][n]의 n^2 개 지점에서 씨앗을 최소 비용으로 심는 3개 지점 선택하기
   => 중복 X, 순서 상관 X 하는 조합 Combination
 - 씨앗을 심을 수 있는 3개 지점 선택 후, cost 계산 및 출력 minCost 갱신
   => 백트래킹: 현재 지점 [y][x] 를 선택 O / X

2. 자료구조
 - long minCost: 출력, 최소 비용
   => 최대 3 x 200^5 = 96 x 10^10 (9천 6백억) >> 21억
   => int 불가능 하므로, long 사용

3. 시간 복잡도
 - 전체 조합 개수: C(n^2, 3)
   => 최대 C(100, 3) = 161,700 << 2억
*/

public class Main {
	static int n;					// n x n 행렬
	static int[][] map;
	static long minCost = Integer.MAX_VALUE;		// 출력, 최소 비용
	static boolean[][] blossoms;					// 만개한 꽃잎 위치 표시
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void solution(int selectedCount) {
		// 씨앗을 심을 수 있는 3개 지점 선택 완료
		if (selectedCount == 3) {
			minCost = Math.min(minCost, calcCost());
			return;
		}

		// 행렬의 가장 바깥 칸에는 씨앗을 심을 수 없음 (꽃잎이 행렬 범위를 벗어남)
		for (int i = 1; i < n - 1; i++) {
			for (int j = 1; j < n - 1; j++) {
				if (canPlant(i, j)) {			// 현재 지점에 심을 수 있는지 확인
					// 1) 현재 지점에 씨앗을 심는 경우
					plant(i, j);
					solution(selectedCount + 1);

					// 2) 현재 지점에 씨앗을 심지 않는 경우 - 씨앗 심기 취소 (백트래킹)
					cancelPlant(i, j);
				}
			}
		}
	}

	/* [y][x] 지점에 씨앗을 심을 수 있는지 확인 - 다른 꽃잎과 겹치는지 확인 */
	static boolean canPlant(int y, int x) {
		if (blossoms[y][x])
			return false;

		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (blossoms[ny][nx])
				return false;
		}

		return true;
	}

	/* [y][x] 지점에 씨앗을 심음 - 5개 지점에 꽃잎 표시 */
	static void plant(int y, int x) {
		blossoms[y][x] = true;

		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			blossoms[ny][nx] = true;
		}
	}

	/* [y][x] 지점에 심은 씨앗을 취소 (되돌림) - 5개 지점에 꽃잎 표시 취소 */
	static void cancelPlant(int y, int x) {
		blossoms[y][x] = false;

		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			blossoms[ny][nx] = false;
		}
	}

	/* 씨앗 3개를 심는 비용 계산 */
	static long calcCost() {
		long cost = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (blossoms[i][j])
					cost += map[i][j];
			}
		}

		return cost;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		blossoms = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		solution(0);
		System.out.println(minCost);
	}
}

package Divide_And_Conquer.풀링_222;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - n x n 행렬에 풀링 한 번 적용 => (n / 2) x (n / 2) 행렬
 - n = 2^k 일 때, n x n 행렬을 1 x 1 로 만들기
   => 풀링 k 번 반복

 - 재귀 함수를 이용한 분할 정복
   1) n x n 행렬에 대해 4등분
     - solution(int startY, int startX, int endY, int endX)
   2) 4등분된 행렬의 크기가 2 x 2 가 되면, 풀링 계산
     - int[] 에 분할된 2 x 2 행렬의 요소 저장 후, 정렬하여 2번째 큰 원소 도출

2. 자료구조
 - int[][]: 행렬
 - int[]: 정렬하여 두 번째 큰 원소 도출 (풀링 계산)

3. 시간 복잡도
 - n 최대 1,024 = 2^10	(k = 10)
   => 풀링 최대 10번 반복
 - 풀링 수행 함수에서 행렬을 4등분 하므로, 재귀 호출 한번에 4번 수행
 ex) 8x8 행렬 (k = 3)을 1x1 행렬로 변환하려면, 풀링 3번 반복
    - 1) 8x8 => 2) 4x4 => 3) 2x2 => 4) 1x1
    1) 8x8 행렬 => 4x4 행렬 4^1 개 => 2x2 행렬 4^2 개
    2) 4x4 행렬 => 2x2 행렬 4^1 개
    3) 2x2 행렬
    => 재귀 호출 횟수: (4^2 + 4^1) + 4^1
 => 최대값 k=10 에 대해, 전체 시간 복잡도
    = (4^10 + 4^9 + ... 4^1) + (4^9 + 4^8 + ... + 4^1) + ... 4^1
*/

public class Main_Recursive {
	static int n;					// 입력 n x n 행렬, n = 2^k
	static int[][] map;
	static int[] arrSort = new int[4];			// 2 x 2 행렬 원소들을 저장 및 정렬하여, 풀링 값 계산

	/* 행렬을 2 x 2 행렬들로 분할하여, 풀링 1번 수행 */
	static void solution(int startY, int startX, int endY, int endX) {
		int size = endX - startX + 1;		// 행렬 크기 n
		if (size == 2) {					// 2 x 2 행렬까지 분할 => 풀링 계산
			int idx = 0;
			for (int i = startY; i <= endY; i++) {
				for (int j = startX; j <= endX; j++)
					arrSort[idx++] = map[i][j];		// 4개의 정수 (2 x 2 행렬) 추가됨
			}

			Arrays.sort(arrSort);
			int maxSecond = arrSort[2];				// 2번째 큰 수
			map[(startY + endY) / 4][(startX + endX) / 4] = maxSecond;

			return;
		}

		// 행렬을 4등분 => 상단 좌측, 상단 우측, 하단 좌측, 하단 우측
		solution(startY, startX, (startY + endY) / 2, (startX + endX) / 2);
		solution(startY, (startX + endX) / 2 + 1, (startY + endY) / 2, endX);
		solution((startY + endY) / 2 + 1, startX, endY, (startX + endX) / 2);
		solution((startY + endY) / 2 + 1, (startX + endX) / 2 + 1, endY, endX);
	}

	static int baseLog(int x, int base) {
		return (int)(Math.log(x) / Math.log(base));
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
			for (int j = 0; j < n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		// n x n 행렬 (n = 2^k)에 대해, 풀링 k 번 반복하여 1 x 1 행렬로 변환
		int k = baseLog(n, 2);
		for (int i = k; i >= 0; i--) {
			int end = (int)Math.pow(2, k) - 1;
			solution(0, 0, end, end);
		}

		System.out.println(map[0][0]);
	}
}

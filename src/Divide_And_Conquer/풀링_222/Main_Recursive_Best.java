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
     - solution(int y, int x, int size)
     - (y, x): 행렬의 상단 좌측 좌표, size: 행렬 크기 (size x size 행렬)
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
    = (4^10 + 4^9 + ... + 4^1) + (4^9 + 4^8 + ... + 4^1) + ... 4^1
      => 4^10 + 4^9 + ... + 4^1 = 1,398,100 이므로,
         (4^10 + 4^9 + ... + 4^1) + (4^9 + 4^8 + ... + 4^1) + ... 4^1 << 1억
*/

public class Main_Recursive_Best {
	static int n;			// n x n 행렬, n = 2^k
	static int[][] map;

	// 4등분 (상단 좌측, 상단 우측, 하단 좌측, 하단 우측) 좌표
	// => { 그대로, 오른쪽, 아래, 오른쪽 대각선 아래 }
	static int[] dy = { 0, 0, 1, 1 };
	static int[] dx = { 0, 1, 0, 1 };

	/* (y, x): 입력 행렬의 상단 좌측의 좌표, size: 입력 행렬의 크기 (size x size 행렬) */
	static void solution(int y, int x, int size) {
		if (size == 2) {		// 2 x 2 행렬까지 분할한 경우 => 풀링 계산
			int[] arrSort = {
					map[y][x], map[y][x + 1],
					map[y + 1][x], map[y + 1][x + 1]
			};
			Arrays.sort(arrSort);

			int maxSecond = arrSort[2];
			map[y / 2][x / 2] = maxSecond;
			return;
		}

		// 파라미터 입력 행렬을 4등분
		for (int i = 0; i < 4; i++) {
			int nSize = size / 2;
			int ny = y + (dy[i] * nSize);
			int nx = x + (dx[i] * nSize);
			solution(ny, nx, nSize);
		}

//		solution(y, x, size / 2);
//		solution(y, x + size / 2, size / 2);
//		solution(y + size / 2, x, size / 2);
//		solution(y + size / 2, x + size / 2, size / 2);
	}

	static int baseLog(int base, int x) {
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

		int size = n;
		int k = baseLog(2, n);			// n = 2^k
		for (int i = 0; i < k; i++) {		// n x n 행렬 => 1 x 1 행렬 만드려면, 풀링 k 번
			solution(0, 0, size);
			size /= 2;
		}

		System.out.println(map[0][0]);
	}
}

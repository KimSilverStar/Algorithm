package Divide_And_Conquer.풀링_222;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - n x n 행렬에 풀링 한 번 적용 => (n / 2) x (n / 2) 행렬
 - n = 2^k 일 때, n x n 행렬을 1 x 1 로 만들기
   => 풀링 k 번 반복

 1) 전체 입력 n x n 행렬을 2 x 2 행렬씩 (가로 세로 2칸씩) 확인
   - [0, 0], [0, 2], ..., [0, n-2], ..., [n-2, n-2]
   - 해당 2 x 2 행렬의 4개 원소에서 2번째 큰 원소만 행렬에 남김
   => n x n 행렬이 (n/2) x (n/2) 행렬이 됨
 2) 위 과정을 k 번 반복 (n == 1이 될때까지 반복)

2. 자료구조
 - int[][]: 행렬
 - int[]: 정렬하여 두 번째 큰 원소 도출 (풀링 계산)

3. 시간 복잡도
 - n 최대 1,024 = 2^10	(k = 10)
   => 풀링 최대 10번 반복
 - n x n 행렬에 대해 총 k 번 재귀 호출 발생		(n = 2^k)
   ex) 8x8 => 4x4 => 2x2 => 1x1
   => 전체 재귀 호출 최대 10번 발생
*/

public class Main_Loop {
	static int n;					// 입력 n x n 행렬, n = 2^k
	static int[][] map;

	/* 행렬을 2 x 2 행렬 단위로 풀링 1번 수행 */
	static void solution(int size) {
		if (size == 1)				// 1 x 1 행렬로 변환 완료
			return;

		for (int i = 0; i < size; i += 2) {
			for (int j = 0; j < size; j += 2) {
				// 2 x 2 행렬 단위로 원소들을 저장 및 정렬하여, 풀링 값 계산
				int[] arrSort = {
						map[i][j], map[i][j + 1], map[i + 1][j], map[i + 1][j + 1]
				};
				Arrays.sort(arrSort);

				map[i / 2][j / 2] = arrSort[2];		// 2번째 큰 원소
				// list.add(arrSort[2]);			// 다른 방법) List 에 arrSort[2] 값 담아둠
			}
		}
		
		// int[][] map 을 풀링 결과 행렬 크기만큼 새로 할당하여, List 에 저장된 풀링 값들 차례로 저장
//		map = new int[size / 2][size / 2];
//		int idx = 0;
//		for (int i = 0; i < size / 2; i++) {
//			for (int j = 0; j < size / 2; j++)
//				map[i][j] = list.get(idX++);
//		}

		solution(size / 2);		// n x n 행렬 => (n/2) x (n/2) 행렬
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

		solution(n);

		System.out.println(map[0][0]);
	}
}

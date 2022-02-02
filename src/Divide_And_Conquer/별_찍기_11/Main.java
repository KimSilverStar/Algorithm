package Divide_And_Conquer.별_찍기_11;
import java.io.*;

/*
1. 아이디어
 - 입력 n 만큼 출력 행
 - 전체 큰 삼각형을 봤을 때, 작은 삼각형 3개로 구성
   => 상단 1개, 하단 좌측 1개, 하단 우측 1개
 - 각 상단, 하단 좌측, 하단 우측의 작은 삼각형들도
   같은 방식으로 각각의 더 작은 삼각형 3개로 구성
 => 재귀 함수를 이용한 분할 정복
 - 재귀 호출 종료 조건: 가장 작은 높이 3 짜리 삼각형 까지 분할한 경우
   => 출력 값 저장

2. 자료구조
 - char[][]: 출력 값 (공백, *) 저장

3. 시간 복잡도
 - 1개의 큰 삼각형이 3개의 작은 삼각형으로 구성
   => 1개의 삼각형에 대해 재귀 호출 3번 수행
 - 시간 복잡도 = O(3^k)
   => k 최대값 10 대입: 3^10 = 59,049 << 1억
*/

public class Main {
	static int n;			// n = 3 x 2^k	(n = 3, 6, 12, 24, 48, ...), (0 <= k <= 10)
	static char[][] map;

	/* h: 삼각형 높이, (y, x): h 짜리 삼각형에서 상단 꼭짓점의 위치 */
	static void solution(int h, int y, int x) {
		// 재귀 종료 조건: 가장 작은 단위의 삼각형 (높이 3) 까지 분할한 경우
		if (h == 3) {
			map[y][x] = '*';

			map[y+1][x-1] = '*';
			map[y+1][x+1] = '*';

			map[y+2][x-2] = '*';
			map[y+2][x-1] = '*';
			map[y+2][x] = '*';
			map[y+2][x+1] = '*';
			map[y+2][x+2] = '*';

			return;
		}

		solution(h / 2, y, x);							// 상단 작은 삼각형
		solution(h / 2, y + h / 2, x - h / 2);		// 하단 좌측 작은 삼각형
		solution(h / 2, y + h / 2, x + h / 2);		// 하단 우측 작은 삼각형
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());
		map = new char[n + 1][n * 2];			// [1][1] ~ [n][n * 2 - 1] 사용
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n * 2 - 1; j++)
				map[i][j] = ' ';
		}

		// 높이 n 짜리 전체 큰 삼각형 => 상단 꼭짓점: (1, n) 위치
		solution(n, 1, n);

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n * 2 - 1; j++)
				sb.append(map[i][j]);
			sb.append("\n");
		}
		System.out.println(sb);
	}
}

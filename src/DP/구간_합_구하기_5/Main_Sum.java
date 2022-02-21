package DP.구간_합_구하기_5;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 입력 행렬을 입력 받으면서, 각 행 마다 누적합을 구함
   => 1행 누적합 원소, 2행 누적합 원소, ..., n행 누적합 원소
   => int[][] sum
 - 영역 (x1, y1) ~ (x2, y2) 의 합
   = (행 x1 ~ x2 의 누적합) - (행 x1 ~ x2 에서 열 1 ~ y1-1 의 누적합)
   = (sum[x1][y2] + ... + sum[x2][y2]) - (sum[x1][y1 - 1] + ... + sum[x2][y1 - 1])

   !!! 주의: (x1, y1), (x2, y2) 에서 x가 행, y가 열

2. 자료구조
 - int[][] map: 입력 행렬
   => 메모리: n x n x 4 byte
   => n 최대값 대입: 2^20 x 4 byte = 4,194,304 byte ~= 4 MB

 - int[][] sum: 각 행마다 누적합
   => sum[i][n] = map[i][1] ~ map[i][n] 까지의 합
   => sum[i][n] 의 최대값 = 10^3 x n
      => 최대 10^3 x 1024 << 21억 이므로, int 가능

 - Area[]: 입력 구간 (x1, y), (x2, y2)

3. 시간 복잡도
 *** 단순히 테스트케이스마다 매번 일일이 구간의 합을 계산하는 경우
     : O(m x n^2)
     => m, n 최대값 대입: 10^5 x 2^20 >> 1억 (시간 초과 발생)

 *** 누적합을 이용하여 구간의 합을 계산하는 경우:
   - 입력 영역 1개에 대해 합 계산: for 문으로 x1 행 ~ x2 행 확인
     = 대충 O(x2 - x1)
     => Worst 로 첫 행 ~ 마지막 행 확인
        = O(n)
   - 입력 영역 m개에 대해 합 계산 = Worst 로 O(n x m)
     => n, m 최대값 대입: 2^10 x 10^5 = 102,400,000
*/

public class Main_Sum {
	static int n;					// n x n 행렬
	static int m;		 			// 합을 구하는 횟수
	static int[][] map;
	static Area[] areas;			// 입력 구간 (x1, y1), (x2, y2)
	static int[][] sum;				// 각 행마다 누적합
	static StringBuilder sb = new StringBuilder();

	static void solution() {
		for (Area area : areas) {
			int result = 0;				// 출력

			for (int i = area.x1; i <= area.x2; i++) {
				// 1. 행 x1 ~ x2 의 누적합
				// = sum[x1][y2] + ... + sum[x2][y2]
				result += sum[i][area.y2];

				// 2. (행 x1 ~ x2 의 누적합) - (행 x1 ~ x2 에서 열 1 ~ y1-1 의 누적합)
				// = (sum[x1][y2] + ... + sum[x2][y2]) - (sum[x1][y1 - 1] + ... + sum[x2][y1 - 1])
				if (area.y1 - 1 >= 1)
					result -= sum[i][area.y1 - 1];
			}

			sb.append(result).append("\n");
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new int[n + 1][n + 1];			// [1][1] ~ [n][n] 사용
		sum = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());

			int temp = 0;
			for (int j = 1; j <= n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());

				temp += map[i][j];
				sum[i][j] = temp;
			}
		}

		areas = new Area[m];
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());

			areas[i] = new Area(x1, y1, x2, y2);
		}

		solution();
		System.out.println(sb);
	}
}

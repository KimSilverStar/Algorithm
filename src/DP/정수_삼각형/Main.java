package DP.정수_삼각형;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) 규칙
   - 맨 위 칸 -> [i][j] 칸 까지 내려올 때, 최대 합
     = 그 이전 대각선 왼쪽 위 or 대각선 오른쪽 위 까지 내려올 때,
       둘 중 최대 합인 칸으로부터 [i][j] 본인을 더한 값
   - 삼각형의 왼쪽, 오른쪽 2개 빗변에 해당하는 칸들은 합이 고정 (정해짐)
     => 빗변을 따라 위 -> 아래로 내려올 때, 윗 칸이 1개이므로

 2) DP 배열 정의: int[][] dp
   - dp[i][j]: 맨 윗 칸 [0][0] ~ 해당 칸 [i][j] 까지 내려올 때, 가능한 최대 합

 3) 점화식
   - 초기식: 왼쪽, 오른쪽 빗변을 윗 칸에서부터 아랫 칸으로의 누적합으로 초기화
     ① 왼쪽 빗변: 행렬의 [0]열
        => [0][0], [1][0], ..., [n-1][0]
     ② 오른쪽 빗변: 행렬의 대각선 칸
        => [0][0], [1][1], ..., [n-1][n-1]
   - dp[i][j] = max(dp[i-1][j-1], dp[i-1][j]) + triangle[i][j]
     => 출력 maxSum = DP 배열 마지막 행의 최대 값

2. 자료구조
 - int[][] triangle: 입력, 삼각형
   => 대각 행렬 형태로 배열 원소 저장
   => 최대 메모리: 4 x 500 x 500 byte = 10^6 byte = 1 MB
 - int maxSum: 출력, 최대 합
   => 각 정수 최대 9999 ~= 10^4
   => 최대 500 x 10^4 = 5 x 10^6 << 21억 이므로, int 가능

3. 시간 복잡도
 - 초기식으로 왼쪽 빗변, 오른쪽 빗변 dp[][] 초기화
   => n 만큼 for 문 반복: O(n)
 - DP: 대충 O(n)
*/

public class Main {
	static int n;					// 삼각형 크기
	static int[][] triangle;
	static int[][] dp;
	static int maxSum = Integer.MIN_VALUE;	// 출력, 맨 윗층 -> 아래 층 내려가면서 최대 합

	static void solution() {
		// 초기식: 왼쪽, 오른쪽 빗변 누적합
		int leftSide = 0;
		int rightSide = 0;

		for (int i = 0; i < n; i++) {
			// 왼쪽 빗변: [0][0], [1][0], ..., [n-1][0]
			leftSide += triangle[i][0];
			dp[i][0] = leftSide;

			// 오른쪽 빗변: [0][0], [1][1], ..., [n-1][n-1]
			rightSide += triangle[i][i];
			dp[i][i] = rightSide;
		}

		for (int i = 2; i < n; i++) {			// 점화식
			for (int j = 1; j < i; j++)
				 dp[i][j] = Math.max(dp[i-1][j-1], dp[i-1][j]) + triangle[i][j];
		}

		// 마지막 행에서 maxSum 찾기
		for (int j = 0; j < n; j++) {
			if (maxSum < dp[n-1][j])
				maxSum = dp[n-1][j];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		triangle = new int[n][n];			// 대각 행렬 형태로 저장
		dp = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j <= i; j++)
				triangle[i][j] = Integer.parseInt(st.nextToken());
		}

		solution();
		System.out.println(maxSum);
	}
}

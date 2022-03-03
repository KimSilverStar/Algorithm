package DP.타일_채우기;
import java.io.*;

/*
1. 아이디어
 1) DP 배열 정의: int[] dp
   - dp[짝수 index]만 사용
   - dp[i]: (3 x i) 크기의 벽을 채우는 경우의 수

 2) 규칙
   - (3 x 홀수) 크기의 벽은 채울 수 없음
     => (3 x 짝수) 크기의 벽만 고려

   ① (3 x 2) 크기의 벽
     => 3개 경우
     => dp[2] = 3
   ② (3 x 4) 크기의 벽
     - (3 x 2) + (3 x 2) 벽으로 구성 = 3 x 3 개
     - (3 x 4) 크기의 벽 "특수 케이스" = 2개
     => (3 x 3) + 2 = 11개 경우
     => dp[4] = (dp[2] x dp[2]) + 2
   ③ (3 x 6) 크기의 벽
     - (3 x 4) + (3 x 2) 벽으로 구성 = 11 x 3 개
     - (3 x 2) + (3 x 4) 벽으로 구성 = 3 x 2 개
       => (3 x 4) + (3 x 2) 벽 구성과 중복 X 해야함
       => 마지막 (3 x 4) 벽을 "2가지의 특수 케이스"로 채움
       => 3 (3 x 2 채우는 경우의 수) x 2 (3 x 4 벽의 특수 케이스)
     - (3 x 6) 크기의 벽 "특수 케이스" = 2개
     => (11 x 3) + (3 x 2) + 2 개 경우
     => dp[6] = (dp[4] x dp[2]) + (dp[2] x 2) + 2
   ④ (3 x 8) 크기의 벽
     - (3 x 6) + (3 x 2) 벽으로 구성 = 41 x 3 개
     - (3 x 4) + (3 x 4) 벽으로 구성 = 11 x 2 개
       => (3 x 6) + (3 x 2) 벽 구성과 중복 X 해야함
       => 마지막 (3 x 4) 벽을 "2가지의 특수 케이스"로 채움
       => 11 (3 x 4 채우는 전체 경우의 수) x 2 (3 x 4 벽의 특수 케이스)
     - (3 x 2) + (3 x 6) 벽으로 구성 = 3 x 2 개
       => (3 x 6) + (3 x 2) 벽 구성과 중복 X 해야함
       => 마지막 (3 x 6) 벽을 "2가지의 특수 케이스"로 채움
       => 3 (3 x 2 벽을 채우는 경우의 수) x 2 (3 x 6 벽의 특수 케이스)
     - (3 x 8) 크기의 벽 "특수 케이스" = 2개
     => (41 x 3) + (11 x 2) + (3 x 2) + 2 개 경우
     => dp[8] = (dp[6] x dp[2]) + (dp[4] x 2) + (dp[2] x 2) + 2

  3) 점화식
    - dp[i] = (dp[i-2] x dp[2]) + (dp[i-4] x 2) + (dp[i-6] x 2)
    		  + ... + (dp[2] x 2) + 2


2. 자료구조
 - int[] dp


3. 시간 복잡도
 - 2중 for 문 (n / 2)^2 만큼 반복: 대략 O(n^2)
   => n 최대값 대입: 30^2 = 900 << 2억
*/

public class Main {
	static int n;			// (3 x n) 크기 벽
	static int count;		// 출력, 벽 채우는 경우의 수
	static int[] dp;

	static void solution() {
		dp[2] = 3;			// 초항: (3 x 2) 크기의 벽

		for (int i = 4; i <= n; i += 2) {
			// dp[i] = (dp[i-2] x dp[2])
			//		   + (dp[i-4] x 2) + (dp[i-6] x 2) + ... + (dp[2] x 2)
			//		   + 2

			dp[i] += (dp[i - 2] * dp[2]);		// (3 x i-2) + (3 x 2) 로 채우는 모든 경우의 수
			for (int j = i - 4; j >= 2; j -= 2)	// 중복을 제거하고 채우는 경우의 수
				dp[i] += (dp[j] * 2);
			dp[i] += 2;		// (3 x i) 크기의 벽을 채우는 특수 케이스 2개
		}

		count = dp[n];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());
		dp = new int[n + 1];

		if (n % 2 != 0)				// (3 x 홀수) 크기의 벽 => 못 채움
			System.out.println(0);
		else {
			solution();
			System.out.println(count);
		}
	}
}

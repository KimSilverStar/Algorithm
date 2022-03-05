package DP.동전_1;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) DP 배열 정의: int[][] dp
   - dp[i][j]: 첫 번째 ~ [i] 번째 동전으로 목표 가치 합 j 를 만드는 경우의 수
   - [0]행, [0]열은 패딩

 2) 규칙 및 점화식
  ① [i] 번째 동전을 사용할 수 없는 경우 (coins[i] > 가치 합 j)
    - [i] 번째 동전을 사용하지 않고,
      첫 번째 ~ [i-1] 번째 동전들로 가치 합 j 를 만드는 경우의 수와 동일
    => dp[i][j] = dp[i-1][j]
  ② [i] 번째 동전을 사용할 수 있는 경우 (coins[i] <= 가치 합 j)
    - [i] 번째 동전을 사용하지 않고,
      첫 번째 ~ [i-1] 번째 동전들로 가치 합 j 를 만드는 경우의 수
      => dp[i-1][j]
    - [i] 번째 동전을 사용하여,
      첫 번째 ~ [i] 번째 동전들로 가치 합 j 를 만드는 경우의 수
      => [i] 번째 동전을 1개 사용하고 남은 금액을
         첫 번째 ~ [i] 번째 동전들로 채움
      => dp[i][j - coins[i]]
    => dp[i][j] = dp[i-1][j] + dp[i][j - coins[i]]

  - 점화식
    if (coins[i] > j)
    	dp[i][j] = dp[i-1][j]
   	else
   		dp[i][j] = dp[i-1][j] + dp[i][j - coins[i]]
  - 초기식: [0]열인 dp[i][0] = 1


2. 자료구조
 - int count: 출력, 경우의 수
   => 2^31 미만 이므로, int 가능
 - int[][] dp
   => 메모리: 4 x 10^2 x 10^4 byte = 4 x 10^6 byte = 4 MB >= 4 MB
   => 메모리 초과 발생


3. 시간 복잡도
 - 2중 for 문 n x k 만큼 반복: O(nk)
   => n, k 최대값 대입: 10^2 x 10^4 = 10^6 << 0.5억
*/

public class Main_Memory_Over {
	static int n, k;			// 동전 종류 개수 n, 목표 동전 가치 합 k
	static int[] coins;
	static int count;			// 출력, 경우의 수
	static int[][] dp;

	static void solution() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= k; j++) {
				if (coins[i] > j)
					dp[i][j] = dp[i-1][j];
				else
					dp[i][j] = dp[i-1][j] + dp[i][j - coins[i]];
			}
		}

		count = dp[n][k];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		coins = new int[n + 1];
		dp = new int[n + 1][k + 1];			// [0]행, [0]열은 패딩
		for (int i = 1; i <= n; i++) {
			coins[i] = Integer.parseInt(br.readLine());
			dp[i][0] = 1;					// 초기식: [0]열은 1
		}

		solution();
		System.out.println(count);
	}
}

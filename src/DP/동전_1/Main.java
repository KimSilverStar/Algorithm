package DP.동전_1;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) DP 배열 정의: int[] dp
   - dp[j]: 목표 가치 합 j 를 만드는 경우의 수
     => 첫 번째 동전 ~ 마지막 동전 까지 차례로 확인해가면서 갱신하므로,
        2차원 배열 안쓰고 1차원 배열로 가능

 2) 규칙 및 점화식
  ① [i] 번째 동전을 사용할 수 없는 경우 (coins[i] > 가치 합 j)
    - [i] 번째 동전을 사용하지 않고,
      첫 번째 ~ [i-1] 번째 동전들로 가치 합 j 를 만드는 경우의 수와 동일
    => dp[j] = dp[j]	(이전 상태 값 그대로)
  ② [i] 번째 동전을 사용할 수 있는 경우 (coins[i] <= 가치 합 j)
    - [i] 번째 동전을 사용하지 않고,
      첫 번째 ~ [i-1] 번째 동전들로 가치 합 j 를 만드는 경우의 수
      => dp[j]			(이전 상태 값에서 변화 X)
    - [i] 번째 동전을 사용하여,
      첫 번째 ~ [i] 번째 동전들로 가치 합 j 를 만드는 경우의 수
      => [i] 번째 동전을 1개 사용하고 남은 금액을
         첫 번째 ~ [i] 번째 동전들로 채움
      => dp[j - coins[i]]

  - 점화식
    dp[j] = dp[j] + dp[j - coins[i]]	(coins[i] <= 가치 합 j)
  - 초기식: dp[0] = 1


2. 자료구조
 - int count: 출력, 경우의 수
   => 2^31 미만 이므로, int 가능
 - int[] dp
   => 메모리: 4 x 10^4 byte = 40 KB <= 4 MB


3. 시간 복잡도
 - 2중 for 문 n x k 만큼 반복: O(nk)
   => n, k 최대값 대입: 10^2 x 10^4 = 10^6 << 0.5억
*/

public class Main {
	static int n, k;			// 동전 종류 개수 n, 목표 동전 가치 합 k
	static int[] coins;
	static int count;			// 출력, 경우의 수
	static int[] dp;

	static void solution() {
		// dp[j] = dp[j] + dp[j - coins[i]]		(가치 합 j >= coins[i])
		for (int i = 1; i <= n; i++) {
			for (int j = coins[i]; j <= k; j++)
				dp[j] += dp[j - coins[i]];
		}

		count = dp[k];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		coins = new int[n + 1];			// [1] ~ [n] 사용
		for (int i = 1; i <= n; i++)
			coins[i] = Integer.parseInt(br.readLine());

		dp = new int[k + 1];			// [0]은 패딩
		dp[0] = 1;						// 초기식
		solution();
		System.out.println(count);
	}
}

package Code_Tree.DP.동전_거슬주기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) DP 배열 정의: int[] dp
   - dp[i]: i원을 만드는 데 필요한 동전 최소 개수
   - 출력: dp[m]

 2) 규칙 및 점화식
   - i원을 만든다
     = i원에서 coins[j]을 제외한 금액에서, coins[j]를 추가하여 만듦
   - dp[i] = min(dp[i - coins[j]] + 1)
   ① 1 <= j < i	(coins[1] 부터 사용)
   ② i >= coins[j]
   => 2중 for 문


2. 자료구조
 - int[] dp: DP 배열


3. 시간 복잡도
 - 상태 개수: DP 배열 원소 개수 = m
 - m개 상태를 모두 채우는 데 걸리는 시간: O(m x n)
*/

public class Main {
	static int n, m;			// 동전 종류 n개, 목표 합 m원
	static int[] coins;			// 각 n개 동전
	static int[] dp;
	static int minCount;		// 출력, 최소 동전 개수 (불가능하면 -1)
	static final int INF = 10_001;

	static void solution() {
		for (int i = 1; i <= m; i++)
			dp[i] = INF;

		dp[0] = 0;			// 초기식

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (i >= coins[j])
					dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
			}
		}

		if (dp[m] != INF)
			minCount = dp[m];
		else
			minCount = -1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());

		coins = new int[n + 1];			// [1] ~ [n] 사용
		dp = new int[m + 1];			// [1] ~ [m] 사용
		for (int i = 1; i <= n; i++)
			coins[i] = Integer.parseInt(st.nextToken());

		solution();
		System.out.println(minCount);
	}
}

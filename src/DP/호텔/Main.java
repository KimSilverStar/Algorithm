package DP.호텔;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 적어도 c명 영업 => c명, c+1명, ..., c+100명
   (1개 도시에서 x원으로 영업하는 최대 고객 수 = 100명)
 - 적어도 c명을 늘리기 위한 최소 금액
   => c명, c+1명, ..., c+100명 늘리는 최소 금액에서 최소값
 - 적어도 i명을 늘리기 위한 최소 금액
   => i명, i+1명, ..., i+100명 늘리는 최소 금액에서 최소값

 1) DP 배열 정의: int[] dp
   - dp[i]: 고객을 i명 만큼 늘릴 때, 최소 비용

 2) 규칙 및 점화식
   - 고객을 적어도 i명 늘릴 때의 최소 비용
     = dp[i] ~ dp[i + 100] 중, 최소값
     => 각 도시에서 홍보할 때 늘리는 고객 수 customer에 대해,
        dp[customer] ~ dp[customer + 100] 채워나감
   - 고객을 i명 만큼 늘릴 때의 최소 비용 dp[i]
     => 이번 도시에서 홍보 O or X 2가지 경우

   ① 현재 도시에서 홍보 O하는 경우
     - dp[i] = dp[i - customer] + cost
       => 이전 도시들에서 홍보하여 (i - customer) 고객 수 만큼 늘렸을 때의 최소 비용
          + 현재 도시에서 홍보하여 customer 고객 수 만큼 늘렸을 때의 홍보 비용 cost
   ② 현재 도시에서 홍보 X하는 경우
     - dp[i] = dp[i]
       (이전 도시들의 조합으로 홍보하여 고객 수 늘린 경우)
   => dp[i] = min( dp[i], dp[i - customer] + cost )

   - 출력 값 적어도 c명 늘리기 위한 최소 비용
     = dp[c] ~ dp[c + 100] 중, 최소값


2. 자료구조
 - int[] dp: DP 배열


3. 시간 복잡도
 - DP 배열 채우기: 대략 O(n x (c+100))
   => n 최대값 대입: 200 x 1,100 = 22 x 10^4
 - 출력 최소값 찾기: O(100)
 => 총 시간 복잡도: 대략 22 x 10^4 << 2억
*/

public class Main {
	static int c, n;			// 최소 영업 고객 수 c, 도시 개수 n
	static int INF;
	static int[] dp;			// dp[i]: 고객 i명 만큼 늘릴 때, 최소 비용
	static int minCost = Integer.MAX_VALUE;		// 출력, 최소 비용

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		c = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());

		INF = (c + 100) * 100;		// 비용 최대값
		dp = new int[c + 101];
		Arrays.fill(dp, INF);
		dp[0] = 0;					// 초기식: 고객 0명 늘릴 때의 최소 비용 = 0원

		for (int city = 0; city < n; city++) {
			// 각 도시에서 홍보 비용 cost, 늘어나는 고객 수 customer
			st = new StringTokenizer(br.readLine());
			int cost = Integer.parseInt(st.nextToken());
			int customer = Integer.parseInt(st.nextToken());

			for (int i = customer; i <= c + 100; i++)
				dp[i] = Math.min(dp[i], dp[i - customer] + cost);
		}

		// minCost 찾기 => dp[c] ~ dp[c + 100] 중, 최소값
		for (int i = c; i <= c + 100; i++) {
			if (minCost > dp[i])
				minCost = dp[i];
		}

		System.out.println(minCost);
	}
}

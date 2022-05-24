package Code_Tree.DP.피보나치_수;
import java.io.*;

public class Main {
	static int n;
	static int result;		// 출력, n번째 피보나치 수
	static int[] dp;

	static void solution() {
		if (n <= 2) {
			result = 1;
			return;
		}

		// 초기식
		dp[1] = 1;
		dp[2] = 1;

		// 점화식
		for (int i = 3; i <= n; i++)
			dp[i] = dp[i-1] + dp[i-2];

		result = dp[n];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());

		dp = new int[n + 1];		// [1] ~ [n] 사용
		solution();

		System.out.println(result);
	}
}

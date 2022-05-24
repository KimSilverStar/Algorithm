package Code_Tree.DP.최대_증가_부분_수열;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) DP 배열 정의: int[] dp
   - dp[i]: 마지막으로 고른 원소 위치가 [i]인 부분 수열 중,
     		최장 증가 부분 수열의 길이
   - 출력: dp[i] 값 중, 최대값

 2) 규칙 및 점화식
   - dp[i] = max(dp[j] + 1)		(j: i 이전 index)
   ① 0 <= j < i
      => 2중 for 문 사용
   ② numbers[j] < numbers[i]	(증가 부분 수열)


2. 자료구조
 - int[] dp: DP 배열


3. 시간 복잡도
 - O(n^2)
 ① 상태 개수: n개 (dp[] 배열 길이)
 ② 각 상태 dp[i]를 채우는 데 걸리는 시간: O(n) (for 문)
*/

public class Main {
	static int n;				// 숫자 개수
	static int[] numbers;
	static int[] dp;
	static int maxLen;			// 출력, 최장 증가 부분 수열의 길이

	static void solution() {
		dp[0] = 0;				// 패딩

		for (int i = 1; i <= n; i++) {
			for (int j = 0; j < i; j++) {
				if (numbers[j] < numbers[i])
					dp[i] = Math.max(dp[i], dp[j] + 1);
			}
		}

		for (int i = 1; i <= n; i++)
			maxLen = Math.max(maxLen, dp[i]);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		numbers = new int[n + 1];			// [1] ~ [n] 사용
		dp = new int[n + 1];				// [0] 원소는 패딩
		for (int i = 1; i <= n; i++) {
			numbers[i] = Integer.parseInt(st.nextToken());
			dp[i] = Integer.MIN_VALUE;
		}

		solution();
		System.out.println(maxLen);
	}
}

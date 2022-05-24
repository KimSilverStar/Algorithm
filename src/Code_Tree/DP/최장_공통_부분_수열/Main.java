package Code_Tree.DP.최장_공통_부분_수열;
import java.io.*;

/*
1. 아이디어
 1) DP 배열 정의: int[][] dp
   - dp[i][j]: str1[i] 까지의 문자열과 str2[j] 까지의 문자열에서, LCS 길이
   - 출력: dp[len1][len2]

 2) 규칙 및 점화식
   - 2중 for문으로 str1.charAt(i), str2.charAt(j) 비교
   ① str1.charAt(i) != str2.charAt(j) 인 경우
     - dp[i][j] = max(dp[i-1][j], dp[i][j-1])
   ② str1.charAt(i) == str2.charAt(j) 인 경우
     - dp[i][j] = dp[i-1][j-1] + 1


2. 자료구조
 - int[][] dp: DP 배열


3. 시간 복잡도
 - 상태 개수: DP 배열 원소 개수 = len1 x len2
 - 상태를 채우는 데 걸리는 시간: 2중 for 문으로 DP 배열 원소 모두 채움
   (dp[i][j] 원소 1개 채우는 데 O(1))
 => O(n^2)
*/

public class Main {
	static String str1, str2;		// 입력 문자열
	static int maxLen;				// 출력, 최장 공통 부분 문자열의 길이
	static int[][] dp;

	static void solution() {
		for (int i = 1; i <= str1.length(); i++) {
			for (int j = 1; j <= str2.length(); j++) {
				if (str1.charAt(i-1) != str2.charAt(j-1))
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				else
					dp[i][j] = dp[i-1][j-1] + 1;
			}
		}

		maxLen = dp[str1.length()][str2.length()];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		str1 = br.readLine();
		str2 = br.readLine();

		// [1][1] ~ [len1][len2] 사용, [0]행과 [0]열은 패딩
		dp = new int[str1.length() + 1][str2.length() + 1];

		solution();
		System.out.println(maxLen);
	}
}

package DP.LCS_2;
import java.io.*;

/*
1. 아이디어
 1) DP 배열 정의: String[][] dp
   - dp[i][j]: str1[i] 문자까지와 str2[j] 문자까지에 대한 LCS 문자열
   - 출력, LCS 문자열: dp[str1.length()][str2.lenength()]

 2) 규칙 및 점화식
   - 2중 for문으로 str1의 각 문자, str2의 각 문자 확인 비교
   ① str1[i] 문자 != str2[j] 문자인 경우
     - dp[i][j] = max(dp[i-1][j], dp[i][j-1])
       => 윗 칸 or 왼쪽 칸 중, 길이가 더 큰 문자열
   ② str1[i] 문자 == str2[j] 문자인 경우
     - dp[i][j] = dp[i-1][j-1] + str1[i]
       => dp[i-1][j-1]의 문자열에 동일 문자 str1[i] 추가


2. 자료구조
 - String[][] dp: DP 배열
   ※ 메모리: 대략 최대 (str1 최대 길이) x (str2 최대 길이) x (LCS 문자열 최대 길이) byte
     => 10^3 x 10^3 x 10^3 byte = 10^9 byte = 10^3 MB >> 256 MB
     => 메모리 초과 !!!


3. 시간 복잡도
 - DP 배열 채우기: O(len1 x len2)	(두 문자열 길이의 곱)
   => 문자열 길이 최대값 대입: (10^3)^2 = 10^6 << 1억
*/

public class Main_Memory_Over {
	static String str1, str2;
	static String LCS;
	static String[][] dp;

	static void solution() {
		for (int i = 1; i <= str1.length(); i++) {
			for (int j = 1; j <= str2.length(); j++) {
				// str1[i] 문자와 str2[j] 문자가 같은 경우
				if (str1.charAt(i-1) == str2.charAt(j-1)) {
					if (dp[i-1][j-1] != null)
						 dp[i][j] = dp[i-1][j-1] + str1.charAt(i-1);
					else
						dp[i][j] = String.valueOf(str1.charAt(i-1));
				}
				// str1[i] 문자와 str2[j] 문자가 다른 경우
				else {
					dp[i][j] = getMaxString(dp[i-1][j], dp[i][j-1]);
				}
			}
		}

		LCS = dp[str1.length()][str2.length()];
	}

	static String getMaxString(String s1, String s2) {
		if (s1 == null)
			return s2;
		else if (s2 == null)
			return s1;

		return s1.length() > s2.length() ? s1 : s2;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		str1 = br.readLine();
		str2 = br.readLine();

		// [0]행 [0]열은 패딩, [1][1] ~ [len1][len2] 사용
		dp = new String[str1.length() + 1][str2.length() + 1];
		solution();

		StringBuilder sb = new StringBuilder();
		if (LCS != null) {
			sb.append(LCS.length()).append("\n")
					.append(LCS);
		}
		else {
			sb.append("0");
		}
		System.out.println(sb);
	}
}

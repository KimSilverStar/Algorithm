package DP.LCS_2;
import java.io.*;

/*
1. 아이디어
 1) DP 배열 정의: int[][] dp
   - dp[i][j]: str1의 [i]번째 문자까지와 str2의 [j]번째 문자까지에 대한 LCS 길이

 2) 규칙 및 점화식
   - 2중 for문으로 str1의 각 문자, str2의 각 문자 확인 비교
   ① str1[i] 문자와 str2[j] 문자가 다른 경우
     - dp[i][j] = max(dp[i-1][j], dp[i][j-1])
       => 윗 칸 or 왼쪽 칸 중, 더 긴 문자열의 길이
   ② str1[i] 문자와 str2[j] 문자가 같은 경우
     - dp[i][j] = dp[i-1][j-1] + 1
       => dp[i-1][j-1]의 문자열에 동일 문자 str1[i] 추가

 ※ 최종 LCS 문자열 구하기
  - 마지막 칸 dp[len1][len2]에서 시작하여, 거슬러 올라가면서 확인
  ① 현재 칸과 윗 칸 or 왼쪽 칸이 LCS 길이(dp 배열 원소) 같은 경우
    => "LCS 길이에 변화가 없다" == "LCS 문자열에 추가된 동일 문자가 없다"
	=> 해당 윗 칸 or 왼쪽 칸으로 이동
  ② 현재 칸과 윗 칸, 왼쪽 칸이 LCS 길이 다른 경우 (LCS 길이 감소하는 경우)
    => "현재 칸에 비해 윗 칸, 왼쪽 칸의 LCS 길이가 줄어들었다"
       == "이전 윗 칸 or 왼쪽 칸으로부터 LCS 문자열에 추가된 동일 문자가 있다"
    => 현재 칸에 해당하는 문자를 추가하고, 왼쪽 위 대각선 칸으로 이동
  => 추적한 LCS 문자열은 역순으로 저장됨


2. 자료구조
 - int[][] dp: DP 배열
   ① 자료형: dp 배열 원소 최대값 10^3 << 21억 이므로, int 가능
   ② 메모리: 최대 4 x 10^3 x 10^3 byte = 4 x 10^6 byte = 4 MB


3. 시간 복잡도
 - DP 배열 채우기: O(len1 x len2)	(두 문자열 길이의 곱)
   => 문자열 길이 최대값 대입: (10^3)^2 = 10^6 << 1억
*/

public class Main {
	static String str1, str2;
	static int maxLength;			// LCS 최대 길이
	static StringBuilder LCS = new StringBuilder();
	static int[][] dp;

	static void solution() {
		// 1. DP 배열 채우기
		for (int i = 1; i <= str1.length(); i++) {
			for (int j = 1; j <= str2.length(); j++) {
				// str1[i] 문자와 str2[j] 문자가 같은 경우
				if (str1.charAt(i-1) == str2.charAt(j-1))
					dp[i][j] = dp[i-1][j-1] + 1;
				// str1[i] 문자와 str2[j] 문자가 다른 경우
				else
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
			}
		}

		maxLength = dp[str1.length()][str2.length()];

		if (maxLength == 0)
			return;

		// 2. DP 배열(각 LCS 길이)에서 최종 LCS 구하기
		// 마지막 칸 dp[len1][len2]에서 시작하여, 거슬러 올라가면서 확인
		int i = str1.length();
		int j = str2.length();

		while (i != 0 && j != 0) {
			// 현재 칸과 윗 칸 or 왼쪽 칸이 LCS 길이(dp 배열 원소) 같은 경우
			if (dp[i][j] == dp[i-1][j])
				i--;
			else if (dp[i][j] == dp[i][j-1])
				j--;
			// 현재 칸과 윗 칸, 왼쪽 칸이 LCS 길이 다른 경우 (LCS 길이 감소하는 경우)
			else {
				LCS.append(str1.charAt(i-1));
				i--;
				j--;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		str1 = br.readLine();
		str2 = br.readLine();

		// [0]행 [0]열은 패딩, [1][1] ~ [len1][len2] 사용
		dp = new int[str1.length() + 1][str2.length() + 1];
		solution();

		System.out.println(maxLength);
		if (maxLength > 0)
			System.out.println(LCS.reverse());		// 역순
	}
}

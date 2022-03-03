package DP.LCS;
import java.io.*;

/* LCS (Longest Common Subsequence, 최장 공통 부분 수열)
 - 부분 문자열 순서 상관 O
 - 연속 X 해도 가능
*/

/*
1. 아이디어
 - 각 입력 문자열 최대 길이: 10^3
   => O(len^2) 까지 시간 복잡도 가능
   => 2중 for 문으로 2개 문자열의 각 문자들 차례로 비교

 - 행렬에 두 문자열 str1, str2 의 각 부분 문자열 별 LCS 길이 채워나감
   => 행: str1 의 각 문자
   => 열: str2 의 각 문자
   => 행렬의 각 칸: 공통 부분 수열의 길이
 - 한 행씩 맨 앞 문자부터 차례로 비교하여 부분 수열의 길이를 누적해 나감

 1) 규칙
   - 한 행씩 "행의 문자" str1.charAt() vs "열의 문자" str2.charAt() 를 비교
   ① 두 문자가 같은 경우
     - 행렬 칸 [i][j] 을 대각선 왼쪽 윗 칸 [i-1][j-1] 의 값 + 1 로 채움
   ② 두 문자가 다른 경우
     - 행렬 칸 [i][j] 을 그 이전 칸에 해당하는
       윗 칸 [i-1][j] or 왼쪽 칸 [i][j-1] 중에서 더 큰 값으로 채움

   ※ ① 에서 두 문자가 같은 경우, 대각선 왼쪽 위 칸 + 1 값으로 채우는 이유
    - "두 문자가 같다" == 두 문자열 str1, str2 의 각 부분 수열에 대해 공통 원소가 추가됨
    ex) 예제에서 "ACAYKP" 의 부분 수열 { A, C } 와 "CAPCAK" 의 부분 수열 { C } 에서
        같은 문자 A 가 나타남
        => { A, C, A }, { C, A }
        => 기존 { A, C } 와 { C } 의 공통 부분수열 길이 1 에서,
            + 1 을 한 2 가 해당 칸을 채우게 됨

 2) DP 배열 정의: int[][] dp
   - dp[i][j]: 문자열 str1 의 [i] 번째 까지의 부분 수열과
   			   문자열 str2 의 [j] 번째 까지의 부분 수열을 비교했을 때, 공통 부분 수열의 길이
   - DP 배열의 [0]행, [0]열에 패딩을 주어 0 값으로 채움

 3) 점화식
   ① 두 문자가 같은 경우
     - dp[i][j] = dp[i-1][j-1] + 1
   ② 두 문자가 다른 경우
     - dp[i][j] = max(dp[i-1][j], dp[i][j-1])

2. 자료구조
 - int[][] dp
   => 메모리 최대 4 x 1000 x 1000 byte
      = 4 x 10^6 byte = 4 MB

3. 시간 복잡도
 - 대략 n x n 만큼 2중 for 문 반복: O(n^2)
   => n 최대값 대입: (10^3)^2 = 10^6 << 1억
*/

public class Main {
	static char[] str1;
	static char[] str2;
	static int len1, len2;		// str1, str2 의 길이

	static int maxLen;			// 출력
	static int[][] dp;

	static void solution() {
		for (int i = 1; i <= len1; i++) {
			// 한 행 기준
			for (int j = 1; j <= len2; j++) {
				if (str1[i - 1] == str2[j - 1])			// 두 문자가 같은 경우
					dp[i][j] = dp[i-1][j-1] + 1;		// 왼쪽 대각선 윗 칸의 값 + 1
				else
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
					// 윗 칸 or 왼쪽 칸 중, 더 큰 값
			}
		}

		maxLen = dp[len1][len2];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		str1 = br.readLine().toCharArray();
		str2 = br.readLine().toCharArray();

		len1 = str1.length;
		len2 = str2.length;

		dp = new int[len1 + 1][len2 + 1];		// [0]행, [0]열에 각각 패딩 (0 값)

		solution();
		System.out.println(maxLen);
	}
}

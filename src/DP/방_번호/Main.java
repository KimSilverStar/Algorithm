package DP.방_번호;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) DP 배열 정의: String[] dp
   - dp[cost]: cost원 금액 내로 만들 수 있는 최대 숫자 문자열

 2) 규칙 및 점화식
   - 초기식: 입력 costs[]의 각 원소 cost에 대해, dp[cost] = String(i)
   - 1원 ~ m원까지 각 금액으로 만들 수 있는 최대 숫자 문자열로 dp[] 채우기
   - dp[totalCost] = max(dp[totalCost], dp[totalCost - cost] + String(i))
     => dp[totalCost - cost] + String(i)
        : totalCost 금액에서 cost 금액에 해당하는 숫자 String(i)를 새로 구입하여, 이어붙임

 => 출력, 최대 숫자: dp[m]


2. 자료구조
 - String[] dp: DP 배열
 ※ int[] dp, long[] dp 사용 못하는 이유
   => 자료형: dp[]의 원소가 최대 9999...999 (숫자 9가 50개) 이므로,
      정수 자료형은 스택 오버플로우 발생


3. 시간 복잡도
 - DP 채우기: O(n x m)
   => n, m 최대값 대입: 10 x 50 = 500 << 2억
*/

public class Main {
	static int n;					// 0 ~ (n-1) 방 번호 숫자
	static int[] costs;				// 각 방 번호 숫자의 비용
	static int m;					// 전체 보유 금액
	static String maxNumStr;
	static String[] dp;
	static String[] digitStr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	static String getMaxStr(String str1, String str2) {
		if (str1 == null)
			return str2;
		if (str2 == null)
			return str1;

		if (str1.length() > str2.length())
			return str1;
		else if (str1.length() < str2.length())
			return str2;

		if (str1.compareTo(str2) > 0)
			return str1;
		else
			return str2;

//		for (int i = 0; i < str1.length(); i++) {
//			if (str1.charAt(i) > str2.charAt(i))
//				return str1;
//			else if (str1.charAt(i) < str2.charAt(i))
//				return str2;
//		}
//		return str1;
	}

	static void solution() {
		// 초기식: 입력 cost[]로 초기화
		// - 숫자 "0"의 비용 costs[0]은 제외, costs[1] 부터 dp[] 채움
		// => "02" 같은 Leading Zero 를 제외하고 dp[]에 저장하기 위함
		for (int i = 1; i < n; i++) {
			for (int totalCost = costs[i]; totalCost <= m; totalCost++)
				dp[totalCost] = getMaxStr(dp[totalCost], digitStr[i]);
		}

		// DP 채우기: totalCost 금액으로 만들 수 있는 최대 숫자
		for (int totalCost = 1; totalCost <= m; totalCost++) {
			for (int i = 0; i < n; i++) {
				int cost = costs[i];

				if (totalCost < cost)		// totalCost 금액으로 못 사는 경우 (돈 부족)
					continue;

				if (dp[totalCost - cost] == null)
					continue;

				dp[totalCost] = getMaxStr(
						dp[totalCost], dp[totalCost - cost] + digitStr[i]
				);
			}
		}

		if (dp[m] == null)		// 숫자 "0" 만 살 수 있는 경우
			maxNumStr = "0";
		else
			maxNumStr = dp[m];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		costs = new int[n];
		for (int i = 0; i < n; i++)
			costs[i] = Integer.parseInt(st.nextToken());

		m = Integer.parseInt(br.readLine());
		dp = new String[m + 1];

		solution();
		System.out.println(maxNumStr);
	}
}

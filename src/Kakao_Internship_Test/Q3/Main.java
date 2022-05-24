package Kakao_Internship_Test.Q3;

/*
1. 아이디어
 - 모든 문제들을 풀 수 있는 알고력, 코딩력을 얻는 "최단 시간" 구하기
   => 문제 배열에서, 요구 최대 알고력과 코딩력 찾기
 - 알고력, 코딩력을 높이기 위한 방법 2가지
   ① 공부: 시간 n 소비하여, 알고력 or 코딩력 n 증가
   ② 풀이 가능한 문제 n번 풀기 (n >= 1)

 1) DP 배열 정의: int[][] dp
   - dp[algo][coding]: 현재 [알고력], [코딩력]에 도달하기 위한 최소 시간
   - 출력: dp[문제 최대 algo][문제 최대 coding]

 2) 규칙 및 점화식
   - dp[i][j] 1칸을 채우는 데, 모든 문제 problems[k] 확인
   - 초기식: dp[초기 algo][초기 coding] = 0

   - 점화식
   ① problmes[k] 문제를 풀 수 있는 경우
     - 해당 문제를 풀어서, 알고력 & 코딩력 [i][j] 도달 or 공부를 하여 도달
     - dp[i][j] = min(dp[i][j],
     				  dp[i - algoRwd][j - codingRwd] + cost,
     				  dp[i-1][j] + 1, dp[i][j-1] + 1)
       (algoRwd = problems[k][2], codingRwd = problems[k][3], cost = problems[k][4])

   ② problems[k] 문제를 풀 수 없는 경우
     - 공부를 하여, 알고력 & 코딩력 [i][j] 도달
     => dp[i][j] = min(dp[i][j], dp[i-1][j] + 1, dp[i][j-1] + 1)


2. 자료구조
 - int[][] dp: DP 배열


3. 시간 복잡도
 ① 전체 상태 개수: DP 배열 원소 개수 = algo x coding
   - 최대 150 x 150 = 22,500
 ② 1개 상태를 채우는 데 걸리는 시간: O(problems 행 개수 n)
   - 전체 문제 problems 확인
   - 최대 100
 => 총 시간 복잡도: O(algo x coding x n)
 => 최대 225 x 10^4 << 1억
*/

public class Main {
	static int[][] dp;
	static final int INF = 2_250_001;
	static int maxAlpReq, maxCopReq;			// 문제 요구 최대 알고력, 코딩력

	/* alp, cop: 초기 알고력, 코딩력 */
	static int solution(int alpInit, int copInit, int[][] problems) {
		for (int i = 0; i < problems.length; i++) {
			maxAlpReq = Math.max(maxAlpReq, problems[i][0]);
			maxCopReq = Math.max(maxCopReq, problems[i][1]);
		}

		// 예외 처리
		if (maxAlpReq <= alpInit && maxCopReq <= copInit)
			return 0;

		dp = new int[maxAlpReq + 1][maxCopReq + 1];		// [alpInit][copInit] ~ [maxAlpReq][maxCopReq] 사용
		for (int i = alpInit; i <= maxAlpReq; i++) {
			for (int j = copInit; j <= maxCopReq; j++)
				dp[i][j] = INF;
		}

		// 초기식
		dp[alpInit][copInit] = 0;							// 초기 알고력 & 코딩력
		for (int j = copInit + 1; j <= maxCopReq; j++)		// 첫 행
			dp[alpInit][j] = dp[alpInit][j - 1] + 1;
		for (int i = alpInit + 1; i <= maxAlpReq; i++)		// 첫 열
			dp[i][copInit] = dp[i - 1][copInit] + 1;

		for (int i = alpInit; i <= maxAlpReq; i++) {
			for (int j = copInit; j <= maxCopReq; j++) {
				// 모든 문제에 대해, 풀이 가능 여부 확인
				for (int k = 0; k < problems.length; k++) {
					if (canSolve(i, j, problems[k])) {		// problems[k] 풀이 가능한 경우
						int alpRwd = problems[k][2];
						int copRwd = problems[k][3];
						int cost = problems[k][4];

						if (i - alpRwd >= alpInit && j - copRwd >= copInit)
							dp[i][j] = Math.min(dp[i][j], dp[i - alpRwd][j - copRwd] + cost);
					}

					// 문제를 풀지 않고, 공부하는 경우
					if (i - 1 >= alpInit)
						dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + 1);
					if (j - 1 >= copInit)
						dp[i][j] = Math.min(dp[i][j], dp[i][j-1] + 1);
				}
			}
		}

		return dp[maxAlpReq][maxCopReq];
	}

	/* 현재 alp, cop로 문제 problem 풀이 가능 여부 => 요구 알고력, 코딩력 참조 */
	static boolean canSolve(int alp, int cop, int[] problem) {
		return (alp >= problem[0]) && (cop >= problem[1]);
	}

	public static void main(String[] args) {
//		int alpInit1 = 10;
//		int copInit1 = 10;
//		int[][] problems1 = { { 10, 15, 2, 1, 2 }, { 20, 20, 3, 3, 4} };
//		System.out.println(solution(alpInit1, copInit1, problems1));
//
//		System.out.println("\n--------------------");
//		for (int i = alpInit1; i <= 20; i++) {
//			for (int j = copInit1; j <= 20; j++)
//				System.out.print(dp[i][j] + "\t");
//			System.out.println();
//		}
//		System.out.println("--------------------");

		int alpInit2 = 0;
		int copInit2 = 0;
		int[][] problems2 = { { 0, 0, 2, 1, 2 }, { 4, 5, 3, 1, 2 }, { 4, 11, 4, 0, 2 }, { 10, 4, 0, 4, 2 } };
		System.out.println(solution(alpInit2, copInit2, problems2));

		System.out.println("\n--------------------");
		for (int i = alpInit2; i <= 10; i++) {
			for (int j = copInit2; j <= 11; j++)
				System.out.print(dp[i][j] + "\t");
			System.out.println();
		}
		System.out.println("--------------------");
	}
}

package Code_Tree.DP.둘_중_하나_잘_고르기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 다음이 모두 같을 때, "숫자 합"이 클 수록 더 좋음
 ① 시행 번호(카드 선택 회차 번호) => [1] ~ [2n] 번
 ② 이때까지 선택한 빨간 카드 개수 => [0] ~ [n] 개
 ③ 이때까지 선택한 파란 카드 개수 => [0] ~ [n] 개

 1) DP 배열 정의: int[][][] dp
   - dp[i][r][b]: [i]번 카드 선택 회차까지
     빨간 카드를 [r]개, 파란 카드를 [b]개 선택했을 때, 숫자 최대 합
   - 출력: dp[2n][n][n]

 2) 규칙 및 점화식
   - [i]번 회차에서 2가지 선택
     => 빨간 카드를 고르는 경우, 파란 카드를 고르는 경우
   - 초기식: 첫 행 dp[1][][]
     => dp[1][1][0] = 첫 번째 빨간 카드 값
     => dp[1][0][1] = 첫 번째 파란 카드 값
   - 점화식: dp[i][r][b] = max(dp[i-1][r-1][b] + 현재 red 카드 숫자 값,
   					  		   dp[i-1][r][b-1] + 현재 blue 카드 숫자 값)


2. 자료구조
 - int[][][] dp: DP 배열


3. 시간 복잡도
 - 상태 개수: DP 배열 원소 개수 = 2n x n x n = 2 x n^3
 - 1개 상태를 채우는 데 걸리는 시간:
 => O()
*/

public class Main {
	static int n;				// 최종적으로 빨간 카드 n개, 파란 카드 n개 선택
	static int[] redCards;		// 빨간 카드 2n개
	static int[] blueCards;		// 파란 카드 2n개
	static int maxSum;			// 출력, 카드 숫자 최대 합
	static int[][][] dp;

	static void solution() {
		// 초기식: 첫 행 dp[1][][]
		dp[1][1][0] = redCards[1];
		dp[1][0][1] = blueCards[1];

		// 점화식
		for (int i = 2; i <= 2 * n; i++) {
			for (int r = 0; r <= n; r++) {
				for (int b = 0; b <= n; b++) {
					if (r > 0)
						dp[i][r][b] = Math.max(dp[i][r][b], dp[i-1][r-1][b] + redCards[i]);

					if (b > 0)
						dp[i][r][b] = Math.max(dp[i][r][b], dp[i-1][r][b-1] + blueCards[i]);
				}
			}
		}

//		for (int i = 2; i <= 2 * n; i++) {
//			for (int r = 1; r <= n; r++) {
//				for (int b = 1; b <= n; b++) {
//					dp[i][r][b] = Math.max(
//							dp[i-1][r-1][b] + redCards[i],
//							dp[i-1][r][b-1] + blueCards[i]
//					);
//				}
//			}
//		}

		maxSum = dp[2 * n][n][n];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		redCards = new int[2 * n + 1];		// [1] ~ [2n] 사용
		blueCards = new int[2 * n + 1];

		for (int i = 1; i <= 2 * n; i++) {
			st = new StringTokenizer(br.readLine());
			int redCard = Integer.parseInt(st.nextToken());
			int blueCard = Integer.parseInt(st.nextToken());

			redCards[i] = redCard;
			blueCards[i] = blueCard;
		}

		dp = new int[2 * n + 1][n + 1][n + 1];		// [1][0][0] ~ [2n][n][n] 사용
		for (int i = 1; i <= 2 * n; i++) {
			for (int r = 0; r <= n; r++) {
				for (int b = 0; b <= n; b++)
					dp[i][r][b] = Integer.MIN_VALUE;
			}
		}

		solution();
		System.out.println(maxSum);
	}
}

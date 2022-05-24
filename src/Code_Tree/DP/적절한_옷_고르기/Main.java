package Code_Tree.DP.적절한_옷_고르기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 마지막 옷의 화려함 값에 따라, 만족도가 달라짐
 - 다음 3가지 정보가 모두 일치하는 경우,
   그 이후의 입장(그 다음 옷을 고를 때)에서 동일한 상황
   ① 지금까지 입을 옷을 고려한 "일자"
   ② 마지막 날에 입은 "옷"
   ③ 지금까지 얻은 "만족도"
   => 지금까지 입을 옷을 고려한 "일자", 마지막 날에 입은 "옷"이 모두 같으면,
      지금까지 얻은 "만족도"가 클 수록 좋음

 1) DP 배열 정의: int[][] dp
   - dp[i][j]: [i]번째 날 까지 입을 옷을 결정했고
   			   마지막 날에 입은 옷이 [j]번 옷 일 때, 얻는 최대 만족도
   - 출력: dp[m][] 중, 최대값

 2) 규칙 및 점화식
   - 초기식: 첫 행 dp[1][]
     ① [1]일에 [j] 옷을 입을 수 있는 경우: dp[1][j] = 0
     ② [1]일에 [j] 옷을 입을 수 없는 경우: dp[1][j] = -INF
   - 점화식
     ① [i]일에 [j] 옷을 입을 수 없는 경우: dp[i][j] = -INF
     ② [i]일에 [j] 옷을 입을 수 있는 경우: dp[i][j] = max( dp[i-1][k]
     		+ |이전에 마지막으로 입은 [k] 옷의 화려함 - 현재 마지막으로 입는 [j] 옷의 화려함| )
     	=> 1 <= k <= n


2. 자료구조
 - int[][] dp: DP 배열
 - Clothes[] clothes: 입력 옷들의 정보


3. 시간 복잡도
 - 상태 개수: DP 배열 원소 개수 = n x m
 - 1개 상태 dp[i][j]를 채우는 데 걸리는 시간: n
 => O(n^2 x m)
*/

class Clothes {
	public int s, e, v;		// 옷 시작 날짜, 마지막 날짜, 화려함

	public Clothes(int s, int e, int v) {
		this.s = s;
		this.e = e;
		this.v = v;
	}
}

public class Main {
	static int n, m;			// n개 옷, 일자 수 m
	static Clothes[] clothes;	// 각 옷의 s, e, v
	static int maxResult;		// 출력, 최대 만족도
	static int[][] dp;

	static void solution() {
		// 초기식: 첫 행 dp[1][]
		for (int j = 1; j <= n; j++) {
			if (isValid(clothes[j], 1))
				dp[1][j] = 0;
		}

		// 점화식
		for (int i = 2; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				for (int k = 1; k <= n; k++) {
					if (isValid(clothes[j], i)) {
						dp[i][j] = Math.max(dp[i][j],
								dp[i-1][k] + Math.abs(clothes[j].v - clothes[k].v));
					}
				}
			}
		}

		for (int j = 1; j <= n; j++)
			maxResult = Math.max(maxResult, dp[m][j]);
	}

	/* c 옷을 date 일자에 입을 수 있는지 확인 */
	static boolean isValid(Clothes c, int date) {
		return (c.s <= date && date <= c.e);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		clothes = new Clothes[n + 1];		// [1] ~ [n] 사용
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());

			clothes[i] = new Clothes(s, e, v);
		}

		dp = new int[m + 1][n + 1];			// [1][1] ~ [m][n] 사용
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++)
				dp[i][j] = Integer.MIN_VALUE;
		}

		solution();
		System.out.println(maxResult);
	}
}

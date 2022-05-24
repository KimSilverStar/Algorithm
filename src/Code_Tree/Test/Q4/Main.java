package Code_Tree.Test.Q4;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - [1]일 ~ [n]일 까지 각 [i]일 까지 외주 작업했을 때, 최대 수익 채워나감

 1) DP 배열 정의: int[] dp
   - dp[i]: [i]일 까지 일 했을 때, 최대 수익
   - 출력: dp[n]

 2) 규칙 및 점화식
   - 초기식: dp[0] = 0
   - 점화식: dp[i] = max(dp[i], dp[w.start - 1] + w.reward)
     (w: 해당 [i]일에 수행 가능한 외주 작업)


2. 자료구조
 - int[] dp: DP 배열


3. 시간 복잡도
 - 전체 상태 개수: DP 배열 원소 개수 = n
 - 1개 상태를 채우는 데 걸리는 시간: O(n)	(입력 외주 (t, p) 배열 확인)
 => O(n^2)
*/

class Work {
	public int start, end;		// 외주의 시작일, 마감일
	public int reward;			// 외주의 보상 p

	public Work(int start, int end, int reward) {
		this.start = start;
		this.end = end;
		this.reward = reward;
	}
}

public class Main {
	static int n;					// n일
	static Work[] works;			// n개 외주
	static int maxReward;			// 출력, 수익 최대값
	static int[] dp;

	static void solution() {
		dp[0] = 0;			// 초기식

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				Work w = works[j];

				if (canWork(i, w))		// [i] 날짜에 외주 w를 할 수 있는 경우
					dp[i] = Math.max(dp[i], dp[w.start - 1] + w.reward);
			}
		}

		maxReward = dp[n];
	}

	/* day 날짜까지 work 외주를 마칠 수 있는지 여부 */
	static boolean canWork(int day, Work work) {
		return (work.start <= day && work.end <= day);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		works = new Work[n + 1];			// [1] ~ [n] 사용
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			int date = Integer.parseInt(st.nextToken());		// 소요일
			int reward = Integer.parseInt(st.nextToken());
			int end = i + date - 1;

			works[i] = new Work(i, end, reward);
		}

		dp = new int[n + 1];			// [0] ~ [n] 사용

		solution();
		System.out.println(maxReward);
	}
}

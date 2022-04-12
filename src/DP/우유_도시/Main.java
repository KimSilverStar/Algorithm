package DP.우유_도시;
import java.io.*;
import java.util.StringTokenizer;

/*
- 우유 마시는 규칙: 딸기(0) -> 초코(1) -> 바나나(2) -> 딸기(0) -> ...
- 해당 지점의 우유 마시기 or 않마시기
- 오른쪽으로 1칸, 아래로 1칸 이동
- 지점 중복 방문 X
*/

/*
1. 아이디어
 - 우유 순서: 딸기(0) -> 초코(1) -> 바나나(2)
 - 최근에 마신 우유 종류에 따라
   현재 위치의 우유를 마실 수 있는지 여부가 결정됨
   => 최근 마신 "우유 종류를 구분"하여, DP 배열을 채움

 1) DP 배열 정의: int[][][] dp
   - dp[i][j][k]: [i][j] 지점까지 가장 최근에 k 우유를 마셨을 때,
     마신 최대 우유 개수
     (우유 종류 k: 딸기 0, 초코 1, 바나나 2)
   - 출력, 최대 우유 개수 = dp[n-1][n-1][k] 중, 최대값

 2) 규칙 및 점화식
   - 현재 위치의 우유 종류 currentMilk 와 각 [k] 우유 종류에 따라 DP 배열 채움
     => 현재 currentMilk 를 마시는 경우, 못 마시는 경우

   ① currentMilk == 딸기(0)이면, dp[i][j][딸기 0] = max(dp[i][j-1][이전 바나나 2] + 1, dp[i-1][j][이전 바나나 2] + 1)
      					  아니면, dp[i][j][딸기 0] = max(dp[i][j-1][딸기 0], dp[i-1][j][딸기 0])
      							  => 현재 딸기(0)를 안마심
   ② currentMilk == 초코(1)이고 이전 순서의 딸기(0)를 마셔서 dp[i][j][딸기 0] > dp[i][j][초코 1]이면,
      				 		 dp[i][j][초코 1] = max(dp[i][j-1][이전 딸기 0] + 1, dp[i-1][j][이전 딸기 0] + 1)
      				 아니면, dp[i][j][초코 1] = max(dp[i][j-1][초코 1], dp[i-1][j][초코 1])
      				 		 => 현재 초코(1)를 안마심
   ③ currentMilk = 바나나(2)이고 이전 순서의 초코(1)를 마셔서 dp[i][j][초코 1] > dp[i][j][바나나 2]이면,
   							dp[i][j][바나나 2] = max(dp[i][j-1][이전 초코 1] + 1, dp[i-1][j][이전 초코 1] + 1)
   					아니면, dp[i][j][바나나 2] = max(dp[i][j][바나나 2], dp[i][j][바나나 2])


2. 자료구조
 - int[][][] dp: DP 배열
   ① 자료형: 대략 최대 (10^3)^2 = 10^6 << 21억 이므로, int 가능
   ② 메모리: 최대 4 x 10^3 x 10^3 x 3 byte = 12 MB


3. 시간 복잡도
 - DP 배열 채우기: O(n^2)
   => n 최대값 대입: (10^3)^2 = 10^6 << 1억
*/

public class Main {
	static int n;				// n x n 행렬
	static int[][] map;
	static int[][][] dp;
	static int maxCount;		// 출력, 마시는 우유 최대 개수

	static void solution() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				int currentMilk = map[i][j];			// 현재 위치의 우유

				// 우유 마시는 순서: 딸기(0) -> 초코(1) -> 바나나(2)
				if (currentMilk == 0) {
					// 이전 순서의 바나나(2)를 최근으로 마신 개수 + 1
					dp[i][j][0] = Math.max(dp[i][j-1][2] + 1, dp[i-1][j][2] + 1);
				}
				else {
					dp[i][j][0] = Math.max(dp[i][j-1][0], dp[i-1][j][0]);
				}

				// 추가 조건식: 우유 마시는 순서 지키기 위함 (해당 현재 우유 currentMilk 를 마셔도 되는지 확인)
				if (currentMilk == 1 &&
						dp[i][j][0] > dp[i][j][1]) {	// 이전에 딸기(0)를 먹었는지
					// 이전 순서의 딸기(0)를 최근으로 마신 개수 + 1
					dp[i][j][1] = Math.max(dp[i][j-1][0] + 1, dp[i-1][j][0] + 1);
				}
				else {
					dp[i][j][1] = Math.max(dp[i][j-1][1], dp[i-1][j][1]);
				}

				if (currentMilk == 2 &&
						dp[i][j][1] > dp[i][j][2]) {	// 이전에 초코(1)를 먹었는지
					// 이전 순서의 초코(1)를 최근으로 마신 개수 + 1
					dp[i][j][2] = Math.max(dp[i][j-1][1] + 1, dp[i-1][j][1] + 1);
				}
				else {
					dp[i][j][2] = Math.max(dp[i][j-1][2], dp[i-1][j][2]);
				}
			}
		}

		// maxCount 찾기
		maxCount = Math.max(
				dp[n][n][0],
				Math.max(dp[n][n][1], dp[n][n][2])
		);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		map = new int[n + 1][n + 1];		// [1][1] ~ [n][n] 사용
		dp = new int[n + 1][n + 1][3];		// [0]행, [0]열은 패딩
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 1; j <= n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		solution();
		System.out.println(maxCount);

		System.out.println("--------------------");
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				for (int k = 0; k < 3; k++) {
					System.out.print(dp[i][j][k]);
					if (k != 2)
						System.out.print(", ");
					else
						System.out.print("   ");
				}
			}
			System.out.println();
		}
		System.out.println("--------------------");
	}
}

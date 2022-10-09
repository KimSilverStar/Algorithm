package Samsung_Coding_Test.퇴사;
import java.io.*;
import java.util.*;

/*
- 오늘부터 n+1 일째 되는 날 퇴사 예정
- 남은 n일 동안 최대한 많은 상담하려함
- 각 상담 정보: 상담 시간 t, 상담 보상 금액 p

- 상담 기간이 t일인 어떤 상담을 진행하면,
  상담 시작일 + t일부터 다른 다음 상담 가능
- n+1일에는 회사에 없음
  => n+1일을 넘어서는 상담 진행 불가능

- 출력: 얻을 수 있는 최대 이익
*/

/*
1. 아이디어
 > 조합(백트래킹, 브루트 포스)

- 백트래킹 종료 조건: depth == n+1
  => 상담 정보를 모두 확인한 경우
- 현재 상태에서 상담 가능한 경우
  => 선택 O or X


2. 자료구조
 - int[] t, int[] p: 입력 상담 정보


3. 시간 복잡도
 - 1개 상담에 대해 선택 O / X 하는 2가지 경우 존재
 - 전체 n개 상담에 대한 최대 경우의 수 = 2^n
   => n 최대값 대입: 2^15 = 32,768
*/

public class Main {
	static int n;				// 남은 n일
	static int[] t;
	static int[] p;
	static int maxMoney;		// 출력

	/* day: 상담 진행 시작 가능한 일자 */
	static void backtrack(int day, int money) {
		// 마지막 일자의 상담 정보까지 모두 확인한 경우
		if (day == n + 1) {
			maxMoney = Math.max(maxMoney, money);
			return;
		}

		// 선택 O
		if (day + t[day] <= n + 1) {
			backtrack(day + t[day], money + p[day]);
		}

		// 선택 X
		backtrack(day + 1, money);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		t = new int[n + 1];		// [1] ~ [n] 사용
		p = new int[n + 1];		// [1] ~ [n] 사용
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());

			t[i] = Integer.parseInt(st.nextToken());
			p[i] = Integer.parseInt(st.nextToken());
		}

		backtrack(1, 0);		// Init Call

		System.out.println(maxMoney);
	}
}

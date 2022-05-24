package BruteForce.도영이가_만든_맛있는_음식;
import java.io.*;
import java.util.StringTokenizer;

/*
- 완성된 음식의 신맛 = 모든 재료의 신맛 곱
- 완성된 음식의 쓴맛 = 모든 재료의 쓴맛 합
=> 요리의 |신맛 - 쓴맛| 값을 최소,
   n개 재료 중 1개 이상 사용

1. 아이디어
 - 백트래킹을 이용한 브루트 포스로 모든 재료 조합 구성하여, 맛의 차이 값 확인
 - tastes[0] ~ tastes[n-1] 차례로 확인
 - 각 tastes[i]에 대해, 선택 O or 선택 X 하는 2가지 경우
   => 재귀 호출 2번
 - tastes[n-1] 까지 확인 완료한 경우, 맛의 차이 계산
   => 단, 재료를 1개도 선택 안한 경우는 제외

2. 자료구조
 - Taste[] tastes: 입력, 각 재료의 신맛과 쓴맛

3. 시간 복잡도
 - 백트래킹으로 탐색할 상태 개수: 2^n
   => n 최대값 대입: 2^10 = 1,024 개
 - 1개 상태(선택 완료한 k개 재료들)에 대해, 맛의 차이 값 계산: O(k)
 => 전체 대략 1,024 x n = 10,240 << 1억
*/

public class Main3 {
	static int n;					// 재료 개수
	static Taste[] tastes;			// 각 재료의 신맛, 쓴맛
	static int minDiff = Integer.MAX_VALUE;		// 출력, 요리의 신맛과 쓴맛 최소 차이
	static boolean[] selected;		// 선택된 재료들 표시

	static void backtracking(int depth, int selectedCount) {
		// 마지막 재료까지 모두 확인
		if (depth == n) {
			if (selectedCount == 0)		// 재료 1개 이상 선택한 경우만 확인
				return;

			// 요리의 맛 차이 계산, 갱신
			int totalSour = 1;
			int totalBitter = 0;

			for (int i = 0; i < n; i++) {
				if (selected[i]) {
					totalSour *= tastes[i].sour;
					totalBitter += tastes[i].bitter;
				}
			}

			minDiff = Math.min(minDiff, Math.abs(totalSour - totalBitter));
			return;
		}

		// 선택 O
		selected[depth] = true;
		backtracking(depth + 1, selectedCount + 1);

		// 선택 X
		selected[depth] = false;
		backtracking(depth + 1, selectedCount);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		tastes = new Taste[n];
		selected = new boolean[n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int sour = Integer.parseInt(st.nextToken());
			int bitter = Integer.parseInt(st.nextToken());

			tastes[i] = new Taste(sour, bitter);
		}

		backtracking(0, 0);
		System.out.println(minDiff);
	}
}

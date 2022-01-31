package 도영이가_만든_맛있는_음식;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 => 브루트 포스 + 백트래킹: 백트래킹으로 조합(부분 집합)을 구성하고, 구성한 모든 경우를 확인
 - n 개의 재료들 중에서 1 ~ n 개 조합 (중복 X, 순서 X) 선택
   => 1개 조합 선택 후, 차이 계산
   => 2개 조합 선택 후, 차이 계산
   ...
   => n 개 조합 선택 후, 차이 계산
 - 각 조합들에 대해 맛 최소 차이 갱신해나감

2. 자료구조
 - Taste[]: 각 재료들의 신 맛, 쓴 맛
 - List<Integer>, ArrayList<Integer>: 선택된 재료들

3. 시간 복잡도
 - 조합(Combination): C(n, k) = n! / k! x (n-k)!
 - n 최대 10에 대해 C(10, 1) + C(10, 2) + ... + C(10, 10)
   = [ C(10, 1) + C(10, 2) + C(10, 3) + C(10, 4) + C(10, 5) ] x 2
   = 총 1,274 개 경우 << 1억 (1초)
*/

public class Main {
	static int n;						// 재료 개수
	static Taste[] tastes;				// 각 재료들의 신 맛, 쓴 맛
	static int minDiff = Integer.MAX_VALUE;			// 출력: 최소 신 맛, 쓴 맛 차이
	static List<Integer> selected;		// 조합: 선택된 재료들

	/* C(n, k) 구성 => k 개 선택 */
	static void combination(int k, int startIdx) {
		if (selected.size() == k) {		// k 개 재료 선택 완료
			int totalSour = 1;
			int totalBitter = 0;

			for (int idx : selected) {
				Taste taste = tastes[idx];
				totalSour *= taste.sour;
				totalBitter += taste.bitter;
			}

			int diff = Math.abs(totalSour - totalBitter);
			minDiff = Math.min(minDiff, diff);
			return;
		}

		for (int i = startIdx; i < n; i++) {
			selected.add(i);
			combination(k, i + 1);

			// 재귀 호출 복귀 => 선택 복구
			selected.remove(Integer.valueOf(i));
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		tastes = new Taste[n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int sour = Integer.parseInt(st.nextToken());
			int bitter = Integer.parseInt(st.nextToken());
			tastes[i] = new Taste(sour, bitter);
		}

		// C(n, 1) ~ C(n, n)
		for (int i = 1; i <= n; i++) {
			selected = new ArrayList<>();
			combination(i, 0);
		}

		System.out.println(minDiff);
	}
}

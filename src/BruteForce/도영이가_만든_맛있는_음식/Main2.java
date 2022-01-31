package BruteForce.도영이가_만든_맛있는_음식;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 => 브루트 포스 + 백트래킹: 백트래킹으로 조합을 구성하고, 구성한 모든 경우를 확인

 - n 개 재료들을 차례로 확인
   1) idx 번째 재료를 선택하고, 그 다음 재료 확인 (재귀 호출)
   2) idx 번째 재료를 선택하지 않고, 그 다음 재료 확인 (재귀 호출)
 - 재귀 호출 종료 조건: n 개 재료들을 모두 확인
   => 맛 차이 계산
 - 예외 처리) 재료를 1개도 선택하지 않은 경우는 제외 처리

2. 자료구조
 - Taste[]: 각 재료들의 신 맛, 쓴 맛
 - List<Integer>, ArrayList<Integer>: 선택된 재료들

3. 시간 복잡도
 - 1개 재료에 대해, 2가지 선택 => 해당 재료 선택 O / 선택 X
   => 재귀 호출 2번
 - 총 재귀 호출 (전체 경우의 수) = 2^10 = 1,024 << 1억 (1초)
*/

public class Main2 {
	static int n;						// 재료 개수
	static Taste[] tastes;				// 각 재료들의 신 맛, 쓴 맛
	static int minDiff = Integer.MAX_VALUE;			// 출력: 최소 신 맛, 쓴 맛 차이
	static List<Integer> selected = new ArrayList<>();		// 선택된 재료들

	static void solution(int depth) {
		if (depth == n) {				// n 개 재료들을 모두 확인한 경우
			if (selected.isEmpty())		// 재료를 1개도 선택하지 않은 경우는 제외
				return;

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

		selected.add(depth);			// 해당 재료 선택
		solution(depth + 1);

		selected.remove(Integer.valueOf(depth));	// 해당 재료 선택 X
		solution(depth + 1);
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

		solution(0);

		System.out.println(minDiff);
	}
}

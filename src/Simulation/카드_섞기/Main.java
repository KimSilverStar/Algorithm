package Simulation.카드_섞기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 입력 수열 S (카드 섞는 방법)에 따라 카드를 섞고,
   입력 수열 P (목표 카드 분배)와 비교
   1) 섞은 카드 결과 == 목표 카드 분배 상태인 경우
     - 반복문 종료 및 카드 섞은 횟수 출력
   2) 섞은 카드 결과 == 초기 카드 상태 [0, 1, 2, 0, 1, 2, ... ] 인 경우
     - 목표 카드 분배 상태를 만들 수 없음

2. 자료구조
 - int[] cardTargets: 수열 P, 목표 카드 분배 상태
 - int[] shuffles: 수열 S, 카드 섞는 방법
 - int[] 2개: 섞기 전 / 후 카드

3. 시간 복잡도
 1) 카드 1번 섞기: O(n)
 2) 섞인 카드와 목표 상태 비교, 섞인 카드와 초기 상태 비교: O(2n)
 => 카드 1번 섞은 후, 비교: O(3n)
*/

public class Main {
	static int n;					// 카드 개수
	static int[] cardTargets;		// 수열 P, 각 카드의 목표 플레이어 (각 원소: 0, 1, 2)
	// [i]번째 카드 => cardTargets[i] 로 보내야 함
	static int[] shuffle;			// 수열 S, 카드 섞는 방법 (각 원소: 0 ~ n-1)
	// [i]번째 카드 => shuffle[i] 위치로 이동하여 섞음

	static int[] cards;				// 섞기 전, 카드
	static int[] shuffledCards;		// 섞은 후, 카드
	static boolean flag = false;	// true 이면, 카드 목표 분배 상태로 못 섞는 경우 (-1)
	static int minCount;

	static void solution() {
		// 카드 섞기 전, 초기 상태가 목표 상태인지 비교
		if (isTargetState(cards))
			return;

		while (true) {
			// 카드 섞기
			for (int i = 0; i < n; i++) {
				int newIdx = shuffle[i];
				shuffledCards[i] = cards[newIdx];
//				shuffledCards[newIdx] = cards[i];
			}
			minCount++;

			for (int i = 0; i < n; i++)
				cards[i] = shuffledCards[i];

			// 섞은 카드가 목표 분배 상태인지 비교
			if (isTargetState(shuffledCards))
				break;

			if (isInitState(shuffledCards)) {
				flag = true;
				break;
			}
		}
	}

	 /* cardArr[] 의 카드가 목표 분배 상태인지 비교 */
	static boolean isTargetState(int[] cardArr) {
		for (int i = 0; i < n; i++) {
			if (cardArr[i] != cardTargets[i])
				return false;
		}
		return true;
	}

	/* cardArr[] 의 카드가 초기 카드 상태인지 비교 */
	static boolean isInitState(int[] cardArr) {
		for (int i = 0; i < n; i++) {
			if (cardArr[i] != (i % 3))
				return false;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine());
		cardTargets = new int[n];
		for (int i = 0; i < n; i++)
			cardTargets[i] = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		shuffle = new int[n];
		for (int i = 0; i < n; i++)
			shuffle[i] = Integer.parseInt(st.nextToken());

		shuffledCards = new int[n];
		cards = new int[n];
		for (int i = 0; i < n; i++)			// 초기 카드 상태: 0, 1, 2, 0, 1, 2, ...
			cards[i] = (i % 3);

		solution();

		if (!flag)
			System.out.println(minCount);
		else
			System.out.println(-1);
	}
}

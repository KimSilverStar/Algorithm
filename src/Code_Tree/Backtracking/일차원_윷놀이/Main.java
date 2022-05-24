package Code_Tree.Backtracking.일차원_윷놀이;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 각 턴마다 몇번 말을 움직일 지가 관건
   => 각각의 턴에서 k개 말 중, 움직일 말을 선택
   => 1 ~ k 말 중, n개 선택 (중복 O)

2. 자료구조

3. 시간 복잡도
 - O(k^n): 1 ~ k 말 중, n개 선택
*/

public class Main {
	static int n;				// 턴 수
	static int m;				// 윷놀이 판 배열 길이, 1번 ~ m번
	static int k;				// 말의 개수
	static int[] distances;		// 각 턴마다 앞으로 가는 거리
	static int maxScore;		// 출력, 최대 점수
	static int[] selectedHorses;	// 선택된 n개 말들

	static void backtracking(int selectedCount) {
		if (selectedCount == n) {
			maxScore = Math.max(maxScore, getScore());
			return;
		}

		// 1번 ~ k번 말
		for (int horse = 1; horse <= k; horse++) {
			selectedHorses[selectedCount] = horse;
			backtracking(selectedCount + 1);
		}
	}

	/* selectedHorses[] 순서로 말을 움직일 때, 점수 계산 */
	static int getScore() {
		int[] positions = new int[k + 1];		// 1번 ~ k번 말들의 위치
		for (int i = 1; i <= k; i++)
			positions[i] = 1;

		for (int i = 0; i < n; i++) {
			int horse = selectedHorses[i];
			int distance = distances[i];

			positions[horse] += distance;
		}

		int score = 0;
		for (int i = 1; i <= k; i++) {
			if (positions[i] >= m)
				score++;
		}

		return score;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		distances = new int[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			distances[i] = Integer.parseInt(st.nextToken());

		selectedHorses = new int[n];		// 선택된 n개 말들
		backtracking(0);

		System.out.println(maxScore);
	}
}

package Code_Tree.Backtracking.xor_결과_최대_만들기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - n개 숫자 중, m개 선택 (중복 X)
   => 조합 C(n, m)

2. 자료구조
 - int[]: 선택한 m개 숫자들

3. 시간 복잡도
 - O(C(n, m))
   => 최대값: C(20, 10) = 184,756
 ※ C(n, r) = n! / r! x (n-r)!
*/

public class Main {
	static int n;				// n개 음이 아닌 정수
	static int m;				// 정수 m개 선택
	static int[] numbers;
	static int maxResult;		// XOR 최대값
	static int[] selectedNumbers;		// 선택한 m개 숫자들

	/* selectedCount: 현재까지 선택한 숫자 개수, lastIdx: 마지막에 선택한 숫자 idx */
	static void backtracking(int selectedCount, int lastIdx) {
		if (selectedCount == m) {
			int result = selectedNumbers[0];
			for (int i = 1; i < m; i++)
				result ^= selectedNumbers[i];

			maxResult = Math.max(maxResult, result);
			return;
		}

		// n개 숫자 중, m개 선택 (중복 X) => 조합 C(n, m)
		for (int i = lastIdx + 1; i < n; i++) {
			selectedNumbers[selectedCount] = numbers[i];
			backtracking(selectedCount + 1, i);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());

		numbers = new int[n];			// 전체 n개 숫자
		for (int i = 0; i < n; i++)
			numbers[i] = Integer.parseInt(st.nextToken());

		selectedNumbers = new int[m];	// 선택한 m개 숫자
		for (int i = 0; i < n; i++) {
			int number = numbers[i];

			selectedNumbers[0] = number;
			backtracking(1, i);
		}

		System.out.println(maxResult);
	}
}

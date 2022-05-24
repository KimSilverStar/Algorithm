package Code_Tree.Backtracking.k개_중에_1개를_n번_뽑기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 중복 순열: 1 ~ k 숫자 중, n개 선택 (중복 O)
 - 재귀 호출로 중복 순열 구성

2. 자료구조
 - int[]: 재귀호출로 선택한 n개 숫자들

3. 시간 복잡도
 - O(k^n): 1 ~ k 숫자 중, n개 선택 (중복 O)
*/

public class Main {
	static int k, n;					// 1 ~ k 숫자 중, n개 선택 (중복 O) => 중복 순열
	static int[] selectedNumbers;		// 선택한 n개 숫자들
	static StringBuilder sb = new StringBuilder();

	/* n개 선택하여 중복 순열 완성 - count: 현재까지 선택한 숫자 개수 */
	static void backtracking(int count) {
		// 재귀 종료 조건: n개 숫자 모두 선택
		if (count == n) {
			for (int i = 0; i < n; i++)
				sb.append(selectedNumbers[i]).append(" ");
			sb.append("\n");

			return;
		}

		// 1 ~ k 숫자 중 1개 선택 후 재귀 호출
		for (int num = 1; num <= k; num++) {
			selectedNumbers[count] = num;
			backtracking(count + 1);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		k = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());

		selectedNumbers = new int[n];
		backtracking(0);

		System.out.println(sb);
	}
}

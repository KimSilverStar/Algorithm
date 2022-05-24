package Code_Tree.Backtracking.특정_조건에_맞게_k개_중에_1개를_n번_뽑기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 1 ~ k 숫자 중, n개를 선택 (중복 O), 3번 연속 동일 숫자는 제외
 - 각 재귀에서 1 ~ k 숫자 중, 1개를 선택 (배열에 추가)
 - 숫자 선택 후, 3개 숫자가 연속하는 지 확인
   => 연속하는 경우, backtrack (선택 취소)
   => 연속하지 않는 경우, 재귀 호출로 다음 숫자 선택

2. 자료구조
 - int[]: 선택한 n개 숫자

3. 시간 복잡도
 - O(k^n)
*/

public class Main {
	static int k, n;					// 1 ~ k 숫자 중, n개
	static int[] selectedNumbers;		// 선택한 n개 숫자
	static StringBuilder sb = new StringBuilder();

	/* count - 현재까지 선택한 숫자 개수 */
	static void backtracking(int count) {
		if (count == n) {
			for (int i = 0; i < n; i++)
				sb.append(selectedNumbers[i]).append(" ");
			sb.append("\n");

			return;
		}

		// 1 ~ k 숫자 중, 1개 선택 후 재귀 호출
		for (int num = 1; num <= k; num++) {
			selectedNumbers[count] = num;		// 숫자 num 선택
			if (!isContinuous(count))		// 선택한 숫자가 3개 이상 연속 X 하는 경우
				backtracking(count + 1);
		}
	}

	/* 현재까지 선택한 숫자들이 3개 이상 연속하는지 여부 */
	static boolean isContinuous(int idx) {
		int count = 1;			// 연속하여 동일한 숫자 개수
		int maxCount = 1;		// 연속하여 동일한 숫자 최대 개수

		for (int i = 0; i < idx; i++) {
			if (selectedNumbers[i] == selectedNumbers[i+1])
				count++;
			else
				count = 1;

			maxCount = Math.max(maxCount, count);
		}

		return maxCount >= 3;
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

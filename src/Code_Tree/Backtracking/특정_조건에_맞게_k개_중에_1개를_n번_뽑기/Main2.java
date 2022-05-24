package Code_Tree.Backtracking.특정_조건에_맞게_k개_중에_1개를_n번_뽑기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 1 ~ k 숫자 중, n개를 선택 (중복 O)
   + 3번 연속 동일 숫자는 제외
 - 각 재귀에서 1 ~ k 숫자 중, 1개를 선택 (배열에 추가)
   => 이전에 선택한 2개 숫자와 비교하여, 모두 같은 경우 (3번 연속)는 제외
   => 연속하지 않는 경우, 재귀 호출로 다음 숫자 선택

2. 자료구조
 - int[]: 선택한 n개 숫자

3. 시간 복잡도
 - O(k^n)
*/

public class Main2 {
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
			// 이전에 선택한 2개 숫자가 모두 num과 같으면, 3개 숫자가 연속함
			if (count >= 2 && selectedNumbers[count-1] == num
					&& selectedNumbers[count-2] == num)
				continue;

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

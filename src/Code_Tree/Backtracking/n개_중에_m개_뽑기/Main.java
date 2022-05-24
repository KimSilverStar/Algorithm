package Code_Tree.Backtracking.n개_중에_m개_뽑기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 1 ~ n 숫자들 중, m개를 선택하여 조합 구성
   => 중복 X, 순서 상관 O


2. 자료구조
 - boolean[]
 - int[]

3. 시간 복잡도
 - O(m x C(n, m))
*/

public class Main {
	static int n, m;				// 1 ~ n 숫자들 중, m개를 선택하여 조합 구성 (중복 X, 순서 상관 O)
	static int[] selectedNumbers;	// 선택한 m개 숫자들
	static StringBuilder sb = new StringBuilder();

	/* count: 현재까지 선택한 개수, lastNum: 마지막으로 선택한 숫자  */
	static void backtracking(int count, int lastNum) {
		// 재귀 종료 조건: m개 선택 완료
		if (count == m) {
			for (int i = 0; i < m; i++)
				sb.append(selectedNumbers[i]).append(" ");
			sb.append("\n");

			return;
		}

		for (int num = lastNum + 1; num <= n; num++) {
			selectedNumbers[count] = num;
			backtracking(count + 1, num);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		selectedNumbers = new int[m];
		for (int num = 1; num <= n; num++) {
			selectedNumbers[0] = num;
			backtracking(1, num);
		}

		System.out.println(sb);
	}
}

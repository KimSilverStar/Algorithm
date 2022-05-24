package Code_Tree.Backtracking.아름다운_수;
import java.io.*;

/*
1. 아이디어
 - 재귀 함수로 1 ~ 4 숫자 선택하여 n 자리 수 구성 (중복 O)
 - 구성한 n 자리 수가 아름다운 수 인지 확인

2. 자료구조
 - int[]: 선택한 n 자리 수

3. 시간 복잡도
 - 1 ~ 4 숫자로 n 자리 수 구성: O(4^n)
 - 구성된 n 자리 수 1개에 대해, 아름다운 수 인지 판단: O(n)
 => 총 시간 복잡도: O(n x 4^n)
*/

public class Main {
	static int n;						// n 자리, 각 숫자는 1 ~ 4 로 구성
	static int count;					// 출력, n 자리 아름다운 수의 개수
	static int[] selectedNumbers;		// 선택된 n 자리 수

	static void backtracking(int selectedCount) {
		if (selectedCount == n) {
			if (isBeautifulNumber())
				count++;
			return;
		}

		// 1 ~ 4 숫자
		for (int num = 1; num <= 4; num++) {
			selectedNumbers[selectedCount] = num;	// 숫자 num 선택
			backtracking(selectedCount + 1);
		}
	}

	/* 선택된 수 selectedNumbers[]가 아름다운 수 인지 확인 */
	static boolean isBeautifulNumber() {
		for (int i = 0; i < n; i += selectedNumbers[i]) {
			int number = selectedNumbers[i];

			if (i + number - 1 >= n)
				return false;

			// [i] ~ [i + number - 1] 까지 숫자 number 가 나와야 함
			for (int j = i; j <= i + number - 1; j++) {
				if (selectedNumbers[j] != number)
					return false;
			}
		}

		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());

		selectedNumbers = new int[n];
		backtracking(0);

		System.out.println(count);
	}
}

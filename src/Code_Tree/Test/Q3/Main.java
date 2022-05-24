package Code_Tree.Test.Q3;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 전체 2n개 숫자를 차례로 확인하면서, 선택 O or X 하여 2개의 그룹으로 나눔
   => 선택된 그룹, 선택 안된 그룹
 - 백트래킹

2. 자료구조

3. 시간 복잡도
 - O(2^2n)
*/

public class Main {
	static int n;					// 2n개 숫자
	static int[] numbers;
	static int minDiff = Integer.MAX_VALUE;		// 출력, 합 차이 최소값
	static List<Integer> list1 = new ArrayList<>();		// 그룹 1
	static List<Integer> list2 = new ArrayList<>();		// 그룹 2
	static int sum1, sum2;			// 나눠진 각 그룹의 숫자들 합

	/* numbers[index] 숫자에 대해 2개 그룹 고려 */
	static void backtracking(int index) {
		if (index == 2 * n) {
			int diff = Math.abs(sum1 - sum2);
			minDiff = Math.min(minDiff, diff);

			return;
		}

		// numbers[index]를 그룹 1로
		if (list1.size() < n) {
			list1.add(numbers[index]);
			sum1 += numbers[index];
			backtracking(index + 1);

			list1.remove(Integer.valueOf(numbers[index]));
			sum1 -= numbers[index];
		}

		// numbers[index]를 그룹 2로
		if (list2.size() < n) {
			list2.add(numbers[index]);
			sum2 += numbers[index];
			backtracking(index + 1);

			list2.remove(Integer.valueOf(numbers[index]));
			sum2 -= numbers[index];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		numbers = new int[n * 2];
		for (int i = 0; i < n * 2; i++) {
			int num = Integer.parseInt(st.nextToken());
			numbers[i] = num;
		}

		backtracking(0);
		System.out.println(minDiff);
	}
}

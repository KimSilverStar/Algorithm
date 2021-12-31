package Backtracking.N과M_1;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Stack;

/*
1. 아이디어
 - for 문에서 1 ~ n 까지 1개씩 확인
 - 백트래킹 재귀함수
   => 방문(선택) 안한 숫자 선택
   => 종료 조건: 선택한 숫자 개수 == m
   => 재귀함수 호출 종료 후 복귀하여 방문(선택) 배열 복구, 최근에 선택한 수 복구

2. 자료구조
 - boolean[]: 1 ~ n 선택(방문) 여부
 - Stack<Integer>: 선택된 숫자들 저장

3. 시간 복잡도
 - Backtracking Algorithm 의 시간 복잡도
   1) 중복 있는 경우 => O(n^n) => n <= 8 까지 가능
   2) 중복 없는 경우 => O(n!) => n <= 10 까지 가능
*/

public class Main {
	static int n, m;			// 1 ~ n 까지 자연수, 중복없이 m개 선택
	static boolean[] check;		// 1 ~ n 까지 자연수 선택 여부
	static Stack<Integer> selectedNumbers = new Stack<>();		// 선택된 숫자들

	/* selectCount: 현재까지 선택한 숫자 개수 */
	public static void solution(int selectCount) {
		// 재귀 종료 조건: m개의 숫자를 선택한 경우
		if (selectCount == m) {
			for (int number : selectedNumbers)
				System.out.print(number + " ");
			System.out.println();
			return;
		}

		for (int i = 1; i <= n; i++) {
			if (!check[i]) {
				check[i] = true;
				selectedNumbers.push(i);
				solution(selectCount + 1);

				// 백트래킹: 재귀 호출 종료 후, 복귀 시점 => 방문(선택) 표시 및 선택 복구
				check[i] = false;
				selectedNumbers.pop();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		check = new boolean[n + 1];

		solution(0);
	}
}

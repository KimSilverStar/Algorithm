package Backtracking.N과M_2;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Stack;

/*
1. 아이디어
 - for 문에서 1 ~ n 까지 1개씩 확인
 - 백트래킹 재귀함수
   => 선택(방문)하지 않고, 이전에 선택한 숫자보다 값이 큰 숫자 선택
   => 종료 조건: 선택한 숫자 개수 == m
   => 재귀함수 호출 종료 후 복귀하여 방문(선택) 배열 복구, 최근에 선택한 수 복구

2. 자료구조
 - boolean[]: 1 ~ n 선택(방문) 여부
 - Stack<Integer>: 선택한 숫자들 저장

3. 시간 복잡도
 - Backtracking Algorithm 의 시간 복잡도
   1) 중복 있는 경우 => O(n^n) => n <= 8 까지 가능
   2) 중복 없는 경우 => O(n!) => n <= 10 까지 가능
     - n 최대값 대입: 8! = 40,320 << 1억 (1초)
*/

public class Main_Stack {
	static int n, m;			// 1 ~ n 까지 자연수, 중복없이 m개 선택 (오름차순)
	static boolean[] check;
	static Stack<Integer> selectedNumbers = new Stack<>();

	/* depth: 현재까지 선택한 숫자 개수, 백트래킹 재귀 호출(트리)에서의 깊이 */
	static void solution(int depth) {
		// 재귀함수 종료 조건
		if (depth == m) {
			for (int number : selectedNumbers)
				System.out.print(number + " ");
			System.out.println();
			return;
		}

		for (int i = 1; i <= n; i++) {
			// 아직 방문하지 않았고, 이전에 선택한 수 보다 더 큰 수 선택
			if (!check[i] &&
					(depth == 0 || i > selectedNumbers.peek())) {
				// depth == 0 대신 selectedNumbers.isEmpty() 가능
				check[i] = true;
				selectedNumbers.push(i);
				solution(depth + 1);

				// 재귀함수 호출 종료 후, 복귀 시점
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

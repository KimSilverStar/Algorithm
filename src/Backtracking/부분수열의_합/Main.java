package Backtracking.부분수열의_합;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 *** 백트래킹을 이용한 브루트 포스로 모든 조합에 대해 확인
 - 입력 수열의 원소 [0] ~ [n-1] 까지 차례로 확인
 - 각 원소에 대해 2가지 경우: 선택 O / 선택 X
 - 재귀 종료 조건: 입력 수열의 마지막 원소까지 확인한 경우
 - 예외 처리) 구성한 부분수열 중, 공집합은 제외
   => 입력 수열의 원소에서 1개도 선택하지 않은 경우는 제외

2. 자료구조
 - int[]: 입력 수열
 - boolean[]: 부분 수열 선택

3. 시간 복잡도
 - 입력 수열의 각 원소에 대해 2가지 경우 존재 (선택 O / 선택 X)
   => 재귀 호출 매번 각 2번
 - 시간 복잡도: O(2^n)
   => n 최대값 대입: 2^20 = 1,048,576 << 2억
*/

public class Main {
	static int n;
	static int s;					// 수열 원소의 합
	static int[] numbers;			// n개의 정수로 이루어진 수열

	static boolean[] selected;		// 부분 수열 선택
	static int selectedCount;		// 부분 수열의 원소 개수 (입력 수열의 원소에서 선택한 개수)
	static int count;				// 출력: 수열의 합 == s 인 부분수열의 개수

	static void solution(int depth) {
		if (depth == n) {				// 입력 수열의 마지막 원소까지 확인한 경우
			if (selectedCount == 0)		// 공집합은 제외
				return;

			// 부분수열의 합 계산
			int sum = 0;
			for (int i = 0; i < n; i++) {
				if (selected[i])
					sum += numbers[i];
			}

			if (sum == s)
				count++;

			return;
		}

		// 부분 수열 구성
		selected[depth] = true;				// 선택 O
		selectedCount++;
		solution(depth + 1);

		selected[depth] = false;			// 선택 X
		selectedCount--;
		solution(depth + 1);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		s = Integer.parseInt(st.nextToken());

		numbers = new int[n];
		selected = new boolean[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			numbers[i] = Integer.parseInt(st.nextToken());

		solution(0);
		System.out.println(count);
	}
}

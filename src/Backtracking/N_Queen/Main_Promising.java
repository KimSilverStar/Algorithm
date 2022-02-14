package Backtracking.N_Queen;
import java.io.*;

/*
1. 아이디어
 => Backtracking 으로 가지치기를 해가며, 유망한 노드에 대해서만 확인

 - 행렬의 한 행씩 확인
   => 기본적으로, 퀸은 한 행에 1 개씩만 배치 가능 (상하좌우 직선 상으로 위협 X 해야함)
   => 한 행에서 좌 ~ 우로 열을 확인해가면서,
      promising 하면 (퀸을 놓을 수 있는 경우) 퀸 배치

 - 배치하려는 퀸의 promising 여부 => boolean isPromising(int depth)
   1) 이전 윗 행의 퀸들과 서로 다른 열에 위치
     - col[depth] 와 col[0 ~ depth - 1] 비교
   2) 이전 윗 행의 퀸들과 서로 다른 대각선 상에 위치
     - 같은 대각선 상에 위치 => "행의 차이 == 열의 차이"

 - 재귀 종료 조건: 마지막 행까지 모두 확인한 경우
 => void solution(int depth)

2. 자료구조
 - int[] col: 각 행에 배치된 퀸의 열 위치 저장
   ex) col[0]: 0 행에 배치된 퀸의 열 위치
       col[0] = 3 이면, [0][3] 에 퀸 배치됨

3. 시간 복잡도
 * 브루트 포스로 N x N 의 모든 칸을 확인: N ^ N
   => 시간 초과
 * 백트래킹 + 가지치기로 유망한 노드만 확인: N! 미만
   => N 최대값 대입: 대충 14! = 87,178,291,200 미만
*/

public class Main_Promising {
	static int n;						// n x n 행렬, n개 퀸 (1 ~ 14)
	static int[] col;					// 각 행에 배치한 퀸의 열 위치
	static int count;					// 출력: 가능한 경우의 수

	static void solution(int depth) {
		if (depth == n) {				// 마지막 행까지 확인한 경우
			count++;
			return;
		}

		// 한 행 확인
		for (int i = 0; i < n; i++) {
			col[depth] = i;				// [depth][i] 에 퀸 배치
			if (isPromising(depth))		// 퀸 배치 후 promising 한 경우, 다음 행 탐색
				solution(depth + 1);
		}
	}

	/* 배치한 depth 행의 퀸이 promising 한지 판단 - 이전 윗 행들의 퀸과 비교 */
	static boolean isPromising(int depth) {
		for (int i = 0; i < depth; i++) {
			if (col[i] == col[depth])			// 같은 열인지 확인
				return false;
			if ((depth - i) == Math.abs(col[depth] - col[i]))		// 같은 대각선 상인지 확인
				return false;
		}

		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());
		col = new int[n];

		if (n == 1) {
			System.out.println(1);
			return;
		}
		else if (n == 2) {				// n = 2 인 경우, 퀸 배치 불가
			System.out.println(0);
			return;
		}

		solution(0);
		System.out.println(count);
	}
}

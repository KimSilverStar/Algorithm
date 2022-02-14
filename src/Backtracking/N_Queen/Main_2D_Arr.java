package Backtracking.N_Queen;
import java.io.*;

/* N x N 체스판에 N개의 퀸을 서로 위협하지 않도록 배치 */

/*
1. 아이디어
 => Backtracking 으로 해당 상태에서 가능한 모든 경우를 확인

 - 행렬의 한 행씩 확인
   => 기본적으로, 퀸은 한 행에 1 개씩만 배치 가능 (상하좌우 직선 상으로 위협 X 해야함)
   => 한 행에서 좌 ~ 우로 열을 확인해가면서, 퀸을 놓을 수 있으면 배치

 - 퀸을 배치 후, 배치한 퀸의 위협 영역 표시
   1) 세로 아래 직선 상의 칸
   2) 왼쪽 아래 대각선 상의 칸
   3) 오른쪽 아래 대각선 상의 칸
   => 한 행에 퀸을 1개씩만 배치할 것이므로
      가로 직선 상(같은 행)의 칸, 세로 위 직선 상의 칸,
      왼쪽 / 오른쪽 위 대각선 상의 칸은 위협 표시 불필요

 - 각 칸을 확인하면서, 위협 영역이 아닌 칸에 퀸을 배치
 - 재귀 종료 조건: 마지막 행까지 모두 확인한 경우
 => solution(int depth)

2. 자료구조
 - boolean[][] board: 체스판 행렬, 퀸 배치 표시
 - boolea[][] checkAttack: 각 퀸들이 위협하는 지점들 표시
   => 2개 행렬 메모리: 최대 14 x 14 x 2 = 392 byte

3. 시간 복잡도
 * 브루트 포스로 N x N 의 모든 칸을 확인: N ^ N
   => 시간 초과
 * 백트래킹 + 가지치기로 유망한 노드만 확인: N! 미만
   => N 최대값 대입: 대충 14! = 87,178,291,200 미만
*/

public class Main_2D_Arr {
	static int n;					// n x n 행렬, n개 퀸 (1 ~ 14)
	static boolean[][] board;
	static boolean[][] checkAttack;
	static int count;				// 출력: 가능한 경우의 수

	static void solution(int depth) {
		if (depth == n) {			// 마지막 행까지 확인한 경우
			count++;
			return;
		}

		// 한 행 확인
		for (int i = 0; i < n; i++) {
			if (!checkAttack[depth][i]) {
				checkAttack[depth][i] = true;
				board[depth][i] = true;				// 퀸 배치
				attack(depth, i);					// 배치한 퀸의 위협 칸 표시
				solution(depth + 1);

				// 재귀 호출 복귀 시점: 퀸 배치 복구
				board[depth][i] = false;
//				checkAttack = new boolean[n][n];		// 배열 새로 할당하면 메모리 초과 발생
				for (int row = 0; row < n; row++) {
					for (int col = 0; col < n; col++)
						checkAttack[row][col] = false;
				}

				for (int row = 0; row < n; row++) {
					for (int col = 0; col < n; col++) {
						if (board[row][col])
							attack(row, col);
					}
				}
			}
		}
	}

	/* (y, x) 지점에 퀸을 배치했을 때, 위협 지점 표시 */
	static void attack(int y, int x) {
		// 세로 아래 직선 상
		for (int row = y; row < n; row++)
			checkAttack[row][x] = true;

		// 왼쪽 아래 대각선 상
		int row = y;
		int col = x;
		while (0 <= row && row < n && 0 <= col && col < n) {
			checkAttack[row++][col--] = true;
		}

		// 오른쪽 아래 대각선 상
		row = y;
		col = x;
		while (0 <= row && row < n && 0 <= col && col < n) {
			checkAttack[row++][col++] = true;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());
		board = new boolean[n][n];
		checkAttack = new boolean[n][n];

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

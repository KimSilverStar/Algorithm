package Code_Tree.Simulation.숫자가_가장_큰_인접한_곳으로_동시에_이동;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 각 지점의 구슬 개수를 int[][] counts, int[][] nextCounts 에 저장
 - 각 반복(1초)에 대해서 모든 구슬 옮기기
   1) nextCounts[][] 초기화
   2) 구슬 이동 - nextCounts[][]에 기록
   3) counts[][]에 이동한 구슬 nextCounts[][] 복사
 - 모든 구슬 이동 후, 중복된 위치의 구슬 제거

2. 자료구조

3. 시간 복잡도
*/

public class Main {
	static int n;			// n x n 행렬
	static int m;			// 구슬 개수
	static int t;			// t초
	static int[][] map;
	static int restCount;	// 출력, 남은 구슬 개수

	static int[][] counts;				// 각 지점의 구슬 개수
	static int[][] nextCounts;
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void solution() {
		for (int i = 0; i < t; i++) {
			moveAll();
			removeDuplicates();
		}
	}

	/* 모든 구슬을 1번 (1초) 씩 이동 */
	static void moveAll() {
		// 1. nextCounts[][] 초기화
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++)
				nextCounts[i][j] = 0;
		}

		// 2. 구슬 이동 - nextCounts[][]에 기록
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (counts[i][j] == 1)
					step(i, j);		// [i][j] 위치의 구슬 이동
			}
		}

		// 3. counts[][]에 이동한 구슬 nextCounts[][] 복사
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++)
				counts[i][j] = nextCounts[i][j];
		}
	}

	/* 중복 위치의 구슬 제거 */
	static void removeDuplicates() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				// 한 지점에 2개 이상의 구슬 존재 => 중복
				if (counts[i][j] >= 2) {
					restCount -= counts[i][j];		// 남은 구슬 개수 갱신
					counts[i][j] = 0;
				}
			}
		}
	}

	/* [i][j] 위치의 구슬 이동 - 상하좌우 중, 가장 큰 곳으로 이동 */
	static void step(int y, int x) {
		int max = 0;		// 인접 지점의 값 중, 최대값
		int dir = 0;		// 이동할 방향

		int ny, nx;
		for (int i = 0; i < 4; i++) {
			ny = y + dy[i];
			nx = x + dx[i];

			if (isValid(ny, nx) && max < map[ny][nx]) {
				max = map[ny][nx];
				dir = i;
			}
		}

		ny = y + dy[dir];
		nx = x + dx[dir];
		nextCounts[ny][nx]++;
	}

	static boolean isValid(int y, int x) {
		return (1 <= y && y <= n) && (1 <= x && x <= n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());

		map = new int[n+1][n+1];
		counts = new int[n+1][n+1];
		nextCounts = new int[n+1][n+1];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			counts[r][c] = 1;		// 시작 위치 구슬
		}

		restCount = m;
		solution();
		System.out.println(restCount);
	}
}

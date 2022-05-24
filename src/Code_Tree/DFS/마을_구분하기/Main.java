package Code_Tree.DFS.마을_구분하기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 2중 for문으로 [0][0] ~ [n-1][n-1] 확인
 - 해당 지점을 아직 방문 안했고, 사람(1)인 경우
   => DFS 탐색 시작

2. 자료구조
 - List<Integer>, ArrayList<Integer>: 출력, 각 마을의 사람 수

3. 시간 복잡도
 - O(n^2)
*/

public class Main {
	static int n;				// n x n 행렬
	static int[][] map;
	static boolean[][] visited;
	static int townCount;		// 출력, 마을 개수
	static List<Integer> peopleCounts = new ArrayList<>();	// 출력, 각 마을의 사람 수
	static int peopleCount;		// 각 DFS 탐색으로 셀 마을의 사람 수
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void dfs(int y, int x) {
		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (isValid(ny, nx) && !visited[ny][nx]
					&& map[ny][nx] == 1) {			// 사람(1)인 경우
				peopleCount++;
				visited[ny][nx] = true;
				dfs(ny, nx);
			}
		}
	}

	static boolean isValid(int y, int x) {
		return (0 <= y && y < n) && (0 <= x && x < n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		map = new int[n][n];
		visited = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (!visited[i][j] && map[i][j] == 1) {		// 사람(1)인 경우
					peopleCount = 1;

					townCount++;
					visited[i][j] = true;
					dfs(i, j);

					peopleCounts.add(peopleCount);
				}
			}
		}

		Collections.sort(peopleCounts);

		StringBuilder sb = new StringBuilder();
		sb.append(townCount).append("\n");
		for (int count : peopleCounts)
			sb.append(count).append("\n");
		System.out.println(sb);
	}
}

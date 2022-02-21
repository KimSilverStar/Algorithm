package DFS_BFS.치즈;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 *** 맵의 지점이 외부 공기인지, 내부 공기인지를 구분해야 함 !!
    - "모눈종이의 맨 가장자리에는 치즈가 놓이지 않는 것으로 가정한다."
      => 확정된 외부 공기인 [0][0] 부터 탐색 시작 (DFS / BFS)

 * 1 ~ 3 과정을 모든 치즈가 녹을 때까지 반복
 *** 0: 내부 공기(입력 값), 1: 치즈, 2: 외부 공기 ***

 1) 외부 공기 표시 (외부, 내부 공기 구별) [DFS]
   - 입력 행렬에서 외부 공기인 [0][0] 부터 DFS 탐색 시작
     => 해당 지점이 공기이면, 외부 공기(2)로 표시
   - 인접 지점을 아직 방문 안했고, 치즈(1)가 아닌 공기(0 또는 2)이면, DFS 탐색 확장

 2) 녹일 치즈 표시
   - 녹일 치즈를 리스트에 저장
   방법 ①) DFS
       - 행렬을 차례로 확인
         => 해당 지점이 치즈(1)이고 아직 방문 안한 경우, 치즈의 인접 지점 확인
   	   - 치즈의 인접 지점 중 2 군데 이상이 외부 공기(2)이면, DFS 탐색 확장
   	     => 해당 치즈의 위치를 리스트에 저장
   방법 ②) 2중 for문으로 완전 탐색
       - 행렬을 차례로 확인
         => 해당 지점이 치즈(1)인 경우, 치즈의 인접 지점 확인
       - 치즈의 인접 지점 중 2 군데 이상이 외부 공기(2)이면, 해당 치즈의 위치를 리스트에 저장

 3) 표시한 녹일 치즈를 녹이기
   - for 문으로 녹일 치즈 리스트를 반복하여, 치즈를 녹임
     => 치즈 녹이기: 치즈(1) -> 입력 공기(0)
     => 남은 치즈 개수 갱신

2. 자료구조
 - boolean[][]: 방문 확인
 - List<Point>, ArrayList<Point>: 녹일 치즈 위치들 저장

3. 시간 복잡도
 1) 외부 공기 표시 (외부, 내부 공기 구별) [DFS]
   - DFS 1번 수행: O(V + E) ~= O(V + 4V) = O(5V) = O(5 x n x m)
     => n, m 최대값 대입: 5 x 100 x 100 = 5 x 10^4

 2) 녹일 치즈 표시
   방법 ①) DFS: 대충 O(5V) = O(5 x n x m)
            => n, m 최대값 대입: 5 x 100 x 100 = 5 x 10^4
   방법 ②) 2중 for문으로 완전 탐색: O(n x m)
   			=> n, m 최대값 대입: 100 x 100 = 10^4

 3) 표시한 녹일 치즈를 녹이기
   - O(k)	(k: 리스트에 저장된 녹일 치즈 개수)
*/

public class Main_DFS {
	static int n, m;                        // n행 m열
	static int[][] map;
	static int time;                        // 출력: 모든 치즈가 녹는 데 걸리는 시간

	static int cheeseCount;					// 남은 치즈 개수
	static List<Point> cheeseList;			// 녹일 치즈 위치 저장
	static boolean[][] visited;
	static int[] dy = {-1, 1, 0, 0};        // 상하좌우
	static int[] dx = {0, 0, -1, 1};

	/* 1) DFS로 외부 공기(2) 표시 */
	static void checkOutterAir(int y, int x) {
		visited[y][x] = true;
		map[y][x] = 2;                // 외부 공기로 표시

		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (ny < 0 || ny >= n ||
					nx < 0 || nx >= m)
				continue;

			// 다음 지점을 아직 방문 안했고, 치즈(1)가 아닌 공기(0, 2)인 경우
			if (!visited[ny][nx] && map[ny][nx] != 1)
				checkOutterAir(ny, nx);
		}
	}

	/* 2) 녹일 치즈 표시 - 리스트에 저장 */
	/* 방법 ① - DFS */
	static void checkMeltingCheese(int y, int x) {
		visited[y][x] = true;
		cheeseList.add(new Point(y, x));        // 녹일 치즈 저장

		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (ny < 0 || ny >= n ||
					nx < 0 || nx >= m)
				continue;

			if (map[ny][nx] != 1)        // 치즈만 확인
				continue;

			// 다음 지점을 아직 방문 안했고, 녹는 치즈인 경우
			if (!visited[ny][nx] && isMelting(ny, nx))
				checkMeltingCheese(ny, nx);
		}
	}

	/* 방법 ② - 2중 for문으로 완전 탐색 */
	static void checkMeltingCheese_2() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] == 1 && isMelting(i, j))
					cheeseList.add(new Point(i, j));
			}
		}
	}

	/* 3) 치즈 녹이기 */
	static void meltCheese() {
		for (Point p : cheeseList) {		// 녹일 치즈 리스트
			map[p.y][p.x] = 0;
			cheeseCount--;
		}
	}

	/* 치즈가 녹는 치즈인지 반환 - (y, x): 치즈 지점 */
	static boolean isMelting(int y, int x) {
		int count = 0;						// 치즈가 외부 공기와 맞닿은 지점 개수

		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (ny < 0 || ny >= n ||
				nx < 0 || nx >= m)
				continue;

			if (map[ny][nx] == 2)		// 외부 공기
				count++;
		}

		return count >= 2;			// 외부 공기와 2군데 이상 맞닿으면, 녹는 치즈
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		map = new int[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 1)
					cheeseCount++;
			}
		}

		while (cheeseCount > 0) {
			/* 1) DFS로 외부 공기(2) 표시 */
			visited = new boolean[n][m];
			checkOutterAir(0, 0);		// 외부 공기인 [0][0] 부터 DFS 탐색 시작

			/* 2) 녹일 치즈 표시 - 리스트에 저장 */
			// 방법 ①) DFS
			visited = new boolean[n][m];
			cheeseList = new ArrayList<>();

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (map[i][j] != 1)			// 치즈만 확인
						continue;

					// 아직 방문 안했고 녹는 치즈인 경우
					if (!visited[i][j] && isMelting(i, j))
						checkMeltingCheese(i, j);
				}
			}

			// 방법 ②) 2중 for문으로 완전 탐색
//			list = new ArrayList<>();
//			checkMeltingCheese_2();

			/* 3) 치즈 녹이기 */
			meltCheese();
			time++;
		}

		System.out.println(time);
	}
}

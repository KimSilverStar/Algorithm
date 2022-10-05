package Samsung_Coding_Test.아기_상어;
import java.io.*;
import java.util.*;

/*
- n x n 맵
- 물고기 m마리, 아기 상어 1마리
- 최초에 아기 상어 크기: 2
- 아기 상어
  > 자신의 크기보다 큰 물고기가 있는 칸은 못 지나감
  > 자신의 크기보다 작은 물고기만 먹을 수 있음
  => 자신과 크기가 같은 물고기는 못먹지만, 해당 칸은 지나갈 수 있음

아기 상어 이동 로직
1) 먹을 수 있는 물고기가 맵에 없는 경우, 엄마 상어에게 도움 요청 (반복 종료)
2) 먹을 수 있는 물고기 개수 == 1마리인 경우, 그 물고기를 먹으러 감
3) 먹을 수 있는 물고기 개수 > 1마리인 경우, 거리가 가장 가까운 몰고기를 먹으러 감
  - 거리 = 아기 상어 칸 -> 해당 물고기 칸으로 이동할 때, 지나야하는 칸 개수 min 값
  - 거리가 동일하게 가까운 물고기가 다수일 경우
    => 가장 위에 있는 물고기
    => 가장 왼쪽에 있는 물고기

- 아기 상어: 자신의 크기에 해당하는 개수의 물고기를 먹을 때마다 크기 +1
- 출력: 아기 상어가 엄마 상어에게 도움을 요청하지 않고, 물고기를 잡아먹을 수 있는 시간
*/

/*
1. 아이디어
 > 구현, 시뮬레이션
 > BFS: 먹을 수 있는 물고기 위치 탐색
 > PriorityQueue / 정렬: BFS 탐색하면서, 먹을 수 있는 물고기 찾으면 PQ에 저장

1) 먹을 수 있는 물고기 탐색
 - 현재 아기 상어 위치로부터 BFS 탐색 시작
   => map[][]의 모든 지점을 탐색 (BFS 안에서 while문 종료 조건 명시 X)
 - 다음 인접 지점에 대해
   ① 다음 지점을 아직 방문 안했고, 아기 상어가 지나갈 수 있는 칸인 경우
     - BFS 탐색 확장
   ② 다음 지점이 물고기 칸이고, 아기 상어가 먹을 수 있는 경우
     - 해당 물고기 칸을 pq에 저장

2) 물고기 먹기
 ① pq.isEmpty()인 경우 (먹을 수 있는 물고기가 맵에 없는 경우)
   - 반복 종료 및 출력
 ② pq.isEmpty()가 아닌 경우 (먹을 수 있는 물고기가 1마리 or 다수인 경우)
   - pq에서 꺼낸 Node의 먹을 물고기 위치로 아기 상어 이동 및 물고기 먹기,
     먹은 물고기 개수 증가 및 아기 상어 크기 증가 확인


먹을 수 있는 물고기 개수가 다수일 경우, 먹으러 갈 물고기 우선순위 정하기 (정렬 기준)
① 거리 작은 순
② 해당 물고기 행 번호 작은 순
③ 해당 물고기 열 번호 작은 순


2. 자료구조
 - boolean[][] visited
 - Queue<Node>, LinkedList<Node>: BFS 수행
 - PriorityQueue<Node>: 먹을 물고기 우선순위 지정
 ※ Node: 먹을 물고기 위치 (y, x), 지나온 칸 개수 distance
*/

class Node implements Comparable<Node> {
	public int y, x;			// 먹을 물고기 위치
	public int distance;		// 지나온 칸 개수

	public Node(int y, int x, int distance) {
		this.y = y;
		this.x = x;
		this.distance = distance;
	}

	// 거리 작은 순 -> 행 번호 작은 순 -> 열 번호 작은 순
	public int compareTo(Node o) {
		if (this.distance != o.distance)
			return this.distance - o.distance;
		if (this.y != o.y)
			return this.y - o.y;
		return this.x - o.x;
	}
}

public class Main {
	static int n;					// n x n 행렬
	static int[][] map;
	static int time;				// 출력

	static boolean[][] visited;
	static Queue<Node> queue = new LinkedList<>();				// BFS 탐색
	static PriorityQueue<Node> pq = new PriorityQueue<>();		// 먹을 물고기 저장 및 정렬
	static int sharkY, sharkX;			// 아기 상어 위치
	static int sharkSize = 2;			// 아기 상어 크기 (최초 2)
	static int eatCnt;					// 아기 상어가 먹은 물고기 개수 (size 증가할 때마다, 0으로 초기화)

	static final int EMPTY = 0;			// 빈 칸
	static final int SHARK = 9;			// 아기 상어 칸
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void solution() {
		while (true) {
			visited = new boolean[n][n];		// Init
			queue.clear();
			pq.clear();

			// 1) 먹을 수 있는 물고기 count
			// BFS 탐색: 아기 상어 칸 [sharkY][sharkX]에서 나머지 모든 지점 탐색
			visited[sharkY][sharkX] = true;
			queue.add(new Node(sharkY, sharkX, 0));
			bfs();

			// 2) 물고기 먹기
			// 먹을 수 있는 물고기가 맵에 없는 경우
			if (pq.isEmpty()) {
				return;
			}

			// 먹을 수 있는 물고기가 1마리 or 다수인 경우
			Node fish = pq.remove();
			map[sharkY][sharkX] = EMPTY;		// 기존 아기 상어 위치

			map[fish.y][fish.x] = SHARK;
			sharkY = fish.y;
			sharkX = fish.x;

			eatCnt++;
			if (eatCnt == sharkSize) {
				sharkSize++;
				eatCnt = 0;
			}

			time += fish.distance;
		}
	}

	/* 아기 상어 칸 [sharkY][sharkX]에서부터 나머지 모든 지점 탐색 */
	static void bfs() {
		while (!queue.isEmpty()) {
			Node current = queue.remove();

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (!isValid(ny, nx))
					continue;

				// 다음 지점을 아직 방문 안했고, 지나갈 수 있는 칸인 경우
				if (!visited[ny][nx] && sharkSize >= map[ny][nx]) {
					Node next = new Node(ny, nx, current.distance + 1);
					visited[ny][nx] = true;
					queue.add(next);

					// 아기 상어가 먹을 수 있는 물고기인 경우
					if (map[ny][nx] != EMPTY && sharkSize > map[ny][nx]) {
						pq.add(next);
					}
				}
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
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == SHARK) {
					sharkY = i;
					sharkX = j;
				}
			}
		}

		solution();

		System.out.println(time);
	}
}

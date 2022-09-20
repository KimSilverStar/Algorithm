package Samsung_Coding_Test.스타트_택시;
import java.io.*;
import java.util.*;

/*
- 손님을 도착지로 데려다 줄때마다 연료 충전
- 연료를 다 쓰면, 업무 종료
- 특정 위치로 이동할 때, 최단경로로 이동하려 함

- 태울 승객 선택: 현재 위치에서 최단거리가 가장 짧은 승객 선택
  ※ 같은 거리의 승객이 여러 명일 경우,
    그중 행 번호가 가장 작은 승객 + 그중 열 번호가 가장 작은 승객
- 한 승객을 목적지로 이동 성공시키면, 그 승객을 태우면서 소비한 연료 양 2배가 충전
- 이동 중 연료를 다 쓰면, 업무 종료

- 출력: 모든 손님을 이동시키고 연료를 충전한 후, 남은 연료 양
  => 이동 중 연료를 다 쓴 경우 -1, 모든 손님을 이동할 수 없는 경우 -1
*/

/*
1. 아이디어
 - 최단경로 => BFS
 - 현재 택시 위치 - BFS 1 -> 우선순위 높은 승객 위치 - BFS 2 -> 태운 승객의 목적지
 - map[][]에 승객 출발 위치 표시 (승객 번호)

 1) 승객 선택
   - 현재 택시 위치로부터 BFS 탐색
   - 최단거리 + 낮은 행 번호 + 낮은 열 번호 순으로 승객 선택
     => PriorityQueue 이용한 BFS 탐색
   - 가장 먼저 만나는 승객(우선순위가 높은 승객)을 태움
     => map[i][j] = 0 으로 설정 (승객 태움 표시)
     => 태운 승객 위치 저장
   - 현재 위치에서 인접 4개 방향 다음 위치에 대해
     아직 방문 안했고, 벽이 아닌 경우 탐색 확장

 2) 태운 승객을 목적지로 이동시킴
   - 현재 태운 승객의 목적지 좌표 확인
   - 승객을 태운 위치에서부터 BFS 탐색
     => 목적지로 가는 최단거리 값을 계산 및 반환
     => 현재 연료 양으로 목적지 갈 수 있는지 여부 확인

 * BFS 탐색 노드 상태
  - 현재 택시 좌표 (y, x)
  - 현재까지 비용 cost


 ※ 오답 노트
  1) 승객 선택 - bfs1
    - 우선순위 높은 노드를 선택하여 탐색 확장하기 위한 PriorityQueue 를 떠올리지 못함
    - 현재 탐색 노드 current에 대해 fuel <= current.cost 인 경우,
      return 하여 탐색을 아예 종료 시켜버림
      => 정답) fuel <= current.cost 인 경우, 해당 경로를 더 보지 않는 식으로 처리(continue) 해야 함

  2) 태운 승객을 목적지로 이동 - bfs2
    - 목적지에 도착한 경우, 남은 연료 양에서 현재까지 비용 current.cost 를 빼고
      바로 current.cost * 2를 더해버림
      fuel -= current.cost;		fuel += (current.cost * 2);
      => 문제점) current.cost를 소모하여 목적지까지 갈 수 있는지 여부를 먼저 확인해야 함
      => 정답) bfs2()에서 탐색 결과 값으로 승객 목적지까지 가는 최단거리 값을 반환하고,
      		   main 메소드에서 반환된 최단거리 값을 이용하여 실패(-1) 여부 판단


2. 자료구조
 - Customer[] customers: 각 승객의 출발 좌표, 목적지 좌표
 - PriorityQueue<Node>: BFS 수행 (택시 위치 -> 승객 최단거리)
   => Node: (현재 택시 위치), 현재까지 소모 cost
   => 최단거리 + 낮은 행 번호 + 낮은 열 번호 승객 우선 선택
 - Queue<Node>, LinkedList<Node>: BFS 수행 (태운 승객을 목적지까지 이동시키는 거리)
 - boolean[][] visited: 방문 처리 배열
*/

class Point {
	public int y, x;

	public Point(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

class Customer {
	public int startY, startX;		// 승객 출발 좌표
	public int destY, destX;		// 승객 목적지 좌표

	public Customer(int startY, int startX, int destY, int destX) {
		this.startY = startY;
		this.startX = startX;
		this.destY = destY;
		this.destX = destX;
	}
}

class Node implements Comparable<Node> {
	public int taxiY, taxiX;		// 현재 택시 위치
	public int cost;				// 승객을 태운 후, 목적지까지 도착할 때까지 소비한 연료 양

	public Node(int taxiY, int taxiX, int cost) {
		this.taxiY = taxiY;
		this.taxiX = taxiX;
		this.cost = cost;
	}

	// 최단거리(최소 cost), 최소 행 번호, 최소 열 번호
	public int compareTo(Node o) {
		if (this.cost != o.cost)
			return this.cost - o.cost;

		if (this.taxiY != o.taxiY)
			return this.taxiY - o.taxiY;

		return this.taxiX - o.taxiX;
	}
}

public class Main {
	static int n;				// n x n 행렬
	static int m;				// 목표 m명 승객
	static int fuel;			// 연료 양

	static int[][] map;
	static int startTaxiY, startTaxiX;			// 택시 시작 위치
	static Customer[] customers;				// 각 승객들의 출발, 목적지 좌표

	static Point currentTaxi;			// 현재 택시 위치
	static int currentCustomerIdx;		// 현재 택시에 태운 승객 index: [1] ~ [m]

	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };
	static boolean[][] visited;
	static Queue<Node> queue;
	static PriorityQueue<Node> pq;

	/* 현재 택시 위치로부터 BFS 탐색하여, 최단거리에 있는 승객을 태움 */
	static int bfs1() {
		while (!pq.isEmpty()) {
			Node current = pq.remove();

			// 승객 만난 경우 (승객 번호: 1 ~ m)
			if (map[current.taxiY][current.taxiX] >= 1) {
				// 현재 택시 위치 저장 => 승객을 태운 위치
				currentTaxi = new Point(current.taxiY, current.taxiX);
				currentCustomerIdx = map[current.taxiY][currentTaxi.x];	// 태운 승객 번호 저장

				map[current.taxiY][current.taxiX] = 0;			// 승객 태움 처리
				return current.cost;
			}

			// 현재까지 비용 >= 남은 연료인 경우, 해당 경로를 더 보지 않고 다른 경로 탐색
			if (current.cost >= fuel)
				continue;

			for (int i = 0; i < 4; i++) {
				int ny = current.taxiY + dy[i];
				int nx = current.taxiX + dx[i];

				if (!isValid(ny, nx))
					continue;

				// 다음 지점을 아직 방문 안했고, 벽이 아닌 경우
				if (!visited[ny][nx] && map[ny][nx] != -1) {
					visited[ny][nx] = true;
					pq.add(new Node(ny, nx, current.cost + 1));
				}
			}
		}

		return -1;			// 벽에 막혀서 승객을 못 태운 경우
	}

	/* 태운 승객을 목적지로 이동시킴 */
	static int bfs2() {
		Customer customer = customers[currentCustomerIdx];		// 현재 태운 승객의 출발, 목적지 좌표

		while (!queue.isEmpty()) {
			Node current = queue.remove();

			// 목적지에 도착한 경우
			if (current.taxiY == customer.destY && current.taxiX == customer.destX) {
				currentTaxi = new Point(current.taxiY, current.taxiX);
				return current.cost;
			}

			// 현재까지 비용 >= 남은 연료인 경우, 해당 경로를 더 보지 않고 다른 경로 탐색
			if (current.cost >= fuel)
				continue;

			for (int i = 0; i < 4; i++) {
				int ny = current.taxiY + dy[i];
				int nx = current.taxiX + dx[i];

				if (!isValid(ny, nx))
					continue;

				// 다음 지점을 아직 방문 안했고, 벽이 아닌 경우
				if (!visited[ny][nx] && map[ny][nx] != -1) {
					visited[ny][nx] = true;
					queue.add(new Node(ny, nx, current.cost + 1));
				}
			}
		}

		return -1;		// 벽에 막혀서 승객을 목적지까지 이동시키지 못한 경우
	}

	static boolean isValid(int ny, int nx) {
		return (1 <= ny && ny <= n) && (1 <= nx && nx <= n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		fuel = Integer.parseInt(st.nextToken());

		map = new int[n + 1][n + 1];		// [1][1] ~ [n][n] 사용
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 1; j <= n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 1) {
					map[i][j] = -1;		// 벽: -1로 바꿔서 저장 (승객 번호 1과 겹치지 않게)
				}
			}
		}

		st = new StringTokenizer(br.readLine());
		startTaxiY = Integer.parseInt(st.nextToken());
		startTaxiX = Integer.parseInt(st.nextToken());

		customers = new Customer[m + 1];			// [1] ~ [m] 사용
		for (int i = 1; i <= m; i++) {
			st = new StringTokenizer(br.readLine());
			int startY = Integer.parseInt(st.nextToken());		// 승객 출발 좌표
			int startX = Integer.parseInt(st.nextToken());
			int destY = Integer.parseInt(st.nextToken());		// 승객 목적지 좌표
			int destX = Integer.parseInt(st.nextToken());

			customers[i] = new Customer(startY, startX, destY, destX);
			map[startY][startX] = i;		// 승객 번호 표시
		}

		currentTaxi = new Point(startTaxiY, startTaxiX);

		for (int i = 0; i < m; i++) {
			// 1) 현재 택시 위치로부터 최단거리에 있는 승객을 태움
			pq = new PriorityQueue<>();				// 우선순위 큐, 방문 배열 초기화
			visited = new boolean[n + 1][n + 1];
			currentCustomerIdx = -1;
			visited[currentTaxi.y][currentTaxi.x] = true;
			pq.add(new Node(currentTaxi.y, currentTaxi.x, 0));
			int cost1 = bfs1();

			// 벽에 막혀서 승객을 못태우는 경우 => 실패
			if (cost1 == -1 || currentCustomerIdx == -1) {
				fuel = -1;
				break;
			}
			fuel -= cost1;

			// 연료를 다 쓴 경우 => 실패
			if (fuel <= 0) {
				fuel = -1;
				break;
			}

			// 2) 태운 승객을 목적지로 이동시킴 => 승객을 태운 위치에서부터 BFS 탐색
			queue = new LinkedList<>();				// 큐, 방문 배열 초기화
			visited = new boolean[n + 1][n + 1];
			visited[currentTaxi.y][currentTaxi.x] = true;
			queue.add(new Node(currentTaxi.y, currentTaxi.x, 0));
			int cost2 = bfs2();

			// 벽에 막혀서 승객을 목적지까지 이동시키지 못하는 경우 => 실패
			if (cost2 == -1) {
				fuel = -1;
				break;
			}
			fuel -= cost2;

			// 연료를 다 쓴 경우 => 실패
			// ※ 단, fuel == 0 까지는 가능 (밑에서 연료 충전 하므로)
			if (fuel < 0) {		// fuel < 0 이면, 승객을 태우고 목적지까지 가는 도중 연료가 다 떨어짐을 의미
				fuel = -1;
				break;
			}

			fuel += (cost2 * 2);
		}

		System.out.println(fuel);
	}
}

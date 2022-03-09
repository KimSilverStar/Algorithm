package Shortest_Path.Dijkstra.미로_만들기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 단순히 시작 지점 [0][0] -> 끝 지점 [n-1][n-1] 으로의 최단경로는 BFS 로 해결 가능.
   하지만, 방을 바꾸는 최소 개수에 해당하는 경로는 최단경로가 아닐 수 있음
 - 시작 지점으로부터 나머지 모든 지점으로 갈 때,
   방을 바꾸는 최소 개수 (최소 비용)
   => 다익스트라

 1) 비용 배열, 우선순위 큐 초기화
   - count[0][0] = 0, 나머지 지점은 INF 로 초기화
   - pq.add(시작 지점, 0개)
 2) 우선순위 큐가 empty 할 때까지, 다음을 반복
   - 비용 w (바꾸는 방 개수) 작은 순으로 우선순위 큐에서 꺼냄
     => 해당 지점 [y][x] 의 비용이 이미 갱신된 경우는 제외
   - 상하좌우 다음 위치에 대해,
     현재 지점 [y][x]를 거쳐서 갈때 비용이 더 작은 경우
      => 비용 갱신, 우선순위 큐에 추가
      => 다음 위치를 가는 비용 = 현재 비용 + 0 or 1 (다음 위치 검은 방 여부)


2. 자료구조
 - int[][] count: 비용 배열
   => count[i][j]: 시작 지점 [0][0] -> [i][j] 로 갈 때, 바꾸는 방 최소 개수
   => 비용 최대값 INF
      = 노드 최대 개수 50^2 x 가중치 최대값 1 = 2500 << 21억 이므로, int 가능
 - PriorityQueue<Node>: [0][0] -> [y][x] 로 갈 때, 바꾸는 방 최소 개수 w
   => Node: 연결된 노드 위치 (y, x), 비용 w


3. 시간 복잡도
 - 다익스트라 시간 복잡도: O(E log_2 V)
   => V: 최대 n^2 개, E: 한 Vertex 당 Edge 4개 가정
   = 4V log_2 V = (4 x n^2) x log_2 n^2 = (8 x n^2) log_2 n
   => n 최대값 대입: (8 x 25 x 10^2) x log_2 50 ~= 12 x 10^4 << 1억
*/

class Node implements Comparable<Node> {
	public int y, x;		// 위치
	public int w;			// 가중치 (바꾼 방 개수)

	public Node(int y, int x, int w) {
		this.y = y;
		this.x = x;
		this.w = w;
	}

	public int compareTo(Node n) {
		return this.w - n.w;		// 바꾼 방 개수 적은 순
	}
}

public class Main_Dijkstra {
	static int n;					// n x n 행렬
	static int[][] map;
	static int minCount;			// 출력, 바꾸어야 할 검은 방 최소 개수

	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static final int INF = 2500;	// 노드 최대 개수 50^2 x 간선 가중치 최대값 1
	static int[][] count;			// 비용 배열
	static PriorityQueue<Node> pq = new PriorityQueue<>();

	static void dijkstra() {
		// count[][], pq 초기화 - 시작 지점
		count[0][0] = 0;
		pq.add(new Node(0, 0, 0));

		while (!pq.isEmpty()) {
			Node current = pq.remove();		// 바꾸는 방 개수 w 작은 순

			if (count[current.y][current.x] < current.w)	// 이미 갱신된 count[][] 는 제외
				continue;

			// 현재 지점과 연결된 인접 지점 [ny][nx] 의 비용 확인
			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (ny < 0 || ny >= n || nx < 0 || nx >= n)
					continue;

				// [0][0] -> 현재 [y][x] -> [ny][nx] 의 최소 비용 nw
				int nw = (map[ny][nx] == 0) ? current.w + 1 : current.w;	// 검은 방(0) 이면 바꿈
//				int nw = (map[ny][nx] == 0) ?
//						count[current.y][current.x] + 1 : count[current.y][current.x];

				// 현재 [y][x] 를 경유하지 않고, [ny][nx] 로 가는 비용: count[ny][nx]
				// 현재 [y][x] 를 경유해서 갈 때의 비용: nw
				if (count[ny][nx] > nw) {		// 현재 [y][x] 를 경유해서 갈 때, 비용 더 적은 경우
					count[ny][nx] = nw;
					pq.add(new Node(ny, nx, nw));
				}
			}
		}

		minCount = count[n-1][n-1];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		count = new int[n][n];
		for (int i = 0; i < n; i++) {
			String input = br.readLine();
			for (int j = 0; j < n; j++) {
				map[i][j] = Character.getNumericValue(input.charAt(j));
				count[i][j] = INF;		// 다익스트라 최소 비용 배열 초기화
			}
		}

		dijkstra();
		System.out.println(minCount);
	}
}

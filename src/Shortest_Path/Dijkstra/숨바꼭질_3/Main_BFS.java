package Shortest_Path.Dijkstra.숨바꼭질_3;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 목표 지점을 찾으면 탐색을 종료하는,
   일반적인 DFS / BFS 는 간선의 가중치가 모두 같아야 함.
   하지만, 본 문제는 +1, -1 로 가는 경우 가중치가 1,
   x2 로 가는 경우 가중치가 0 으로 가중치가 다름
 - BFS 를 사용하되, Queue 에서 꺼낸 노드가 목표 지점일 때
   BFS 탐색을 종료하지 않아야 함
   => Queue 가 empty 할 때까지, Queue 에 들어있는 노드를 모두 확인하여 탐색

 - 방문 처리 배열을 boolean[] 이 아닌, int[] 사용
   => 해당 지점을 방문하는 최소 가중치(최소 시간)를 갱신해나가며 탐색
   => 다음 지점을 방문할 때 소비 시간
      < 현재까지 갱신된 최적 경로로 다음 지점에 도달하는 최소 시간
	  인 경우, Queue 에 다음 지점 노드를 추가 (BFS 탐색 확장)


2. 자료구조
 - Queue<State>, LinkedList<State>: BFS
   => State: 현재 지점 position, 소비 시간 time
 - int[] visited: 방문 확인
   ex) visited[10] = 5 이면, 지점 10 을 5초 소비하여 방문
   => 메모리: 4 x 10^5 byte = 0.4 MB


3. 시간 복잡도
 - BFS 의 시간 복잡도: O(V + E)
   => 한 Edge 에 Vertex 3개 가정
   => V + E = V + 3V = 4V = 4n
   => n 최대값 대입: 4 x 10^5 << 2억
*/

class State {
	public int position;		// 현재 지점
	public int time;			// 소비 시간

	public State (int position, int time) {
		this.position = position;
		this.time = time;
	}
}

public class Main_BFS {
	static int n, k;				// 수빈이의 시작 지점 n, 목표 동생 지점 k
	static int minTime;				// 출력, 최소 시간

	static final int MAX_POSITION = 100_000;
	static final int INF = 100_000;
	static int[] visited = new int[MAX_POSITION + 1];
	static Queue<State> queue = new LinkedList<>();

	static void bfs() {
		Arrays.fill(visited, INF);
		visited[n] = 0;
		queue.add(new State(n, 0));

		while (!queue.isEmpty()) {
			State current = queue.remove();

			int np1 = current.position - 1;
			// 현재까지 갱신된 최적 경로로 다음 지점 도달 최소 시간 visited[np1]
			// > 현재 경로로 다음 지점 도달 시간 current.time + 1
			if (isValid(np1) && visited[np1] > current.time + 1) {
				visited[np1] = current.time + 1;
				queue.add(new State(np1, visited[np1]));
			}

			int np2 = current.position + 1;
			if (isValid(np2) && visited[np2] > current.time + 1) {
				visited[np2] = current.time + 1;
				queue.add(new State(np2, visited[np2]));
			}

			int np3 = current.position * 2;
			if (isValid(np3) && visited[np3] > current.time) {
				visited[np3] = current.time;			// x 2 순간 이동은 시간 그대로
				queue.add(new State(np3, visited[np3]));
			}
		}

		minTime = visited[k];
	}

	static boolean isValid(int position) {
		return 0 <= position && position <= MAX_POSITION;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		if (n >= k)
			minTime = n - k;	// -1 칸씩 n-k 번 이동하는 한 가지
		else
			bfs();

		System.out.println(minTime);
	}
}

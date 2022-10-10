package Samsung_Coding_Test.연구소_3;
import java.io.*;
import java.util.*;

/*
- 바이러스: 활성 상태, 비활성 상태
- 최초: 모든 바이러스가 비활성 상태
- 활성 상태인 바이러스는 상하좌우 인접한 모든 빈 칸으로 동시에 복제됨
  => 1초 걸림
- 승원이가 바이러스 m개를 활성 상태로 변경하려고 함
- 각 칸: 빈 칸(0) / 벽(1) / 바이러스(2)
- 활성 바이러스가 비활성 바이러스 칸으로 가면,
  해당 비활성 바이러스는 활성으로 변함

* 출력: 모든 빈 칸에 바이러스를 퍼뜨리는 최소 시간 (불가능한 경우, -1)
	   => 주어진 k개 바이러스 중, 활성시킬 바이러스 m개를 적절히 선택
*/

/*
1. 아이디어
 > 조합(백트래킹 + 브루트 포스): 전체 k개 바이러스에서 활성화 시킬 m개 선택
 > BFS: 바이러스 퍼뜨리기

1) 활성화 시킬 바이러스 m개 선택
 - void backtrack(int virusIdx, int selectCnt)
 - 재귀함수 종료 조건: selectCnt == m
   => 활성화 시킬 바이러스 m개 선택 완료한 경우
 - for문으로 i = virustIdx ~ 전체 바이러스 개수만큼 반복
   => 해당 [i]번 바이러스를 선택 O or 선택 X

2) 바이러스 퍼뜨리기
 - 활성화 선택한 m개 바이러스를 방문 처리, 큐에 추가
 - BFS 수행 (Queue가 Empty 할 때까지 반복)
   ① 인접 칸을 아직 방문 안했고, 빈 칸인 경우
     - 방문 처리 및 큐에 추가
     - emptyCnt -1 감소 처리
   ② 인접 칸을 아직 방문 안했고, 바이러스 칸인 경우
     - 방문 처리 및 큐에 추가
       => 바이러스 칸 지나가기
     ※ 활성 바이러스가 비활성 바이러스가 있는 칸으로 가면, 비활성 바이러스가 활성으로 변함
   > emptyCnt == 0 인 경우, while문 break 및 current.time + 1 반환


2. 자료구조
 - int[][] map
 - int emptyCnt: 빈 칸 개수 저장
 - List<Node> virusList: 입력 전체 k개 바이러스 위치 저장
 - boolean[] selected: 활성화 시킬 바이러스 선택 표시
 - boolean[][] visited: BFS 방문 처리
 - Queue<Node>, LinkedList<Node>: BFS 수행


3. 시간 복잡도
 - 전체 k개 바이러스에서 m개 선택하여 조합 구성 (순서 상관 X, 중복 X)
   => C(k, m)
 - 전체 최대 경우의 수 = C(10, 5) = 212개
*/

class Node {
	public int y, x;
	public int time;

	public Node(int y, int x, int time) {
		this.y = y;
		this.x = x;
		this.time = time;
	}
}

public class Main {
	static int n;					// n x n 맵
	static int m;					// 활성시킬 바이러스 m개 선택
	static int[][] map;
	static int minTime = Integer.MAX_VALUE;		// 출력

	static int emptyCnt;			// 맵에서 빈 칸 개수
	static List<Node> virusList = new ArrayList<>();	// 전체 k개 바이러스 위치
	static boolean[] selected;		// 활성화 시킬 바이러스 선택 표시

	static boolean[][] visited;
	static Queue<Node> queue = new LinkedList<>();
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };
	static final int EMPTY = 0, WALL = 1, VIRUS = 2;

	static void backtrack(int vIdx, int selectCnt) {
		// 활성화 시킬 바이러스 m개 선택 완료한 경우
		if (selectCnt == m) {
			// minTime 갱신
			int time = spreadVirus();
			if (time != -1) {
				minTime = Math.min(minTime, time);
			}

			return;
		}

		// 활성화 시킬 바이러스 선택
		for (int i = vIdx; i < virusList.size(); i++) {
			// 선택 O
			selected[i] = true;
			backtrack(i + 1, selectCnt + 1);

			// 선택 X
			selected[i] = false;
		}
	}

	/* 바이러스 퍼뜨리기 */
	static int spreadVirus() {
		int copyEmptyCnt = emptyCnt;
		int[][] copyMap = new int[n][n];	// map copy
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				copyMap[i][j] = map[i][j];
			}
		}

		visited = new boolean[n][n];		// Init
		queue = new LinkedList<>();

		// 선택한 활성화 시킬 바이러스 칸 => 방문 처리 및 큐에 추가
		for (int vIdx = 0; vIdx < virusList.size(); vIdx++) {
			if (selected[vIdx]) {
				Node current = virusList.get(vIdx);

				visited[current.y][current.x] = true;
				queue.add(new Node(current.y, current.x, 0));
				// 활성화 시킨 바이러스 칸: 시간 0으로 설정
			}
		}

		while (!queue.isEmpty()) {
			Node current = queue.remove();

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (!isValid(ny, nx) || visited[ny][nx])
					continue;

				// 인접 칸이 빈 칸인 경우, 바이러스 퍼뜨림
				if (copyMap[ny][nx] == EMPTY) {
					visited[ny][nx] = true;
					queue.add(new Node(ny, nx, current.time + 1));

					copyEmptyCnt--;
				}
				// 인접 칸이 바이러스 칸인 경우, 지나감
				// => 활성 바이러스가 비활성 바이러스가 있는 칸으로 가면, 비활성 바이러스가 활성으로 변함
				else if (copyMap[ny][nx] == VIRUS) {
					visited[ny][nx] = true;
					queue.add(new Node(ny, nx, current.time + 1));
				}

				if (copyEmptyCnt == 0)
					return current.time + 1;
			}
		}

		return -1;
	}

	static boolean isValid(int y, int x) {
		return (0 <= y && y < n) && (0 <= x && x < n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());

				if (map[i][j] == EMPTY) {
					emptyCnt++;
				}
				else if (map[i][j] == VIRUS) {
					virusList.add(new Node(i, j, VIRUS));
				}
			}
		}

		// 예외 처리: 기존 빈 칸이 없는 경우, 출력 0초
		if (emptyCnt == 0) {
			System.out.println(0);
			return;
		}

		selected = new boolean[virusList.size()];
		backtrack(0, 0);

		if (minTime == Integer.MAX_VALUE) {
			System.out.println(-1);
		}
		else {
			System.out.println(minTime);
		}
	}
}

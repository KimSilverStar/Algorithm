package Samsung_Coding_Test.인구_이동;
import java.io.*;
import java.util.*;

/*
- n x n 맵
- 맵 1칸 당 1개 나라
- 인접한 나라 사이에는 국경선 존재

* 하루 동안 수행되는 인구 이동 로직 (인구 이동이 없을 때까지 반복)

1) l <= 국경선을 공유하는 두 나라의 인구 차이 <= r 인 경우
  - 두 나라가 공유하는 국경선을 오늘 하루 동안 오픈

2) 위 조건을 만족하는 국경선을 모두 오픈한 경우
  - 인구 이동 시작

3) 국경선이 열려있어 인접한 칸만으로 이동 가능한 경우
  - 해당 나라를 오늘 하루 동안 '연합'이라고 함

4) 연합을 이루는 각 칸의 인구 수 = (연합의 총 인구 수) / (연합을 이루고 있는 칸의 수)

5) 연합을 해체하고, 모든 국경선을 닫음
*/

/*
1. 아이디어
 > 구현, 시뮬레이션
 > BFS: 국경선 오픈 및 같은 연합인 칸들 찾기

* 인구 이동이 없을 때까지 반복
 - breakFlag == true인 경우, 반복 종료
** 2중 for문으로 각 나라 칸들 차례로 확인

1) 오픈 가능한 국경선을 따라가며 연합을 탐색
  - BFS 수행
    > 연합의 총 인구 수, 연합을 이루는 나라 칸 수 저장
    > 연합을 이루는 칸을 리스트에 저장
  - l <= |map[y][x] - map[ny][nx]| <= r 인 경우, 탐색 확장

2) 저장한 연합 칸 리스트를 통해 인구 이동 수행
  - 리스트에 저장된 연합 칸 개수 > 1인 경우
    > 인구 이동 수행
    > breakFlag = false 처리


2. 자료구조
 - int[][] map: 각 나라 칸의 인구 수
 - boolean[][] visited
 - Queue<Node>, LinkedList<Node>: BFS 수행
   ※ Node: 현재 탐색 지점 (y, x)
 - List<Node>, ArrayList<Node>: BFS 탐색한 연합 칸들 저장
*/

class Node {
	public int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static int n;						// n x n 맵
	static int l, r;					// 국경선을 공유하는 두 나라의 인구 차이가 l명 이상, r명 이하
	static int[][] map;
	static int numOfDay;				// 출력, 인구 이동이 발생하는 일수 (2,000 이하)

	static boolean breakFlag;			// 인구 이동 반복 종료 플래그 변수
	static boolean[][] visited;
	static Queue<Node> queue = new LinkedList<>();
	static List<Node> list = new ArrayList<>();		// 탐색한 연합 칸들 저장
	static int unionPopulation, unionAreaCnt;		// 연합의 총 인구 수, 연합의 나라 칸 수

	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void solution() {
		// 인구 이동이 없을 때까지 반복
		while (true) {
			breakFlag = true;				// Init
			visited = new boolean[n][n];

			for (int y = 0; y < n; y++) {
				for (int x = 0; x < n; x++) {
					// 1) 오픈 가능한 국경선을 따라가며 연합을 탐색
					if (!visited[y][x]) {
						list = new ArrayList<>();

						unionPopulation = map[y][x];
						unionAreaCnt = 1;

						visited[y][x] = true;
						queue.add(new Node(y, x));
						list.add(new Node(y, x));	// 연합에 속하는 나라 칸 추가
						bfs();

						// 2) 저장한 연합 칸 리스트를 통해 인구 이동 수행
						if (list.size() > 1) {
							movePopulation();
							breakFlag = false;
						}
					}
				}
			}

			if (breakFlag)
				break;

			numOfDay++;
		}
	}

	static void bfs() {
		while (!queue.isEmpty()) {
			Node current = queue.remove();

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (!isValid(ny, nx) || visited[ny][nx])
					continue;

				// 인접 나라 칸끼리 인구 차이
				int diff = Math.abs(map[current.y][current.x] - map[ny][nx]);
				if (l <= diff && diff <= r) {
					visited[ny][nx] = true;
					queue.add(new Node(ny, nx));
					list.add(new Node(ny, nx));

					unionPopulation += map[ny][nx];
					unionAreaCnt++;
				}
			}
		}
	}

	static void movePopulation() {
		// 연합을 이루는 각 칸의 인구 수 = (연합의 총 인구 수) / (연합을 이루고 있는 칸의 수)
		int movedPopulation = unionPopulation / unionAreaCnt;

		for (Node n : list) {
			map[n.y][n.x] = movedPopulation;
		}
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
		l = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());

		map = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		solution();

		System.out.println(numOfDay);
	}
}

package Samsung_Coding_Test.상어_중학교;
import java.io.*;
import java.util.*;

/*
- 블록: 검은색(-1), 무지개(0), 일반(1 ~ m)
- 블록 그룹: 연결된 2개 이상의 블록들
  > 일반 블록(1 ~ m)이 1개 이상 존재해야 함
  > 일반 블록의 색이 모두 동일해야 함
  > 검은색 블록(-1)은 포함되면 안되고, 무지개 블록(0)은 포함 가능
  => 블록 그룹 = 같은 색상의 일반 블록 + 무지개 블록(0)으로 구성된 2개 이상의 블록 집합
- 블록 그룹의 기준 블록 = 일반 블록 중, 행 번호가 가장 작은 블록 -> 열 번호가 가장 작은 블록

오토 플레이: 블록 그룹이 존재하는 동안 반복
1) 크기가 가장 큰 블록 그룹 찾기
 - 동일 크기 블록 그룹이 여러 개인 경우, 무지개 블록(0) 개수가 많은 블록 그룹
   -> 기준 블록의 행 번호가 큰 것 -> 기준 블록의 열 번호가 큰 것
2) 1)에서 찾은 블록 그룹의 모든 블록 제거
 - 제거한 (블록 수)^2 점 획득
3) 격자에 중력 작용
 - 검은색 블록을 제외한 모든 블록(무지개, 일반 블록)이 행 번호가 큰 칸(아래 칸)으로 이동
 - 이동: 다른 블록 or 격자 경계를 만나기 전까지 계속됨
4) 격자 90도 반시계 방향 회전
5) 격자에 중력 작용
 - 검은색 블록을 제외한 모든 블록(무지개, 일반 블록)이 행 번호가 큰 칸(아래 칸)으로 이동
 - 이동: 다른 블록 or 격자 경계를 만나기 전까지 계속됨

- 출력: 최종 획득 점수 합
*/

/*
1. 아이디어
 - BFS, 구현, 시뮬레이션

블록 그룹의 기준 블록 = 일반 블록 중, 행 번호가 가장 작은 블록 -> 열 번호가 가장 작은 블록

오토 플레이: 블록 그룹이 존재하는 동안 반복

1) 크기가 가장 큰 블록 그룹 찾기
 - 동일 크기 블록 그룹이 여러 개인 경우, 무지개 블록 개수가 많은 블록 그룹
   -> 기준 블록의 행 번호가 큰 것 -> 기준 블록의 열 번호가 큰 것
 - map[0][0] ~ map[n-1][n-1] 차례로 확인, map[i][j] 지점에서 BFS 탐색 시작
   => bfs_findBlockGroup() 로 탐색한 BlockGroup을 PriorityQueue<BlockGroup>에 추가
 - PriorityQueue<BlockGroup>에서 remove한 BlockGroup 선택
   => PriorityQueue.isEmpty()인 경우, 오토플레이 반복 종료 및 출력

2) 1)에서 찾은 블록 그룹의 모든 블록 제거, 제거한 (블록 수)^2 점 획득
 - BFS 탐색 수행하며 블록 제거
   => bfs_deleteBlockGroup()
 - 1)에서 찾은 BlockGroup의 블록 크기 (groupSize)^2 을 출력 변수에 더함

3) 격자에 중력 작용
 - 검은색 블록을 제외한 모든 블록(무지개, 일반 블록)이 행 번호가 큰 칸(아래 칸)으로 이동
 - 이동: 다른 블록 or 격자 경계를 만나기 전까지 계속됨
 - 격자 맵 1열씩 확인: 아래에서 두 번째 칸부터 윗 칸으로 확인, j = n-2 ~ 0
   => map[j][i]에 대해 아랫 칸 map[j-1][i]이 삭제된 칸(DELETED)인 경우,
      map[j][i]의 블록이 아랫 칸 map[j-1][i]으로 내려감 (map[j-1][i] = map[j][i])

4) 격자 90도 반시계 방향 회전
 - int[][] rotatedMap 에 회전한 맵 저장 후, 기존 map 에 복사
 - map[0][0] ~ map[n-1][n-1] 확인
   => rotatedMap[n-1-j][i] = map[i][j];

5) 격자에 중력 작용


※ 오답 노트

1) 방문 처리 배열
 - 무지개 블록은 블록 그룹 간에 중복 가능하므로,
   무지개 블록의 방문 처리를 구분해야 함
 - 2중 for문에서 블록 그룹을 찾는 BFS를 호출할 때(BFS 바깥)의 방문 처리와
   블록 그룹을 찾는 BFS 내부에서의 방문 처리를 구분해야 함
   ① boolean[][] visited1
     - 일반 블록만 방문 처리 (무지개 블록은 방문 처리 X)
       => 블록 그룹 간에 무지개 블록이 중복 가능함을 고려
     - 블록 그룹을 찾는 BFS 바깥에서 해당 지점 방문 여부 확인
       => map[y][x] >= 1 (일반 블록)이고 !visited1[y][x] 인 경우, BFS 탐색 시작
     - BFS 탐색 중, 무지개 블록은 방문 처리 X, 일반 블록만 방문 처리
       => 한 블록 그룹에 대해 BFS 탐색이 끝나면, 해당 블록 그룹의 일반 블록만 방문 처리됨
   ② boolean[][] visited2
     - 일반 블록 + 무지개 블록 방문 처리
     - 블록 그룹을 찾는 BFS 내부에서 해당 지점 방문 여부 확인
       => BFS 탐색 시작할 때, visited2 초기화
       => map[y][x] >= 0 (무지개 or 일반 블록)이고 !visited2[y][x] 인 경우, BFS 탐색 확장
     - map[y][x] == 0 (무지개 블록) 이면, visited2[y][x] = true; 처리
     - map[y][x] >= 1 (일반 블록) 이면, visited1[y][x] = true; visited2[y][x] = true; 처리
     - BFS 탐색 중, 일반 블록과 무지개 블록 모두 방문 처리

2) 방문 처리 배열, 큐 초기화 시점 순서 처리

3) 블록 그룹 찾는 BFS 메소드에서 블록 그룹 크기 groupSize 초기화 실수
 - 블록 그룹 찾는 BFS 메소드에 들어온 순간(BFS 탐색을 시작한 순간)
   = 이미 일반 블록 1개를 찾은 시점
 - groupSize = 0; 이 아닌, groupSize = 1; 로 초기화 해야함

4) 격자 맵에 중력 작용 메소드
 - while문 내부에서, curRow 사용 안한 실수


2. 자료구조
 - boolean[][] visited1: 블록 그룹을 찾는 BFS 바깥에서 일반 블록만 방문 처리
 - boolean[][] visited2: 블록 그룹을 찾는 BFS 내부에서 일반 + 무지개 블록 방문 처리
 - boolean[][] visited3: 찾은 블록 그룹을 삭제하는 BFS에서 블록 방문 처리
 - Queue<Node>, LinkedList<Node>: BFS 수행
   => Node: 현재 위치 (y, x)
 - PriorityQueue<BlockGroup>: 블록 그룹의 정보 BlockGroup들을 기준에 따라 정렬하여, 오토플레이할 블록 그룹 선택
   ※ BlockGroup: 블록 그룹 크기, 무지개 블록 개수, 기준 블록 위치
*/

class Node {
	public int y, x;			// 현재 탐색 위치

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

class BlockGroup implements Comparable<BlockGroup> {
	public int groupSize;				// 블록 그룹 크기 (일반 블록 개수 + 무지개 블록 개수)
	public int rainbowCnt;				// 무지개 블록 개수
	public int standardY, standardX;	// 기준 블록 위치

	public BlockGroup(int groupSize, int rainbowCnt, int standardY, int standardX) {
		this.groupSize = groupSize;
		this.rainbowCnt = rainbowCnt;
		this.standardY = standardY;
		this.standardX = standardX;
	}

	// 블록 그룹 크기(일반 블록 개수 + 무지개 블록 개수)가 큰 순 -> 무지개 블록 개수가 많은 순
	// -> 기준 블록의 행 번호가 큰 순 -> 기준 블록의 열 번호가 큰 순
	public int compareTo(BlockGroup o) {
		if (this.groupSize != o.groupSize) {
			return o.groupSize - this.groupSize;
		}
		if (this.rainbowCnt != o.rainbowCnt) {
			return o.rainbowCnt - this.rainbowCnt;
		}
		if (this.standardY != o.standardY) {
			return o.standardY - this.standardY;
		}
		return o.standardX - this.standardX;
	}
}

public class Main {
	static int n;			// n x n 행렬
	static int m;			// m개 일반 블록 색상 (1 ~ m)
	static int[][] map;
	static int sumPoint;	// 출력, 획득한 점수 합

	static final int RAINBOW = 0;			// 무지개 블록
	static final int DELETED = -999;		// 제거된 블록 표시
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	// visited1: 블록 그룹 찾기할 때, BFS 바깥에서 방문 처리 (무지개 블록은 방문 처리 X)
	// visited2: 블록 그룹을 찾는 BFS 안에서 방문 처리 (해당 블록 그룹의 모든 블록(일반, 무지개)에 대해 방문 처리)
	// visited3: 블록 그룹을 삭제하는 BFS에서 방문 처리
	static boolean[][] visited1;
	static boolean[][] visited2;
	static boolean[][] visited3;
	static Queue<Node> queue;
	static PriorityQueue<BlockGroup> pq = new PriorityQueue<>();	// Node 정렬하여, 규칙에 따라 블록 그룹 선택

	static void solution() {
		// 오토 플레이: 블록 그룹이 존재하는 동안 반복
		while (true) {
			// 1) 규칙 우선순위에 따라 삭제할 블록 그룹 찾기
			visited1 = new boolean[n][n];		// 무지개 블록은 방문 처리 X

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					// 아직 방문 안했고 일반 블록인 경우, BFS 탐색 시작
					if (!visited1[i][j] && map[i][j] >= 1) {
						bfs_findBlockGroup(i, j, map[i][j]);	// PQ에 탐색한 블록 그룹 추가
					}
				}
			}

			if (pq.isEmpty()) {		// 블록 그룹이 더 이상 없는 경우, 종료 및 출력
				break;
			}

			BlockGroup blockGroup = pq.remove();		// 삭제할 블록 그룹
			pq.clear();

			// 2) 찾은 블록 그룹의 모든 블록 제거, 제거한 (블록 수)^2 점 획득
			bfs_deleteBlockGroup(blockGroup.standardY, blockGroup.standardX);
			sumPoint += (blockGroup.groupSize * blockGroup.groupSize);

			// 3) 격자 맵에 중력 작용
			fallGravity();

			// 4) 격자 맵 90도 반시계 방향 회전
			rotateCounterClock();

			// 5) 격자 맵에 중력 작용
			fallGravity();
		}
	}

	/** 블록 그룹(normalColor 색상의 일반 블록 + 무지개 블록)을 BFS 탐색
	 * - (standardY, standardX): 기준 블록 위치, BFS 탐색 시작 위치 */
	static void bfs_findBlockGroup(int standardY, int standardX, int normalColor) {
		int groupSize = 1;		// 블록 그룹 크기 (일반 블록 개수 + 무지개 블록 개수)
		int rainbowCnt = 0;		// 무지개 블록 개수

		visited2 = new boolean[n][n];
		queue = new LinkedList<>();

		visited1[standardY][standardX] = true;
		visited2[standardY][standardX] = true;
		queue.add(new Node(standardY, standardX));

		while (!queue.isEmpty()) {
			Node current = queue.remove();

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (!isValid(ny, nx) || visited2[ny][nx])
					continue;

				// 색상이 같은 일반 블록 or 무지개 블록인 경우, 탐색 확장 (블록 그룹에 포함)
				if (map[ny][nx] == normalColor) {
					groupSize++;
					visited1[ny][nx] = true;
					visited2[ny][nx] = true;
					queue.add(new Node(ny, nx));
				}
				else if (map[ny][nx] == RAINBOW) {
					groupSize++;
					rainbowCnt++;
					// visited1: 무지개 블록은 방문 처리 X => 블록 그룹 간에 무지개 블록 중복 가능
					visited2[ny][nx] = true;
					queue.add(new Node(ny, nx));
				}
			}
		}

		if (groupSize >= 2) {		// 블록 그룹은 2개 이상의 블록으로 구성되어야 함
			pq.add(new BlockGroup(groupSize, rainbowCnt, standardY, standardX));
		}
	}

	/** 블록 그룹의 블록들을 BFS로 탐색하며 삭제 */
	static void bfs_deleteBlockGroup(int y, int x) {
		visited3 = new boolean[n][n];
		queue = new LinkedList<>();

		// blockGroup의 일반 블록 색상
		int normalColor = map[y][x];

		// blockGroup의 기준 블록 위치 (standardY, standardX)에서 BFS 탐색 시작
		map[y][x] = DELETED;		// 블록 삭제
		visited3[y][x] = true;
		queue.add(new Node(y, x));

		while (!queue.isEmpty()) {
			Node current = queue.remove();

			for (int i = 0; i < 4; i++) {
				int ny = current.y + dy[i];
				int nx = current.x + dx[i];

				if (!isValid(ny, nx))
					continue;

				if (visited3[ny][nx])
					continue;

				if (map[ny][nx] == normalColor || map[ny][nx] == RAINBOW) {
					map[ny][nx] = DELETED;			// 블록 삭제
					visited3[ny][nx] = true;
					queue.add(new Node(ny, nx));
				}
			}
		}
	}

	/** 격자 맵에 중력 작용
	 * - 검은색 블록을 제외한 모든 블록(무지개, 일반 블록)이 행 번호가 큰 칸(아래 칸)으로 이동*/
	static void fallGravity() {
		// 격자 맵 1열씩 확인: [0]열 ~ [n-1]열 확인
		for (int col = 0; col < n; col++) {
			// 아래에서 두 번째 칸부터 윗 칸으로 확인: [n-2]행 ~ [0]행
			for (int row = n - 2; row >= 0; row--) {
				if (map[row][col] >= RAINBOW) {                // 무지개 or 일반 블록인 경우, 중력 작용
					if (map[row + 1][col] == DELETED) {        // 블록 아래 칸이 삭제된 칸인 경우
						int curRow = row;		// 중력 작용하는 블록(일반 or 무지개)의 행
						int nRow = row + 1;		// 중력 작용하는 블록 아랫 칸에 있는 삭제된 칸의 행

						// 중력이 작용하는 블록의 아래 칸에 다른 블록 or 격자가 나올 때까지 중력 작용 반복
						while (nRow < n && map[nRow][col] == DELETED) {
							map[nRow][col] = map[curRow][col];
							map[curRow][col] = DELETED;

							curRow++;
							nRow++;
						}
					}
				}
			}
		}
	}

	/** 격자 맵 90도 반시계 방향으로 회전 */
	static void rotateCounterClock() {
		// rotatedMap에 회전한 맵 저장 후, 기존 map에 복사
		int[][] rotatedMap = new int[n][n];
		for (int i = 0 ; i < n; i++) {
			for (int j = 0; j < n; j++) {
				rotatedMap[n - 1 - j][i] = map[i][j];
				// rotatedMap[i][j] = map[j][n - 1 - i];
			}
		}
		map = rotatedMap;
	}

	static boolean isValid(int ny, int nx) {
		return (0 <= ny && ny < n) && (0 <= nx && nx < n);
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
			}
		}

		solution();

		System.out.println(sumPoint);
	}
}

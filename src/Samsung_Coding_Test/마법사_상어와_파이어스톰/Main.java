package Samsung_Coding_Test.마법사_상어와_파이어스톰;
import java.io.*;
import java.util.*;

/*
- 2^n x 2^n 격자 맵

파이어스톰 시전
1) 전체 맵을 2^L x 2^L 부분 격자로 나눔
2) 분할된 부분 격자 단위로 시계 방향 90도 회전
3) 인접한 얼음 칸 개수가 3개 미만이면, 해당 칸의 얼음 양 -1
  - map[i][j]의 상하좌우 위치에 얼음이 있는 칸이 3개 미만이면, map[i][j]--;

- 출력1: 전체 남은 얼음 양
- 출력2: 가장 큰 덩어리가 차지하는 칸의 개수
  => 인접하여 연결된 얼음 칸 개수
*/

/*
1. 아이디어
 > 구현, 시뮬레이션, BFS

1) map을 2^L x 2^L 부분 격자로 나눈 후, 부분 격자 단위로 시계 방향 90도 회전
 - 2중 for문으로 map 확인
   => i, j = 0 ~ len 반복, 2^L 만큼 증감
   rotatePartMap(i, j, 2^L) 호출
 ※ rotatePartMap(int startY, int startX, int partLen)		// partLen = 2^L

2) [i][j]의 인접 위치에 얼음 칸이 3개 미만이면, 해당 위치 얼음 감소
 - 2중 for문으로 rotatedMap[i][j]에 인접한 위치의 얼음 칸 개수 count
 ※ 얼음 개수를 바로 감소 시키면 안됨 !!
   => boolean[][] checkMelted에 감소시킬 얼음 위치 표시 후, 한꺼번에 처리

3) 가장 큰 덩어리가 차지하는 칸의 개수 maxIceMapCnt 세기
 - 2중 for문으로 map 전체 확인
 - map[i][j] > 0 (얼음 존재)인 경우, BFS 탐색 시작
 - 다음 인접 지점 map[ny][nx]에 대해
   아직 방문 안했고, 다음 지점 map[ny][nx]에 얼음이 존재하는 경우 탐색 확장


2. 자료구조
 - boolean[][] checkMelted: 얼음 감소시킬 위치 표시 후, 한꺼번에 얼음 감소 처리
 - boolean[][] visited
 - Queue<Node>, LinkedList<Node>: BFS 수행
   => Node: (y, x)
*/

class Node {
	public int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static int n;				// 2^n x 2^n 격자 맵
	static int q;				// 파이어스톰 q번 시전
	static int len;				// 격자 맵 세로, 가로 길이 len = 2^n
	static int[][] map;
	static int[][] rotatedMap;
	static int[] lValues;		// 각 시전 단계의 L 값

	static int totalIceCnt;		// 출력1, 전체 얼음 양
	static int maxIceMapCnt;	// 출력2, 최대 얼음 덩어리의 칸 개수

	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };
	static boolean[][] checkMelted;
	static boolean[][] visited;
	static Queue<Node> queue;

	static void solution() {
		for (int l : lValues) {
			int partLen = (int) Math.pow(2, l);		// 부분 격자 크기: 2^L

			// 1) 2^L x 2^L 부분 격자 단위로 90도 회전
			rotatedMap = new int[len][len];			// init
			for (int i = 0; i < len; i += partLen) {
				for (int j = 0; j < len; j += partLen)  {
					rotatePartMap(i, j, partLen);	// 부분 격자 1개 90도 회전
				}
			}

			// 2) [i][j]의 인접 위치에 얼음 칸이 3개 미만이면, 해당 위치 얼음 감소
			checkMelted = new boolean[len][len];	// 얼음 감소시킬 위치 표시 후, 한꺼번에 처리
			for (int i = 0; i < len; i++) {
				for (int j = 0; j < len; j++) {
					if (getNearbyIceMapCnt(i, j) < 3 && rotatedMap[i][j] > 0) {
						checkMelted[i][j] = true;
					}
				}
			}

			for (int i = 0; i < len; i++) {
				for (int j = 0; j < len; j++) {
					if (checkMelted[i][j]) {
						rotatedMap[i][j]--;
						totalIceCnt--;
					}
				}
			}

			// map에 rotatedMap 복사
			for (int i = 0; i < len; i++) {
				for (int j = 0; j < len; j++) {
					map[i][j] = rotatedMap[i][j];
				}
			}
		}

		queue = new LinkedList<>();
		visited = new boolean[len][len];
		// 3) 가장 큰 덩어리가 차지하는 칸의 개수 maxIceMapCnt 세기
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (!visited[i][j] && map[i][j] > 0) {
					visited[i][j] = true;
					queue.add(new Node(i, j));
					int iceMapCnt = bfs();

					maxIceMapCnt = Math.max(maxIceMapCnt, iceMapCnt);
				}
			}
		}
	}

	 /* partLen = 2^L */
	 static void rotatePartMap(int startY, int startX, int partLen) {
		 for (int i = 0; i < partLen; i++) {
			 for (int j = 0; j < partLen; j++) {
				 rotatedMap[startY + j][startX + partLen - 1 - i] =
						 map[startY + i][startX + j];
			 }
		 }
	 }

	 /* (y, x) 위치의 인접한 얼음 칸이 몇 개인지 반환 (0 ~ 4) */
	 static int getNearbyIceMapCnt(int y, int x) {
		 int cnt = 0;

		 for (int i = 0; i < 4; i++) {
			 int ny = y + dy[i];
			 int nx = x + dx[i];

			 if (!isValid(ny, nx))
				 continue;

			 if (rotatedMap[ny][nx] > 0)
				 cnt++;
		 }

		 return cnt;
	 }

	 static int bfs() {
		 int iceMapCnt = 1;

		 while (!queue.isEmpty()) {
			 Node current = queue.remove();

			 for (int i = 0; i < 4; i++) {
				 int ny = current.y + dy[i];
				 int nx = current.x + dx[i];

				 if (!isValid(ny, nx))
					 continue;

				 //	다음 지점을 아직 방문 안했고, 다음 지점이 얼음 칸인 경우
				 if (!visited[ny][nx] && map[ny][nx] > 0) {
					 visited[ny][nx] = true;
					 queue.add(new Node(ny, nx));
					 iceMapCnt++;
				 }
			 }
		 }

		 return iceMapCnt;
	 }

	 static boolean isValid(int y, int x) {
		 return (0 <= y && y < len) && (0 <= x && x < len);
	 }

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		q = Integer.parseInt(st.nextToken());
		len = (int) Math.pow(2, n);

		map = new int[len][len];
		for (int i = 0; i < len; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < len; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				totalIceCnt += map[i][j];
			}
		}

		st = new StringTokenizer(br.readLine());
		lValues = new int[q];
		for (int i = 0; i < q; i++) {
			lValues[i] = Integer.parseInt(st.nextToken());
		}

		solution();

		System.out.println(totalIceCnt + "\n" + maxIceMapCnt);
	}
}

package DFS_BFS.숨바꼭질2;
import java.io.*;
import java.util.*;

/*
- 수빈, 동생: 0 ~ 100,000 위치
- 수빈 이동
  1) +1 or -1
  2) x 2
=> 수빈이가 동생을 따라잡기 위해 필요한 최소 시간, 방법 수 구하기
*/

/*
1. 아이디어
 - BFS 로 최소 시간 갱신해가면서 탐색
 - 이미 탐색한 위치를 다시 탐색하는 경우
   => 최소 시간이 아니므로 탐색 제외
   e.g. 1 -> 2 -> 1
 - 탐색 종료 조건
   => 소요된 시간 > 최소 시간인 경우
 - 다음 탐색 지점 추가
   1) 다음 후보 지점이 범위 안에 있고, 아직 방문 안한 경우
   2) 다음 후보 지점을 이미 방문 했더라도 이동 시간이 해당 지점으로의 최소 이동 시간과 같은 경우
     => 다른 경로로 같은 최소 시간으로, 같은 지점을 가는 경우
     e.g. start -> 1 -> 2 -> 3
     	  start -> 1 -> 4 -> 3

  !!! DFS 가 아닌 BFS 를 사용한 이유
     - 탐색으로 목표 지점(동생)을 찾은 경우,
       해당 경우로 찾은 탐색 시간 = 트리의 Depth (Level)
       => BFS 는 같은 Depth 를 순차적으로 탐색하므로,
          탐색 시작 후 맨 처음 찾은 탐색 Depth 를 최소 시간으로 설정하면 됨
       => 이후, 찾은 최소 시간 (최소 Depth)만 마저 탐색을 수행하고
          더 깊은 Depth 로는 탐색을 하지 않아도 됨
          (더 깊은 Depth 에서 찾을 수 있더라도, 어차피 최소 시간이 아니므로)
     - 또한 DFS 를 사용하면, 모든 경우의 수를 다 찾아보고,
       그 중에서 최소 시간을 찾아야 함
       => 모든 경우의 수를 전부 확인하기 전까지는 최소 시간을 모름

2. 자료구조
 - Queue<>, LinkedList<>: BFS 수행
 - boolean[]: 방문 확인
 - int[]: 해당 위치까지 도달하는 데 걸리는 최소 시간 갱신해나감

3. 시간 복잡도
 - 인접 리스트 DFS / BFS 의 시간 복잡도: O(V + E)
   => V: 10만 개, E: 한 vertex 당 3개 edge (+1, -1, *2)
   => O(V + E) = O(V + 3V) = O(4V)
   => 4 x 10^5 << 2억 (2초)
*/

/* 오답 노트 1
 - DFS 의 재귀 함수 안에서, 재귀 호출이 3번 이상 이루어지면
   Stack Overflow 발생 가능성 높음
   => DFS 가 아닌, BFS 방식으로 다시 접근해볼 것
*/

/* 오답 노트 2
 1) BFS 풀이로 메모리 초과 발생
   => 조건이 만족되지 않는 부분을 탐색하지 말아야 하는데, 탐색 해버림
   => 탐색 제한하는 조건이 빠짐
   => 최종 해결 방안) boolean[] check = new boolean[100001] 사용
   
 2) 다음 탐색 지점 추가 시, 중복 방문이 허용되는 경우가 존재
   => 이미 방문한 지점이더라도, 최소 시간(거리)로 도달하는 경우
   1. 다음 후보 지점이 범위 안에 있고, 아직 방문 안한 경우
   2. 다음 후보 지점을 이미 방문 했더라도 이동 시간이 해당 지점으로의 최소 이동 시간과 같은 경우
      => 다른 경로로 같은 최소 시간으로, 같은 지점을 가는 경우
      e.g. start -> 1 -> 2 -> 3
       	   start -> 1 -> 4 -> 3
*/

class Pair {
	private int position;		// 위치
	private int time;			// 소요 시간

	public Pair(int position, int time) {
		this.position = position;
		this.time = time;
	}
	public int getPosition() { return position; }
	public int getTime() { return time; }
}

public class Main {
	static int n, k;			// 입력 값: 수빈과 동생 위치
	static int minTime = Integer.MAX_VALUE;		// 최소 시간
	static int count = 0;						// 최소 시간으로 찾는 방법 수
	static final int MAX_POSITION = 100000;		// 최대 맨 끝 위치

	static Queue<Pair> queue = new LinkedList<>();
	static boolean[] check = new boolean[MAX_POSITION + 1];	// [1 ~ 10만] 사용
	static int[] minTimes = new int[MAX_POSITION + 1];		// 각 지점으로의 최소 시간 저장

	static void bfs() {
		while (!queue.isEmpty()) {
			Pair current = queue.remove();
			int position = current.getPosition();		// 현재 위치
			int time = current.getTime();				// 현재까지 소요된 시간

			// 종료 조건: 목표 지점 (동생)으로 도달한 최소 시간 < 현재 위치까지 도달한 최소 시간
			// => 더 탐색해도 목표 지점까지 최소 시간으로 도달하지 못함 (가망 없음)
			if (minTime < minTimes[position])
				return;

			// 최초로 최단 시간으로 찾은 경우
			if (position == k) {
//				minTime = Math.min(minTime, time);
				minTime = time;
				count++;
			}

			// 연산하여 다음 지점 탐색
			int nextPos1 = position - 1;
			if (0 <= nextPos1 && nextPos1 <= MAX_POSITION) {
				// 다음 지점을 방문 안했거나 or 방문 했더라도 최소 시간으로 가는 경우
				if (!check[nextPos1] || minTimes[nextPos1] == time + 1) {
					check[nextPos1] = true;
					minTimes[nextPos1] = time + 1;
					queue.add(new Pair(nextPos1, time + 1));
				}
			}
			int nextPos2 = position + 1;
			if (0 <= nextPos2 && nextPos2 <= MAX_POSITION) {
				if (!check[nextPos2] || minTimes[nextPos2] == time + 1) {
					check[nextPos2] = true;
					minTimes[nextPos2] = time + 1;
					queue.add(new Pair(nextPos2, time + 1));
				}
			}
			int nextPos3 = position * 2;
			if (0 <= nextPos3 && nextPos3 <= MAX_POSITION ) {
				if (!check[nextPos3] || minTimes[nextPos3] == time + 1) {
					check[nextPos3] = true;
					minTimes[nextPos3] = time + 1;
					queue.add(new Pair(nextPos3, time + 1));
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());		// 수빈 위치
		k = Integer.parseInt(st.nextToken());		// 동생 위치

		if (n >= k) {
			// -1 칸씩 n-k 번 이동하는 한 가지
			minTime = n - k;
			count = 1;
		}
		else {
			check[n] = true;
			minTimes[n] = 0;
			queue.add(new Pair(n, 0));
			bfs();
		}

		System.out.println(minTime);
		System.out.println(count);
	}
}

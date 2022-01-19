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
   => 다음 후보 지점이 범위 안에 있고, 아직 방문 안한 경우

2. 자료구조
 - Queue<>, LinkedList<>: BFS 수행
 - boolean[]: 방문 확인

3. 시간 복잡도
 -
*/

/* 오답 노트 1
 - DFS 의 재귀 함수 안에서, 재귀 호출이 3번 이상 이루어지면
   Stack Overflow 발생 가능성 높음
   => DFS 가 아닌, BFS 방식으로 다시 접근해볼 것
*/

/* 오답 노트 2
 - BFS 풀이로 메모리 초과 발생
   => 조건이 만족되지 않는 부분을 탐색하지 말아야 하는데, 탐색 해버림
   => 탐색 제한하는 조건이 빠짐
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
	static boolean[] check = new boolean[100001];		// [1 ~ 10만] 사용

	static void bfs() {
		queue.add(new Pair(n, 0));

		while (!queue.isEmpty()) {
			Pair current = queue.remove();
			int position = current.getPosition();		// 현재 위치
			int time = current.getTime();				// 현재까지 소요된 시간
			check[position] = true;			// 현재 위치 방문 처리

			// 탐색 종료 조건: 소요된 시간 > 최소 시간
			if (time > minTime)
				return;

			if (position == k) {						// 찾은 경우
				minTime = Math.min(minTime, time);
				count++;
			}

			// 다음 지점 탐색
			int nextPos1 = position + 1;
			int nextPos2 = position - 1;
			int nextPos3 = position * 2;

			if (nextPos1 <= MAX_POSITION && !check[nextPos1])
				queue.add(new Pair(nextPos1, time + 1));
			if (nextPos2 >= 0 && !check[nextPos2])
				queue.add(new Pair(nextPos2, time + 1));
			if (nextPos3 <= MAX_POSITION && !check[nextPos3])
				queue.add(new Pair(nextPos3, time + 1));
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());		// 수빈 위치
		k = Integer.parseInt(st.nextToken());		// 동생 위치

		if (n == k) {			// 시작하자마자 찾은 경우
			System.out.println(0);
			System.out.println(1);
			return;
		}
		else if (n > k) {		// 수빈 위치가 동생보다 더 뒤인 경우
			// -1 칸씩 n-k 번 이동하는 한 가지
			System.out.println(n - k);
			System.out.println(1);
			return;
		}

		bfs();

		System.out.println(minTime);
		System.out.println(count);
	}
}

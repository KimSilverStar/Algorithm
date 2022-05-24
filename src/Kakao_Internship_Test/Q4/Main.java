package Kakao_Internship_Test.Q4;
import java.util.*;

/*
- 쉼터 or 산봉우리에서 휴식 가능
- intensity: 휴식 없이 이동해야 하는 시간 중, 최대 시간
  => 목표: intensity 최소가 되는 코스
- 산봉우리는 경로에 1번만 포함
- 무방향 가중치 그래프
- 출입구 a -> ... -> 산봉우리 -> ... -> 출입구 a

*/

class State {
	public int pos;				// 현재 지점 번호
	public int sumitNumber;		// 거쳐온 산봉우리 번호 (안거쳤으면 -1)

	public State(int pos, int sumitNumber) {
		this.pos = pos;
		this.sumitNumber = sumitNumber;
	}
}

public class Main {
	static List<State>[] lists;
	static Queue<State> queue = new LinkedList<>();
	static boolean[] visited;

	/* n개 지점 (1 ~ n번), 간선 정보 paths[][], 출입구 번호 gates[], 산봉우리 번호 summits[]
	* 출력: 최소 intensity 코스의 산봉우리 번호, intensity 최소값
	* */
	static int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
		visited = new boolean[n + 1];			// [1] ~ [n] 사용

		lists = new ArrayList[n + 1];			// [1] ~ [n] 사용
		for (int i = 0; i <= n; i++)
			lists[i] = new ArrayList<>();

		for (int i = 0; i < paths.length; i++) {
			int v1 = paths[i][0];
			int v2 = paths[i][1];
			int cost = paths[i][2];

			lists[v1].add(new State(v2, cost));
			lists[v2].add(new State(v1, cost));
		}

		// 모든 출발 지점 (출입구 번호) 큐에 추가
		for (int gate : gates) {
			visited[gate] = true;
			queue.add(new State(gate, -1));
		}

		// BFS 수행
		while (!queue.isEmpty()) {
			State current = queue.remove();

			// lists 확인
			for (List<State> list : lists) {


			}
		}

		return new int[] {1,1};
	}

	public static void main(String[] args) {

	}
}

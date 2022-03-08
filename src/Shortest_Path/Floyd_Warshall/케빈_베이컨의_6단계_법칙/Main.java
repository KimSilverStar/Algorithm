package Shortest_Path.Floyd_Warshall.케빈_베이컨의_6단계_법칙;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 케빈 베이컨의 수 = "모든 사람과 케빈 베이컨 게임을 했을 때, 나오는 단계의 합"
 - 모든 노드 -> 나머지 모든 노드
   => 플로이드-와샬
 - [i]번 노드의 케빈 베이컨 수 = [i]행 dist[i]의 원소 합
 - 친구 관계 O or 친구 관계 X
   => 간선의 가중치 0 or 1
   => INF = 노드 최대 개수 100 x 간선 가중치 최대값 1 = 100

 1) 비용 배열 초기화
   - 노드 본인 -> 노드 본인은 0 으로 초기화
     => dist[i][i] = 0
   - 나머지는 INF 로 초기화
     => INF 값 = (노드 V 최대 개수) x (각 간선의 가중치 최대값)
 2) 비용 배열에 간선 가중치 저장
   - 노드 i -> 노드 j 로 가는 간선이 여러 개일 경우, 간선 가중치 작은 값으로 저장
 3) 3중 for문
   - 중간 경유 노드, 시작 노드, 끝 노드


2. 자료구조
 - int[][] dist: 비용 배열


3. 시간 복잡도
 - 플로이드-와샬 시간 복잡도: O(V^3)
   => V = n 최대값 대입: 100^3 = 10^6 << 2억
*/

public class Main {
	static int n, m;				// 유저(노드) 수 n, 친구 관계(간선)의 수 m
	static int minNode;				// 출력, 비용 합이 최소인 노드 번호

	static final int INF = 100;
	static int[][] dist;			// 비용 배열
	static int[] numbers;			// 각 유저의 케빈 베이컨 수

	static void floyd() {
		// 3) 3중 for문 - 중간 경유 노드, 시작 노드, 끝 노드
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					// 중간 경유 노드를 거쳐갈 때, 비용 값이 더 작은 경우 갱신
					if (dist[i][j] > dist[i][k] + dist[k][j])
						dist[i][j] = dist[i][k] + dist[k][j];
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		// 1) 비용 배열 초기화
		dist = new int[n + 1][n + 1];			// [1][1] ~ [n][n] 사용
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i == j)
					dist[i][j] = 0;
				else
					dist[i][j] = INF;
			}
		}

		// 2) 비용 배열에 간선 가중치 저장
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int startV = Integer.parseInt(st.nextToken());
			int destV = Integer.parseInt(st.nextToken());

			dist[startV][destV] = 1;	// 친구 관계: 양방향
			dist[destV][startV] = 1;
		}

		floyd();

		numbers = new int[n + 1];		// 각 유저의 케빈 베이컨 수 계산
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++)
				numbers[i] += dist[i][j];
		}

		minNode = 1;					// 케빈 베이컨 수가 최소인 유저 찾기
		for (int i = 1; i <= n; i++) {
			if (numbers[minNode] > numbers[i])
				minNode = i;
		}

		System.out.println(minNode);
	}
}

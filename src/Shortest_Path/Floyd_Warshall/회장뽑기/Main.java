package Shortest_Path.Floyd_Warshall.회장뽑기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - [i]번 회원의 점수 = 가장 먼 회원과의 거리 (최대 거리)
 - 모든 회원(노드) -> 다른 모든 회원(노드)의 거리
   => 플로이드-와샬

   1) 비용 배열 초기화
     - dist[][] 모든 원소 INF 로 초기화 후, dist[i][i] = 0
     - INF = 노드 최대 개수 50 x 간선 가중치 최대값 1 = 50
   2) 비용 배열에 간선 가중치 저장
     - 노드 i -> 노드 j 로 가는 간선이 여러 개일 경우, 더 작은 가중치로 저장
   3) 3중 for문
     - 중간 경유 노드, 시작 노드, 끝 노드
     - 중간 경유 노드를 거쳐서 갈 때, 비용이 더 작은 경우
       비용 배열 갱신


2. 자료구조
 - int[][] dist: 비용 배열
   => 최대값 INF = 50 이므로, int 가능


3. 시간 복잡도
 1) 플로이드-와샬 시간 복잡도: O(V^3)
   - V = n 최대값 대입: 50^3 = 125,000
 2) 각 회원의 점수 찾기, 후보 점수 찾기: O(n^2)
   - 50^2 = 2,500
 3) 후보 회원 찾기: O(n)
   - 50
 => 총 시간 복잡도: 125,000 + 2,500 + 50 = 127,550 << 1억
*/

public class Main {
	static int n;						// 회원 수
	static int[] scores;				// 각 회원의 점수

	static int candidateScore = Integer.MAX_VALUE;	// 후보 점수 (회원 최소 점수)
	static int candidateCount;						// 후보의 수
	static List<Integer> candidateList = new ArrayList<>();		// 후보 리스트

	static final int INF = 50;
	static int[][] dist;				// 비용 배열

	static void solution() {
		scores = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			scores[i] = dist[i][1];

			// 각 회원의 점수(최대 거리) 찾기
			for (int j = 1; j <= n; j++) {
				if (scores[i] < dist[i][j])
					scores[i] = dist[i][j];
			}

			// 후보 점수(최소 점수) 찾기
			if (candidateScore > scores[i])
				candidateScore = scores[i];
		}

		// 후보 인원 수, 후보 인원 찾기
		for (int i = 1; i <= n; i++) {
			if (candidateScore == scores[i]) {
				candidateList.add(i);
				candidateCount++;
			}
		}
	}

	static void floyd() {
		// 3) 3중 for문 - 중간 경유 노드, 시작 노드, 끝 노드
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					// 중간 경유 노드를 거쳐서 갈 때 비용이 더 작은 경우, 비용 배열 갱신
					if (dist[i][j] > dist[i][k] + dist[j][k])
						dist[i][j] = dist[i][k] + dist[j][k];
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		// 1) 비용 배열 초기화
		dist = new int[n + 1][n + 1];			// [1][1] ~ [n][n] 사용
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i != j)
					dist[i][j] = INF;
				else
					dist[i][j] = 0;
			}
		}

		// 2) 비용 배열에 간선 가중치 저장
		while (true) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());

			if (v1 == -1 && v2 == -1)
				break;

			dist[v1][v2] = 1;
			dist[v2][v1] = 1;
		}

		floyd();
		solution();

		StringBuilder sb = new StringBuilder();
		sb.append(candidateScore).append(" ").
				append(candidateCount).append("\n");
		for (int i : candidateList)
			sb.append(i).append(" ");
		System.out.println(sb);
	}
}

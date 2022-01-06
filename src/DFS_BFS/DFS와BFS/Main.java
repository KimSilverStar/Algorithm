package DFS_BFS.DFS와BFS;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - n x n 행렬에 입력 그래프 연결 상태 저장 (n: vertex 개수)
   => 인접 행렬 Adjacency List
   => 대각 행렬 형태로 저장 e.g. [1][3] 연결되면 [3][1]도 연결

 1) DFS
  - 재귀함수 종료 조건: 매 재귀에서 시작 vertex 에 연결된 vertex 가 없거나,
                     시작 vertex 로부터 연결된 모든 vertex 를 모두 방문한 경우
    <=> 탐색 더 수행해야하는 경우: 시작 vertex 로부터 연결된 vertex 가 존재하고, 연결된 vertex 를 방문 안한 경우
  - 시작 vertex 로부터 연결되었고, 아직 방문 안한 vertex 번호 출력

 2) BFS
    (1) Queue 에 시작 vertex 를 넣음
    (2) Queue 가 empty 할 때 까지 반복
       - Queue 에서 vertex 를 하나 꺼냄
       - 꺼낸 vertex 와 연결된 vertex 가 존재하고 해당 연결된 vertex 를 방문 안한 경우,
         해당 연결된 vertex 를 Queue 에 추가

2. 자료구조
 - boolean[][]: 인접 행렬 (그래프 연결 상태 저장)
 - boolean[]: 노드 방문 확인 배열
 - Queue: BFS

3. 시간 복잡도
 - DFS 와 BFS 의 시간 복잡도 = O(V + E) = O(N + M)
   => N, M 최대값 대입: O(1,000 + 10,000) = O(11,000) << 2억
*/

public class Main {
    static int n, m, v;             // n: 정점 vertex 개수, m: 간선 edge 개수, v: 탐색 시작 vertex 번호
    static boolean[][] graph;       // 그래프 (인접 행렬 형태로 저장)
    static boolean[] check;         // 정점 방문 확인

    static void dfs(int start) {
        check[start] = true;
        System.out.print(start + " ");

        for (int i = 1; i <= n; i++) {
            // 연결되어 있고 아직 방문 안한 vertex 가 존재하면, 더 탐색
            if (graph[start][i] && !check[i])
                dfs(i);
        }
    }

    static void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);               // Queue 에 vertex 추가하고, 방문 처리
        check[start] = true;
        System.out.print(start + " ");

        while (!queue.isEmpty()) {
            start = queue.remove();

            for (int i = 1; i <= n; i++) {
                if (graph[start][i] && !check[i]) {
                    queue.add(i);
                    check[i] = true;
                    System.out.print(i + " ");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());       // 정점 vertex 개수
        m = Integer.parseInt(st.nextToken());       // 간선 edge 개수
        v = Integer.parseInt(st.nextToken());       // 탐색 시작 정점 vertex 번호

        graph = new boolean[n + 1][n + 1];          // [1 ~ n][1 ~ n] 사용
        check = new boolean[n + 1];                 // [1 ~ n] 사용

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            graph[start][end] = true;
            graph[end][start] = true;
        }
        System.out.println();

        System.out.print("DFS: ");
        dfs(v);

        System.out.println();
        check = new boolean[n + 1];         // 방문 확인 배열 초기화

        System.out.print("BFS: ");
        bfs(v);
    }
}
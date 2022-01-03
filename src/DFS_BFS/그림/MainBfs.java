package DFS_BFS.그림;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 2중 for 문으로 도화지 paper[][] 를 차례로 확인
   => 해당 지점이 그림(1, true)이고 아직 방문 안했으면, 해당 지점을 기준으로 BFS 수행 (Queue 에 해당 지점 추가)
 - BFS: Queue 가 empty 할 때까지 반복
   1) Queue 에서 좌표를 하나씩 꺼냄
   2) 꺼낸 좌표를 기준으로 상하좌우 확인
   3) 좌표의 상하좌우 각각에서 도화지 범위 안이고, 그림이고, 아직 방문 안했으면 Queue 에 추가

2. 자료구조
 - boolean[][]: 도화지(그래프)
 - boolean[][]: 방문 확인
 - Queue: BFS

3. 시간 복잡도
 - BFS 의 시간 복잡도 = O(V + E)
   => V(vertex): n * m
   => E(edge): 4V (넉넉하게 한 vertex 에 연결된 vertex 상하좌우 4개로 생각)
   => 5 * n * m = 5 * 500 * 500 = 1,250,000 << 2억
*/

public class MainBfs {
    static int n, m;                // n: 도화지 세로 크기, m: 도화지 가로 크기
    static boolean[][] paper;       // 도화지
    static boolean[][] check;       // 방문 확인

    static int[] dy = { -1, 1, 0, 0 };      // 상하좌우
    static int[] dx = { 0, 0, -1, 1 };

    static int numOfPicture = 0;    // 그림 개수
    static int maxArea = 0;         // 최대 그림 넓이
    static int area = 0;            // 탐색한 그림의 넓이

    static void bfs(int row, int col) {
        Queue<Coord> queue = new LinkedList<>();

        queue.add(new Coord(row, col));
        check[row][col] = true;
        area++;

        while (!queue.isEmpty()) {
            Coord c = queue.remove();

            // 해당 지점의 상하좌우 확인
            for (int i = 0; i < 4; i++) {
                int nextRow = c.getRow() + dy[i];
                int nextCol = c.getCol() + dx[i];

                // 1. 다음 지점이 도화지 범위 안에 있고
                if (0 <= nextRow && nextRow < n &&
                    0 <= nextCol && nextCol < m) {
                    // 2. 다음 지점이 그림이고 아직 방문 안한 경우
                    if (paper[nextRow][nextCol] && !check[nextRow][nextCol]) {
                        queue.add(new Coord(nextRow, nextCol));
                        check[nextRow][nextCol] = true;
                        area++;
                    }
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

        paper = new boolean[n][m];
        check = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < m; j++) {
                int input = Integer.parseInt(st.nextToken());
                if (input == 1)
                    paper[i][j] = true;
            }
        }

        // paper[][] 차례로 확인
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 해당 지점이 그림이고 아직 방문 안한 경우
                if (paper[i][j] && !check[i][j]) {
                    numOfPicture++;
                    bfs(i, j);

                    maxArea = Math.max(maxArea, area);
                    area = 0;
                }
            }
        }

        System.out.println(numOfPicture);
        System.out.println(maxArea);
    }
}

package DFS_BFS.그림;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 2중 for 문으로 도화지 배열 paper[][] 를 차례로 확인
   => 해당 지점이 그림(1, true)이고 아직 방문 안했으면, 해당 지점을 기준으로 DFS 수행
 - DFS: 해당 지점을 기준으로 상하좌우 확인, 그림이고 아직 방문 안했으면 탐색 확장해나감

2. 자료구조
 - boolean[][]: 도화지(그래프)
 - boolean[][]: 방문 확인

3. 시간 복잡도
 - DFS 의 시간 복잡도 = O(V + E)
   => V(vertex): n * m
   => E(edge): 4V (넉넉하게 한 vertex 에 연결된 vertex 상하좌우 4개로 생각)
   => 5 * n * m = 5 * 500 * 500 = 1,250,000 << 2억
*/

public class MainDfsRecursion {
    static int n, m;                // n: 도화지 세로 크기, m: 도화지 가로 크기
    static boolean[][] paper;       // 가로 m, 세로 n 도화지
    static boolean[][] check;       // 방문 확인

    static int numOfPicture = 0;    // 그림 개수
    static int maxArea = 0;         // 가장 넓은 그림의 넓이
    static int area = 0;            // 탐색한 그림의 넓이

    static int[] dy = { -1, 1, 0, 0 };           // 상하좌우
    static int[] dx = { 0, 0, -1, 1 };

    static void dfs(int row, int col) {
        check[row][col] = true;
        area++;

        // 상하좌우 확인
        for (int i = 0; i < 4; i++) {
            int nextRow = row + dy[i];
            int nextCol = col + dx[i];

            // 1. 다음 지점이 도화지 범위 안에 있고
            if (0 <= nextRow && nextRow < n &&
                0 <= nextCol && nextCol < m) {
                // 2. 그림이고 아직 방문 안한 경우
                if (paper[nextRow][nextCol] && !check[nextRow][nextCol])
                    dfs(nextRow, nextCol);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());       // 도화지 세로 크기
        m = Integer.parseInt(st.nextToken());       // 도화지 가로 크기

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

        // paper[][] 탐색
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 해당 지점이 그림이고 아직 방문 안했으면, DFS 수행
                if (paper[i][j] && !check[i][j]) {
                    numOfPicture++;
                    dfs(i, j);

                    maxArea = Math.max(maxArea, area);
                    area = 0;
                }
            }
        }

        System.out.println(numOfPicture);
        System.out.println(maxArea);
    }
}

package BruteForce.치킨_배달;
import java.io.*;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

/*
- 빈칸 0, 집 1, 치킨 집 2
- m개 치킨 집을 선택하여, 최단 거리 합 구하기

1. 아이디어
 - 행렬 입력하면서, 치킨 집들의 좌표를 리스트에 저장
 1) 최단 거리를 만드는 m개 치킨 집 선택
   - 치킨 집 좌표 리스트 vs 모든 집들의 좌표 비교하여 거리 계산 (브루트 포스)
     => 집들의 치킨 거리 합이 가장 작은 m개의 치킨 집들을 선택
 2) 최단 거리의 m개 치킨 집과 모든 집들에 대해 거리 계산하면서,
    최소 거리를 배열에 저장

2. 자료구조
 - int[]: 도시 맵 행렬
 - List<Coord>, ArrayList<Coord>: 치킨 집 좌표들 저장
 - int[]: 각 집들을 기준으로 최소 치킨 거리 저장

3. 시간 복잡도
*/

/* 오답 노트
 - 치킨 집 vs 집의 치킨 거리가 최소인 m개 치킨 집을 고른 후,
   해당 m개 치킨 집과 각 집의 거리 값을 Math.min() 으로 갱신해서 문제 발생
 - 전체 치킨 집에서 m개를 선택하여 가능한 모든 경우의 조합을 찾아서,
   해당 조합의 치킨 집들로 각 집의 거리 합 계산해야 함

* 반례 입력
5 2
0 0 0 0 1
0 0 0 0 1
0 0 0 2 1
1 0 2 0 1
2 1 0 0 1
=> 정답 출력: 13
=> 내 출력: 15
*/

class Coord_ {
	private int row;
	private int col;
	private int totalDistance;
	// 치킨 집을 기준으로 각 집들의 거리 합

	public Coord_(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public int getRow() { return row; }
	public int getCol() { return col; }
	public int getTotalDistance() { return totalDistance; }
	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}
}

public class Main_Incorrect {
	static int n;				// n행 n열
	static int m;				// 남길 치킨 집 개수
	static int[][] map;			// 도시 맵 행렬
	static List<Coord_> chickens = new ArrayList<>();		// 모든 치킨 가게 좌표들
	static int[] minDistances;		// 각 집들의 최소 치킨 거리 (최적 m개 치킨 집 선택 기준)

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		map = new int[n][n];
		int numOfHome = 0;				// 집 개수
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 2)			// 치킨 집
					chickens.add(new Coord_(i, j));
				else if (map[i][j] == 1)
					numOfHome++;
			}
		}
		minDistances = new int[numOfHome];		// 각 집들의 최소 치킨 거리

		// 치킨 집을 기준으로 각 집들의 치킨 거리 합 계산
		for (int k = 0; k < chickens.size(); k++) {
			Coord_ chicken = chickens.get(k);
			int totalDistance = 0;

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (map[i][j] == 1)
						totalDistance += calcDistance(chicken, i, j);
				}
			}
			chicken.setTotalDistance(totalDistance);
		}

		// 각 집들에 대해 최소 치킨 거리인 m개 치킨 집 선택을 위해 정렬
		Collections.sort(chickens, (c1, c2) ->
				c1.getTotalDistance() - c2.getTotalDistance()
		);

		// 최소 거리를 만드는 m개 치킨 가게
		for (int k = 0; k < m; k++) {
			Coord_ chicken = chickens.get(k);
			int homeIdx = 0;

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (map[i][j] == 1) {
						int distance = calcDistance(chicken, i, j);		// 치킨 집과 집의 거리
						minDistances[homeIdx] = minDistances[homeIdx] != 0 ?
								Math.min(
										minDistances[homeIdx], distance
								) :
								distance;
						homeIdx++;
					}
				}
			}
		}

		int minTotalDistance = 0;
		for (int min : minDistances)
			minTotalDistance += min;

		System.out.println(minTotalDistance);
	}

	/* chicken: 치킨 집 좌표, row, col: 집 좌표 */
	static int calcDistance(Coord_ chicken, int row, int col) {
		int rowDiff = Math.abs(chicken.getRow() - row);
		int colDiff = Math.abs(chicken.getCol() - col);
		return rowDiff + colDiff;
	}
}

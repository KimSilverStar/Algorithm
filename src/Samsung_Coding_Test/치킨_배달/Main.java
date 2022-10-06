package Samsung_Coding_Test.치킨_배달;
import java.io.*;
import java.util.*;

/*
- n x n 행렬
- 치킨 거리 = 집과 가장 가까운 치킨 집 사이의 거리 (집 기준)
- 도시의 치킨 거리 = 모든 집의 치킨 거리 합

- 전체 k개 치킨 집 중, 최대 m개를 선택하여 남김 (나머지는 폐업)
  => 도시의 치킨 거리가 최소가 되도록 선택
*/

/*
1. 아이디어
 > 구현, 시뮬레이션
 > 조합(백트래킹 + 브루트 포스): 폐업하지 않고 남길 치킨 집 m개 선택

1) m개 치킨 집 선택
 - 치킨 집을 많이 남길수록(폐업시키는 치킨 집이 적을수록) 도시의 치킨 거리가 최소가 됨
   => 최대 m개 선택 (m개 이하 선택) => 정확히 m개 선택

2) 도시의 치킨 거리 계산
 - 2중 for문으로 전체 집, 선택한 치킨 집 각각 거리 계산하여 최소 거리 찾기


2. 자료구조
 - ArrayList<Pair> homeList: 전체 집 위치 저장
 - ArrayList<Pair> chickenList: 전체 치킨 집 위치 저장
 - boolean[] selected: 선택한 치킨 집 표시


3. 시간 복잡도
 - 치킨 집 개수 최대 13개 중, m개 선택하여 조합 구성: C(13, m)
   => 최대 C(13, 6) or C(13, 7) = 최대 1,716개 경우의 수
*/

class Pair {
	public int y, x;

	public Pair(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static int n;					// n x n 행렬
	static int m;					// 선택하여 남길 치킨 집 최대 m개
	static int minSumDist = Integer.MAX_VALUE;		// 출력

	static List<Pair> homeList = new ArrayList<>();			// 전체 집
	static List<Pair> chickenList = new ArrayList<>();		// 전체 치킨 집
	static boolean[] selected;		// 선택한 치킨 집 표시
	static final int EMPTY = 0, HOME = 1, CHICKEN = 2;

	/* chickenIdx: 치킨 집 선택 시작 index, selectCnt: 현재까지 선택한 치킨 집 개수 */
	static void backtrack(int chickenIdx, int selectCnt) {
		// 치킨 집 m개 선택 완료한 경우
		if (selectCnt == m) {
			int sumDist = 0;

			for (Pair home : homeList) {
				int minDist = Integer.MAX_VALUE;		// home과 가장 가까운 치킨 집과의 치킨 거리

				for (int i = 0; i < chickenList.size(); i++) {
					if (selected[i]) {		// 선택된 치킨 집인 경우
						minDist = Math.min(minDist, calcDist(home, chickenList.get(i)));
					}
				}

				sumDist += minDist;
			}

			minSumDist = Math.min(minSumDist, sumDist);
			return;
		}

		// 치킨 집 선택
		for (int i = chickenIdx; i < chickenList.size(); i++) {
			selected[i] = true;			// 선택 O
			backtrack(i + 1, selectCnt + 1);

			selected[i] = false;		// 선택 X - 복구
		}
	}

	static int calcDist(Pair home, Pair chicken) {
		return Math.abs(home.y - chicken.y) + Math.abs(home.x - chicken.x);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < n; j++) {
				int input = Integer.parseInt(st.nextToken());
				if (input == HOME) {
					homeList.add(new Pair(i, j));
				}
				else if (input == CHICKEN) {
					chickenList.add(new Pair(i, j));
				}
			}
		}

		selected = new boolean[chickenList.size()];
		backtrack(0, 0);		// Init Call

		System.out.println(minSumDist);
	}
}

package BruteForce.치킨_배달;
import java.io.*;
import java.util.*;

/*
- 빈칸 0, 집 1, 치킨 집 2
- m개 치킨 집을 선택하여, 최단 거리 합 구하기

1. 아이디어
 - 행렬 입력하면서, 집과 치킨 집들의 좌표를 각각 리스트에 저장
 1) 전체 치킨 집들 중에서 중복없이 m개 치킨 집 선택
 2) 선택한 m개 치킨 집들에서 치킨 집 1개씩 확인
   - 각 집들을 기준으로, 각 집과 m개 치킨 집들의 거리 계산하여 최소 거리로 갱신해나감
   - 선택한 m개 치킨 집들의 치킨 거리 합 (도시의 치킨 거리) 계산하여 최소 값으로 갱신해나감

* 브루트 포스 + 백트래킹 으로 가능한 모든 치킨 가게 조합을 구성하여 확인
 - 13개 (최대 치킨 집 개수)에서 중복없이, 순서 상관없이 m개 선택
 => 조합 (Combination): C(13, m)

2. 자료구조
 - List<Coord>, ArrayList<Coord> 3개: 모든 치킨 집 / 집 좌표들, 선택한 m개 치킨 집 좌표들
 - boolean[]: 치킨 집 선택(방문) 확인 배열

3. 시간 복잡도
 - 전체 치킨 집에서 중복없이, 순서 상관없이 m개 선택: C(13, m)
   m = 6일 때, 최대 => C(13, 6) = 1,716
   => 최대 가능한 경우의 수: 1,716 개
 - 1개 조합에서 도시의 최소 치킨 거리 계산: O(전체 집 개수 x m)
   = 2 x n x m = 2 x 50 x 13 = 1,300
 => 총 시간 복잡도 = 1,716 x 1,300 = 2,230,800 << 1억 (1초)
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

public class Main {
	static int n;				// n행 n열
	static int m;				// 선택할 치킨 집 개수
	static int minCityDistance = Integer.MAX_VALUE;
	// 출력 값: 도시의 최소 치킨 거리 (선택한 m개의 치킨 집과 모든 집들의 거리 합 중 최소)

	static List<Coord> homeList = new ArrayList<>();		// 모든 집 좌표들
	static List<Coord> chickenList = new ArrayList<>();		// 모든 치킨 가게 좌표들
	static List<Coord> selectedChickenList = new ArrayList<>();		// 선택한 m개 치킨 집들
	static boolean[] checkChickenList;			// chickenList 방문 확인

	/* idx: 치킨 집 선택 시작 index */
	static void solution(int idx) {
		// 치킨 집 m개 선택 완료 => 선택한 m개 치킨 집들과 각 집들의 거리 계산
		if (selectedChickenList.size() == m) {
			int cityDistance = 0;
			for (int i = 0; i < homeList.size(); i++) {
				int minDistance = Integer.MAX_VALUE;			// 각 집의 최소 치킨 거리

				for (Coord chicken : selectedChickenList) {
					minDistance = Math.min(
							minDistance,
							calcDistance(chicken, homeList.get(i))
					);
				}
				cityDistance += minDistance;
			}

			minCityDistance = Math.min(minCityDistance, cityDistance);
			return;
		}

		// 치킨 집 선택
		for (int i = idx; i < chickenList.size(); i++) {
			if (!checkChickenList[i]) {
				checkChickenList[i] = true;
				selectedChickenList.add(chickenList.get(i));
				solution(i + 1);

				// 재귀 복귀 시점: check 배열 및 선택 리스트 복구
				checkChickenList[i] = false;
				selectedChickenList.remove(chickenList.get(i));
			}
		}
	}

	static int calcDistance(Coord c1, Coord c2) {
		return Math.abs(c1.getRow() - c2.getRow())
				+ Math.abs(c1.getCol() - c2.getCol());
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
				if (input == 1)
					homeList.add(new Coord(i, j));
				else if (input == 2)
					chickenList.add(new Coord(i, j));
			}
		}

		checkChickenList = new boolean[chickenList.size()];

		// 전체 치킨 집들 중에서, 중복없이 m개 선택
		solution(0);

		System.out.println(minCityDistance);
	}
}

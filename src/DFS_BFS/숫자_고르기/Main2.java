package DFS_BFS.숫자_고르기;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
- 최대 많은 개수 선택 => DFS
- 뽑은 수 index 의 집합 == 뽑은 수 value 의 집합
*/

/*
1. 아이디어
 - 선택한 수들의 index, value 가 싸이클을 구성하면 두 집합이 같음
   ex 1) 선택한 수 index 집합: 1, 3
         선택한 수 value 집합: 3, 1
         => 1 -> 3 -> 1
   ex 2) 선택한 수 index 집합: 5
         선택한 수 value 집합: 5
         => 5 -> 5
 - 1 ~ n 까지 차례로 확인
   1) 해당 i 에서 DFS 탐색 시작
   2) 탐색하다가 탐색 시작 index i 로 돌아오면, 싸이클을 구성하여 두 집합이 같음
     => 리스트에 탐색 시작 index i 추가

2. 자료구조
 - boolean[]: 방문 확인
 - List<Integer>, ArrayList<Integer>: 싸이클을 구성하는 경우, 탐색 시작 index 저장

3. 시간 복잡도
 - V: n => 최대 100
 - E: 1 (링크 따라 이동하므로, vertex 1개에 edge 1개)
   => index i 를 DFS 한번 수행할 때, 시간 복잡도 = O(V + E) = 101 ~= 대충 100
   => 최대 정수 1 ~ 100 까지 DFS 수행할 때, 총 시간 복잡도 = 100 x 100 = 10,000 << 1억 (1초)
*/

public class Main2 {
	static int n;					// 입력 정수 개수
	static int[] numbers;
	static boolean[] check;

	static List<Integer> selected = new ArrayList<>();	// 최대 개수로 선택한 수들 저장
	static int startIdx;				// 탐색 시작 수 index => 싸이클 구성 확인

	static void dfs(int idx) {
		int value = numbers[idx];		// 다음
		if (!check[value]) {			// 방문 안한 경우, 더 탐색 (연결따라 이동)
			check[value] = true;
			dfs(value);
			check[value] = false;
		}

		if (startIdx == value)			// 싸이클 구성한 경우, 리스트에 시작 index 추가
			selected.add(startIdx);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());
		numbers = new int[n + 1];			// [1 ~ n] 사용
		check = new boolean[n + 1];
		for (int i = 1; i <= n; i++)
			numbers[i] = Integer.parseInt(br.readLine());

		// 1 ~ n 각각이 싸이클 구성하는지 확인
		// => i 가 싸이클 구성하면(탐색하여 다시 i로 돌아오면), 리스트에 i (시작 index) 추가
		for (int i = 1; i <= n; i++) {
			if (!check[i]) {
				check[i] = true;		// 방문 표시하고, 탐색 시작
				startIdx = i;			// 시작 index 저장 => 싸이클 구성 확인
				dfs(i);
				check[i] = false;		// 방문 취소
			}
		}

		Collections.sort(selected);			// 작은 순으로 정렬

		StringBuilder sb = new StringBuilder();
		sb.append(selected.size()).append("\n");
		for (int num : selected)
			sb.append(num).append("\n");

		System.out.println(sb.toString());
	}
}

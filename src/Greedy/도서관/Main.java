package Greedy.도서관;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 모든 책을 제자리에 놔둔 후, 다시 원점 0 으로 돌아올 필요 X
   => 가장 먼 거리의 m개 책을 마지막에 놔두고 종료해야 함

 1) 각 책의 위치 리스트를 거리가 먼(절댓값이 큰) 순으로 정렬
   - 음수 위치 리스트, 양수 위치 리스트 각각 나누어 저장 및 정렬
     => 원점을 기준으로 서로 반대편(음수, 양수)에 있는 책들은 왕복을 각각 수행하므로
     e.g. 한 번에 들 수 있는 책이 2권이고 남은 책이 -6 과 2 일때
     => (2 x 6) + (2 x 2)
 2) 거리가 가장 먼 m개 책은 가장 마지막에 정리하고 종료하므로, 단순히 + 편도 거리
 3) 이후, 나머지 책들은 거리 먼 순으로 + 왕복 거리 (+ 2배 거리)
 => 음수 좌표 리스트, 양수 좌표 리스트 각각에서 반복문으로 m개 씩 선택

 e.g. 예제 1
    1) 가장 먼 -39에 가면서 책 2권(-39, -37)을 정리
      => + 39
    2) 이후 차례로 (-29, -28) 2권, (-6) 1권, (2, 11) 2권 정리
      => + (29 x 2) + (6 x 2) + (11 x 2)


2. 자료구조
 - ArrayList<Integer> 2개: 책 음수 좌표, 양수 좌표 리스트
   => 거리 먼 순(절댓값 큰 순)으로 정렬


3. 시간 복잡도
 - 리스트 2개 정렬: 대충 O(2 x n log2 n)
   => n 최대값 대입: 2 x 50 log2 50 ~= 500
*/

public class Main {
	static int n, m;			// 책의 개수 n, 한 번에 들 수 있는 책 최대 개수 m
	static List<Integer> plusPosList = new ArrayList<>();
	static List<Integer> minusPosList = new ArrayList<>();
	static int maxDistPos;		// 원점으로부터 가장 먼 거리의 위치
	static int minStep;			// 출력, 최소 걸음 수

	static void solution_better() {
		// 거리가 먼 순으로 m개 씩 원점을 왕복하며(거리 x 2) minStep 에 더함
		while (!plusPosList.isEmpty()) {
			// 한 번에 최대 m개 책 이동
			for (int i = 0; i < m; i++) {
				if (plusPosList.isEmpty())
					break;

				// 왕복 이동 거리: m개 책 묶음에서, 첫 번째 가장 먼 책의 거리 x 2
				if (i == 0)
					minStep += plusPosList.get(0) * 2;
				plusPosList.remove(0);
			}
		}

		while (!minusPosList.isEmpty()) {
			// 한 번에 최대 m개 책 이동
			for (int i = 0; i < m; i++) {
				if (minusPosList.isEmpty())
					break;

				// 왕복 이동 거리: m개 책 묶음에서, 첫 번째 가장 먼 책의 거리 x 2
				if (i == 0)
					minStep += -(minusPosList.get(0)) * 2;
				minusPosList.remove(0);
			}
		}

		// 마지막에 옮기는 가장 먼 책은 왕복 X (위에서 왕복 처리한 거리를 다시 빼줌)
		minStep -= Math.abs(maxDistPos);
	}

	static void solution() {
		// 1) 가장 먼 책의 거리를 minStep 에 더한 후, m개 책을 리스트에서 빼줌
		minStep += Math.abs(maxDistPos);
		if (maxDistPos > 0) {
			for (int i = 0; i < m; i++) {
				if (plusPosList.isEmpty())
					break;
				plusPosList.remove(0);
			}
		}
		else {
			for (int i = 0; i < m; i++) {
				if (minusPosList.isEmpty())
					break;
				minusPosList.remove(0);
			}
		}

		// 2) 나머지 남은 책 중에서, 거리가 먼 순으로 m개 씩 원점을 왕복하며(거리 x 2) minStep 에 더함
		while (!plusPosList.isEmpty()) {
			// 한 번에 최대 m개 책 이동
			for (int i = 0; i < m; i++) {
				if (plusPosList.isEmpty())
					break;

				// 왕복 이동 거리: m개 책 묶음에서, 첫 번째 가장 먼 책의 거리 x 2
				if (i == 0)
					minStep += plusPosList.get(i) * 2;

				plusPosList.remove(0);
			}
		}

		while (!minusPosList.isEmpty()) {
			// 한 번에 최대 m개 책 이동
			for (int i = 0; i < m; i++) {
				if (minusPosList.isEmpty())
					break;

				// 왕복 이동 거리: m개 책 묶음에서, 첫 번째 가장 먼 책의 거리 x 2
				if (i == 0)
					minStep += -(minusPosList.get(i)) * 2;

				minusPosList.remove(0);
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
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < n; i++) {
			int pos = Integer.parseInt(st.nextToken());

			if (Math.abs(maxDistPos) < Math.abs(pos))
				maxDistPos = pos;

			if (pos > 0)
				plusPosList.add(pos);
			else
				minusPosList.add(pos);
		}

		// 원점으로부터 거리 먼 순(절댓값 큰 순)으로 정렬
		Collections.sort(plusPosList, Collections.reverseOrder());
		Collections.sort(minusPosList);

		solution_better();
		System.out.println(minStep);
	}
}

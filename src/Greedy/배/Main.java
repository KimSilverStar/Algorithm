package Greedy.배;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 각 크레인의 최대 무게 리스트, 각 박스의 무게 리스트를 큰 순으로 정렬
 - 가장 큰 무게의 박스, 가장 큰 무게의 크레인부터 확인
   1) 해당 박스를 해당 크레인으로 옮길 수 있는 경우
     - 해당 박스를 박스 리스트에서 삭제
     - 다음 박스, 다음 크레인 확인
   2) 해당 박스를 해당 크레인으로 옮길 수 없는 경우
     - 다음 박스(현재 해당 박스 이하의 무게)를
       현재 크레인으로 옮길 수 있는지 확인
   => 모든 크레인을 확인한 경우, 경과 시간 +1

2. 자료구조
 - ArrayList<Integer> 2개: 각 크레인의 최대 무게 리스트, 각 박스의 무게 리스트

3. 시간 복잡도
 - 크레인 최대 무게 리스트 정렬: 50 log_2 50 ~= 250
 - 박스 무게 리스트 정렬: 10^4 log_2 10^4 ~= 12 x 10^4
*/

public class Main {
	static int n;					// 크레인 개수
	static List<Integer> craneWeights = new ArrayList<>();		// 각 크레인의 최대 무게
	static int m;					// 박스 개수
	static List<Integer> boxWeights = new ArrayList<>();		// 각 박스의 무게
	static int minTime;				// 출력, 최소 시간

	static void solution() {
		// 박스를 모두 옮길 수 없는 경우
		if (boxWeights.get(0) > craneWeights.get(0)) {
			minTime = -1;
			return;
		}

		while (!boxWeights.isEmpty()) {
			int boxIdx = 0;

			for (int i = 0; i < craneWeights.size(); ) {	// 주의) for 문에 i++ 없음
				if (boxIdx == boxWeights.size())	// 마지막 박스까지 확인한 경우
					break;							// => 남은 첫 번째 박스, 첫 번째 크레인부터 다시 확인

				if (boxWeights.get(boxIdx) <= craneWeights.get(i)) {
					boxWeights.remove(boxIdx);		// 해당 박스 옮김(제거)
					i++;							// 다음 크레인
				}
				else				// [i] 크레인으로 박스 못 옮기는 경우
					boxIdx++;		// => [i] 크레인으로 다음 박스 옮길 수 있는지 확인
			}

			minTime++;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			craneWeights.add(Integer.parseInt(st.nextToken()));

		m = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < m; i++)
			boxWeights.add(Integer.parseInt(st.nextToken()));

		// 2개 리스트를 각각 무게 큰 순으로 정렬
//		Collections.sort(craneWeights, (w1, w2) -> w2 - w1);
		Collections.sort(craneWeights, Collections.reverseOrder());
		Collections.sort(boxWeights, Collections.reverseOrder());

		solution();
		System.out.println(minTime);
	}
}

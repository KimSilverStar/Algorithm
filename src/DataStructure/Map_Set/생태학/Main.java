package DataStructure.Map_Set.생태학;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - TreeMap<String, Integer> 사용
   => Key: 나무 종 이름, Value: 해당 나무 종의 등장 횟수
   => TreeMap 으로 Key (나무 종 이름) 사전 순 정렬

 1) 입력 받으면서, 트리 맵에 (나무 종 이름 String, 등장 횟수 Integer) 저장
 2) for문으로 트리 맵에서 원소들을 확인
   - 각 나무 종 이름에 대해, 차지 비율 계산
   - 차지 비율: 소수점 넷째 자리까지만 남김
     => String.format("%.4f", 차지 비율 실수 값)


2. 자료구조
 - TreeMap<String, Integer>: 나무 종 이름이 Key, 종 등장 횟수가 Value


3. 시간 복잡도
 1) 트리 맵 저장: O(n log_2 n)
   - 최대 10^6 x log_2 10^6 = (6 x 10^6) log_2 10 ~= 18 x 10^6
 2) 차지 비율 계산 및 출력: O(k)
   - 최대 10^4
*/

public class Main {
	static StringBuilder sb = new StringBuilder();
	static int totalCount;			// 전체 나무 등장 횟수 (입력 문자열 개수)
	static Map<String, Integer> treeMap = new TreeMap<>();

	static void solution() {
		// 2) 각 나무 종 별로 차지 비율 계산
		for (String key : treeMap.keySet()) {
			int count = treeMap.get(key);
			double rate = ((double) count / totalCount) * 100;

			// 소수점 넷째 자리까지만 남김
			sb.append(key).append(" ")
					.append(String.format("%.4f", rate)).append("\n");
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		// 1) Map에 각 나무 종 별로 등장 횟수 저장
		while (true) {
			String key = br.readLine();				// 나무 종 이름
			if (key == null || key.equals("") || key.equals("\n"))
				break;

			totalCount++;

			if (treeMap.containsKey(key)) {
				int count = treeMap.get(key);
				treeMap.put(key, count + 1);
			}
			else {		// 없는 경우
				treeMap.put(key, 1);
			}
		}

		solution();
		System.out.println(sb);
	}
}

package Code_Tree.Test.Q6;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - HashMap 에 각 숫자(key)들과 등장 횟수(value) 저장
 - value 큰 순으로 key 출력

2. 자료구조
 - Map<Integer, Integer>, HashMap<Integer, Integer>

3. 시간 복잡도
 - HashMap 저장: O(n)
 - 정렬: O(n log_2 n)
*/

public class Main {
	static int n, k;			// 전체 n개 숫자, 자주 등잔한 k개 숫자
	static Map<Integer, Integer> map = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());

		// 각 숫자의 등장 횟수 저장
		for (int i = 0; i < n; i++) {
			int num = Integer.parseInt(st.nextToken());		// key

			if (map.containsKey(num)) {
				int value = map.get(num);
				map.put(num, value + 1);
			}
			else {
				map.put(num, 1);
			}
		}

		// 정렬 - 등장 횟수(value) 높은 순, 같으면 숫자(key) 높은 순
		List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(map.entrySet());
		entryList.sort(new Comparator<Map.Entry<Integer, Integer>>() {
			@Override
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				int freqDiff = o2.getValue() - o1.getValue();
				if (freqDiff != 0)
					return freqDiff;

				// 등장 횟수(value) 같으면, 숫자(key) 높은 순
				return o2.getKey() - o1.getKey();
			}
		});

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < k; i++) {
			sb.append(entryList.get(i).getKey())
					.append(" ");
		}
		System.out.println(sb);
	}
}

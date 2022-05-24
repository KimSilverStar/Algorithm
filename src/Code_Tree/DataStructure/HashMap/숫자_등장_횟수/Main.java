package Code_Tree.DataStructure.HashMap.숫자_등장_횟수;
import java.io.*;
import java.util.*;

/*
1. 아이디어


2. 자료구조


3. 시간 복잡도
 - O()
*/

public class Main {
	static int n, m;				// n개 숫자, m개 쿼리
	static Map<Integer, Integer> map = new HashMap<>();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < n; i++) {
			int key = Integer.parseInt(st.nextToken());

			int value = map.getOrDefault(key, 0);
			map.put(key, value + 1);

//			if (map.containsKey(key)) {
//				int value = map.get(key);
//				map.put(key, value + 1);
//			}
//			else {
//				map.put(key, 1);
//			}
		}

		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < m; i++) {
			int key = Integer.parseInt(st.nextToken());

			sb.append(map.getOrDefault(key, 0)).append(" ");
		}

		System.out.println(sb);
	}
}
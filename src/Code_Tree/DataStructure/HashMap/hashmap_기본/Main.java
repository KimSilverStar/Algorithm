package Code_Tree.DataStructure.HashMap.hashmap_기본;
import java.io.*;
import java.util.*;

/*
1. 아이디어

2. 자료구조
 - Map<Integer, Integer>, HashMap<Integer, Integer>

3. 시간 복잡도
 - O(n)
*/

public class Main {
	static int n;				// 명령 개수
	static String command;		// 입력된 명령 (add, remove, find)
	static int key, value;		// 입력
	static Map<Integer, Integer> map = new HashMap<>();
	static StringBuilder sb = new StringBuilder();		// 출력 저장

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			command = st.nextToken();
			if (st.hasMoreTokens())
				key = Integer.parseInt(st.nextToken());
			if (st.hasMoreTokens())
				value = Integer.parseInt(st.nextToken());

			if (command.equals("add")) {
				map.put(key, value);
			}
			else if (command.equals("remove")) {
				map.remove(key);
			}
			else if (command.equals("find")) {
				if (map.containsKey(key))
					sb.append(map.get(key)).append("\n");
				else
					sb.append("None").append("\n");
			}
		}

		System.out.println(sb);
	}
}

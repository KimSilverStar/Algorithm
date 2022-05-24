package Code_Tree.DataStructure.HashMap.대응되는_수와_문자;
import java.io.*;
import java.util.*;

public class Main {
	static int n, m;			// n개 문자열, 조사할 m개
	static String str;			// 입력 문자열
	static String query;		// 조사할 문자열 or 번호
	static Map<String, Integer> mapStr = new HashMap<>();		// 문자열이 Key
	static Map<Integer, String> mapNumber = new HashMap<>();	// 번호가 Key
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		for (int num = 1; num <= n; num++) {
			str = br.readLine();

			mapStr.put(str, num);
			mapNumber.put(num, str);
		}

		for (int i = 1; i <= m; i++) {
			query = br.readLine();

			if (Character.isDigit(query.charAt(0))) {	// 번호로 조사
				int num = Integer.parseInt(query);
				sb.append(mapNumber.get(num)).append("\n");
			}
			else {		// 문자열로 조사
				sb.append(mapStr.get(query)).append("\n");
			}
		}

		System.out.println(sb);
	}
}

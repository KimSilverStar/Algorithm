package Code_Tree.DataStructure.HashMap.가장_많은_데이터;
import java.io.*;
import java.util.*;

public class Main {
	static int n;			// 입력 문자열 개수
	static Map<String, Integer> map = new TreeMap<>();
	static int maxCount;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());

		for (int i = 0; i < n; i++) {
			String key = br.readLine();

			int value = map.getOrDefault(key, 0);
			map.put(key, value + 1);
		}

		for (int value : map.values())
			maxCount = Math.max(maxCount, value);

		System.out.println(maxCount);
	}
}

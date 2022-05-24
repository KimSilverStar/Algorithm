package Code_Tree.DataStructure.HashMap.낮은_지점들;
import java.io.*;
import java.util.*;

/*
- x 좌표가 중복되지 않도록 함
  => x 좌표가 HashMap 의 Key
*/

public class Main {
	static int n;			// 점 개수
	static Map<Integer, Integer> map = new HashMap<>();		// Key: x 좌표, Value: y 좌표 (최소)
	static long sum;		// 출력, y 좌표 합

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			if (map.containsKey(x)) {
				int originY = map.get(x);		// 기존에 저장된 y 좌표
				map.put(x, Math.min(y, originY));
			}
			else {
				map.put(x, y);
			}
		}

		for (int y : map.values())
			sum += y;
		System.out.println(sum);
	}
}

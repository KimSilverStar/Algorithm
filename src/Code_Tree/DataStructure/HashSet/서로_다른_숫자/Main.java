package Code_Tree.DataStructure.HashSet.서로_다른_숫자;
import java.io.*;
import java.util.*;

public class Main {
	static int n;			// 숫자 개수
	static Set<Integer> set = new HashSet<>();
	static int count;		// 출력, 서로 다른 숫자 개수

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < n; i++) {
			int num = Integer.parseInt(st.nextToken());
			set.add(num);
		}

		count = set.size();
		System.out.println(count);
	}
}

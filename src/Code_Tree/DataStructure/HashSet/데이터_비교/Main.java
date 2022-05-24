package Code_Tree.DataStructure.HashSet.데이터_비교;
import java.io.*;
import java.util.*;

public class Main {
	static int n;				// 수열 1의 원소 개수
	static Set<Integer> set1 = new HashSet<>();

	static int m;				// 수열 2의 원소 개수
	static int[] arr2;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < n; i++) {
			int num = Integer.parseInt(st.nextToken());
			set1.add(num);
		}

		m = Integer.parseInt(br.readLine());
		arr2 = new int[m];
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < m; i++) {
			int num = Integer.parseInt(st.nextToken());
			arr2[i] = num;
		}

		for (int i = 0; i < m; i++) {
			if (set1.contains(arr2[i]))
				sb.append(1).append(" ");
			else
				sb.append(0).append(" ");
		}

		System.out.println(sb);
	}
}

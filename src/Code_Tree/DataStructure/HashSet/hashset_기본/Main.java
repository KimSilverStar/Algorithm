package Code_Tree.DataStructure.HashSet.hashset_기본;
import java.io.*;
import java.util.*;

public class Main {
	static int n;					// 명령 개수
	static String command;			// 입력 명령 (add, remove, find)
	static int x;
	static Set<Integer> set = new HashSet<>();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			command = st.nextToken();
			x = Integer.parseInt(st.nextToken());

			if (command.equals("add"))
				set.add(x);
			else if (command.equals("remove"))
				set.remove(x);
			else if (command.equals("find"))
				sb.append(set.contains(x)).append("\n");
		}

		System.out.println(sb);
	}
}

package Code_Tree.DataStructure.PriorityQueue.정수_명령_처리_6;
import java.io.*;
import java.util.*;

public class Main {
	static int n;				// 명령 개수
	static String command;		// 명령 (push, pop, size, empty, top)
	static int num;
	static PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
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
			if (st.hasMoreTokens())
				num = Integer.parseInt(st.nextToken());

			if (command.equals("push")) {
				pq.add(num);
			}
			else if (command.equals("pop")) {
				sb.append(pq.remove()).append("\n");
			}
			else if (command.equals("size")) {
				sb.append(pq.size()).append("\n");
			}
			else if (command.equals("empty")) {
				int value = pq.isEmpty() ? 1 : 0;
				sb.append(value).append("\n");
			}
			else if (command.equals("top")) {
				sb.append(pq.peek()).append("\n");
			}
		}

		System.out.println(sb);
	}
}

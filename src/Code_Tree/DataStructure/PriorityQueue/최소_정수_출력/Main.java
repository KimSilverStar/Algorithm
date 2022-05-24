package Code_Tree.DataStructure.PriorityQueue.최소_정수_출력;
import java.io.*;
import java.util.*;

public class Main {
	static int n;			// 연산 개수
	static int num;			// 입력 정수
	static PriorityQueue<Integer> pq = new PriorityQueue<>();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());

		for (int i = 0; i < n; i++) {
			num = Integer.parseInt(br.readLine());

			if (num > 0) {
				pq.add(num);
			}
			else {		// num == 0
				if (!pq.isEmpty())
					sb.append(pq.remove()).append("\n");
				else
					sb.append(0).append("\n");
			}
		}

		System.out.println(sb);
	}
}

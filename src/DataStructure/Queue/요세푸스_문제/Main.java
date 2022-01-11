package DataStructure.Queue.요세푸스_문제;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

/*
1. 아이디어
 - 1 ~ n 을 Queue 에 저장한 후, 다음을 반복
   1) (k-1)명을 Queue 에서 뽑아서, 다시 Queue 에 넣음
     - 앞에서부터 (k-1) 명을 Queue 의 뒤로 보냄
   2) 이후, 1명을 Queue 에서 뽑아서 출력
     - k 번째 사람을 삭제

2. 자료구조
 - Queue<Integer>: 1 ~ n 명

3. 시간 복잡도
 - O(n * k)
   => n, k 최대값 대입: 5,000 x 5,000 = 25,000,000 << 2억 (2초)
*/

public class Main {
	static int n, k;		// 1 ~ n 명의 사람들에서 k 번째 사람 반복 삭제
	static Queue<Integer> queue = new LinkedList<>();

	static String solution() {
		StringBuilder sb = new StringBuilder("<");

		while (!queue.isEmpty()) {
			// 앞에서부터 (k-1)명을 뽑아서, 뒤로 보냄
			for (int i = 1; i <= k - 1; i++) {
				int popped = queue.remove();
				queue.add(popped);
			}
			sb.append(queue.remove());		// k 번째 사람을 뽑아서 출력

			if (!queue.isEmpty())
				sb.append(", ");
		}

		sb.append(">");
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		for (int i = 1; i <= n; i++)
			queue.add(i);

		System.out.println(solution());
	}
}

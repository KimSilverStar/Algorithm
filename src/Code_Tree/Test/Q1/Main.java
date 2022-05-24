package Code_Tree.Test.Q1;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 각 식당에 팀장 1명 배치 후,
   고객이 남으면 팀원들 배치
 => 단순 그리디

2. 자료구조

3. 시간 복잡도
 - O(n)
*/

public class Main {
	static int n;					// 식당 수
	static int[] customers;			// 각 식당의 고객 수
	static int head, member;		// 팀장, 팀원이 검사 가능한 인원
	static long minCount;			// 출력, 필요한 검사자 수 최소값

	static void solution() {
		for (int i = 0; i < n; i++) {
			int customer = customers[i];		// 1개 식당의 고객 수

			int count = 1;				// 1개 식당에 필요한 (팀장 + 팀원) 수
			customer -= head;			// 팀장이 검사

			if (customer > 0)
				count += Math.ceil((double)customer / member);

			minCount += count;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		customers = new int[n];
		for (int i = 0; i < n; i++) {
			int customer = Integer.parseInt(st.nextToken());
			customers[i] = customer;
		}

		st = new StringTokenizer(br.readLine());
		head = Integer.parseInt(st.nextToken());
		member = Integer.parseInt(st.nextToken());

		solution();
		System.out.println(minCount);
	}
}

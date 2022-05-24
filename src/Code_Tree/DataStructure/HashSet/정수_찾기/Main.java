package Code_Tree.DataStructure.HashSet.정수_찾기;
import java.io.*;
import java.util.*;

/*
- 수열 b의 각 원소가 수열 a에 포함되는지 확인
  => 수열 a의 원소를 HashSet에 저장
*/

public class Main {
	static int n;			// 수열 a 원소 개수
	static Set<Integer> setA = new HashSet<>();		// 수열 a
	static int m;			// 수열 b 원소 개수
	static int[] arrB;		// 수열 b
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < n; i++) {
			int numA = Integer.parseInt(st.nextToken());
			setA.add(numA);
		}

		m = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		arrB = new int[m];
		for (int i = 0; i < m; i++) {
			int numB = Integer.parseInt(st.nextToken());
			arrB[i] = numB;
		}

		for (int numB : arrB) {
			if (setA.contains(numB))
				sb.append(1).append("\n");
			else
				sb.append(0).append("\n");
		}

		System.out.println(sb);
	}
}

package Code_Tree.DataStructure.HashSet.대칭_차집합;
import java.io.*;
import java.util.*;

public class Main {
	static int sizeA, sizeB;		// 집합 A, B의 원소 개수
	static Set<Integer> setA = new HashSet<>();
	static Set<Integer> setB = new HashSet<>();
	static int count;		// 대칭 차집합의 원소 개수

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		sizeA = Integer.parseInt(st.nextToken());
		sizeB = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < sizeA; i++) {
			int numA = Integer.parseInt(st.nextToken());
			setA.add(numA);
		}

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < sizeB; i++) {
			int numB = Integer.parseInt(st.nextToken());
			setB.add(numB);
		}

		Set<Integer> setA_B = new HashSet<>(setA);			// 차집합 A - B
		for (int numB : setB)
			setA_B.remove(numB);

		Set<Integer> setB_A = new HashSet<>(setB);			// 차집합 B - A
		for (int numA : setA)
			setB_A.remove(numA);

		Set<Integer> resultSet = new HashSet<>(setA_B);		// 대칭 차집합
		resultSet.addAll(setB_A);

		count = resultSet.size();
		System.out.println(count);
	}
}

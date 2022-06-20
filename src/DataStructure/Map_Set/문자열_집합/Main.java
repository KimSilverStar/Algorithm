package DataStructure.Map_Set.문자열_집합;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - 문자열 집합에서 검색 문자열이 몇 개 있는지 개수 세기

2. 자료구조
 - HashSet<String>: 입력 n개 문자열 (집합 S)

3. 시간 복잡도
 1) 입력 n개 문자열 (집합 S) 저장: O(n)
   => 최대 10^5
 2) 집합 S 에서 m개 문자열 검색: O(m)
   => 최대 10^5
 - 총 시간 복잡도: O(n + m)
   => 최대 10^5 + 10^5 = 2 x 10^5 << 2억
*/

public class Main {
	static int n, m;			// 전체 n개 문자열, 검사할 m개 문자열
	static int count;			// 출력, 포함된 개수
	static Set<String> set = new HashSet<>();		// 전체 n개 문자열 (집합 S)

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		for (int i = 0; i < n; i++) {
			String input = br.readLine();
			set.add(input);
		}

		for (int i = 0; i < m; i++) {
			String target = br.readLine();		// 검색할 타겟 문자열

			if (set.contains(target))
				count++;
		}

		System.out.println(count);
	}
}

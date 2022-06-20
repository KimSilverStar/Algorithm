package DataStructure.Map_Set.숫자놀이;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 1) 0 "zero" ~ 9 "nine" 을 String[] 에 저장
 2) for 문으로 m ~ n 사이의 정수들을 한 숫자씩 읽어서,
    영어로 읽은 값을 ArrayList, HashMap 에 저장
   - HashMap: 정수를 영어로 읽은 문자열이 Key, 정수 값이 Value
 3) ArrayList 를 정렬
 4) 정렬된 ArrayList 의 요소를 HashMap 의 Key 로 하여,
    HashMap 에서 정수 값들을 꺼내 차례로 출력

2. 자료구조
 - List<String>, ArrayList<String>: 정수를 한 숫자씩 영어로 읽은 문자열을 저장
 - Map<String, Integer>, HashMap<String, Integer>
   : 정수를 영어로 읽은 문자열이 Key, 정수 값이 Value

3. 시간 복잡도
 1) 각 정수를 영어로 읽은 문자열을 저장: O(n)
 2) 정렬: O(n log n)
 3) 정렬된 문자열들을 정수에 맵핑하여 출력: O(n)
 - 총 시간 복잡도: O(2n + n log n)		(n: 정수 개수)
   => n 최대값 대략 100 대입: 200 + (100 x 2) = 400 << 2억
*/

public class Main_HashMap {
	static int m, n;					// m 이상 n 이하의 정수
	static String[] numberStrArr;		// "zero" ~ "nine" 저장
	static StringBuilder sb = new StringBuilder();

	static List<String> list = new ArrayList<>();
	static Map<String, Integer> map = new HashMap<>();

	static void solution() {
		// 1) 정수를 영어로 읽은 문자열을 List 와 HashMap 에 저장
		for (int i = m; i <= n; i++) {
			 String strNumber = String.valueOf(i);			// "80" 형태 문자열
			 StringBuilder strByDigit = new StringBuilder();
			 // 한 숫자(문자)씩 읽은 결과 문자열 ("eight zero" 형태)

			 for (int j = 0; j < strNumber.length(); j++) {
				 int digit = Character.getNumericValue(strNumber.charAt(j));
				 strByDigit.append(numberStrArr[digit])
						 .append(" ");
			 }

			 list.add(strByDigit.toString());
			 map.put(strByDigit.toString(), i);
		}

		// 2) List 를 사전 순으로 정렬
		Collections.sort(list);

		// 3) 정렬된 List 의 문자열들을 HashMap 에 맵핑하여, 정수를 꺼내어 출력
		int count = 1;
		for (String s : list) {
			int num = map.get(s);
			sb.append(num).append(" ");

			if (count % 10 == 0)
				sb.append("\n");

			count++;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		m = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());

		numberStrArr = new String[] {
				"zero", "one", "two", "three", "four",
				"five", "six", "seven", "eight", "nine"
		};

		solution();
		System.out.println(sb);
	}
}

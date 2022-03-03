package DataStructure.HashMap.숫자놀이;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 1) 0 "zero" ~ 9 "nine" 을 String[] 에 저장
 2) for 문으로 m ~ n 사이의 정수들을 한 숫자씩 읽어서,
    정수의 값과 영어로 읽은 값을 Word[] 에 저장
 3) Word[] 를 사전 순으로 정렬
   - implements Comparable<Word>
     => public int compareTo(Word w) 메소드 오버라이딩
 4) 정렬된 Word[] 에서 정수 값들을 차례로 출력

2. 자료구조
 - Word[]: 각 정수들의 정수 값 int, 영어로 읽은 문자열 String 저장

3. 시간 복잡도
 1) 각 정수의 값과 영어로 읽은 문자열을 Word[] 에 저장: O(n)
 2) 정렬: O(n log n)
 3) 정렬된 문자열들을 정수로 출력: O(n)
 - 총 시간 복잡도: O(2n + n log n)		(n: 정수 개수)
   => n 최대값 대략 100 대입: 200 + (100 x 2) = 400 << 2억
*/

class Word implements Comparable<Word> {
	public int number;
	public String strByDigit;	// 정수 number 를 한 숫자씩 영어로 읽은 문자열

	public Word(int number, String strByDigit) {
		this.number = number;
		this.strByDigit = strByDigit;
	}

	public int compareTo(Word w) {
		return strByDigit.compareTo(w.strByDigit);
	}
}

public class Main_Comparable {
	static int m, n;					// m 이상 n 이하의 정수
	static String[] numberStrArr;		// "zero" ~ "nine" 저장
	static StringBuilder sb = new StringBuilder();
	static Word[] words;

	static void solution() {
		// 정수 값과 정수를 영어로 읽은 문자열을 Word[] 에 저장
		int idx = 0;
		for (int i = m; i <= n; i++) {
			String strNumber = String.valueOf(i);			// "80" 형태 문자열
			StringBuilder strByDigit = new StringBuilder();
			// 한 숫자(문자)씩 읽은 결과 문자열 ("eight zero" 형태)

			for (int j = 0; j < strNumber.length(); j++) {
				int num = Character.getNumericValue(strNumber.charAt(j));
				strByDigit.append(numberStrArr[num])
						.append(" ");
			}

			words[idx++] = new Word(i, strByDigit.toString());
		}

		Arrays.sort(words);			// 사전 순으로 정렬

		// 정렬된 Word[] 에서 정수 값을 꺼내어 출력
		int count = 1;
		for (Word w : words) {
			int num = w.number;
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
		words = new Word[n - m + 1];

		numberStrArr = new String[] {
				"zero", "one", "two", "three", "four",
				"five", "six", "seven", "eight", "nine"
		};

		solution();
		System.out.println(sb);
	}
}

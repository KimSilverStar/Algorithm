package String.회문;
import java.io.*;

/*
- 회문이면 0
- 유사 회문이면 1
- 회문, 유사 회문 둘 다 아니면 2

1. 아이디어
 1) word 가 회문인지 판단
   - [i] == [len - 1 - i] 인지 비교 반복
   - 회문이면, 0 반환
   - 회문이 아니면,
     [i] 문자 삭제 후 유사 회문인지 판단
     [len - 1 - i] 문자 삭제 후 유사 회문인지 판단
 2) word 가 회문이 아니면, 유사 회문인지 판단
   - [i] 문자 / [len - 1 - i] 문자 삭제 후,
     유사 회문이면 1 반환
   - 유사 회문이 아니면 2 반환
 
 * 회문 or [i] / [len - 1 - i] 문자 삭제 후 회문(유사 회문) 판단
  - 회문 판단 함수

2. 자료구조
 - String[]: 입력 문자열 단어들

3. 시간 복잡도
 - 최악의 상황으로 모든 입력 단어들을 유사 회문인지 까지 판단해야 하는 경우
   => 1개 단어 당 O(len)		(len: 단어 문자열 길이)
 => 총 시간 복잡도: O(t x len)	(t: 단어 문자열 개수)
 => t, len 최대값 대입: 30 x 10^5 = 3 x 10^6 << 1억 (1초)
*/

public class Main {
	static int t;				// 문자열(단어) 개수
	static String[] words;		// 각 입력 문자열 단어들

	/* 회문: 0, 유사 회문: 1, 둘 다 아니면: 2 반환 */
	static int solution(String word) {
		for (int i = 0; i <= word.length() / 2; i++) {
			if (word.charAt(i) != word.charAt(word.length() - 1 - i)) {
				// [i] 삭제 후, 유사 회문인지 판단
				String removedWord = word.substring(0, i) + word.substring(i + 1);
				if (isPalindrome(removedWord))
					return 1;

				// [len - 1 - i] 삭제 후, 유사 회문인지 판단
				removedWord = word.substring(0, word.length() - 1 - i)
						+ word.substring(word.length() - i);
				if (isPalindrome(removedWord))
					return 1;

				return 2;		// 회문, 유사 회문 둘 다 X
			}
		}

		return 0;				// 회문
	}

	/* word 가 회문이면 true 반환 */
	static boolean isPalindrome(String word) {
		for (int i = 0; i <= word.length() / 2; i++) {
			if (word.charAt(i) != word.charAt(word.length() - 1 - i))
				return false;
		}

		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringBuilder sb = new StringBuilder();

		t = Integer.parseInt(br.readLine());
		words = new String[t];
		for (int i = 0; i < t; i++)
			words[i] = br.readLine();

		for (String word : words)
			sb.append(solution(word)).append("\n");

		System.out.println(sb.toString());
	}
}

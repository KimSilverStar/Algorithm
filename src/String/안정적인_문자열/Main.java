package String.안정적인_문자열;
import java.io.*;
import java.util.*;

/*
- 안정적인 문자열을 만들기 위한 최소 연산 횟수 구하기
  1) 빈 문자열
  2) {안정적인 문자열}
  3) 안정적인 문자열 + 안정적인 문자열	(append, 연결)
  => 빈 문자열 or 괄호 쌍이 전부 매칭되되면, 안정적인 문자열

- 연산
  1) 여는 괄호 -> 닫는 괄호 변환
  2) 닫는 괄호 -> 여는 괄호 변환

1. 아이디어
 - Stack 으로 괄호 매칭
   => 매칭되는 괄호 쌍들은 제거하고, 매칭 안되는 괄호들만 남김
   => 남은 괄호들에 대해 BFS 수행

2. 자료구조
 - String: 입력 문자열
 - Stack<Character>: 괄호 쌍 매칭
 - Queue<String>, LinkedList<String>: BFS 수행

3. 시간 복잡도

*/

public class Main {
	static String str;			// 입력 문자열
	static int minCount;		// 출력 값, 최소 연산 횟수

	static Stack<Character> stack = new Stack<>();
	static Queue<String> queue = new LinkedList<>();

	/* str 에서 매칭되는 괄호를 제거하고 남은 불안정한 문자열 (매칭 안된 괄호들) 반환 */
	static String getUnstableString(String str) {
		for (int i = 0; i < str.length(); i++) {
			stack.push(str.charAt(i));
			if (stack.size() < 2)
				continue;

			char current = stack.pop();
//			if (stack.peek() == '{')
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringBuilder sb = new StringBuilder();

		int i = 1;
		while (true) {
			minCount = 0;
			str = br.readLine();
			if (str.charAt(0) == '-')
				break;

			sb.append(i).append(". ");





			i++;
		}

		System.out.println(sb.toString());
	}
}

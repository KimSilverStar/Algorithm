package String.문자열_폭발;
import java.io.*;
import java.util.Stack;

/*
1. 아이디어
 - 입력 문자열을 한 문자씩 차례로 Stack 에 담아가면서 확인
   => Stack 의 size >= 폭발 문자열의 길이인 경우,
      Stack 에서 폭발 문자열 길이만큼의 문자를 get 하여 폭발 문자열과 비교
 - 폭발 문자열과 동일한 문자열이 Stack 에 존재하면, 삭제

2. 자료구조
 - Stack<Character>: 입력 문자열을 한 문자씩 Stack 에 담아가면서 폭발 문자열인지 확인

3. 시간 복잡도
 - 문자열 최대 길이: 10^6 (100만)
   => O(n)
*/

public class Main_Stack {
	static String input;			// 입력 문자열
	static String bomb;				// 폭발 문자열
	static Stack<Character> stack = new Stack<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		input = br.readLine();
		bomb = br.readLine();

		for (int i = 0; i < input.length(); i++) {
			stack.push(input.charAt(i));

			if (stack.size() >= bomb.length()) {
				boolean isSame = true;
				int idx = stack.size() - bomb.length();

				for (int j = idx; j < stack.size(); j++) {
					if (stack.get(j) != bomb.charAt(j - idx)) {
						isSame = false;
						break;
					}
				}

				if (isSame) {
					for (int j = 0; j < bomb.length(); j++)
						stack.pop();
				}
			}
		}

		if (stack.isEmpty()) {
			System.out.println("FRULA");
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (char ch : stack)
			sb.append(ch);
		System.out.println(sb.toString());
	}
}

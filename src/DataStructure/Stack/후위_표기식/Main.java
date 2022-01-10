package DataStructure.Stack.후위_표기식;
import java.io.*;
import java.util.Stack;

/*
* 중위 표기식 -> 후위 표기식
1) 알파벳(피연산자)
 - 그냥 출력
2) 연산자 *, /, +, -, (
 - Stack 에 자신보다 연산자 우선순위가 같거나 높은 연산자가 존재하면,
   해당 연산자들을 Stack 에서 pop 하여 출력 후,
   본인 연산자를 Stack 에 push
   !!! 여는 괄호 '('는 우선순위 가장 낮음 (Stack 에 그대로 남아야 함)
3) 닫는 괄호 )
 - 여는 괄호 '('가 Stack 에서 나올 때까지 pop 하여 출력


1. 아이디어
 - 입력 문자열을 반복문으로 한 문자씩 확인
   => 문자 유형(피연산자, 연산자 및 우선순위)에 따라 출력 및 Stack 에 처리
 - Stack 에 남은 연산자들을 pop 하여 출력

2. 자료구조
 - Stack<Character>
 - StringBuilder: 변환한 후위 표기식 저장

3. 시간 복잡도
*/

public class Main {
	static Stack<Character> stack = new Stack<>();

	// 연산자 우선순위
	static int precedence(char op) {
		if (op == '*' || op == '/')
			return 2;
		else if (op == '+' || op == '-')
			return 1;
		else			// op == '('
			return 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		String str = br.readLine();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			if (Character.isAlphabetic(ch)) {		// 알파벳(피연산자)
				sb.append(ch);
			}
			else if (ch == '(') {
				stack.push(ch);
			}
			else if (ch == ')') {
				// Stack 에서 '('가 나올 때까지 pop 하여 출력
				while (!stack.isEmpty()) {
					if (stack.peek() == '(') {
						stack.pop();		// '(' 가 나오면 pop 하여 버림
						break;
					}
					sb.append(stack.pop());
				}
			}
			else {		// 연산자 *, /, +, -
				// 본인보다 우선순위가 같거나 더 높은 Stack 에 존재하는 연산자들을 pop 하여 출력
				while (!stack.isEmpty() &&
						(precedence(stack.peek()) >= precedence(ch)))
					sb.append(stack.pop());
				stack.push(ch);		// 연산자 본인을 Stack 에 push
			}
		}

		while (!stack.isEmpty())
			sb.append(stack.pop());

		System.out.println(sb.toString());
	}
}

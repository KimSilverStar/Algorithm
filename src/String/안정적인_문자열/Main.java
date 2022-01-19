package String.안정적인_문자열;
import java.io.*;
import java.util.Stack;

/*
- 안정적인 문자열을 만들기 위한 최소 연산 횟수 구하기
  1) 빈 문자열
  2) {안정적인 문자열}
  3) 안정적인 문자열 + 안정적인 문자열	(append, 연결)
  => 빈 문자열 or 괄호 쌍이 전부 매칭되되면, 안정적인 문자열

- 연산
  1) 여는 괄호 -> 닫는 괄호 변환
  2) 닫는 괄호 -> 여는 괄호 변환
*/

/*
1. 아이디어
*** 괄호 문제 => Stack 활용

 - 입력 문자열에서 한 문자씩 확인
 1) 여는 괄호 { 는 Stack 에 push
 2) 닫는 괄호 }
   - Stack 이 비어있지 않은 경우 (여는 괄호 존재)
     => Stack 에서 pop 하여 여는 괄호 + 닫는 괄호 매칭시킴
   - Stack 이 비어있는 경우
     => Stack 에 해당 닫는 괄호 } 를 여는 괄호 { 로 바꿔서 push,
        연산 횟수 + 1
 3) 입력 문자열에서 모든 문자 확인 종료
   - Stack 에는 짝수 개의 여는 괄호 { 만 존재
     => 여는 괄호 {: 입력 문자열에서 원래 여는 괄호 {
        or 입력 문자열에서 닫는 괄호 } 였는데 Stack 으로 변환되서 들어온 경우
   - 연산 횟수 += (Stack 의 size / 2)
     => Stack 에 남은 여는 괄호들 중, 그 중 절반만 닫는 괄호로 바꾸면
        남은 모든 괄호들 매칭이 완료됨

2. 자료구조
 - String: 입력 문자열
 - Stack<Character>: 괄호 쌍 매칭

3. 시간 복잡도
 - 한 테스트 케이스 (문자열) 당 O(len)
   => 한 문자열 당 최대 2,000
*/

public class Main {
	static String str;					// 입력 문자열
	static Stack<Character> stack;

	static int solution(String str) {
		stack = new Stack<>();
		int minCount = 0;				// 최소 연산 횟수

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '{')
				stack.push(str.charAt(i));
			else {        // 닫느 괄호 '}'
				if (!stack.isEmpty())
					stack.pop();		// 매칭되는 여는 괄호 { 제거
				else {
					// 닫는 괄호 } 를 여는 괄호 { 로 변환하여 추가
					stack.push('{');
					minCount++;
				}
			}
		}

		minCount += (stack.size() / 2);
		return minCount;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringBuilder sb = new StringBuilder();

		int i = 1;
		while (true) {
			str = br.readLine();
			if (str.contains("-"))
				break;

			sb.append(i).append(". ")
					.append(solution(str)).append("\n");
			i++;
		}

		System.out.println(sb.toString());
	}
}

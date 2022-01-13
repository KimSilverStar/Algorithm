package DataStructure.Stack.괄호_제거;
import java.io.*;
import java.util.*;

/*
* 입력 수식에서 괄호를 제거해서 도출되는 서로 다른 식의 조합 개수 구하기
 - 괄호 쌍은 1 ~ 10개
 - 도출되는 괄호 제거 수식들은 사전 순으로 정렬하여 출력

1. 아이디어
 1) Stack 으로 짝을 이루는 괄호 index (Pair) 저장
 2) 재귀 함수로 괄호 쌍을 제거 or 제거 안한 문자열 구하기
   - 종료 조건: 제거한 괄호 쌍 개수 == 전체 괄호 쌍 개수
     => Set 에 결과 수식 문자열 저장
   - 재귀 호출
     => 현재 괄호 쌍을 제거하고, 재귀 호출
     => 현재 괄호 쌍을 제거하지 않고, 재귀 호출
 !!! 괄호 쌍을 제거하지 않고 재귀 호출하는 케이스가 존재하여,
     괄호 쌍을 1개도 삭제하지 않아서 (결과 수식 == 입력 수식) 인 상황 발생
     => 모든 재귀 호출이 끝나고,
        결과 수식들이 저장된 Set 에서 입력 수식을 제거해줘야 함

e.g. 입력 수식 "(1 + (2 * (3 + 4)))" 에서 재귀 함수 init 호출 이후 상황
 1) 첫 번째 괄호 쌍 제거하고, 재귀 호출
   => 그 다음 호출로 "1 + (2 * (3 + 4))" 가 전달되고,
      여기서 다음 괄호 쌍 각각 제거 O / 제거 X 후 재귀 호출
 2) 첫 번째 괄호 쌍 제거하지 않고, 재귀 호출
   => 그 다음 호출로 "(1 + (2 * (3 + 4)))" 가 전달되고,
      여기서 다음 괄호 쌍 각각 제거 O / 제거 X 후 재귀 호출

2. 자료구조
 - String: 입력 수식 (변경 X)
 - char[]: 입력 수식에서 괄호 쌍 제거 작업
 - Stack<Integer>: 괄호의 index 저장. 괄호 쌍 매칭 용도
 - ArrayList<Pair>: 괄호 쌍 index 들을 저장
 - TreeSet<String>: 괄호 쌍을 제거한 수식들 저장
   => 중복 수식 제거, 오름차순 정렬

3. 시간 복잡도
 - 괄호 쌍 매칭: O(n)		(n: 입력 수식 문자열 길이)
   => 수식 최대 길이 대입: 200
 - 재귀 함수: F(n) = F(2^n)	(n: 괄호 쌍 개수)
   => 괄호 쌍 최대 개수 대입: 2^10
 => 총 시간 복잡도 = 200 + 2^10 = 1,224 << 1억 (1초)
*/

class Pair {
	private int start;		// 시작, 종료 index
	private int end;

	public Pair(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int getStart() { return start; }
	public int getEnd() { return end; }
}

public class Main {
	static String input;			// 입력 수식
	static char[] expression;
	static List<Pair> pairs = new ArrayList<>();		// 매칭된 괄호 쌍 index 들
	static Stack<Integer> stack = new Stack<>();		// 괄호 쌍 매칭
	static Set<String> set = new TreeSet<>();			// 최종 도출 수식들 저장

	static void solution(int pairIdx) {
		// 재귀 종료
		if (pairIdx == pairs.size()) {
			// Set 에 결과 수식 문자열 저장
			String output = new String(expression);
			output = output.replaceAll(" ", "");		// 괄호 제거 처리한 공백을 제거
			set.add(output);
			return;
		}

		// 1) 괄호 쌍 제거하고, 재귀 호출
		Pair currentPair = pairs.get(pairIdx);
		expression[currentPair.getStart()] = ' ';
		expression[currentPair.getEnd()] = ' ';
		solution(pairIdx + 1);

		// 2) 괄호 쌍 제거하지 않고, 재귀 호출
		expression[currentPair.getStart()] = '(';		// 제거한 괄호 쌍 복구
		expression[currentPair.getEnd()] = ')';
		solution(pairIdx + 1);
	}

	static void matchBrackets() {
		// 괄호 쌍 매칭하여 괄호 쌍 index 를 Pair 로 저장
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '(')
				stack.push(i);
			else if (input.charAt(i) == ')')
				pairs.add(new Pair(stack.pop(), i));
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringBuilder sb = new StringBuilder();

		input = br.readLine();		// 입력 수식
		expression = input.toCharArray();
//		expression = new char[input.length()];
//		for (int i = 0; i < input.length(); i++)
//			expression[i] = input.charAt(i);

		matchBrackets();
		solution(0);		// Init Call

		set.remove(input);
		// 예외 처리: 괄호 쌍이 제거되지 않은 수식(입력 수식과 같은 결과 수식) 제외

		for (String str : set)
			sb.append(str).append("\n");

		System.out.println(sb.toString());
	}
}

package String.부분_문자열;
import java.io.*;
import java.util.StringTokenizer;

/*
- "s가 t의 부분 문자열"
  = t에서 일부 문자를 제거한 후 순서 변경없이 합쳤을 때, s와 t가 같음
- s가 t의 부분 문자열인지 판단하기
*/

/*
1. 아이디어
 - 두 문자열 s, t의 문자를 가리키는 2개의 포인터 사용

2. 자료구조
 - String s, t: 입력 문자열
 - int sPtr, tPtr: s와 t의 문자를 가리키는 포인터

3. 시간 복잡도
 - O(문자열 t의 길이)
   => 문자열 길이 최대 값 대입: 10^5 << 1억
*/

public class Main {
	static String s, t;			// 입력 문자열
	static int sPtr, tPtr;		// s, t의 문자를 가리키는 포인터: 0 ~ (len-1)
	static StringBuilder sb = new StringBuilder();		// 출력

	static void solution() {
		sPtr = 0;
		tPtr = 0;

		while (sPtr < s.length() && tPtr < t.length()) {
			// 문자 일치하는 경우, s와 t의 다음 문자 일치 비교
			if (s.charAt(sPtr) == t.charAt(tPtr)) {
				sPtr++;
				tPtr++;
			}
			// 문자 일치하지 않는 경우, 일치할 때까지 t의 다음 문자 확인
			else {
				tPtr++;
			}
		}

		if (sPtr == s.length()) {
			sb.append("Yes").append("\n");
		}
		else {
			sb.append("No").append("\n");
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		while (true) {
			String inputStr = br.readLine();
			if (inputStr == null || inputStr.isEmpty()) {
				break;
			}

			st = new StringTokenizer(inputStr);
			s = st.nextToken();
			t = st.nextToken();

			solution();
		}

		System.out.println(sb);
	}
}

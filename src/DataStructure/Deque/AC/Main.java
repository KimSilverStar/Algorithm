package DataStructure.Deque.AC;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Deque;
import java.util.ArrayDeque;

/*
 - 정수 배열에 연산
 1) 함수 R: 배열 순서 뒤집기
 2) 함수 D: 배열 첫 번째 요소 arr[0] 버리기
   - 빈 배열에 D 연산하면 "error" 출력

1. 아이디어
 - Deque 를 통해 주어진 R, D 함수 수행
 1) R 함수 (뒤집기)
   - reverse flag 변수 조정
 2) D 함수 (맨 앞 요소 삭제)
   - reverse == true (배열이 뒤집힌 상태)이면,
     Deque 의 뒤에서 삭제 후, 결과 배열 뒤에서부터 출력
   - reverse == false 이면,
     Deque 의 앞에서 삭제 후, 결과 배열 앞에서부터 출력

2. 자료구조
 - Deque<Integer>: ArrayDeque<Integer>
 - boolean isReversed: R 함수 적용 여부 (배열 뒤집힌 상태 여부)

3. 시간 복잡도
 - Deque 의 시간 복잡도
   => 삽입 / 삭제: O(1)

 - 입력 배열 문자열 파싱: 대충 O(t x n)
 - 배열에 주어진 함수 수행: O(t x p)
 => 총 시간 복잡도: O(t(n + p))
 => t, n, p 최대값 대입: 10^2(10^5 + 10^5) = 2 x 10^7 << 1억 (1초)
*/

public class Main {
	static int t;		// 테스트 케이스 개수

	static String p;	// 수행할 함수들
	static int n;		// 배열에 들어있는 정수의 개수
	static Deque<Integer> deque = new ArrayDeque<>();	// 입력 배열을 Deque 에 저장
	static boolean isReversed = false;		// R (뒤집힌 상태) 여부

	// 테스트 케이스 1개 수행
	static String ac() {
		// R 또는 D 함수들 실행
		for (int i = 0; i < p.length(); i++) {
			if (p.charAt(i) == 'R')
				isReversed = !isReversed;
			else if (p.charAt(i) == 'D') {	// 빈 배열이면 "error" 출력 후, 해당 테스트 케이스 종료
				if (deque.isEmpty())
					return "error";

				if (!isReversed)		// 앞에서 삭제
					deque.removeFirst();
				else		// 뒤에서 삭제
					deque.removeLast();
			}
		}

		// sb 에 결과 배열 저장
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (!isReversed) {		// 앞에서부터 저장
			while (!deque.isEmpty()) {
				sb.append(deque.removeFirst());
				if (!deque.isEmpty())
					sb.append(",");
			}
		}
		else {		// 뒤에서부터 저장
			while (!deque.isEmpty()) {
				sb.append(deque.removeLast());
				if (!deque.isEmpty())
					sb.append(",");
			}
		}
		sb.append("]");

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		t = Integer.parseInt(br.readLine());		// 테스트 케이스 개수
		for (int i = 0; i < t; i++) {
			p = br.readLine();		// 수행할 함수들
			n = Integer.parseInt(br.readLine());
			deque = new ArrayDeque<>();

			// 콤마 "," 단위로 파싱 (구분자인 콤마도 토큰에 포함)
			st = new StringTokenizer(br.readLine(), ",", true);
			while (st.hasMoreTokens())  {
				StringBuilder parsedNum = new StringBuilder();		// 파싱한 정수 문자열
				String token = st.nextToken();

				for (int j = 0; j < token.length(); j++) {
					if (Character.isDigit(token.charAt(j)))
						parsedNum.append(token.charAt(j));
				}

				if (parsedNum.length() != 0)
					deque.addLast(Integer.parseInt(parsedNum.toString()));
			}

			System.out.println(ac());

			isReversed = false;
		}
	}
}

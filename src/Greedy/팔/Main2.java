package Greedy.팔;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) L 의 전체 자릿 수 != R 의 전체 자릿 수인 경우
   - 8의 최소 개수는 0
 2) L 의 전체 자릿 수 == R 의 전체 자릿 수인 경우
   - 높은 자릿 수 부터 각 동일 자릿 수를 비교하여, 8로 같은 자릿 수의 개수
   - 두 자릿 수가 다르면, 비교 종료
     e.g. 800, 899 => 1 (백의 자리 8)
     	  8808, 8880 => 2 (천의 자리 8, 백의 자리 8)
     	  1208, 1288 => 0 (십의 자리 비교 했을 때, 서로 다르므로 비교 종료)
     	  88083, 88084 => 3 (만의 자리 8, 천의 자리 8, 백의 자리는 0으로 같으므로 비교 계속, 십의 자리 8)

2. 자료구조

3. 시간 복잡도
 - O(len)
   => len: 입력 L 또는 R 의 문자열 길이
   => len 최대값 대입: 10
*/

public class Main2 {
	static String L, R;				// 1 <= L <= R (최대 20억)
	static int minCount;			// 가장 적은 8 개수

	static void solution() {
		// 1) L 의 전체 자릿 수 != R 의 전체 자릿 수인 경우
		if (L.length() != R.length()) {
			minCount = 0;
			return;
		}

		// 2) L 의 전체 자릿 수 == R 의 전체 자릿 수인 경우
		for (int i = 0; i < L.length(); i++) {
			// 서로 자릿 수 값이 다르면, 비교 종료
			if (L.charAt(i) != R.charAt(i))
				break;
			// 각 동일 자릿 수를 비교하여, 8로 같은 자릿 수의 개수
			else if (L.charAt(i) == '8' && R.charAt(i) == '8')
				minCount++;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		L = st.nextToken();
		R = st.nextToken();

		solution();
		System.out.println(minCount);
	}
}

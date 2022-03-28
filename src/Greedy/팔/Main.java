package Greedy.팔;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 1) L == R 인 경우, 8 개수 세서 반환
 2) L 값에 8이 없는 경우, 바로 0 반환
 3) L 값에 8이 있는 경우
   - L 값에서 맨 앞 자리 digit 부터 8의 자리를 9로 바꾸고,
     나머지 뒷 자리는 0으로 채운 값이 R 이하인지 확인
     e.g. 8808(L) ~ 8880(R) 이면
          9000(천의 자리 8), 8900(백의 자리 8), 8809(일의 자리 8) 차례로 확인

2. 자료구조
 - boolean[]: L 값에 들어있는 8의 자리 표시

3. 시간 복잡도
 - O(k x len)
   => k: 입력 L 값에 들어있는 8의 개수, len: 입력 L 값의 문자열 길이
   => k, len 최대값 대입: 10 x 10 = 100
*/

public class Main {
	static int L, R;				// 1 <= L <= R (최대 20억)
	static int minCount;			// 가장 적은 8 개수
	static boolean[] eightIdx;		// L 값에서 8이 있는 자리 표시

	/* number 에 들어있는 숫자 8의 개수를 반환 */
	static int countEight(int number) {
		int count = 0;

		String strNumber = String.valueOf(number);
		for (int i = 0; i < strNumber.length(); i++) {
			if (strNumber.charAt(i) == '8') {
				count++;
				eightIdx[i] = true;		// 8의 자리 표시
			}
		}

		return count;
	}

	/* idx 자리에 8이 들어있는 수 number 에서, idx 자리의 8을 9로 바꾸고 뒤의 자리를 0으로 바꾼 수 반환 */
	static int getNineNumber(int number, int idx) {
		char[] charsNumber = String.valueOf(number).toCharArray();

		charsNumber[idx] = '9';
		for (int i = idx + 1; i < charsNumber.length; i++)
			charsNumber[i] = '0';

		return Integer.parseInt(String.valueOf(charsNumber));
	}

	static void solution() {
		int len = String.valueOf(L).length();
		eightIdx = new boolean[len];

		int count = countEight(L);		// L에 들어있는 숫자 8 개수 카운트

		// L과 R이 같거나, L에 숫자 8이 없는 경우
		if (L == R || count == 0) {
			minCount = count;
			return;
		}

		// L 에 숫자 8이 있는 경우
		for (int i = 0; i < len; i++) {
			if (eightIdx[i]) {
				int nineNumber = getNineNumber(L, i);

				if (nineNumber <= R) {
					minCount = countEight(nineNumber);
					return;
				}
			}
		}

		// 9로 바꾼 값 > R 인 경우
		// => 8의 최소 개수 = 기존 L 값에 들어있는 8 개수
		minCount = count;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		solution();
		System.out.println(minCount);
	}
}

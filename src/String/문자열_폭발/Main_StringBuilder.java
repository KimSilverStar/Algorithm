package String.문자열_폭발;
import java.io.*;

/*
1. 아이디어
 - 입력 문자열을 한 문자씩 차례로 StringBuilder 에 담아가면서 확인
 - 폭발 문자열과 동일한 문자열이 StringBuilder 에 존재하면, 삭제

2. 자료구조
 - StringBuilder: 입력 문자열을 한 문자씩 담아가면서 폭발 문자열인지 확인

3. 시간 복잡도
 - 문자열 최대 길이: 10^6 (100만)
   => O(n)
*/

/* 오답 노트
* 시간 초과된 풀이
 - 입력 문자열을 StringBuilder 에 담음
   => StringBuilder input = new StringBuilder(br.readLine());
 - 입력 문자열을 한 문자씩 차례로 확인
   1) 폭발 문자열과 동일한 문자열이 존재하면, 동일 문자열 시작 index 저장
   2) 폭발 문자열 제거 후, 저장한 시작 index 의 폭발 문자열 길이 이전부터 다시 확인 반복
     - 폭발 문자열 제거: input.delete(i, i + bomb.length());
*/

public class Main_StringBuilder {
	static String input;			// 입력 문자열
	static String bomb;				// 폭발 문자열

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		StringBuilder sb = new StringBuilder();
		input = br.readLine();
		bomb = br.readLine();

		for (int i = 0; i < input.length(); i++) {
			sb.append(input.charAt(i));

			if (sb.length() >= bomb.length()) {
				boolean isSame = true;
				int idx = sb.length() - bomb.length();

				for (int j = idx; j < sb.length(); j++) {
					if (sb.charAt(j) != bomb.charAt(j - idx)) {
						isSame = false;
						break;
					}
				}

				if (isSame) {
					sb.delete(idx, idx + bomb.length());
				}
			}
		}

		if (sb.length() == 0)
			System.out.println("FRULA");
		else
			System.out.println(sb.toString());
	}
}

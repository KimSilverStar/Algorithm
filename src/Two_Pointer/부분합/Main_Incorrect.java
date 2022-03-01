package Two_Pointer.부분합;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - "연속된 수들의 부분합 ~"
   => 연속하다는 특징 이용가능하므로, 투 포인터 사용

* 오답 처리된 풀이
 1) 최초 모든 수들의 합을 구함
   - 모든 수들의 합 >= s 이면, 0 출력 및 종료
 2) 2개의 포인터 ptr1, ptr2 가 각각 첫 번째 원소, 마지막 원소를 가리킴
 3) 다음을 합 >= s 가 유지될때 까지 반복
   - 2개의 포인터가 가리키는 2개의 수 중에서, 더 작은 값을 합에서 뺌
     (단, 합에서 뺀 값 >= s 가 되어야 함)
   - ptr1 이 가리키는 수를 뺀 경우, ptr1 을 오른쪽으로 한 칸 이동,
     ptr2 가 가리키는 수를 뺀 경우, ptr2 를 왼쪽으로 한 칸 이동

2. 자료구조
 - int s: 입력, 부분합 최소 목표
   => 10^8 (1억) << 21억 이므로, int 가능

3. 시간 복잡도
 - 대략 반복문을 수열 길이만큼 반복: O(n)
*/

public class Main_Incorrect {
	static int n, s;			// 수열 길이 n, 부분합 최소 목표 s
	static int[] numbers;
	static int minLen;			// 출력, 최소 길이
	static int sum;				// 수열의 합

	static void solution() {
		int ptr1 = 0;
		int ptr2 = n - 1;

		while (true) {
			int minValue;

			if (ptr1 >= n || ptr2 < 0)
				break;

			if (numbers[ptr1] < numbers[ptr2]) {
				minValue = numbers[ptr1];

				if (sum - minValue < s)
					break;

				ptr1++;
			}
			else {
				minValue = numbers[ptr2];

				if (sum - minValue < s)
					break;

				ptr2--;
			}

			sum -= minValue;
		}

		minLen = ptr2 - ptr1 + 1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		s = Integer.parseInt(st.nextToken());

		numbers = new int[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			numbers[i] = Integer.parseInt(st.nextToken());
			sum += numbers[i];
		}

		if (sum < s)
			System.out.println(0);
		else {
			solution();
			System.out.println(minLen);
		}
	}
}

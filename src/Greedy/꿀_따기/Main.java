package Greedy.꿀_따기;
import java.io.*;
import java.util.StringTokenizer;

/*
- 벌 위치 2곳, 벌통 위치 1곳 선택
  => 채집한 총 꿀 양이 최대가 되도록 함
- 최대 꿀 양 출력
*/

/*
1. 아이디어
 case 1) 꿀 통이 맨 오른쪽, 벌 1이 맨 왼쪽 => 벌 2의 위치 선택
        - 벌 1 채집량: 모든 장소들의 꿀 양 합 - (벌 1 위치의 꿀 양 + 벌 2 위치의 꿀 양)
        - 벌 2 채집량: 모든 장소들의 꿀 양 합 - [0 ~ 벌 2 위치] 누적합

 case 2) 꿀 통이 맨 왼쪽, 벌 1이 맨 오른쪽 => 벌 2의 위치 선택
        - 벌 1 채집량: 모든 장소들의 꿀 양 합 - (벌 1 위치의 꿀 양 + 벌 2 위치의 꿀 양)
        - 벌 2 채집량: 모든 장소들의 꿀 양 합 - [끝 ~ 벌 2 위치] 누적합

 case 3) 꿀 통이 벌 사이에 위치 => 무조건 벌 1이 맨 왼쪽, 벌 2가 맨 오른쪽

2. 자료구조
 - long[] toRightTotal: [0 ~ 벌 2 위치] 누적합
 - long[] toLeftTotal: [끝 ~ 벌 2 위치] 누적합
   => 장소 개수 n 최대 10^5, 각 장소의 꿀 양 최대 10^5

3. 시간 복잡도
 - 1개의 case 에 대해 반복문 대략 O(n)
 => 총 시간 복잡도: O(3n)
 => 3n < 10^8 (10억)
 => 대략 n 이 33,333,333 까지 가능
*/

public class Main {
	static int n;					// n 개 장소
	static int[] honeys;			// 각 장소의 꿀 양
	static long maxCount;			// 출력: 최대 꿀 양

	static long total;				// 모든 장소들의 꿀 양 합
	static long[] toRightTotal;		// [0 ~ 벌 2 위치] 누적합
	static long[] toLeftTotal;		// [끝 ~ 벌 2 위치] 누적합

	/* 꿀 통: 맨 오른쪽 고정, 벌 1: 맨 왼쪽 고정 => 벌 2의 위치 선택 */
	static void case1() {
		long bee1, bee2;		// 벌 1, 벌 2의 꿀 채집량

		for (int i = 1; i <= n - 2; i++) {
			bee1 = total - honeys[0] - honeys[i];
			bee2 = total - toRightTotal[i];
			maxCount = Math.max(maxCount, bee1 + bee2);
		}
	}

	/* 꿀 통: 맨 왼쪽 고정, 벌 1: 맨 오른쪽 고정 => 벌 2의 위치 선택 */
	static void case2() {
		long bee1, bee2;

		for (int i = n - 2; i >= 1; i--) {
			bee1 = total - honeys[n - 1] - honeys[i];
			bee2 = total - toLeftTotal[i];
			maxCount = Math.max(maxCount, bee1 + bee2);
		}
	}

	/* 벌 1: 맨 왼쪽 고정, 벌 2: 맨 오른쪽 고정 => 꿀 통: 벌 사이에서 선택 */
	static void case3() {
		long bee1, bee2;

		for (int i = 1; i <= n - 2; i++) {
			bee1 = total - honeys[0] - toLeftTotal[i + 1];
			bee2 = total - honeys[n - 1] - toRightTotal[i - 1];
			maxCount = Math.max(maxCount, bee1 + bee2);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		honeys = new int[n];
		toRightTotal = new long[n];			// [0 ~ 벌 2 위치] 누적합
		toLeftTotal = new long[n];			// [끝 ~ 벌 2 위치] 누적합
		long temp = 0;
		for (int i = 0; i < n; i++) {
			honeys[i] = Integer.parseInt(st.nextToken());

			temp += honeys[i];
			toRightTotal[i] = temp;
		}

		temp = 0;
		for (int i = n - 1; i >= 0; i--) {
			temp += honeys[i];
			toLeftTotal[i] = temp;
		}

		total = toRightTotal[n - 1];

		case1();
		case2();
		case3();

		System.out.println(maxCount);
	}
}

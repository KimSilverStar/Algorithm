package BinarySearch.나무_자르기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 절단기 높이를 크게 설정할 수록, 가져가는 나무 길이가 작아짐
   => 필요한 최소 나무 길이 m 을 만족하는 절단기 최대 높이 구하기

 - 1 ~ maxHeight (입력 나무 최대 길이) 에 대해 이진 탐색
 - midHeight 에 대해, sumLength (가져가는 나무 길이 합) 계산
 1) sumLength >= m 인 경우
   - 출력 resultMaxHeight 갱신
   - start = midHeight + 1, end = 그대로 다시 탐색
     => 절단기 높이를 더 크게 하여, 다시 탐색
 2) sumLength < m 인 경우
   - end = midHeight - 1, start = 그대로 다시 탐색
     => 절단기 높이를 더 작게 하여, 다시 탐색


2. 자료구조
 - int[]: 입력 나무 길이


3. 시간 복잡도
 1) 이진 탐색: O(log_2 maxHeight)
   - maxHeight: 입력 나무 높이 중, 최대 높이
   => 최대 log_2 10^9 = 9 x log_2 10 ~= 27
 2) 나무 길이 합 계산: O(n)
   => 최대 10^6
 - 총 시간 복잡도: O(n x log_2 maxHeight)
   => 최대 27 x 10^6 << 1억
*/

public class Main_Recursive {
	static int n, m;				// 전체 n개 나무, 필요한 최소 나무 길이 m
	static int[] heights;			// n개 나무 길이
	static int resultMaxHeight;		// 출력, 절단기 설정 최대 높이
	static int maxHeight = Integer.MIN_VALUE;		// 입력 n개 나무 길이 중, 최대 길이

	static void binarySearch(long start, long end) {
		if (start > end)
			return;

		long midHeight = (start + end) / 2;				// int 로 설정 시 (start + end)에서 Overflow 발생 가능
		long sumLength = getSumLength((int) midHeight);	// midHeight 로 잘랐을 때, 가져가는 나무 길이 합

		if (sumLength >= m) {
			resultMaxHeight = Math.max(resultMaxHeight, (int) midHeight);
			binarySearch(midHeight + 1, end);
		}
		else {
			binarySearch(start, midHeight - 1);
		}
	}

	/* 절단기 높이를 cutHeight 로 설정 했을 때, 가져가는 나무 길이 합 계산 */
	static long getSumLength(int cutHeight) {
		long sumLength = 0;
		for (int height : heights) {
			if (height > cutHeight)
				sumLength += (height - cutHeight);
		}
		return sumLength;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());

		heights = new int[n];
		for (int i = 0; i < n; i++) {
			heights[i] = Integer.parseInt(st.nextToken());
			maxHeight = Math.max(maxHeight, heights[i]);
		}

		binarySearch(1, maxHeight);
		System.out.println(resultMaxHeight);
	}
}

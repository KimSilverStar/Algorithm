package BinarySearch.선분_위의_점;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 ※ 완전 탐색하는 경우
   - 1개 선분에 대해 n개 좌표 확인: O(n)
   - m개 선분에 대해 n개 좌표 확인: O(n x m)
     => n, m 최대값 대입: 10^5 x 10^5 = 10^10 >> 1억 (시간 초과 !!)

 - 각 선분의 시작, 끝 좌표에 대해 이진 탐색 수행
   => 각 선분에 포함되는 입력 좌표의 최소값, 최대값 찾음
   ex) 선분 1 ~ 10에 속하는 최소 좌표값 1, 최대 좌표값 10
       선분 20 ~ 60에 속하는 최소 좌표값 20, 최대 좌표값 30
 - 이진 탐색을 위해 입력 좌표 배열 정렬

 1) 시작 좌표 startPos 에 대해 이진 탐색
   ① startPos < positions[midIdx] 인 경우
     - minIdx 갱신
     - start = start, end = midIdx - 1 로 다시 탐색
   ② startPos > positions[midIdx] 인 경우
     - start = midIdx + 1, end = end 로 다시 탐색
   ③ startPos == positions[midIdx] 인 경우
     - minIdx = midIdx, 탐색 종료

 2) 끝 좌표 endPos 에 대해 이진 탐색
   ① endPos < positions[midIdx] 인 경우
     - start = start, end = midIdx - 1 로 다시 탐색
   ② endPos > positions[midIdx] 인 경우
     - maxIdx 갱신
     - start = midIdx + 1, end = end 로 다시 탐색
   ③ endPos == positions[midIdx] 인 경우
     - maxIdx = midIdx, 탐색 종료


2. 자료구조
 - int[] positions: 입력 n개 좌표
 - Pair[] lines: 입력 m개 선분 (각 선분의 시작, 끝 좌표)


3. 시간 복잡도
 - 배열 정렬: O(n log_2 n)
 - 1개 선분에 대해 이진 탐색 2번(시작, 끝 좌표) 수행: O(2 log_2 n)
 - m개 선분에 대해 이진 탐색 2번(시작, 끝 좌표) 수행: O(2 x m x log_2 n)
 => 총 시간 복잡도: O((2m + n) log_2 n)
 => n, m 최대값 대입: (3 x 10^5) x log_2 10^5 = (15 x 10^5) log_2 10 ~= 45 x 10^5 << 1억
*/

public class Main_Recursive {
	static int n, m;				// n개 점, m개 선분
	static int[] positions;			// 좌표
	static Pair[] lines;			// 각 선분의 시작, 끝 좌표
	static StringBuilder sb = new StringBuilder();
	static int minIdx, maxIdx;		// 각 선분에 속하는 좌표 최소값, 최대값

	static void solution() {
		// 각 선분마다 시작, 끝 좌표에 대해 각각 이진 탐색
		for (int i = 0; i < m; i++) {
			minIdx = Integer.MAX_VALUE;
			binarySearch1(0, n - 1, lines[i].startPos);

			maxIdx = Integer.MIN_VALUE;
			binarySearch2(0, n - 1, lines[i].endPos);

			int count = 0;				// 선분에 포함되는 입력 좌표 개수
			if (minIdx <= maxIdx)
				count = maxIdx - minIdx + 1;
			sb.append(count).append("\n");
		}
	}

	/* 선분의 시작 좌표에 대해 이진 탐색 */
	static void binarySearch1(int startIdx, int endIdx, int target) {
		if (startIdx > endIdx)
			return;

		int midIdx = (startIdx + endIdx) / 2;
		if (target < positions[midIdx]) {
			minIdx = Math.min(minIdx, midIdx);
			binarySearch1(startIdx, midIdx - 1, target);
		}
		else if (target > positions[midIdx]) {
			binarySearch1(midIdx + 1, endIdx, target);
		}
		else {
			minIdx = midIdx;
			return;
		}
	}

	/* 선분의 끝 좌표에 대해 이진 탐색 */
	static void binarySearch2(int startIdx, int endIdx, int target) {
		if (startIdx > endIdx)
			return;

		int midIdx = (startIdx + endIdx) / 2;
		if (target < positions[midIdx]) {
			binarySearch2(startIdx, midIdx - 1, target);
		}
		else if (target > positions[midIdx]) {
			maxIdx = Math.max(maxIdx, midIdx);
			binarySearch2(midIdx + 1, endIdx, target);
		}
		else {
			maxIdx = midIdx;
			return;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());

		positions = new int[n];
		for (int i = 0; i < n; i++)
			positions[i] = Integer.parseInt(st.nextToken());

		lines = new Pair[m];
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int startPos = Integer.parseInt(st.nextToken());
			int endPos = Integer.parseInt(st.nextToken());

			lines[i] = new Pair(startPos, endPos);
		}

		Arrays.sort(positions);		// 이진 탐색을 위한 배열 정렬

		solution();
		System.out.println(sb);
	}
}

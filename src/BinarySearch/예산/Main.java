package BinarySearch.예산;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 상한액 지정 액수에 따라, 지방 예산액이 정해짐
   => 상한액을 1원 ~ m원까지 탐색
 - 완전 탐색할 경우, O(n x m) 으로 시간 초과 !!!
   => 이진 탐색 수행

 - Init Call: binarySearch(1, m)
 - mid: 상한액
 1) 예산 분배 합 sum > 총 예산 m 인 경우
   - start = start, end = mid - 1 로 다시 탐색
 2) 예산 분배 합 sum < 총 예산 m 인 경우
   - 출력 maxCost 갱신
   - start = mid + 1, end = end 로 다시 탐색
 3) 예산 분배 합 sum == 총 예산 m 인 경우
   - 출력 maxCost = 지방에 분배한 예산 중, 최대값
   - 탐색 종료


2. 자료구조
 - int[]: 입력, 각 지방의 요청 예산
   ① 자료형: 원소 최대값 10^5 << 21억 이므로, int 가능
   ② 메모리: 최대 4 x 10^4 byte = 40 KB


3. 시간 복잡도
 - 이분 탐색의 시간 복잡도: O(log_2 m)
 - 전체 시간 복잡도: O(n log_2 m)
   => 이분 탐색에서 매 탐색마다 for문으로 각 지방에 예산 분배: O(n)
   => n, m 최대값 대입: 10^4 x log_2 10^9 = 9 x 10^4 x log_2 10
      ~= 9 x 10^4 x 3 = 27 x 10^4 << 1억
*/

public class Main {
	static int n;					// 지방의 수
	static int[] requestCosts;		// 각 지방의 요청 예산
	static int m;					// 총 예산
	static int maxCost;				// 배정된 최대 지방 예산

	static void binarySearch(int start, int end) {
		if (start > end)
			return;

		int mid = (start + end) / 2;		// 상한액
		int sum = 0;						// 분배 예산 합
		int max = 0;						// 지방에 분배한 예산 중, 최대값
		for (int i = 0; i < n; i++) {
			if (requestCosts[i] <= mid) {
				sum += requestCosts[i];
				max = Math.max(max, requestCosts[i]);
			}
			else if (requestCosts[i] > mid) {
				sum += mid;
				max = Math.max(max, mid);
			}
		}

		// 1) 예산 분배 합 sum > 총 예산 m 인 경우 (예산 부족)
		if (sum > m) {
			binarySearch(start, mid - 1);
		}
		// 2) 예산 분배 합 sum < 총 예산 m 인 경우
		else if (sum < m) {
			maxCost = Math.max(maxCost, max);		// 지방 최대 분배 예산 갱신
			binarySearch(mid + 1, end);
		}
		// 3) 예산 분배 합 sum == 총 예산 m 인 경우
		else {
			maxCost = max;
			return;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		requestCosts = new int[n];
		for (int i = 0; i < n; i++)
			requestCosts[i] = Integer.parseInt(st.nextToken());

		m = Integer.parseInt(br.readLine());

		binarySearch(1, m);
		System.out.println(maxCost);
	}
}

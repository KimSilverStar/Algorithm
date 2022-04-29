package BinarySearch.입국심사;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 0초 ~ (maxTime x m)초 범위에서 mid 초에 대해, 심사 가능한 인원을 계산
 - maxTime x m: 최장 심사 시간(입력 심사 시간 배열 중, 최대값) x m명
 - mid 초 안에 심사 가능한 인원 = (mid / times[i])의 합

 - Init Call: binarySearch(0, maxTime * m)
 1) mid 초 안에 심사 가능한 총 인원 sum < 총 인원 m 인 경우
   - mid 초 안에 모든 인원을 심사하지 못하므로, mid 초를 더 늘려서 다시 탐색
   => start = mid + 1, end = end 로 다시 탐색
 2) mid 초 안에 심사 가능한 총 인원 sum >= 총 인원 m 인 경우
   - mid 초 안에 모든 인원을 심사하고도 시간이 남으므로, mid 초를 줄여서 다시 탐색
   => 출력 minSumTime 갱신
   => start = start, end = mid - 1 로 다시 탐색


2. 자료구조
 - long minSumTime: 출력, 최소 시간
   => 최대값: 10^9 x 10^9 = 10^18 > 21억으로, int 불가능
 - long mid: 이진 탐색 범위 0초 ~ (maxTime x m)초
   => 최대값: maxTime x m = 10^18 > 21억으로, int 불가능
 - long sum: mid 초 안에 심사 가능한 총 인원
   => 최대값: mid 와 같음 (times[i] 가 모두 1 일 경우)


3. 시간 복잡도
 - 이진 탐색 수행, 각 탐색(mid)에 대해 for문 n만큼 반복: O(n log_2 (maxTime x m))
   => 최대값 대입: 10^5 x log_2 (10^9 x 10^9) = 10^5 x log_2 10^18
      = (18 x 10^5) log_2 10 ~= 54 x 10^5 << 1억
*/

public class Main_Iterative {
	static int n, m;			// n개 심사대, m명 인원
	static int[] times;			// 각 심사대의 심사 시간
	static int maxTime;
	static long minSumTime = Long.MAX_VALUE;		// 출력, 최소 시간

	static void binarySearch(long start, long end) {
		while (start <= end) {
			long mid = (start + end) / 2;
			long sum = 0;

			for (int i = 0; i < n; i++)
				sum += (mid / times[i]);

			if (sum < m) {
				start = mid + 1;
			}
			else {
				minSumTime = Math.min(minSumTime, mid);
				end = mid - 1;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		times = new int[n];
		for (int i = 0; i < n; i++) {
			times[i] = Integer.parseInt(br.readLine());
			maxTime = Math.max(maxTime, times[i]);
		}

		binarySearch(0, (long)maxTime * m);
		System.out.println(minSumTime);
	}
}

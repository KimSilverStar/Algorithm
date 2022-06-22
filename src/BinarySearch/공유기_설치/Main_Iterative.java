package BinarySearch.공유기_설치;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - n개 집에 c개의 공유기 설치
 - 최대한 많은 곳에서 와이파이 사용하도록 설치
 - 인접한 두 공유기 사이의 거리를 최대로 하여 설치
 => 출력: "가장 인접한 두 공유기" 사이의 최대 거리

 - "c개의 공유기를 n개의 집에 적당히 설치해서, 가장 인접한 두 공유기 사이의 거리를 최대로"
   => 공유기 설치 시, 인접한 두 공유기 사이의 거리 값 (설치 간격)을 이진 탐색
   => 설치할 공유기 사이의 거리 (설치 간격)를 이진 탐색

 - n개 집 좌표 homes[]를 작은 순으로 정렬
 - 1 ~ (homes[n-1] - homes[0]) 범위에 대해 이진 탐색
 - 최소 인접 설치 거리를 midDist 로 하여 공유기 설치
   => 설치한 공유기 개수 count 계산
 1) count >= c 인 경우
   - 출력 resultDist 갱신
   - start = midDist + 1, end = 그대로 하여 재탐색
     => 최소 인접 설치 거리를 더 크게하여 설치 (좀더 듬성듬성 설치)
 2) count < c 인 경우
   - start = 그대로, end = midDist - 1 로 재탐색
     => 최소 인접 설치 거리를 더 작게하여 설치 (좀더 촘촘히 설치)


2. 자료구조
 - int[]: 입력 n개 집의 좌표
   => 작은 순으로 정렬


3. 시간 복잡도
 1) 입력 집 좌표 정렬: O(n log_2 n)
   - 최대 (2 x 10^5) log_2 (2 x 10^5) ~= 32 x 10^5
 2) 이진 탐색: O(log_2 maxDist)
   - maxDist = 정렬된 homes[n-1] - homes[0]
   - 최대 log_2 10^9 = 9 log_2 10 ~= 27
 - 총 시간 복잡도 = O(n log_2 n + log_2 maxDist)
   => 최대 32 x 10^5 + 27
*/

public class Main_Iterative {
	static int n, c;				// n개 집, 설치할 c개 공유기
	static int[] homes;				// n개 집의 좌표
	static int resultWifiDist;		// 출력, 인접한 두 공유기 사이의 최대 거리

	static void binarySearch(int startDist, int endDist) {
		while (startDist <= endDist) {
			int midDist = (startDist + endDist) / 2;
			int count = getCount(midDist);		// 최소 인접 설치 거리를 midDist 로 하여, 설치한 공유기 개수

			if (count >= c) {
				resultWifiDist = Math.max(resultWifiDist, midDist);
				startDist = midDist + 1;
			}
			else {
				endDist = midDist - 1;
			}
		}
	}

	/* 인접 설치 최소 거리를 dist 로 하였을 때, 설치하는 공유기 개수 계산 */
	static int getCount(int dist) {
		int count = 1;					// 첫 번째 집에 공유기 설치하고 시작
		int prevWifi = homes[0];		// 이전에 설치한 공유기 좌표

		for (int i = 1; i < n; i++) {
			// 설치 최소 거리 dist 이상이면, 해당 위치에 공유기 설치
			if (homes[i] - prevWifi >= dist) {
				count++;
				prevWifi = homes[i];
			}
		}

		return count;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		homes = new int[n];
		for (int i = 0; i < n; i++)
			homes[i] = Integer.parseInt(br.readLine());

		Arrays.sort(homes);			// 집 좌표 작은 순으로 정렬

		binarySearch(1, homes[n - 1] - homes[0]);
		System.out.println(resultWifiDist);
	}
}

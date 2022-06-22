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
   => 공유기 설치 간격을 이진 탐색

 - n개 집 좌표 homes[]를 작은 순으로 정렬
 - 1 ~ (homes[n-1] - homes[0]) 범위에 대해 이진 탐색
 - 설치 간격을 midDist 로 하여 공유기 설치
   => 설치한 공유기 개수 wifiCount 계산
 1) wifiCount >= c 인 경우
   - 출력 resultWifiDist 갱신
   - start = midDist + 1, end = 그대로 하여 재탐색
     => 설치 간격을 더 크게하여, 좀더 듬성듬성 설치
 2) wifiCount < c 인 경우
   - start = 그대로, end = midDist - 1 로 재탐색
     => 설치 간격을 더 작게하여, 좀더 촘촘히 설치


2. 자료구조
 - int[]: 입력 n개 집의 좌표
   => 작은 순으로 정렬


3. 시간 복잡도
 1) 입력 집 좌표 정렬: O(n log_2 n)
 2) 이진 탐색: O(log_2 maxDist)
   - maxDist = 정렬된 homes[n-1] - homes[0]
 3) 이진 탐색 1회 마다 설치 공유기 개수 계산: O(n)

 - 총 시간 복잡도 = O(n log_2 n + n log_2 maxDist)  = O(n log_2 (n x maxDist))
   => 최대 (2 x 10^5) log_2 (2 x 10^5 x 10^9)
      = (2 x 10^5) log_2 (2 x 10^14) = (2 x 10^5) x (log_2 2 + log_2 10^14)
      = (2 x 10^5) x (1 + 14 log_2 10) ~= (2 x 10^5) x 43 = 86 x 10^5 << 2억
*/

public class Main_Recursive {
	static int n, c;				// n개 집, 설치할 c개 공유기
	static int[] homes;				// n개 집의 좌표
	static int resultWifiDist;		// 출력, 인접한 두 공유기 사이의 최대 거리

	static void binarySearch(int startDist, int endDist) {
		if (startDist > endDist)
			return;

		int midDist = (startDist + endDist) / 2;
		int wifiCount = getWifiCount(midDist);		// 설치 간격을 midDist 로 했을 때, 공유기 설치 개수

		if (wifiCount >= c) {
			resultWifiDist = Math.max(resultWifiDist, midDist);
			binarySearch(midDist + 1, endDist);
		}
		else {
			binarySearch(startDist, midDist - 1);
		}
	}

	/* 인접 설치 최소 거리를 dist 로 하였을 때, 설치하는 공유기 개수 계산 */
	static int getWifiCount(int dist) {
		int wifiCount = 1;				// 첫 번째 집에 공유기 설치하고 시작
		int prevWifi = homes[0];		// 이전에 설치한 공유기 좌표

		for (int i = 1; i < n; i++) {
			// 설치 최소 거리 dist 이상이면, 해당 위치에 공유기 설치
			if (homes[i] - prevWifi >= dist) {
				wifiCount++;
				prevWifi = homes[i];
			}
		}

		return wifiCount;
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

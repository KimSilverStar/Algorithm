package Greedy.센서;
import java.io.*;
import java.util.*;

/*
- n 개 센서, 최대 k 개 집중국
- 각 센서가 최소 1개의 집중국과 통신
- 각 집중국의 수신 가능 영역 거리의 합의 최소값
=> 최대 k 개 집중국의 위치를 선택하여,
   각 집중국의 수신 가능 영역 거리 합의 최소값 도출
- 조건
  => 집중국 수신 영역 길이 >= 0
  => 각 센서의 좌표 동일(중복) 가능

ex 예제 입력 1)
 - 집중국 1: [1, 3] => 영역 2
 - 집중국 2: [6, 9] => 영역 3
 => 총 영역 합 (최소): 5
*/

/*
1. 아이디어
 - 인접한 센서 간 거리가 먼 곳에 집중국을 설치해나감
   => 인접한 센서 간 거리가 가장 먼 곳들을 분리하여, 집중국을 할당하는 느낌

 1) 센서 배열을 센서 위치 빠른 순으로 정렬
 2) 인접 센서 간 거리를 계산하여 배열에 저장 후, 거리 큰 순으로 정렬
   - SensorDistance[]
     => SensorDistance: 2개 센서 번호 (sensor1, sensor2), 2개 센서의 거리
 3) 정렬된 센서 간 거리 배열에서 큰 거리 순으로 (k-1) 개 선택
    => 집중국 설치, 센서 영역 분할
   - List 에 SensorDistance 배열의 각 원소에서 sensor1 저장

2. 자료구조
 - SensorDistance[]: 인접 센서 간 거리, 2개 센서 번호 저장한 배열
 - List<Integer>, ArrayList<Integer>: 집중국 설치할 위치 (거리가 먼 인접 센서 분할) 저장

3. 시간 복잡도
 - 센서 빠른 위치 순으로 정렬: O(n log n)
 - 인접 센서 간 거리 배열 정렬: O(n log n)
 - 영역 최소 합 계산: O(2k)
 => 총 시간 복잡도: O(2n log n + 2k)
 => n, k 최대값 대입: 대충 (2 x 10^5 + log 2^13) + (2 x 10^3)
    = (26 x 10^5) + (2 x 10^3) = (26 x 10^5) + (2 x 10^3) << 2억
*/

class SensorDistance implements Comparable<SensorDistance> {
	public int sensor1, sensor2;
	public int distance;

	public SensorDistance(int sensor1, int sensor2, int distance) {
		this.sensor1 = sensor1;
		this.sensor2 = sensor2;
		this.distance = distance;
	}

	public int compareTo(SensorDistance sd) {
		int distanceDiff = sd.distance - this.distance;
		if (distanceDiff != 0)			// 거리 큰 순
			return distanceDiff;
		else							// 거리가 같으면, sensor 번호가 빠른 순
			return this.sensor1 - sd.sensor1;
	}
}

public class Main {
	static int n, k;					// 센서 개수 n, 집중국 개수 k
	static int[] sensors;				// 각 센서의 위치
	static int minSum;					// 출력: 영역 길이 최소 합

	static SensorDistance[] sds;		// 인접한 센서 간 거리
	static List<Integer> herbList = new ArrayList<>();

	static void solution() {
		// 인접 센서 간 거리 큰 순으로 (k-1)개 선택
		for (int i = 0; i < k - 1; i++) {
			SensorDistance sd = sds[i];
			herbList.add(sd.sensor1);
		}

		// 각 집중국의 영역 계산
		int startIdx = 0;
		for (int herbIdx : herbList) {
			minSum += (sensors[herbIdx] - sensors[startIdx]);
			startIdx = herbIdx + 1;
		}
		minSum += (sensors[n - 1] - sensors[startIdx]);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		k = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		sensors = new int[n];
		sds = new SensorDistance[n - 1];
		for (int i = 0; i < n; i++)
			sensors[i] = Integer.parseInt(st.nextToken());

		// 예외 처리) 집중국 개수 k >= 센서 개수 n 인 경우, 센서마다 집중국 설치
		if (k >= n) {
			System.out.println(0);
			return;
		}

		Arrays.sort(sensors);		// 센서 위치 빠른 순으로 정렬

		// 인접 센서 간 거리 저장
		for (int i = 1; i < n; i++) {
			sds[i - 1] = new SensorDistance(
					i - 1, i, Math.abs(sensors[i - 1] - sensors[i])
			);
		}

		Arrays.sort(sds);			// 인접 센서 간 거리 큰 순으로 정렬

		solution();
		System.out.println(minSum);
	}
}

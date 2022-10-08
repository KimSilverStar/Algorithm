package Samsung_Coding_Test.이차원_배열과_연산;
import java.io.*;
import java.util.*;

/*
- 3 x 3 배열 A, 인덱스 1부터 시작

1초마다 배열에 R 연산 or C 연산 수행

1) R 연산
 - 행 개수 >= 열 개수인 경우, A의 모든 행에 대해 정렬 수행
 - 가장 큰 행을 기준으로, 모든 행의 크기 변화 (행 크기가 커진 곳은 0으로 채움)
2) C 연산
 - 행 개수 < 열 개수인 경우, A의 모든 열에 대해 정렬 수행
 - 가장 큰 열을 기준으로, 모든 열의 크기 변화 (행 크기가 커진 곳은 0으로 채움)

※ 정렬
 - 한 행 or 열에 대해 정렬하려면, 각각의 수가 몇 번 등장했는지 알아야 함
 - 정렬 기준: 숫자의 등장 횟수 작은 순 -> 숫자 작은 순
 - A에 정렬된 결과를 다시 넣음
   => 수와 등장 횟수를 모두 넣음 (숫자, 해당 숫자의 등장 횟수 짝으로 넣음)
 - 수를 정렬할 때, 정렬로 인해 채워진 0은 무시

- 행 or 열 크기 > 100인 경우, 처음 100개를 제외하고 나머지는 버림

- 출력: A[r][c] == k가 되기 위한 최소 시간
*/

/*
1. 아이디어
 > 구현, 시뮬레이션
 > 정렬, PriorityQueue

- R 연산: 배열의 열 개수 변동 가능
- C 연산: 배열의 행 개수 변동 가능


2. 자료구조
 - int[][] arr: 실사용 크기 100 x 100으로 할당해서 사용
 - PriorityQueue<Pair>: 한 행 or 열 정렬
   ※ Pair: 숫자 number, 해당 숫자의 등장 횟수 cnt
*/

class Pair implements Comparable<Pair> {
	public int number, cnt;		// 숫자 number의 등장 횟수 cnt

	public Pair(int number, int cnt) {
		this.number = number;
		this.cnt = cnt;
	}

	// 숫자의 등장 횟수 작은 순 -> 숫자 작은 순
	public int compareTo(Pair o) {
		if (this.cnt != o.cnt)
			return this.cnt - o.cnt;
		return this.number - o.number;
	}
}

public class Main {
	static int r, c, k;			// 목표: arr[r][c] == k
	static int minTime;			// 출력

	static int[][] arr = new int[101][101];		// [1][1] ~ 최대 [100][100] 사용
	static int sizeRow = 3, sizeCol = 3;		// 배열의 행, 열 크기
	static Map<Integer, Integer> hashMap = new HashMap<>();			// 각 숫자의 등장 횟수 count
	static PriorityQueue<Pair> pq = new PriorityQueue<>();
	static boolean finished;

	static void solution() {
		while (minTime <= 100) {
			// 목표 조건 만족하는지 확인
			if (arr[r][c] == k) {
				finished = true;
				break;
			}

			if (sizeRow >= sizeCol) {
				operationR();
			}
			else {
				operationC();
			}

			minTime++;
		}

		if (!finished) {
			minTime = -1;
		}
	}

	/* 행 기준으로 정렬 */
	static void operationR() {
		int[][] tempArr = new int[101][101];
		sizeCol = 0;		// R 연산은 열 개수 변동 가능 (밑에 코드에서 갱신)

		for (int y = 1; y <= 100; y++) {
			hashMap.clear();

			for (int x = 1; x <= 100; x++) {
				int number = arr[y][x];
				if (number == 0)		// 0은 제외
					continue;

				// 각 숫자의 등장 횟수를 hashMap에 저장
				if (!hashMap.containsKey(number)) {
					hashMap.put(number, 1);
				}
				else {
					int cnt = hashMap.get(number);
					hashMap.put(number, cnt + 1);
				}
			}

			// 행에서 각 숫자와 해당 숫자의 등장 횟수를 pq에 저장 및 정렬
			for (int number : hashMap.keySet()) {
				int cnt = hashMap.get(number);
				pq.add(new Pair(number, cnt));
			}

			// 배열의 해당 행에 정렬 결과 저장
			// 배열의 열 개수 갱신
			int x = 1;
			while (!pq.isEmpty()) {
				Pair p = pq.remove();
				tempArr[y][x++] = p.number;
				tempArr[y][x++] = p.cnt;
			}
			sizeCol = Math.max(sizeCol, x - 1);
		}

		arr = tempArr;
	}

	/* 열 기준으로 정렬 */
	static void operationC() {
		int[][] tempArr = new int[101][101];
		sizeRow = 0;		// C 연산은 행 개수 변동 가능 (밑에 코드에서 갱신)

		for (int x = 1; x <= 100; x++) {
			hashMap.clear();

			for (int y = 1; y <= 100; y++) {
				int number = arr[y][x];
				if (number == 0)		// 0은 제외
					continue;

				// 각 숫자의 등장 횟수를 hashMap에 저장
				if (!hashMap.containsKey(number)) {
					hashMap.put(number, 1);
				}
				else {
					int cnt = hashMap.get(number);
					hashMap.put(number, cnt + 1);
				}
			}

			// 열에서 각 숫자와 해당 숫자의 등장 횟수를 pq에 저장 및 정렬
			for (int number : hashMap.keySet()) {
				int cnt = hashMap.get(number);
				pq.add(new Pair(number, cnt));
			}

			// 배열의 해당 열에 정렬 결과 저장
			// 배열의 행 개수 갱신
			int y = 1;
			while (!pq.isEmpty()) {
				Pair p = pq.remove();
				tempArr[y++][x] = p.number;
				tempArr[y++][x] = p.cnt;
			}
			sizeRow = Math.max(sizeRow, y - 1);
		}

		arr = tempArr;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		for (int i = 1; i <= 3; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 1; j <= 3; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		solution();

		System.out.println(minTime);
	}
}

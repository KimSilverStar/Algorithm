package DataStructure.PriorityQueue.Yonsei_TOTO;
import java.io.*;
import java.util.*;

/*
- 주어진 마일리지로 최대한 많은 과목 신청
- 과목 당 1 ~ 36 마일리지 투자 가능
- 과목에 마일리지 많이 투자한 순으로 수강 신청


1. 아이디어
 - 각 과목에 대해 수강 신청한 학생들의 마일리지들(마일리지 배열)을 높은 순으로 정렬
 - 정렬된 투자 마알리지 배열에서 얼마의 마일리지를 투자하면 최적인지 찾아서
   PriorityQueue 에 각 과목에 투자할 마일리지 저장

   1) 해당 과목의 수강 신청 인원이 넉넉한 경우
     - 1 투자
   2) 해당 과목의 수강 신청 인원이 넉넉하지 않은 경우
     - 수강 가능 마지막 인원이 투자한 만큼의 마일리지를 나도 투자


2. 자료구조
 - Integer[]: 각 과목에서 학생들이 투자한 마일리지 배열
   => 내림차순으로 정렬 해야하므로, int[]가 아닌 Integer[] 사용
      (Arrays.sort()의 추가 인자로 들어가는 정렬 기준은 int[] 와 같은 primitive type 배열에선 사용 X)
 - PriorityQueue<Integer>: 각 과목에서 내가 투자할 최소 마일리지 순으로 정렬

3. 시간 복잡도
 1) 마일리지 배열 정렬: 한번 정렬에 O(n log n)	(n: 해당 과목에 투자 수강 신청한 인원 수)
   => 전체 과목에서 마일리지 배열 정렬: n x P_i log P_i
   => 입력 n, P_i 최대값 대입: 100 x 100 log 100 = 20,000
 2) PriorityQueue 에 각 과목에 대해 최소 투자 마일리지 추가 (한 과목에서 pq.add() 한번 수행)
    : log 1 + log 2 + ... + log n = log(n!) = n log n	(n: 전체 과목 수)
    => 입력 n 최대값 대입: 100 log 100 = 200
 3) PriorityQueue 모두 저장한 후, 투자할 과목 선택 (pq.remove())
    : 대충 최대로 모든 과목 투자 가능하다고 가정하면, n번 pq.remove() 수행,
      log n + log n-1 + ... + log 1 = log(n!) = n log n	(n: 전체 과목 수)
    => 입력 n 최대값 대입: 100 log 100 = 200
 => 총 시간 복잡도 = 20,000 + 200 + 200 = 20,400 << 1억 (1초)
*/

public class Main {
	static int n;						// 전체 과목 수
	static int m;						// 주어진 마일리지
	static int numOfStudent, numOfPass;	// 각 과목에 투자한 학생 수, 수강 가능 인원
	static Integer[] mArr;				// 각 과목에서 학생들이 투자한 마일리지 배열 => 높은 순으로 정렬
	static PriorityQueue<Integer> pq = new PriorityQueue<>();
	// 각 과목에서 내가 투자할 최소 마일리지 순으로 정렬

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			numOfStudent = Integer.parseInt(st.nextToken());	// 해당 과목에 투자한 학생 수
			numOfPass = Integer.parseInt(st.nextToken());		// 수강 가능 인원

			st = new StringTokenizer(br.readLine());
			mArr = new Integer[numOfStudent];
			for (int j = 0; j < numOfStudent; j++)
				mArr[j] = Integer.parseInt(st.nextToken());

			// 1) 수강 신청 인원이 넉넉한 경우: 1 투자
			if (numOfStudent < numOfPass) {
				pq.add(1);
				continue;
			}

			// 2) 수강 신청 인원이 넉넉하지 않은 경우
			Arrays.sort(mArr, (m1, m2) -> m2 - m1);		// 해당 과목에서 투자 마일리지 높은 순으로 정렬
			pq.add(mArr[numOfPass - 1]);				// 마지막 인원이 투자한 만큼 나도 투자
		}

		int maxNum = 0;					// 수강 가능한 최대 강의 수
		int usedMileage = 0;			// 투자(소비)한 누적 마일리지
		while (!pq.isEmpty()) {
			if (usedMileage + pq.peek() > m)
				break;

			usedMileage += pq.remove();
			maxNum++;
		}

		System.out.println(maxNum);
	}
}

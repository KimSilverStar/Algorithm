package DataStructure.PriorityQueue.강의실;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.util.PriorityQueue;

/*
1. 아이디어
 - 입력 강의들을 배열에 저장한 후, 시작 시간이 빠른 순으로 정렬
 - 현재 진행중인 강의들을 PriorityQueue 에 저장
 - 시작 시간이 빠른 순으로 정렬된 강의 배열을 차례로 확인,
   PriorityQueue 에서 현재 진행 중인 강의 중, 종료 시간이 가장 빠른 강의 비교
 => Greedy + PriorityQueue 문제

   1) 현재 진행 중인 강의의 end > 다음 진행할 강의의 start 인 경우
     - 강의실 개수 + 1 증가
     - 다음 진행할 강의를 PriorityQueue 에 추가
   2) 현재 진행 중인 강의의 end <= 다음 진행할 강의의 start 인 경우
     - PriorityQueue 에서 현재 진행 중인 강의 1개 제거
       => 종료된 강의
     - 다음 진행할 강의를 PriorityQueue 에 추가

2. 자료구조
 - Lesson[]: 진행할 강의들 저장, 시작 시간 빠른 순으로 정렬
 - PriorityQueue<Lesson>: 현재 진행 중인 강의 저장 (강의실 개념), 종료 시간 빠른 순으로 힙 정렬

3. 시간 복잡도
 - 강의 배열 정렬: O(n log n)
 - PriorityQueue / Heap 의 시간 복잡도: O(log n)	(n: 노드 개수)
 - 강의들을 PriorityQueue 에 삽입 및 삭제 반복
   : 대충 log 1 + log 2 + ... + log n
      = log(n!) = n log n
 => 총 시간 복잡도: O(2n log n)
 => n 최대값 대입: 2 x 10^5 x 5 = 10^6 << 2억 (2초)
*/


/* 더 나은 방법 (메모리 줄이는 방법)
 - 강의 배열 Lesson[] 대신 start 만 담은 int[] 배열 사용
 - 현재 진행 중인 강의 PriorityQueue<Lesson> 대신
   현재 진행 중인 강의의 end 만 담은 PriorityQueue<Integer> 사용
 => int[] 의 요소 start 와 PriorityQueue<Integer> 의 요소 end 를 비교
*/

class Lesson implements Comparable<Lesson> {
	private int start;		// 강의 시작, 종료 시간
	private int end;

	public Lesson(int start, int end) {
		this.start = start;
		this.end = end;
	}
	public int getStart() { return start; }
	public int getEnd() { return end; }

	// 시작 시간 빠른 순으로 정렬
	public int compareTo(Lesson lesson) {
		int startDiff = this.start - lesson.start;
		if (startDiff != 0)
			return startDiff;
		else		// 시작 시간이 같으면, 빠른 종료 시간 순
			return this.end - lesson.end;
	}
}

public class Main {
	static int n;				// 전체 강의 개수
	static Lesson[] lessons;	// 전체 강의 저장
	static PriorityQueue<Lesson> pq = new PriorityQueue<>((l1, l2) -> {
		int endDiff = l1.getEnd() - l2.getEnd();
		if (endDiff != 0)
			return endDiff;
		else
			return l1.getStart() - l2.getStart();
	});
	// 현재 진행중인 강의들을 빠른 end 순으로 저장

	static int solution() {
		pq.add(lessons[0]);		// 가장 빠른 start 의 강의
		int minNum = 1;			// 최소 강의실 개수

		if (lessons.length == 1)
			return minNum;

		for (int i = 1; i < lessons.length; i++) {
			if (!pq.isEmpty()) {
				// pq 에 저장된 현재 진행 중인 강의 중, 가장 빠른 end 의 강의
				Lesson currentLesson = pq.peek();

				// 현재 진행 중인 강의 end <= 다음 강의 start 인 경우 (시간 안겹치는 경우)
				if (currentLesson.getEnd() <= lessons[i].getStart()) {
					pq.remove();			// 현재 진행 중인 강의는 이제 종료
					pq.add(lessons[i]);		// 다음 강의 추가
				}
				else {
					pq.add(lessons[i]);		// 다음 강의 추가
					minNum++;				// 필요 강의실 개수 추가
				}
			}
			else		// 현재 진행 중인 강의가 없는 경우 (강의실이 비어있는 경우)
				pq.add(lessons[i]);
		}

		return minNum;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		lessons = new Lesson[n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int id = Integer.parseInt(st.nextToken());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			lessons[i] = new Lesson(start, end);
		}

		Arrays.sort(lessons);		// 진행할 강의들: start 빠른 순 정렬

		System.out.println(solution());
	}
}

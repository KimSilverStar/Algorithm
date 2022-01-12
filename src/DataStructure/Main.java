package DataStructure;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.util.PriorityQueue;

/*
1. 아이디어
 - 입력 강의들을 배열에 저장한 후, 시작 시간이 빠른 순으로 정렬
 - PriorityQueue 에 종료 시간이 빠른 순으로 저장

2. 자료구조


3. 시간 복잡도
*/

/*
- 전체 n개의 강의를 진행하는데 필요한 최소 강의실 개수
- 강의 번호 (1 ~ n), 강의 시작 시간, 강의 종료 시간

1. 아이디어
 - 입력 강의들을 배열에 저장하고, 종료 시간이 빠른 순으로 정렬
 - PriorityQueue 에서 빠른 end 순으로 1개씩 peek()하여 확인
   1) peek()한 이전 강의 end <= 현재 강의 start 인 경우
     - peek()한 이전 강의를 remove()
     - 현재 강의를 PriorityQueue 에 add()
   2) peek()한 이전 강의 end > 현재 강의 start 인 경우
     - 필요 강의실 개수 + 1 증가
     - 현재 강의를 PriorityQueue 에 add()
   => PriorityQueue 에는 빠른 종료 시간 순으로, 현재 진행중인 강의만 남음

2. 자료구조
 - Lesson[]
 - PriorityQueue<Lesson>

3. 시간 복잡도
 - 강의 배열 정렬: O(n log n)
*/

class Lesson implements Comparable<Lesson> {
	private int id;				// 강의 번호 (1 ~ n)
	private int start, end;		// 강의 시작, 종료 시간

	public Lesson(int id, int start, int end) {
		this.id = id;
		this.start = start;
		this.end = end;
	}
	public int getId() { return id; }
	public int getStart() { return start; }
	public int getEnd() { return end; }

	public int compareTo(Lesson lesson) {
		int endDiff = this.end - lesson.end;
		if (endDiff != 0)
			return endDiff;
		else		// 종료 시간이 같으면, 빠른 시작 시간 순
			return this.start - lesson.start;
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
		pq.add(lessons[0]);		// 가장 빠른 end 의 강의
		int minNum = 1;			// 최소 강의실 개수

		if (lessons.length == 1)
			return minNum;

		for (int i = 1; i < lessons.length; i++) {
			if (!pq.isEmpty()) {
				Lesson prevLesson = pq.peek();

				pq.add(lessons[i]);		// 현재 강의 추가

				// 현재 강의 start >= 이전 강의 end 인 경우,
				if (lessons[i].getStart() >= prevLesson.getEnd()) {
					pq.remove();			// 이전 강의는 종료
//					pq.add(lessons[i]);		// 현재 강의 추가
				}
				else {
//					pq.add(lessons[i]);		// 현재 강의 추가
					minNum++;
				}
			}
			else {

			}
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
			lessons[i] = new Lesson(id, start, end);
		}

		Arrays.sort(lessons);		// end 빠른 순 정렬

		System.out.println(solution());
	}
}

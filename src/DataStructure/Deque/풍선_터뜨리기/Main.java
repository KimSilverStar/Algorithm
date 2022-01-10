package DataStructure.Deque.풍선_터뜨리기;
import java.io.*;
import java.util.ArrayDeque;
import java.util.StringTokenizer;
import java.util.Deque;

/*
1. 아이디어
 - 1 ~ n 까지의 풍선을 Deque 에 저장
 - Deque 이 empty 할 때까지 반복
   1) 터뜨린 풍선의 값이 양수 k인 경우
     - (k-1)개의 풍선을 덱의 First 에서 뽑아서 Last 로 넣음
     - 이후, 1개의 풍선을 덱의 First 에서 뽑아서 터뜨림
   2) 터뜨린 풍선의 값이 음수 -k인 경우
     - (k-1)개의 풍선을 덱의 Last 에서 뽑아서 First 로 넣음
     - 이후, 1개의 풍선을 덱의 Last 에서 뽑아서 터뜨림

2. 자료구조
 - Deque<Balloon> = new ArrayDeque<>()
   : 풍선(풍선 번호, 풍선에 적힌 값)들 저장
 - StringBuilder: 출력 값(터뜨리는 풍선 번호 순서) 저장

3. 시간 복잡도
 - while 문으로 Deque 이 empty 할 때까지 반복: O(n)
 - 터뜨린 풍선 값에 따라 총 (k-1) + 1 = k번 removeXXXX() 반복: O(n)
 => 총 O(n^2)
 => n 최대값 대입: 1,000 x 1,000 = 1,000,000 << 2억 (2초)
*/

/* 오답 노트: 메모리 초과
 - Deque 구현체로 LinkedList 사용하여, 메모리 초과 발생
   => Deque<Balloon> deque = new LinkedList();
   => 원인) LinkedList 는 이중 연결리스트로 메모리량이 많음
 - 단순히 덱의 앞/뒤에서 item 을 삽입/삭제 하는 경우,
   LinkedList 보다는 ArrayDeque 을 사용하는 것이 메모리량에서 효율적
*/

class Balloon {
	private int id;			// 풍선 번호
	private int value;		// 풍선에 적힌 값 숫자

	public Balloon(int id, int value) {
		this.id = id;
		this.value = value;
	}
	public int getId() { return id; }
	public int getValue() { return value; }
}

public class Main {
	static int n;			// 1 ~ n 번 까지의 풍선
	static Deque<Balloon> deque = new ArrayDeque<>();

	static String solution() {
		StringBuilder sb = new StringBuilder();

		// 맨 처음) 맨 앞의 1번 풍선을 터뜨림
		Balloon balloon = deque.removeFirst();
		int poppedValue = balloon.getValue();	// 터뜨린 풍선의 값
		sb.append(balloon.getId()).append(" ");

		while (!deque.isEmpty()) {
			// 1) 터뜨린 풍선의 값이 양수 k인 경우
			if (poppedValue > 0) {
				for (int i = 1; i < poppedValue; i++) {
					balloon = deque.removeFirst();
					deque.addLast(balloon);
				}

				balloon = deque.removeFirst();
				poppedValue = balloon.getValue();
				sb.append(balloon.getId())
						.append(" ");
			}
			// 2) 터뜨린 풍선의 값이 음수 -k인 경우
			else {
				for (int i = 1; i < -poppedValue; i++) {
					balloon = deque.removeLast();
					deque.addFirst(balloon);
				}

				balloon = deque.removeLast();
				poppedValue = balloon.getValue();
				sb.append(balloon.getId())
						.append(" ");
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			int value = Integer.parseInt(st.nextToken());
			deque.addLast(new Balloon(i, value));
		}

		System.out.println(solution());
	}
}

package DFS_BFS.A_to_B;
import java.io.*;
import java.util.*;

/*
- 정수 A 에 연산을 하여 정수 B로 변환
  연산 1) 2를 곱함
  연산 2) 1을 수의 가장 오른쪽에 추가
- A 를 B 로 바꾸는데 필요한 연산 최소 횟수 구하기
  => 변환 가능하면, 연산의 최소 횟수 + 1 출력
  => 변환 불가능하면, -1 출력

1. 아이디어
 - BFS => Queue 에서 이전 값을 꺼내서 2가지 연산 수행
   1) 연산 결과 값 == B 이면, 탐색 성공
   2) 연산 결과 값 < B 이면, Queue 에 추가
   3) 연산 결과 값 > B 이면, 버림

 !!! BFS 사용 이유
  - 현재 상태의 정수에서 2가지 연산(선택) 가능 (탐색이 이진 트리 형태로 뻗어나감)
  - 최소 연산 횟수를 구하려면, 탐색 Depth (Level)이 낮아야 함
    (연산 횟수 = 탐색 트리의 Depth)
  - BFS 로 한 Depth 씩 연산 결과 값 확인해가며 탐색 확장

 !!! DFS 를 사용하면, A -> B 로 만드는 경우의 수를 찾을 수 있지만,
     최소 연산 횟수가 아닐 수 있음
     (DFS 로 최소 연산 횟수를 구하도록 코딩하려면, 모든 경우의 수를 다 확인해야 함)

2. 자료구조
 - Queue<Pair>, LinkedList<Pair>: BFS 수행
   => Pair: 연산 수행 결과 값, 연산 수행 횟수 쌍

3. 시간 복잡도
*/

/* 오답 노트
 - 틀린 이유: Overflow
   => 연산 결과 값 Pair 의 value 를 long 이 아닌 int 사용
 - 입력 정수 A, B 자체는 10억 이하 이므로, int 사용 가능
   (int 범위: 대략 -21억 ~ 21억)
 - 하지만, BFS 에서 A 에 연산을 하다보면, int 범위를 넘어갈 수 있음
*/

class Pair {
	private long value;
	private int opCount;		// 연산 횟수

	public Pair(long value, int opCount) {
		this.value = value;
		this.opCount = opCount;
	}
	public long getValue() { return value; }
	public int getOpCount() { return opCount; }
}

public class Main {
	static long A, B;				// 입력 정수
	static int minCount = 0;		// 출력, 최소 연산 횟수

	static Queue<Pair> queue = new LinkedList<>();

	static int bfs() {
		queue.add(new Pair(A, 0));

		while (!queue.isEmpty()) {
			Pair p = queue.remove();
			int opCount = p.getOpCount() + 1;

			// 연산 1: 곱하기 2
			long opValue1 = p.getValue() * 2;
			if (opValue1 == B)
				return opCount + 1;
			else if (opValue1 < B)		// 연산 결과 값 > B 이면, Queue 에 추가 X
				queue.add(new Pair(opValue1, opCount));

			// 연산 2: 오른쪽에 1 추가
			long opValue2 = (p.getValue() * 10) + 1;
			if (opValue2 == B)
				return opCount + 1;
			else if (opValue2 < B)		// 연산 결과 값 > B 이면, Queue 에 추가 X
				queue.add(new Pair(opValue2, opCount));
		}

		return -1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 연산 결과 값 Pair 의 value 를 long 으로 선언하여,
		// 입력 정수 A, B 도 long 사용
		A = Long.parseLong(st.nextToken());
		B = Long.parseLong(st.nextToken());

		System.out.println(bfs());
	}
}

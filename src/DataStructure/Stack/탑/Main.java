package DataStructure.Stack.탑;
import java.io.*;
import java.util.*;

/*
- n개의 서로 다른 높이의 탑
- 레이저: 각 탑 꼭대기에서 왼쪽 직선으로 발사, 가장 먼저 만나는 1개의 탑에서 수신
- 각 탑에서 발사한 레이저 신호를 어느 탑에서 수신하는지 찾기
  (수신하지 못한 레이저에 대해서는 0 출력)
*/

/*
1. 아이디어
 1) 입력 각 탑의 높이를 배열 int[] heights 에 저장
   - 배열 index: 탑 번호 [1] ~ [n] 사용
 2) 왼쪽 탑 hegihts[1] 부터 오른쪽 탑 heights[n] 까지 확인
   - heights[i]에서 발사한 레이저를 수신하는 탑 번호를 result[i]에 저장
   - 이전 왼쪽 탑들의 높이와 현재 탑의 높이 heights[i] 비교
     ① 이전 왼쪽 탑의 높이가 더 크거나 같으면, 레이저 수신 O
       - 이전 왼쪽 탑의 번호 출력
     ② 이전 왼쪽 탑의 높이가 더 작으면, 레이저 수신 X
       - 확인한 이전 왼쪽 탑을 completed Stack에서 pop 하여, temp Stack에 push
       - 그 다음 왼쪽 탑과 다시 비교
   - temp Stack의 원소들을 completed Stack으로 복구
     : 현재 탑보다 높이가 큰 탑만 남긴 후, completed Stack에 복구
   - 확인 완료한 [i]번 탑을 completed Stack에 push

2. 자료구조
 - int[] heights: 입력 탑 높이
 - int[] result: 출력
   => result[i]: [i]번 탑에서 발사한 레이저를 수신하는 탑 번호
 - Stack<Pair> completed: 확인 완료된 탑의 (idx, height) 저장
 - Stack<Pair> temp: 탑 높이 순서 복구하기 위한 temp 저장 용도

3. 시간 복잡도
 - 왼쪽 탑 부터 오른쪽 탑 까지 차례로 레이저 수신한 탑 찾기 (전부 확인): O(n^2)
   = 0 + 1 + 2 + ... + (n-1) = (n-1)n / 2
   => n 최대값 대입: 약 125 x 10^9 >> 1.5억 (시간 초과 !!)
 ※ [i]번 탑이 발사한 레이저를 수신한 탑 찾기 (전부 확인): [i-1] ~ [1]번 탑 확인
  - 최대 (i-1)번 확인

 - Stack에 현재 탑보다 높이가 낮은 탑을 제외시켜 비교 대상 아이템 개수 줄이는 방법: O(n^2) 미만
*/

public class Main {
	static int n;					// n개 탑
	static int[] heights;			// 각 탑의 높이
	static int[] result;			// 출력

	static Stack<Pair> completed = new Stack<>();	// 확인 완료된 탑 (번호, 높이)
	static Stack<Pair> temp = new Stack<>();

	static void solution() {
		// 가장 왼쪽 탑이 발사한 레이저를 수신하는 탑 존재 X
		completed.push(new Pair(1, heights[1]));
		result[1] = 0;

		for (int i = 2; i <= n; i++) {
			// [i]번 탑이 발사한 레이저를 수신하는 탑 찾기
			result[i] = getReceiverTopIdx(i);

			// temp Stack의 원소들을 completed Stack에 복구
			// ※ 현재 [i]번 탑보다 높이가 큰 탑만 completed Stack에 복구
			backup(i);

			// 확인 완료된 [i]번 탑 추가
			completed.push(new Pair(i, heights[i]));
		}
	}

	/* [idx]번 탑이 발사한 레이저를 수신하는 탑의 번호 반환 */
	static int getReceiverTopIdx(int idx) {
		while (!completed.isEmpty()) {
			Pair completedTop  = completed.peek();

			// [idx]번 탑이 발사한 레이저를 수신한 경우
			if (completedTop.height >= heights[idx]) {
				return completedTop.idx;
			}
			// [idx]번 탑이 발사한 레이저를 수신하지 못한 경우
			else {
				temp.push(completed.pop());		// pop() 하여 temp에 저장 후, 다음 탑 확인
			}
		}

		return 0;		// 레이저를 수신하는 탑 존재 X
	}

	/* [idx]번 탑보다 높이가 큰 탑만 남긴 후, completed Stack에 복구 */
	static void backup(int idx) {
		while (!temp.isEmpty()) {
			Pair prevTop = temp.pop();
			if (prevTop.height >= heights[idx]) {
				completed.push(prevTop);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		heights = new int[n + 1];			// 탑 번호 [1] ~ [n] 사용
		result = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			heights[i] = Integer.parseInt(st.nextToken());
		}

		solution();

		StringBuilder sb = new StringBuilder();		// 출력
		for (int i = 1; i <= n; i++) {
			sb.append(result[i]).append(" ");
		}
		System.out.println(sb);
	}
}

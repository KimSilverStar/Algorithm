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
       - 확인한 더 낮은 이전 왼쪽 탑을 Stack에서 pop
       - 그 다음 왼쪽 탑과 다시 비교
   - 확인 완료한 [i]번 탑을 Stack에 push
     ※ Stack에서 [i]번 탑보다 먼저 저장된 아이템 = [i]번 탑보다 높이가 더 큰 탑
     => Stack에서 비교 대상 아이템 개수를 줄임

2. 자료구조
 - int[] heights: 입력 탑 높이
 - int[] result: 출력
   => result[i]: [i]번 탑에서 발사한 레이저를 수신하는 탑 번호
 - Stack<Pair> stack: 확인 완료된 탑의 (idx, height) 저장
   => 레이저 수신 확인 과정에서, Stack에 저장된 탑들 중 현재 탑보다 낮은 높이의 탑은 pop하여 제거

3. 시간 복잡도
 - 왼쪽 탑 부터 오른쪽 탑 까지 차례로 레이저 수신한 탑 찾기 (전부 확인): O(n^2)
   = 0 + 1 + 2 + ... + (n-1) = (n-1)n / 2
   => n 최대값 대입: 약 125 x 10^9 >> 1.5억 (시간 초과 !!)
 ※ [i]번 탑이 발사한 레이저를 수신한 탑 찾기 (전부 확인): [i-1] ~ [1]번 탑 확인
  - 최대 (i-1)번 확인

 - Stack에 현재 탑보다 높이가 낮은 탑을 제외시켜 비교 대상 아이템 개수 줄이는 방법: O(n^2) 미만
*/

public class Main_Better {
	static int n;					// n개 탑
	static int[] heights;			// 각 탑의 높이
	static int[] result;			// 출력

	static Stack<Pair> stack = new Stack<>();

	static void solution() {
		// 가장 왼쪽 탑이 발사한 레이저를 수신하는 탑 존재 X
		stack.push(new Pair(1, heights[1]));
		result[1] = 0;

		// 왼쪽 탑부터 오른쪽 탑 차례로 확인
		for (int i = 2; i <= n; i++) {
			while (!stack.isEmpty()) {
				Pair prevTop = stack.peek();

				// [i]번 탑이 발사한 레이저를 수신한 경우
				if (prevTop.height >= heights[i]) {
					result[i] = prevTop.idx;
					break;
				}
				// [i]번 탑이 발사한 레이저를 수신하지 못한 경우
				else {
					stack.pop();	// 현재 [i]번 탑보다 높이가 낮은 이전 왼쪽 탑 pop
				}
			}

			// 확인 완료된 [i]번 탑 추가
			stack.push(new Pair(i, heights[i]));
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

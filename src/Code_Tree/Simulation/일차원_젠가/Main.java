package Code_Tree.Simulation.일차원_젠가;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 첫 번째 제거 구간에서 블럭 제거 후, 새로운 배열에 남은 블럭 복사
 - 두 번째 제거 구간에서 블럭 제거 후, 새로운 배열에 남은 블럭 복사

2. 자료구조

3. 시간 복잡도
 - O(n)
*/

class Pair {
	public int s, e;			// 블럭 제거 구간 s ~ e

	public Pair (int s, int e) {
		this.s = s;
		this.e = e;
	}
}

public class Main {
	static int n;				// 초기 블럭 개수
	static int[] originBlocks;
	static Pair[] pairs;		// 블럭 제거 구간
	static StringBuilder sb = new StringBuilder();
	static int restCount;		// 남은 블럭 개수

	static void solution() {
		// 첫 번째 구간 블럭 제거
		int[] restBlocks1 = deleteBlocks(originBlocks, pairs[0]);

		// 두 번째 구간 블럭 제거
		int[] restBlocks2 = deleteBlocks(restBlocks1, pairs[1]);

		// 출력
		sb.append(restBlocks2.length - 1).append("\n");
		for (int i = 1; i < restBlocks2.length; i++)
			sb.append(restBlocks2[i]).append("\n");
	}

	/* blocks[]에서 제거 구간에 해당하는 블럭 제거 후, 남은 블럭을 담은 배열 반환 */
	static int[] deleteBlocks(int[] blocks, Pair pair) {
		// 구간 블럭 제거
		for (int i = pair.s; i <= pair.e; i++) {
			blocks[i] = -1;
			restCount--;
		}

		// 남은 블럭 새로운 배열에 복사
		int[] restBlocks = new int[restCount + 1];		// [1] ~ [restCount] 사용
		int idx = 1;
		for (int i = 1; i < blocks.length; i++) {
			if (blocks[i] != -1)
				restBlocks[idx++] = blocks[i];
		}

		return restBlocks;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		restCount = n;

		originBlocks = new int[n + 1];				// [1] ~ [n] 사용
		for (int i = 1; i <= n; i++)
			originBlocks[i] = Integer.parseInt(br.readLine());

		pairs = new Pair[2];
		for (int i = 0; i < 2; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());

			pairs[i] = new Pair(s, e);
		}

		solution();
		System.out.println(sb);
	}
}

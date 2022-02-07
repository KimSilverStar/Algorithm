package Greedy.박스_채우기;
import java.io.*;
import java.util.StringTokenizer;

/*
- 박스 크기: length x width x height
- 큐브 크기: 2^n x 2^n x 2^n		(정육면체)
=> 입력: 박스 크기, 큐브 크기 및 개수
=> 출력: 박스를 채우는 데 필요한 큐브 최소 개수
	(채우지 못하는 경우, -1 출력)
*/

/*
1. 아이디어: 그리디 + 분할 정복
 - 그리디: 넣을 수 있는 큰 큐브부터 채움
   => 큐브가 한 변의 길이 2^k 인 정육면체 형태,
      작은 큐브 여러 개로 큰 큐브를 만들어낼 수 있으므로 그리디 가능
      (단, 큐브 개수가 충분할 경우)
 - 분할 정복: 그리디로 현재 가능한 가장 큰 큐브로 채운 후, 남은 영역을 채움
   => 채우고 남은 영역에 대해 분할 정복 (재귀 호출)

2. 자료구조
 - int[] cubes: 큐브의 종류 (2^i) 별 개수 저장
   ex) cubes[i]: 한 변의 길이가 2^i 인 큐브 개수

3. 시간 복잡도
 1) 그리디로 채울 수 있는 가장 큰 큐브 찾음
   - 매 반복에서 O(20)
 2) 분할 정복
   - 매 재귀 호출 3번
*/

public class Main {
	static int length, width, height;		// 박스 크기
	static int n;				// 큐브 종류 개수 (최대 20)
	static int[] cubes;			// 각 정육면체 큐브의 한 변의 길이 크기
	static int minCount;		// 출력: 큐브 최소 개수
	static boolean flag;		// 출력: 채울 수 없는 경우 판단

	/* l, w, h: 채울 영역 크기 length, width, height */
	static void solution(int l, int w, int h) {
		// 채울 영역이 없는 경우 (이전 단계에서 큐브로 채웠을 때, 영역이 맞아 떨어지는 경우)
		if (l == 0 || w == 0 || h == 0)
			return;

		flag = false;
		int cubeLen = 0;

		// 채울 수 있는 가장 큰 큐브 찾음
		for (int i = 19; i >= 0; i--) {
			if (cubes[i] == 0)			// 큐브 개수 == 0 (큐브가 없는 경우)
				continue;

			cubeLen = 1 << i;			// 큐브의 한 변 길이: 2^i
			if (l >= cubeLen && w >= cubeLen && h >= cubeLen) {
				minCount++;				// 채울 수 있는 가장 큰 큐브로 채움
				cubes[i]--;
				flag = true;
				break;
			}
		}

		if (!flag)			// 채울 수 없는 경우 => 큐브 개수 부족한 경우
			return;

		// 채우고 남은 영역에 대해 재귀 호출하여, 분할 정복
		solution(cubeLen, w - cubeLen, cubeLen);
		solution(l - cubeLen, w, cubeLen);
		solution(l, w, h - cubeLen);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		length = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		height = Integer.parseInt(st.nextToken());

		n = Integer.parseInt(br.readLine());		// 큐브 종류 개수
		cubes = new int[20];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int idx = Integer.parseInt(st.nextToken());
			int count = Integer.parseInt(st.nextToken());
			cubes[idx] = count;		// 길이 2^idx 인 큐브가 count 개수
		}

		solution(length, width, height);

		if (flag)
			System.out.println(minCount);
		else
			System.out.println(-1);
	}
}

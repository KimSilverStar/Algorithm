package Greedy.물병;
import java.io.*;
import java.util.StringTokenizer;

/*
- 처음: 모든 물병, 새로 산 물병에는 물 1리터씩 들어있음
- 물이 들어있는 물병이 k 개 이하가 되도록, 물을 재분배
- 물 재분배 조건) 같은 물 양의 2개의 물병을 선택하여, 한 쪽으로 물을 몰아줌
=> 새로 사야하는 물병의 최소 개수 출력
*/

/*
1. 아이디어
 - 그리디 알고리즘
 ※ 그리디 가능 이유: 물병의 물 양이 1, 2, 4, 8, ..., 2^k 형태의 2의 거듭제곱으로 늘어남
    => 작은 물병으로 큰 물병을 만들어낼 수 있으므로, 구하려는 해를 찾지 못하는 경우가 없음
 - int[] bottles 에 해당 물 양에 따른 물통 개수 저장
   ex) bottles[1] = 13 이면, 1L 물병이 13개

 1) 같은 물 양(index)에 2병 이상이 존재하면,
    해당 물 양 x 2 index로 물병을 합침
 2) 같은 물 양(index)에 2병 이상이 존재하지 않으면, 새로 물병을 구입
   - 1L 보다 큰, 가장 작은 물 양을 찾음
   - (1L 물병 보다 큰 최소 물병의 물 양) - (현재 보유한 1L 물병의 개수) 만큼
     새로운 물병을 구입
     e.g. 현재 1L 물병 1개 보유, 1L 물병보다 큰 최소 물병이 4L 이면
          새로운 물병을 (4 - 1) = 3개 구입
          => 새로운 물병을 구입하여, 더 큰 물양의 최소 물병을 만듦

2. 자료구조
 - int[] bottles: 해당 물 양에 따른 물통 개수
   => n 최대값 10^7
   => 넉넉하게 [2 * n] 할당
   => 4 x (2 x 10^7) byte = 8 x 10 MB

3. 시간 복잡도
*/

public class Main {
	static int n, k;			// 처음 소지한 물병 개수 n, 한 번에 이동 가능한 물병 개수 k
	static int minCount;		// 새로 사야하는 물병 최소 개수

	static int bottleCount;		// 물이 담겨있는 물통 개수
	static int maxWater;		// 현재 보유한 물통 중, 최대 물양
	static int[] bottles;
	// bottles[물 양]: 해당 물 양을 담고있는 물병 개수
	// ex) bottles[1] = 13 이면, 1L 물병이 13개

	static void solution() {
		while (bottleCount > k) {
			boolean isSameExist = false;    // 같은 물 양의 물병들이 존재하는지

			for (int i = 1; i <= maxWater; i++) {
				// 같은 물 양의 물병이 2개 이상 존재하는 경우 => 물병 합침
				if (bottles[i] >= 2) {
					int num = bottles[i];			// 같은 물 양의 물병 개수
					bottles[i] = num % 2;
					bottles[i * 2] += num / 2;

					maxWater = Math.max(maxWater, i * 2);
					bottleCount = (bottleCount - num) + (num % 2) + (num / 2);

					isSameExist = true;
					break;
				}
			}

			// 같은 물 양의 물병이 존재 X => 새로 물병 구입
			if (!isSameExist) {
				// (1L 물병 보다 큰 최소 물병의 물 양) - (현재 보유한 1L 물병의 개수) 만큼 구입
				int newCount = 0;
				for (int i = 2; i <= maxWater; i++) {
					if (bottles[i] > 0) {
						newCount = i - bottles[1];
						break;
					}
				}

				bottles[1] += newCount;		// 1L 물병 새로 구입
				bottleCount += newCount;
				minCount += newCount;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		bottles = new int[n * 2];
		bottles[1] = n;				// 처음) 1L 짜리 물통 n개
		bottleCount = n;
		maxWater = 1;

		if (n <= k) {
			System.out.println(0);
			return;
		}

		solution();
		System.out.println(minCount);
	}
}

package Greedy.카드_정렬하기;
import java.io.*;
import java.util.*;

/*
1. 아이디어
 - n개 카드 묶음의 경우, 총 (n-1)번 합침
 - 2개 카드 묶음을 합치고,
   합쳐진 카드 묶음은 또 다시 다른 카드 묶음과 합침
   => 최소 비교 횟수로 모두 합치려면, 적은 카드 묶음끼리 합쳐나가야 함
   => 각 카드 개수를 우선순위 큐에 저장 및 정렬해가면서 합침

 1) PriorityQueue 에 각 묶음의 카드 개수를 저장하여 정렬
   - 카드 개수 적은 순으로 정렬
 2) PriorityQueue 에 원소가 1개 남을 때까지 다음을 반복
   - 가장 작은 2개 원소를 뽑아서 더한 후,
     더한 값을 다시 PriorityQueue 에 추가


2. 자료구조
 - PriorityQueue<Integer>: 각 카드 묶음의 카드 개수
   => 작은 순으로 정렬


3. 시간 복잡도
 - PriorityQueue 의 삽입/삭제 시간 복잡도: O(log_2 n)
 - 2개 카드 묶음 합치기 1번
   => PQ 에서 가장 작은 2개 원소 뽑아서 더한 후, 더한 값을 PQ 에 추가
   => PQ 에서 삭제 2번 + 삽입 1번: O(3 log_2 n)
 - 전체 n개 카드 묶음 합치기: 카드 합치기 (n-1)번
   => 총 시간 복잡도: O(3(n-1) log_2 n) ~= O(3n log_2 n)
   => n 최대값 대입: 3 x 10^5 x log_2 10^5 = 15 x 10^5 x log_2 10
   	  				 ~= 45 x 10^5 << 2억
*/

public class Main {
	static int n;				// 카드 묶음의 개수
	static PriorityQueue<Integer> pq = new PriorityQueue<>();		// 각 묶음의 카드 개수
	static int minCount;		// 출력, 최소 비교 횟수

	static void solution() {
		while (pq.size() > 1) {
			int card1 = pq.remove();
			int card2 = pq.remove();

			int mergedCard = card1 + card2;
			pq.add(mergedCard);

			minCount += mergedCard;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		n = Integer.parseInt(br.readLine());

		for (int i = 0; i < n; i++)
			pq.add(Integer.parseInt(br.readLine()));

		solution();
		System.out.println(minCount);
	}
}

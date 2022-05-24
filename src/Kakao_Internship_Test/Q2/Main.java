package Kakao_Internship_Test.Q2;
import java.util.*;

/*
1. 아이디어
 - 작업 1회: 한쪽 큐에서 remove() 한 원소를 다른 큐에 add()
 - 작업을 두 큐의 원소 합이 서로 같을 때 (큐 2개의 합 / 2 가 되도록, sum1 == sum2) 까지 반복
 - 그리디 ??

 ① 각 큐의 원소 2개 서로 swap
   - queue1.remove() 원소를 queue2.add()
   - queue2.remove() 원소를 queue1.add()
   => sum1 += (queue2.remove() - queue1.remove())
   	  sum2 += (queue1.remove - queue2.remove())
   => minCount += 2
   => sum1, sum2 를 확인하여, 원소 값 차이만큼 더해졌을 때 targetSum 이 되는 경우

 ② 한쪽 큐의 원소 1개를 다른 큐에 추가
   => minCount += 1
   => ①번의 swap을 해도 targetSum이 안되는 경우,
      sum이 더 큰 큐의 원소를 remove() 하여 다른 큐에 add()


2. 자료구조
 - Queue<Integer>, LinkedList<Integer> 2개


3. 시간 복잡도
 - queue1, queue2 의 길이 <= 3 x 10^5
 - O()
*/

public class Main {
	static Queue<Integer> queue1 = new LinkedList<>();
	static Queue<Integer> queue2 = new LinkedList<>();

	/* 2개 큐위 원소 1개 씩을 서로 swap */
	static void swap() {
		int num1 = queue1.remove();
		int num2 = queue2.remove();

		queue1.add(num2);
		queue2.add(num1);
	}

	static int solution(int[] arr1, int[] arr2) {
		long sum1 = 0, sum2 = 0;	// queue1, queue2 의 각 원소 합
		long targetSum;				// 목표 합 = (sum1 + sum2) / 2
		int minCount = 0;			// 출력, 작업 (Queue에서 remove + add) 최소 횟수 (불가능하면 -1)

		for (int num1 : arr1) {
			queue1.add(num1);
			sum1 += num1;
		}

		for (int num2 : arr2) {
			queue2.add(num2);
			sum2 += num2;
		}

		targetSum = (sum1 + sum2) / 2;

		while (sum1 != targetSum) {
			if (queue1.isEmpty() || queue2.isEmpty()) {
				minCount = -1;
				break;
			}

			int num1 = queue1.peek();
			int num2 = queue2.peek();

			// 2개 큐의 원소 1개 씩을 각각 swap 했을 때, 합이 같아지는 경우
			if (targetSum == sum1 + num2 - num1) {
				swap();
				// sum1, sum2 갱신
				minCount += 2;
				break;
			}

			// swap 해도 합이 같아지지 않는 경우 => swap 안하고, 큰 쪽의 원소를 작은 큐에 추가
			if (sum1 > sum2) {			// queue1 의 원소를 queue2 에 추가
				sum1 -= num1;
				sum2 += num1;

				queue2.add(queue1.remove());
				minCount++;
			}
			else if (sum1 < sum2) {		// queue2 의 원소를 queue1 에 추가
				sum1 += num2;
				sum2 -= num2;

				queue1.add(queue2.remove());
				minCount++;
			}
		}

		return minCount;
	}

	public static void main(String[] args) {
//		int[] arr1 = { 3, 2, 7, 2 };
//		int[] arr2 = { 4, 6, 5, 1 };
//		System.out.println(solution(arr1, arr2));		// 2

//		int[] arr3 = { 1, 2, 1, 2 };
//		int[] arr4 = { 1, 10, 1, 2 };
//		System.out.println(solution(arr3, arr4));		// 7

		int[] arr5 = { 1, 1 };
		int[] arr6 = { 1, 5 };
		System.out.println(solution(arr5, arr6));		// -1
	}
}

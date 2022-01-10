package DataStructure.Queue.카드2;
import java.io.*;
import java.util.Queue;
import java.util.LinkedList;

/*
1. 아이디어
 - Queue 에 1 ~ n 까지 차례로 저장 (1이 맨 앞)
 - Queue 에 카드 1개가 남을 때 까지 반복
   1) 맨 앞 카드를 remove
   2) 그 다음 맨 앞 카드를 뽑은 후, 다시 Queue 에 넣음

2. 자료구조
 - Queue<Integer>: 카드 저장

3. 시간 복잡도
 - Queue 에 1 ~ n 까지 저장: O(n)
 - Queue 에 카드 1개 남을 때 까지 반복: O(n)
 => 총 O(2n)
 => n에 최대값 대입: 2 x 500,000 = 1,000,000 << 2억 (2초)
*/

public class Main {
    static int n;           // 1 ~ n 카드
    static Queue<Integer> queue = new LinkedList<>();

    static int solution() {
        while (true) {
        	if (queue.size() == 1)
        		break;

        	queue.remove();							// 1. 맨 앞 카드는 버림

			int prevFirstCard = queue.remove();		// 2. 다음 맨 앞 카드는 뽑아서 맨 뒤에 추가
			queue.add(prevFirstCard);
		}

        return queue.peek();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        n = Integer.parseInt(br.readLine());

        for (int i = 1; i <= n; i++)
        	queue.add(i);

        System.out.println(solution());
    }
}

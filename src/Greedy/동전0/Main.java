package Greedy.동전0;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 그리디 알고리즘 => 값이 큰 동전이 작은 동전들의 배수 관계이므로 그리디 풀이 가능
 - 반복문에서 coins 의 마지막 요소(가장 큰 동전)부터 확인
   => totalPrice 를 coin 으로 나누어나감

2. 자료구조
 - totalPrice => K 가 최대 10^8 (e^8) => int 가능 => int totalPrice
 - coins => 동전 1개 최대 금액 10^6 (e^6) => int 가능 => int[] coins

3. 시간 복잡도
 - O(n): 동전의 종류 개수 n 만큼 반복문
*/

public class Main {
    static int numOfCoin;           // 동전의 종류 개수
    static int totalPrice;          // 동전 값의 목표 합
    static int[] coins;             // 동전들 => 오름차순, 배수 관계

    public static int solution() {
        int minNumOfCoin = 0;       // totalPrice 를 만드는 최소 동전 개수

        // 가장 큰 동전부터 확인
        for (int i = coins.length - 1; i >= 0; i--) {
            if (totalPrice == 0)
                break;

            minNumOfCoin += (totalPrice / coins[i]);
            totalPrice %= coins[i];
        }

        return minNumOfCoin;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );
        StringTokenizer st = new StringTokenizer(br.readLine());

        numOfCoin = Integer.parseInt(st.nextToken());       // 동전의 종류 개수
        totalPrice = Integer.parseInt(st.nextToken());      // 동전 값의 목표 합
        coins = new int[numOfCoin];                         // 동전들 => 오름차순, 배수 관계
        for (int i = 0; i < numOfCoin; i++)
            coins[i] = Integer.parseInt(br.readLine());

        System.out.println(solution());
    }
}

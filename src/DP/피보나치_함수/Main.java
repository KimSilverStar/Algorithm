package DP.피보나치_함수;
import java.io.*;

/*
1. 아이디어
 - 초항: f(0) = (1, 0), f(1) = (0, 1)
   f(2) = f(1) + f(0) = (1, 0) + (0, 1) = (1, 1)
   f(3) = f(2) + f(1) = (1, 1) + (0, 1) = (1, 2)
 - 점화식: f(n) = f(n-1) + f(n-2)

 1) 입력 테스트 케이스 배열에서 가장 큰 n 찾기
 2) 찾은 가장 큰 n으로 DP 배열 채움
   - for 문으로 2 ~ n 까지 반복
 3) 입력 테스트 케이스 배열의 각 요소 n에 대해 DP[n] 출력

2. 자료구조
 - Pair[]: DP 배열 (각 요소가 출력되는 0, 1 개수 저장)
   => 테스트 케이스 중에서 가장 큰 n으로 채운 DP 배열
 - 다른 방법) Pair[] 대신 int[] 2개 사용
   => int[] numOfZero, int[] numOfOne

3. 시간 복잡도
 - 입력 테스트 케이스 배열에서 가장 큰 n 찾기: O(t)
 - 찾은 가장 큰 n으로 DP 배열 채움: O(n)
 - 채운 DP 배열의 요소 출력: O(t)
 => O(n + 2t)
*/

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        int t = Integer.parseInt(br.readLine());    // 테스트 케이스 개수
        int[] arrN = new int[t];                    // 각 테스트 케이스(n) 저장한 배열
        for (int i = 0; i < arrN.length; i++)
            arrN[i] = Integer.parseInt(br.readLine());

        int maxN = arrN[0];        // 테스트 케이스 배열 n[]에서 가장 큰 요소
        for (int i = 1; i < arrN.length; i++) {
            if (maxN < arrN[i])
                maxN = arrN[i];
        }

        Pair[] dp = new Pair[maxN + 1];
        Pair dp0 = new Pair(1, 0);      // 초항
        Pair dp1 = new Pair(0, 1);

        if (maxN == 0)
            dp[0] = dp0;
        else if (maxN >= 1) {
            dp[0] = dp0;
            dp[1] = dp1;
        }

        for (int i = 2; i < dp.length; i++) {
            dp[i] = new Pair(
                    dp[i-1].getNumOfZero() + dp[i-2].getNumOfZero(),
                    dp[i-1].getNumOfOne() + dp[i-2].getNumOfOne()
            );
        }

        for (int n : arrN)
            System.out.println(dp[n]);
    }
}

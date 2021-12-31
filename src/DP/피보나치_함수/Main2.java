package DP.피보나치_함수;
import java.io.*;

/*
1. 아이디어
 - 초항: f(0) = (1, 0), f(1) = (0, 1)
 - 점화식: f(n) = f(n-1) + f(n-2)

 1) n = 39 (n의 최대 입력 값) 로 하여, DP 배열 채움
   - for 문으로 2 ~ 40 까지 반복, DP[0 ~ 40] 채움
 2) 입력 테스트 케이스 배열의 각 요소 n에 대해 DP[n] 출력

2. 자료구조
 - Pair[]: DP 배열 (각 요소가 출력되는 0, 1 개수 저장),
   배열 길이 41, DP[0] ~ DP[40] 채움
 - 다른 방법) Pair[] 대신 int[] 2개 사용
   => int[] numOfZero, int[] numOfOne

3. 시간 복잡도
 - DP 배열 채움: O(n)
 - 채운 DP 배열의 요소 출력: O(t)
 => O(n + t)
*/

public class Main2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        int t = Integer.parseInt(br.readLine());    // 테스트 케이스 개수
        int[] arrN = new int[t];                    // 각 테스트 케이스(n) 저장한 배열
        for (int i = 0; i < arrN.length; i++)
            arrN[i] = Integer.parseInt(br.readLine());

        Pair[] dp = new Pair[41];       // n의 최대 입력 값 = 39
        dp[0] = new Pair(1, 0);     // 초항
        dp[1] = new Pair(0 ,1);

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

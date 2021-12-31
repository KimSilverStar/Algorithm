package DP.twoxn_타일링;
import java.io.*;

/*
1. 아이디어
 - 초항(규칮 찾기): A(1) = 1, A(2) = 2, A(3) = 3
 - 점화식: A(n) = A(n - 1) + A(n - 2)
 - for 문으로 3 ~ n 까지 반복, (이전 값 + 그 이전 값) % 10,007 저장

2. 자료구조
 - int[]: (1 ~ n 까지 2xn 타일을 채우는 방법 수 % 10,007)를 담을 DP 배열

3. 시간 복잡도
 - O(n): for 문 반복
*/

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        int n = Integer.parseInt(br.readLine());
        int[] dp = new int[n + 1];      // index 1 ~ n 사용

        if (n <= 2) {
            System.out.println(n);
            return;
        }

        dp[1] = 1;      dp[2] = 2;      // 초항 A(1), A(2)
        for (int i = 3; i <= n; i++)
            dp[i] = (dp[i - 1] + dp[i - 2]) % 10007;

        System.out.println(dp[n]);
    }
}

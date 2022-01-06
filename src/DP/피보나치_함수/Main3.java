package DP.피보나치_함수;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

/*
1. 아이디어
 - 초항: f(0) = (1, 0), f(1) = (0, 1)
 - 점화식: f(n) = f(n-1) + f(n-2)

 1) 입력 테스트 케이스 배열 확인
   - for 문으로 0 ~ t-1 반복
 2) DP 배열에서 각 입력 테스트 케이스 n에 해당하는 DP[n]이 없으면,
    for 문으로 DP 배열 채우기
   - 단, 처음 0 index 부터 반복 시작이 아닌 DP 배열에 빈 index 부터 시작
     => 반복 최소화
     (각 테스트 케이스 값에 해당하는 solution 이 이미 DP 배열에 저장되어 있으면, 반복 X)

2. 자료구조
 - ArrayList<Pair>: DP 배열 (각 요소가 출력되는 0, 1 개수 저장),

3. 시간 복잡도
 - DP 배열 채움: O(maxN)
 - 채운 DP 배열의 요소 출력: O(t)
 => O(n + t)
*/

public class Main3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        int t = Integer.parseInt(br.readLine());    // 테스트 케이스 개수
        int[] arrN = new int[t];                    // 각 테스트 케이스(n) 저장한 배열
        for (int i = 0; i < arrN.length; i++)
            arrN[i] = Integer.parseInt(br.readLine());

        List<Pair> dp = new ArrayList<>();
        dp.add(new Pair(1, 0));     // 초항
        dp.add(new Pair(0, 1));

        for (int n : arrN) {
            if (dp.size() - 1 >= n) {
                System.out.println(dp.get(n));
                continue;
            }

            // 각 테스트 케이스 n에 대해 DP[n]이 채워져 있지 않으면, DP 배열 채움
            int start = dp.size();
            for (int i = start; i <= n; i++) {
                Pair dpN = new Pair(
                        dp.get(i-1).getNumOfZero() + dp.get(i-2).getNumOfZero(),
                        dp.get(i-1).getNumOfOne() + dp.get(i-2).getNumOfOne()
                );
                dp.add(dpN);
            }
            System.out.println(dp.get(n));
        }
    }
}

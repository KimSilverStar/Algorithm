package Greedy.회의실_배정;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;

/*
1. 아이디어
 - 각 회의의 종료 시간을 기준으로 Meeting 배열 오름차순 정렬
   !!! 종료 시간이 같으면, 시작 시간 빠른 순서
 - for 문으로 Meeting[] 을 차례로 확인
   => 현재 회의의 end <= 다음 회의의 start 를 만족하는 회의 선택

2. 자료구조
 - Meeting[]: 회의 시작, 종료 시간 배열

3. 시간 복잡도
 - Meeting[] 오름차순 정렬: O(n lg n)
 - 정렬된 Meeting[] 을 for 문으로 확인: O(n)
   => 총 O(n lg n + n)
   => n lg n + n 에 n 최대값 10^5 대입
   => 10^5 * 5 + 10^5 = 6 * 10^5 << 2억
*/

class Meeting implements Comparable<Meeting> {
    private int start;          // 회의 시작, 종료 시간
    private int end;

    public Meeting(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() { return start; }
    public int getEnd() { return end; }

    public int compareTo(Meeting m) {
        int diffEnd = this.end - m.end;
        if (diffEnd != 0)
            return diffEnd;
        else
            return this.start - m.start;
    }
}

public class Main {
    static int n;                   // 회의 수
    static Meeting[] meetings;      // 회의 시작, 종료 배열

    public static int solution() {
        int maxNum = 1;             // 사용 가능한 회의 최대 개수
        Meeting current = meetings[0];

        // 다음 Meeting 을 차례로 확인
        for (int i = 1; i < meetings.length; i++) {
            if (current.getEnd() <= meetings[i].getStart()) {
                current = meetings[i];
                maxNum++;
            }
        }

        return maxNum;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        meetings = new Meeting[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            meetings[i] = new Meeting(start, end);
        }

        // Meeting 배열을 end 를 기준으로 오름차순 정렬
        Arrays.sort(meetings);

        System.out.println(solution());
    }
}

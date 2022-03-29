package Greedy.내일_할거야;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;

/*
1. 아이디어
 - 각 과제의 시작일, 종료일은 서로 겹치지 않아야 함
   e.g. 예제 1에서 과제1의 종료일은 7일, 과제2의 종료일은 8일로 서로 안겹치게 배치됨

 1) 과제 객체(과제 소요일 d_i, 과제 마감일 t_i) 배열을 정렬
   - 과제 마감일(t_i)이 큰 순(뒷 일자가 먼저 오도록 늦은 순)으로 정렬
 2) 마감일이 큰 과제부터 과제 (시작 일자, 종료 일자)를 지정
   - 조건 ①: [기본 조건] 현재 과제의 종료 일자 <= 현재 과제의 마감일
     => 현재 과제의 종료 일자 = 현재 과제의 마감일 - 1일 (하루 전)
   - 조건 ②: 현재 과제의 종료 일자 < 다음 마감일 과제의 시작 일자
     => 조건 ①에서 다음 마감일 과제의 시작 일자와 겹치는 경우
     => 현재 과제의 종료 일자 = 다음 마감일 과제의 시작 일자 - 1일 (하루 전)
   => 현재 과제의 시작 일자 = 현재 과제의 종료 일자 - (과제 소요일 - 1일)
      (과제 시작 일자는 과제 종료 일자를 정하면, 자동적으로 정할 수 있음)
 => 출력 값 = 마감일이 가장 빠른 과제(정렬된 과제 배열에서 마지막 원소)의 시작 일자 - 1일

 ※ 위 그리디 방식의 최적해 보장 이유: 문제의 보장 조건
   "모든 입력에 대해, 오늘 아무 것도 안 해도 과제를 마무리 할 수 있는 방법이 존재함이 보장된다."


2. 자료구조
 - Homework[]: 입력 값인 (과제 소요일 d_i, 과제 마감일 t_i) 쌍과
   그리디 방식으로 채워나갈 (과제 시작 일자, 과제 종료 일자)
   => 최초 과제 마감일이 큰 순으로 정렬


3. 시간 복잡도
 - 과제 배열 정렬: O(n log2 n)
   => n 최대값 대입: 10^6 log2 10^6 = (6 x 10^6) log2 10 ~= 18 x 10^6
 - 과제 시작 일자, 종료 일자 지정: O(n)
   => n 최대값 대입: 10^6
 => 전체 시간 복잡도: (18 x 10^6) + 10^6 = 19 x 10^6 << 2억
*/

class Homework implements Comparable<Homework> {
	public int d, t;			// 과제 입력 값: (소요일 d_i, 마감일 t_i)
	public int start, end;		// 채울 값: (시작 일자, 종료 일자)

	public Homework(int d, int t) {
		this.d = d;
		this.t = t;
	}

	public int compareTo(Homework hw) {
		// 마감일이 큰 순으로 정렬
		return hw.t - this.t;
	}
}

public class Main {
	static int n;					// 과제 개수
	static Homework[] homeworks;	// 과제 입력 값 (소요일 d_i, 마감일 t_i), 채울 값 (시작 일자, 종료 일자)
	static int maxDayCount;			// 출력, 내일(1일)부터 연속으로 최대 놀 수 있는 일자 수

	static void solution() {
		// 마감일이 가장 늦은 과제
		homeworks[0].end = homeworks[0].t;
		homeworks[0].start = homeworks[0].end - (homeworks[0].d - 1);

		for (int i = 1; i < n; i++) {
			// 조건 ①: [기본 조건] 현재 과제의 종료 일자 <= 현재 과제의 마감일
			// - 현재 과제의 종료 일자 = 현재 과제의 마감일 - 1일 (하루 전)
			homeworks[i].end = homeworks[i].t;
			homeworks[i].start = homeworks[i].end - (homeworks[i].d - 1);

			// 현재 과제의 종료 일자 >= 이후 과제의 시작 일자로 겹치는 경우
			if (homeworks[i].end >= homeworks[i-1].start) {
				// 조건 ②: 현재 과제의 종료 일자 < 다음 마감일 과제의 시작 일자
				// - 조건 ①에서 다음 마감일 과제의 시작 일자와 겹치는 경우
				// - 현재 과제의 종료 일자 = 다음 마감일 과제의 시작 일자 - 1일 (하루 전)
				homeworks[i].end = homeworks[i-1].start - 1;
				homeworks[i].start = homeworks[i].end - (homeworks[i].d - 1);
			}
		}

		maxDayCount = homeworks[n - 1].start - 1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());

		homeworks = new Homework[n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());

			homeworks[i] = new Homework(d, t);
		}

		Arrays.sort(homeworks);
		solution();
		System.out.println(maxDayCount);
	}
}

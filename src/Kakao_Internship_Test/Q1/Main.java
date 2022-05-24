package Kakao_Internship_Test.Q1;
import java.util.*;

/*
1. 아이디어
 - n 개 질문, 각 질문에 1가지 지표로 대답
 - survey[i].charAt(0): [i+1]번 질문의 "비동의" 관련 선택지의 결과, 성격 유형
 - survey[i].charAt(1): [i+1]번 질문의 "동의" 관련 선택지의 결과, 성격 유형
 - 각 대립되는 지표 2개 중, 점수가 더 높은 것 선택
   (점수 동일 시, 사전 순으로 빠른 것 선택)
 - 선택지 대답 choices[]: 1 (매우 비동의) ~ 7 (매우 동의)


2. 자료구조
 - int 8개: 각 지표 점수 - 라이언 형 ~ 네오 형

3. 시간 복잡도
 - O(n)
*/

public class Main {
	// R, T, C, F, J, M, A, N 지표의 점수
	static int[] scores = { 0, 0, 0, 0, 0, 0, 0, 0 };
	static StringBuilder sb = new StringBuilder();

	/* 지표 문자 'T' 등을 인자로 받아서, 매칭되는 scores[]의 index 반환  */
	static int getIndex(char indicator) {
		if (indicator == 'R') return 0;
		else if (indicator == 'T') return 1;
		else if (indicator == 'C') return 2;
		else if (indicator == 'F') return 3;
		else if (indicator == 'J') return 4;
		else if (indicator == 'M') return 5;
		else if (indicator == 'A') return 6;
		else return 7;		// 'N'
	}

	static void calcScore(String question, int choice) {
		char notAgree = question.charAt(0);
		char doAgree = question.charAt(1);

		int notAgreeIdx = getIndex(notAgree);
		int doAgreeIdx = getIndex(doAgree);

		if (choice == 1)				// 비동의 관련
			scores[notAgreeIdx] += 3;
		else if (choice == 2)
			scores[notAgreeIdx] += 2;
		else if (choice == 3)
			scores[notAgreeIdx] += 1;
		else if (choice == 5)			// 동의 관련
			scores[doAgreeIdx] += 1;
		else if (choice == 6)
			scores[doAgreeIdx] += 2;
		else if (choice == 7)
			scores[doAgreeIdx] += 3;
	}

	static void getTypeStr() {
		if (scores[0] > scores[1])
			sb.append("R");
		else if (scores[0] < scores[1])
			sb.append("T");
		else
			sb.append("R");

		if (scores[2] > scores[3])
			sb.append("C");
		else if (scores[2] < scores[3])
			sb.append("F");
		else
			sb.append("C");

		if (scores[4] > scores[5])
			sb.append("J");
		else if (scores[4] < scores[5])
			sb.append("M");
		else
			sb.append("J");

		if (scores[6] > scores[7])
			sb.append("A");
		else if (scores[6] < scores[7])
			sb.append("N");
		else
			sb.append("A");
	}

	/* 각 질문에 대한 판단 지표, 각 질문에 대해 선택한 선택지 */
	static String solution(String[] survey, int[] choices) {
		/* qeustion: "RT", "TR" 등 */
		for (int i = 0; i < survey.length; i++) {
			calcScore(survey[i], choices[i]);
		}

		getTypeStr();

		return sb.toString();
	}

	public static void main(String[] args) {
//		String[] survey1 = { "AN", "CF", "MJ", "RT", "NA" };
//		int[] choices1 = { 5, 3, 2, 7, 5 };
//		System.out.println(solution(survey1, choices1));

		// R, T, C, F, J, M, A, N 지표의 점수
//		System.out.println("--------------------");
//		for (int score : scores)
//			System.out.println(score);
//		System.out.println("--------------------");

		String[] survey2 = { "TR", "RT", "TR" };
		int[] choices2 = { 7, 1, 3 };
		System.out.println(solution(survey2, choices2));
	}
}

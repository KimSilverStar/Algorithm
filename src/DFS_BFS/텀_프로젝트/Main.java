package DFS_BFS.텀_프로젝트;
import java.io.*;
import java.util.*;

/*
- 모든 학생이 함께 하고싶은 팀원 1명 선택 (본인 선택 가능)
- 팀 구성 조건
  1) 팀원 선택이 순환 형태로 이루어지는 경우
  2) 본인을 선택한 경우
=> 팀에 속하지 않은 학생들의 수 구하기
*/

/*
1. 아이디어
 *** 팀을 구성하든, 구성하지 못하든 마지막 순서의 노드는 순환을 이룸
 ex 1) 예제 입력 1에서, 2 -> 1 -> 3 -> 3	(2, 1 은 팀을 못이루지만, 3은 혼자 팀을 이룸)
 ex 2) 예제 입력 1에서, 4 -> 7 -> 6 -> 4  (4, 7, 6 이 팀을 이룸)
 => 링크를 따라 확인하면서, 순환을 이루는 학생들만 카운트 하기

 - 입력 배열을 차례로 확인
   => 현재 학생 students[i] 를 아직 방문 안한 경우, DFS 탐색 시작
 - DFS
   1) students[i] 방문 처리 (check 배열)
   2) students[i] 가 가리키는 다음 학생 next
     - next 를 아직 방문 안한 경우, DFS 탐색 확장 (재귀 호출)
     - next 를 이미 방문한 경우
       => 순환을 이루는 학생들을 팀 구성 인원으로 카운트
       ex 1) 4 -> 7 -> 6 -> 4
       		- 순환을 이루는 4, 7, 6 을 카운트
       ex 2) 2 -> 1 -> 3 -> 3
            - 순환을 이루는 3 (혼자 팀 구성)을 카운트
   3) 순환 탐색 종료 처리 (finished 배열)
     - 순환 탐색한 모든 학생들 순환 탐색 종료 처리
       e.g. 4 -> 7 -> 6 -> 4 순환 탐색 했으면,
            6 -> 7 -> 4 순서로 순환 탐색 종료 처리 됨

2. 자료구조
 - boolean[] check: 단순 방문 확인
 - boolean[] finished: 해당 방문 중인 노드의 탐색 종료 여부 확인
   => 링크를 따라 끝까지 이동했는지 여부

3. 시간 복잡도
 - 순환 탐색 종료한 노드에 대해서는 더 이상 탐색 X
   => O(n)
*/

/* 오답 노트
 - 단순히 방문만 확인하는 배열 boolean[] check 만 사용
 - 싸이클(순환)
*/

public class Main {
	static int t;					// 테스트 케이스 개수
	static int n;					// 각 테스트 케이스에서 학생의 수
	static int[] students;			// n 명의 학생들이 선택한 학생 번호
	static int soloCount;			// 출력 값: 팀에 속하지 않은 학생 수

	static boolean[] check;			// 방문 여부
	static boolean[] finished;		// 방문 중인 노드의 탐색 종료 여부

	/* currentId: 현재 탐색 중인 학생의 번호 */
	static void dfs(int currentId) {
		check[currentId] = true;

		int nextId = students[currentId];			// 선택 당한 학생 번호 (다음 학생 번호)
		if (!check[nextId])		// 다음 학생을 방문 안한 경우 => 탐색 확장
			dfs(nextId);
		else {					// 다음 학생을 이미 방문한 경우 => 순환(팀) 이루는지 확인
			if (!finished[nextId]) {
				soloCount--;

				// e.g. 4 -> 7 -> 6 (currentId) -> 4 (nextId)
				// nextId 에서 시작, 링크를 따라 currentId 까지 따라감
				while (nextId != currentId) {
					nextId = students[nextId];
					soloCount--;
				}
			}
		}

		finished[currentId] = true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		t = Integer.parseInt(br.readLine());
		for (int tc = 0; tc < t; tc++) {
			n = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());

			students = new int[n + 1];			// [1] ~ [n] 사용
			check = new boolean[n + 1];
			finished = new boolean[n + 1];
			for (int i = 1; i <= n; i++)
				students[i] = Integer.parseInt(st.nextToken());

			soloCount = n;
			for (int i = 1; i <= n; i++) {
				if (!check[i])			// 방문 안한 학생인 경우, 탐색 시작
					dfs(i);
			}
			sb.append(soloCount).append("\n");
		}

		System.out.println(sb.toString());
	}
}

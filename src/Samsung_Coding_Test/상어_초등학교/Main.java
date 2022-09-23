package Samsung_Coding_Test.상어_초등학교;
import java.io.*;
import java.util.*;

/*
- n x n 맵: map[1][1] ~ map[n][n]
- 학생: n^2명 (번호: 1 ~ n^2번)
- 칸 인접: 가로, 세로 중 1칸 차이

다음 규칙으로 학생의 자리를 차례로 지정
1) 빈 칸 중, 좋아하는 학생이 인접 칸에 가장 많은 칸 선택
2) 1)을 만족하는 칸이 여러 개인 경우, 인접 칸 중, 빈 칸이 가장 많은 칸 선택
  => 인접한 빈 칸이 가장 많은 칸 선택
3) 2)를 만족하는 칸이 여러 개인 경우, 행 번호 낮은 칸, 열 번호 낮은 칸 선택

- 출력: 각 학생의 만족도 합
  => [i]번 학생의 만족도 = [i]번 학생의 인접 칸에 앉은 [i]번 학생이 좋아하는 학생의 수에 따름
*/

/*
1. 아이디어
 - 구현, 시뮬레이션, 자료구조 (PriorityQueue, HashSet)

 모든 학생의 자리를 지정할 때까지, 다음 규칙에 따라 자리 지정 반복

 1) 빈 칸 중, 좋아하는 학생이 인접 칸에 가장 많은 칸 선택
   - map[1][1] ~ map[n][n] 차례로 확인
     => int countNearbyLikeStudent(int studentIdx, int y, int x)
        : [y][x] 지점의 인접 칸에 존재하는 studentIdx번 학생이 좋아하는 학생 수 카운트

 2) 1)을 만족하는 칸이 여러 개인 경우, 인접한 빈 칸이 가장 많은 칸 선택
   - map[1][1] ~ map[n][n] 차례로 확인
     => int countNearbyEmptySeat(int y, int x): [y][x] 지점의 인접한 빈 칸 수 카운트
 => 1), 2) 메소드 한번에 처리

 3) 2)를 만족하는 칸이 여러 개인 경우, 행 번호 낮은 칸, 열 번호 낮은 칸 선택


2. 자료구조
 - int[][] map: 학생 자리 지정 맵
   => map[i][j] = 0 이면, 해당 자리는 빈 칸
   => map[i][j] = k (k != 0) 이면, 해당 자리에 k번 학생 존재
 - List<Integer>, ArrayList<Integer> studentOrderList: 학생 자리 지정 순서 (입력 학생 번호 순서)
 - Set<Integer>[] likeStudentSets: 각 학생이 좋아하는 4명의 학생 저장
   ex) likeStudentSets[i]: [i]번 학생이 좋아하는 학생들의 번호 저장
 - PriorityQueue<Node>: 학생 자리 지정 기준에 따라 정렬
   => Node: 인접 칸에 존재하는 좋아하는 학생 수, 인접 칸에 존재하는 빈 자리 수, 자리 위치
   => 정렬) 좋아하는 학생 수 많은 순 -> 빈 자리 많은 순 -> 자리 행, 열 번호 작은 순


3. 시간 복잡도
 - 학생 1명의 자리 지정: (n^2 x 4) + (n^2 log_2 n^2)
   - n^2 칸의 맵 전체 확인: n^2
   - 각 칸마다 4개 인접 칸 확인
   - 해당 칸의 정보 Node를 PriorityQueue에 추가하여 힙 정렬
   => O(4 x n^2 + 2 x n^2 log_2 n) = O(n^2 + n^2 log_2 n) = O(n^2 log_2 n)
 - 전체 학생 n^2명의 자리 지정: n^2 x n^2 log_2 n
   => O(n^4 x log_2 n)
*/

class Node implements Comparable<Node> {
	public int likeStudentCnt;		// 인접 칸에 존재하는 좋아하는 학생 수
	public int emptySeatCnt;		// 인접 칸에 존재하는 빈 자리 수
	public int y, x;				// 해당 자리 위치

	public Node(int likeStudentCnt, int emptySeatCnt, int y, int x) {
		this.likeStudentCnt = likeStudentCnt;
		this.emptySeatCnt = emptySeatCnt;
		this.y = y;
		this.x = x;
	}

	public int compareTo(Node o) {
		// likeStudentCnt 많은 순 -> emptySeatCnt 많은 순 -> y 작은 순 -> x 작은 순
		if (this.likeStudentCnt != o.likeStudentCnt) {
			return o.likeStudentCnt - this.likeStudentCnt;
		}
		if (this.emptySeatCnt != o.emptySeatCnt) {
			return o.emptySeatCnt - this.emptySeatCnt;
		}
		if (this.y != o.y) {
			return this.y - o.y;
		}
		return this.x - o.x;
	}
}

public class Main {
	static int n;					// n x n 행렬
	static int numOfStudent;		// 전체 n^2명 학생
	static int resultSum;			// 출력, 학생들의 만족도 합

	static int[][] map;				// 학생 자리 지정 맵
	static List<Integer> studentOrderList = new ArrayList<>();	// 학생 자리 지정 순서 (입력 학생 번호 순서)
	static Set<Integer>[] likeStudentSets;						// 각 학생이 좋아하는 4명의 학생
	static PriorityQueue<Node> pq = new PriorityQueue<>();		// 학생 자리 지정 기준 정렬
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void solution() {
		// 모든 학생들의 자리 지정
		selectStudentsSeat();

		// 모든 학생의 자리 지정 완료 후, 학생의 만족도 합 계산
		calcStudentsSatisfaction();
	}

	/* 차례로 학생들의 자리 지정 */
	static void selectStudentsSeat() {
		// 입력 학생 번호 순서에 따라, 차례로 자리 지정
		for (int studentIdx : studentOrderList) {
			// studentIdx번 학생이 좋아하는 학생들의 번호
			Set<Integer> likeStudentSet = likeStudentSets[studentIdx];

			for (int y = 1; y <= n; y++) {
				for (int x = 1; x <= n; x++) {
					if (map[y][x] != 0)			// [y][x] 칸이 빈 자리인 경우만 확인
						continue;

					int likeStudentCnt = 0;		// 인접 칸에 존재하는 좋아하는 학생 수
					int emptySeatCnt = 0;		// 인접 칸에 존재하는 빈 자리 수

					for (int i = 0; i < 4; i++) {
						int ny = y + dy[i];		// 인접 칸 (ny, nx)
						int nx = x + dx[i];

						if (!isValid(ny, nx))
							continue;

						// 인접 칸이 빈 자리인 경우
						if (map[ny][nx] == 0) {
							emptySeatCnt++;
						}
						// 인접 칸에 좋아하는 학생이 존재하는 경우
						else if (likeStudentSet.contains(map[ny][nx])) {
							likeStudentCnt++;
						}
					}

					pq.add(new Node(likeStudentCnt, emptySeatCnt, y, x));
				}
			}

			// 맵의 모든 칸을 확인한 후, studentIdx번 학생의 자리를 규칙에 따라 선택
			Node selected = pq.remove();
			map[selected.y][selected.x] = studentIdx;		// studentIdx번 학생 자리 지정 완료

			pq.clear();			// pq 초기화
		}
	}

	/* 학생의 만족도 합 계산 */
	static void calcStudentsSatisfaction() {
		for (int y = 1; y <= n; y++) {
			for (int x = 1; x <= n; x++) {
				int studentIdx = map[y][x];
				int likeStudentCnt = 0;
				Set<Integer> likeStudentSet = likeStudentSets[studentIdx];

				// studentIdx번 학생의 자리 인접 칸에 앉은 좋아하는 학생 수 카운트
				for (int i = 0; i < 4; i++) {
					int ny = y + dy[i];
					int nx = x + dx[i];

					if (isValid(ny, nx) && likeStudentSet.contains(map[ny][nx])) {
						likeStudentCnt++;
					}
				}

				if (likeStudentCnt == 1) resultSum += 1;
				else if (likeStudentCnt == 2) resultSum += 10;
				else if (likeStudentCnt == 3) resultSum += 100;
				else if (likeStudentCnt == 4) resultSum += 1000;
			}
		}
	}

	static boolean isValid(int ny, int nx) {
		return (1 <= ny && ny <= n && 1 <= nx && nx <= n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		numOfStudent = n * n;

		map = new int[n + 1][n + 1];					// [1][1] ~ [n][n] 사용
		likeStudentSets = new Set[numOfStudent + 1];	// [1] ~ [numOfStudent] 사용
		for (int i = 1; i <= numOfStudent; i++) {
			likeStudentSets[i] = new HashSet<>();
		}

		for (int i = 0; i < numOfStudent; i++) {
			st = new StringTokenizer(br.readLine());
			int studentIdx = Integer.parseInt(st.nextToken());
			int s1 = Integer.parseInt(st.nextToken());
			int s2 = Integer.parseInt(st.nextToken());
			int s3 = Integer.parseInt(st.nextToken());
			int s4 = Integer.parseInt(st.nextToken());

			studentOrderList.add(studentIdx);

			// studentIdx번 학생이 s1, s2, s3, s4 학생을 좋아함
			likeStudentSets[studentIdx].add(s1);
			likeStudentSets[studentIdx].add(s2);
			likeStudentSets[studentIdx].add(s3);
			likeStudentSets[studentIdx].add(s4);
		}

		solution();

		System.out.println(resultSum);
	}
}

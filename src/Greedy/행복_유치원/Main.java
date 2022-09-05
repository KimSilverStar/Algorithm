package Greedy.행복_유치원;
import java.io.*;
import java.util.*;

/*
- 키 순으로 정렬된 n명을 k개의 조로 나눔
- 각 조에는 한명 이상 존재
- 같은 조의 인원은 서로 인접해야 함
- 각 조 티셔츠 비용 = 가장 큰 키 - 가장 작은 키
  => 목표: k개 조의 티셔츠 최소 비용
*/

/*
1. 아이디어
 - 인접한 인원의 키 차이가 큰 경우
   => 큰 키의 인원을 1명 조로 분리하여, 비용 합 최소화
 - 인접 인원의 키 차이가 큰 순으로 (k-1)개 선택하여 조 분리

 1) 인접한 인원들 끼리의 키 차이를 모두 계산
   - List<Integer>에 저장
//   - List<Node>에 저장
//   - Node: 인접 두 인원의 위치 idx1, idx2, 키 차이 diff
// 2) List<Node> 키 차이 diff 큰 순으로 정렬
 2) List<Integer> 키 차이 큰 순으로 정렬
 3) k개 조로 분리
   - 키 차이 큰 순으로 (k-1)개 선택
     : 분리할 위치 (k-1)개 선택

2. 자료구조
 - int[] heights: 입력, 정렬된 키 배열
 - List<Integer> list: 인접 인원의 키 차이 리스트
   => 키 차이 큰 순으로 정렬
// - List<Node> list: 인접 두 인원의 위치, 키 차이 리스트
//   => 키 차이 큰 순으로 정렬

3. 시간 복잡도
 - 리스트 정렬: O(n log_2 n)
 - 정렬된 리스트 원소 반복문: O(n)
*/

class Node implements Comparable<Node> {
	public int idx1, idx2;		// 인접 두 인원의 위치
	public int diff;			// 인접 두 인원의 키 차이

	public Node(int idx1, int idx2, int diff) {
		this.idx1 = idx1;
		this.idx2 = idx2;
		this.diff = diff;
	}

	// 키 차이 큰 순으로 정렬
	public int compareTo(Node n) {
		return n.diff - this.diff;
	}
}

public class Main {
	static int n;				// 전체 n명
	static int k;				// 전체 k개 조
	static int[] heights;		// 정렬된 키 배열

//	static List<Node> list = new ArrayList<>();			// 키 차이 리스트
	static List<Integer> list = new ArrayList<>();		// 인접 인원 키 차이 리스트
	static int minCostSum;		// 출력, 티셔츠 비용 최소합

	static void solution() {
		// 키 차이 큰 순으로 (k-1)개 제외하고, 나머지 키 차이 값들 모두 더함
		for (int i = k - 1; i < list.size(); i++) {
//			minCostSum += list.get(i).diff;
			minCostSum += list.get(i);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());

		heights = new int[n];
		for (int i = 0; i < n; i++) {
			heights[i] = Integer.parseInt(st.nextToken());
		}

		// 인접 인원 키 차이 리스트 저장 및 정렬
		for (int i = 1; i < n; i++) {
			int diff = heights[i] - heights[i-1];
			list.add(diff);
//			list.add(new Node(i-1, i, diff));
		}
		list.sort(Comparator.reverseOrder());

		solution();
		System.out.println(minCostSum);
	}
}

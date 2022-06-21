package BinarySearch.랜선_자르기;
import java.io.*;
import java.util.StringTokenizer;

/*
1. 아이디어
 - 이미 갖고있는 k개 랜선으로, 같은 길이의 n개 이상의 랜선으로 만드려 함
 - 만들 수 있는 최대 랜선 길이 구하기
 => 길이를 몇으로 하여 n개 이상으로 만들지가 관건

 - 자르는 랜선 길이를 길게 할 수록, 만들 수 있는 랜선 개수 적어짐
 - 1 ~ maxLength (입력 k개 랜선 길이 중, 최대 길이) 이진 탐색
 - midLength 길이로 잘랐을 때, 만드는 랜선 개수 lanCount 계산
 1) lanCount >= n 인 경우
   - 출력 resultMaxLength 갱신
   - start = midLength + 1, end = 그대로 다시 탐색
     => 더 큰 길이로 잘라서, 더 적은 개수로 만들 수 있는지 확인
 2) lanCount < n 인 경우
   - start = 그대로, end = midLength - 1 로 다시 탐색
     => 더 짧은 길이로 잘라서, 더 많은 개수로 만들 수 있는지 확인


2. 자료구조
 - int[]: 입력 k개 랜선 길이


3. 시간 복잡도
 1) 이진 탐색: O(log_2 maxLength)
   - maxLength: 입력 랜선 길이 중, 가장 긴 길이
   => 대략 최대 log_2 2^31 = 31
 2) 랜선 개수 계산: O(k)
   => 최대 10^4
 - 총 시간 복잡도: O(k log_2 maxLength)
   => 최대 31 x 10^4 << 2억
*/

public class Main_Iterative {
	static int k, n;				// 이미 갖고있는 k개 랜선, 필요한 최소 n개 랜선
	static int[] lans;				// 이미 갖고있는 랜선의 길이
	static int resultMaxLength;		// 출력, 만들 수 있는 랜선 최대 길이
	static int maxLength = Integer.MIN_VALUE;		// 입력 k개 랜선 중, 가장 긴 랜선의 길이

	static void binarySearch(long start, long end) {
		while (start <= end) {
			long midLength = (start + end) / 2;			// int 사용 시, start + end 과정에서 Overflow 발생 가능
			int lanCount = getLanCount((int) midLength);	// midLength 길이로 만드는 랜선 개수

			if (lanCount >= n) {
				resultMaxLength = Math.max(resultMaxLength, (int) midLength);
				start = midLength + 1;
			}
			else {
				end = midLength - 1;
			}
		}
	}

	static int getLanCount(int cutLength) {
		int count = 0;
		for (int lan : lans)
			count += (lan / cutLength);
		return count;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		k = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());

		lans = new int[k];
		for (int i = 0; i < k; i++) {
			lans[i] = Integer.parseInt(br.readLine());
			maxLength = Math.max(maxLength, lans[i]);
		}

		binarySearch(1, maxLength);
		System.out.println(resultMaxLength);
	}
}

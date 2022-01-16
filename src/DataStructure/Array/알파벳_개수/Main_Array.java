package DataStructure.Array.알파벳_개수;
import java.io.*;

public class Main_Array {
	static String s;			// 입력 단어
	static int[] answer = new int[26];
	// 단어의 각 알파벳 별 개수 => 길이: 알파벳 개수 26
	// 'a' => [0],  ...,  'z' => [25]

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		s = br.readLine();
		for (int i = 0; i < s.length(); i++) {
			char alpha = s.charAt(i);
			answer[alpha - 'a']++;
		}

		for (int num : answer)
			System.out.print(num + " ");
	}
}

package DataStructure.Array.알파벳_개수;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

// Key: 알파벳 문자 char
// Value: 각 알파벳 등장 횟수

public class Main_Hash {
	static String s;			// 입력 단어
	static Map<Character, Integer> map = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		s = br.readLine();
		for (int i = 0; i < s.length(); i++) {
			if (map.containsKey(s.charAt(i))) {
				int prevNum = map.get(s.charAt(i));
				map.put(s.charAt(i), prevNum + 1);
			}
			else
				map.put(s.charAt(i), 1);
		}

		for (char alpha : map.keySet())
			System.out.print(alpha + " ");
	}
}
package UtilMethod;

public class Util {
	/* 밑이 base 인 log_base (x) 값 반환 */
	static int baseLog(int x, int base) {
		return (int)(Math.log(x) / Math.log(base));
	}
}

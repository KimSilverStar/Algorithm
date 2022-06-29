package DataStructure.Map_Set.문제_추천_시스템_Version_1;

class Problem implements Comparable<Problem> {
	public int pIdx, level;			// 문제 번호, 난이도

	public Problem(int pIdx, int level) {
		this.pIdx = pIdx;
		this.level = level;
	}

	// 난이도 낮은 순 (다수일 경우, 문제 번호 작은 순)
	public int compareTo(Problem o) {
		if (this.level != o.level)
			return this.level - o.level;
		else
			return this.pIdx - o.pIdx;
	}
}


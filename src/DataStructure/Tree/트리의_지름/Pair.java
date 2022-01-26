package DataStructure.Tree.트리의_지름;

class Pair {
	public int vertex;			// 연결된 정점 번호
	public int distance;		// 간선 거리

	public Pair(int vertex, int distance) {
		this.vertex = vertex;
		this.distance = distance;
	}
}
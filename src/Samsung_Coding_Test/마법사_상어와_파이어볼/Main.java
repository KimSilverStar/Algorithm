package Samsung_Coding_Test.마법사_상어와_파이어볼;
import java.io.*;
import java.util.*;

/*
- 파이어볼: 위치 좌표 (y, x), 질량 m, 방향 d, 속력 s
- map은 [n]행과 [1]행이 연결, [n]열과 [1]열이 연결됨
  => 끝 행이 첫 행과 연결, 끝 열이 첫 열과 연결됨

파이어볼 이동 명령
1) 각 파이어볼이 자신의 방향 d로 속력 s칸 만큼 이동
 - 이동 중, 같은 칸에 다수의 파이어볼 존재 가능 (칸 중복 가능)

2) 각 파이어볼의 이동이 모두 끝난 뒤, 2개 이상의 파이어볼이 있는 칸에 다음 과정 수행
 ① 같은 칸의 파이어볼을 모두 하나로 합침
   - 같은 칸의 파이어볼들의 질량 합, 속력 합 계산
   - 같은 칸에 존재하는 파이어볼들의 방향이 짝수, 홀수 존재하는지 체크
 ② 합쳐진 1개의 파이어볼을 4개의 파이어볼로 나눔
 ③ 나누어진 파이어볼의 질량, 속력, 방향
   - 질량 = Math.floor(합쳐진 파이어볼 질량의 합 / 5)
   - 속력 = Math.floor(합쳐진 파이어볼 속력의 합 / 합쳐진 파이어볼 개수)
   - 방향
     => 합쳐지는 파이어볼의 방향이 모두 홀수 or 모두 짝수이면, 방향은 0, 2, 4, 6
     => 그렇지 않으면(짝수, 홀수가 섞인 경우), 방향은 1, 3, 5, 7
 ④ 나누어진 파이어볼의 질량이 0이면 소멸시킴

- 출력: 파이어볼 k번 이동한 후, 남은 파이어볼 질량의 합
*/

/*
1. 아이디어
 - 구현, 시뮬레이션
 - 같은 칸에 존재하는 다수의 파이어볼을 표현
   => List<FireBall>[][] fireBallMap
   => FireBall: 파이어볼 m, s, d
   ex) fireBallMap[y][x]: [y][x] 위치에 있는 파이어볼들


1) 각 파이어볼을 자신의 방향 d로 속력 s칸 만큼 이동
 - List<FireBall>[][] nextFireBallMap 에 이동시킨 파이어볼 저장 후,
   기존 fireBallMap = nextFireBallMap 하여 갱신
 - [1][1] ~ [n][n] 차례로 확인
   => fireBallMap[i][j].size() >= 1 인 경우만 확인 (파이어볼이 없는 칸은 확인 X)
 - 다음 칸
   int distance = fireBall.s % n		// 속력 s가 매우 큰 경우 대비
   int ny = fireBall.y + (distance * dy[fireBall.d])
   int nx = fireBall.x + (distance * dx[fireBall.d])
 ※ 다음 칸 (ny, nx)이 맵을 벗어나는 경우 => 끝 행(열)과 첫 행(열) 연결 처리
   - ny > n 인 경우, ny -= n 처리
   - ny <= 0 인 경우, ny += n 처리
   - nx > n 인 경우, nx -= n 처리
   - nx <= 0 인 경우, nx += n 처리
 - nextFireBallMap[i][j].add(이동시킨 FireBall)

2) 각 파이어볼의 이동이 모두 끝난 뒤, 2개 이상의 파이어볼이 있는 칸에 다음 과정 수행
 - [1][1] ~ [n][n] 차례로 확인
   => fireBallMap[i][j].size() >= 2 인 경우만 확인 (2개 이상의 파이어볼이 존재하는 위치만 확인)
 - [i][j] 칸에 존재하는 모든 파이어볼들의 m, s 합 계산
 - boolean existDirOdd, existDirEven 플래그 변수
   => 홀수 / 짝수 방향의 파이어볼 존재 여부 체크
 - 4개로 나누어진 파이어볼의 m, s 계산 (Math.floor())
   ※ 나누어진 파이어볼의 질량 m == 0 인 경우, 파이어볼을 소멸시킴
 - 4개로 나누어진 각 파이어볼의 방향을 existDirOdd, existDirEvent 플래그 변수로 정함


 ※ 오답노트
  - 파이어볼을 이동시킨 다음 위치 계산을 잘못함.
    파이어볼의 속력 s가 매우 큰 경우를 처리하지 못함.
  - 오답 코드)
    int ny = fireBall.y + (fireBall.s * dy[fireBall.d]);
  - 정답 코드)
    int distance = fireBall.s % n;		// 속력 s가 매우 큰 경우 대비한 % n 연산
    int ny = fireBall.y + (distance * dy[fireBall.d]);


2. 자료구조
 - List<FireBall>[][] fireBallMap: 각 위치에 해당하는 파이어볼들 저장
   - FireBall: 파이어볼 m, s, d
   ex) fireBallMap[i][j]: [i][j] 위치에 존재하는 파이어볼들
*/

class FireBall {
	public int m, s, d;			// 파이어볼 질량, 속력, 방향

	public FireBall(int m, int s, int d) {
		this.m = m;
		this.s = s;
		this.d = d;
	}
}

public class Main {
	static int n;				// n x n 행렬
	static int m;				// m개 파이어볼 발사
	static int k;				// 파이어볼 이동 k번 명령
	static int resultSumMass;	// 출력, 남은 파이어볼의 질량 합

	static List<FireBall>[][] fireBallMap;
	// 파이어볼 이동 방향 8개
	static int[] dy = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dx = { 0, 1, 1, 1, 0, -1, -1, -1 };

	// 1) 각 파이어볼을 자신의 방향 d로 속력 s칸 만큼 이동
	static void moveFireBalls() {
		List<FireBall>[][] nextFireBallMap = new List[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				nextFireBallMap[i][j] = new ArrayList<>();
			}
		}

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (fireBallMap[i][j].size() == 0)
					continue;

				// [i][j] 칸에 있는 파이어볼들 확인
				for (FireBall fireBall : fireBallMap[i][j]) {
					int distance = fireBall.s % n;		// 속력 s가 매우 큰 경우 대비, % n 연산
					int ny = i + (distance * dy[fireBall.d]);
					int nx = j + (distance * dx[fireBall.d]);
//					int ny = i + (fireBall.s * dy[fireBall.d]);
//					int nx = j + (fireBall.s * dx[fireBall.d]);

					// 다음 칸이 맵을 벗어나는 경우, 끝 행(열)과 첫 행(열) 연결 처리
					if (!isValid(ny, nx)) {
						if (ny > n) ny -= n;
						else if (ny <= 0) ny += n;

						if (nx > n) nx -= n;
						else if (nx <= 0) nx += n;
					}

					nextFireBallMap[ny][nx].add(new FireBall(fireBall.m, fireBall.s, fireBall.d));
				}
			}
		}

		fireBallMap = nextFireBallMap;
	}

	// 2) 각 파이어볼의 이동이 모두 끝난 뒤, 2개 이상의 파이어볼이 있는 칸에 다음 과정 수행
	static void divideFireBalls() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (fireBallMap[i][j].size() < 2)
					continue;

				// 같은 칸에 2개 이상의 파이어볼 존재하는 경우
				// - 같은 칸에 존재하는 파이어볼들의 질량, 속력 합 계산
				int sumM = 0, sumS = 0;
				// 같은 칸에 존재하는 파이어볼들에 대해 홀수, 짝수 방향의 파이어볼 존재 여부 체크
				boolean existDirOdd = false;
				boolean existDirEven = false;

				for (FireBall fireBall : fireBallMap[i][j]) {
					sumM += fireBall.m;
					sumS += fireBall.s;

					if (fireBall.d % 2 == 0) existDirEven = true;
					else existDirOdd = true;
				}

				int m = (int) Math.floor((double) sumM / 5);		// 4개로 나누어진 각 파이어볼의 m, s
				int s = (int) Math.floor((double) sumS / fireBallMap[i][j].size());

				fireBallMap[i][j].clear();		// [i][j] 칸에 있는 중복 위치의 파이어볼들 모두 제거

				// 나누어진 파이어볼의 질량 m == 0 인 경우, 파이어볼을 소멸시킴 (4개 분할 X)
				if (m == 0) {
					continue;
				}

				// 4개로 나누어진 각 파이어볼의 방향을 existDirOdd, existDirEvent 플래그 변수로 정함
				// 나눈 4개 파이어볼을 맵에 추가

				// 합쳐지는 파이어볼의 방향이 모두 짝수 or 모두 홀수인 경우
				if ((existDirEven && !existDirOdd) || (!existDirEven && existDirOdd)) {
					fireBallMap[i][j].add(new FireBall(m, s, 0));
					fireBallMap[i][j].add(new FireBall(m, s, 2));
					fireBallMap[i][j].add(new FireBall(m, s, 4));
					fireBallMap[i][j].add(new FireBall(m, s, 6));
				}
				else {
					fireBallMap[i][j].add(new FireBall(m, s, 1));
					fireBallMap[i][j].add(new FireBall(m, s, 3));
					fireBallMap[i][j].add(new FireBall(m, s, 5));
					fireBallMap[i][j].add(new FireBall(m, s, 7));
				}
			}
		}
	}

	static void solution() {
		moveFireBalls();
		divideFireBalls();
	}

	static boolean isValid(int ny, int nx) {
		return (1 <= ny && ny <= n) && (1 <= nx && nx <= n);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		fireBallMap = new List[n + 1][n + 1];		// [1][1] ~ [n][n] 사용
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				fireBallMap[i][j] = new ArrayList<>();
			}
		}

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			// 파이어볼 위치 (y, x), 질량 m, 속력 s, 방향 d
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());

			fireBallMap[y][x].add(new FireBall(m, s, d));
		}

		for (int i = 0; i < k; i++) {
			solution();
		}

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				for (FireBall fireBall : fireBallMap[i][j]) {
					resultSumMass += fireBall.m;
				}
			}
		}
		System.out.println(resultSumMass);
	}
}

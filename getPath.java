
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * getPath -- получение пути из начальной в конечную точку в лабиринте
 */
public class getPath {
	/**
	 * 
	 * @param map -- статический лабиринт в виде массива map[][]
	 * @param sx -- координата X начальной точки в лабиринте
	 * @param sy -- координата Y начальной точки в лабиринте
	 * @param WALL -- "стена" в лабиринте
	 * @param BLANK -- свободный "путь" в лабиринте (узел, который можно включить в путь)
	 * @return массив значений распространяемой волны волнового алгоритма
	 */

	public static int[][] enumerateMap(int[][] map, int sx, int sy, int fx, int fy, int WALL, int BLANK) {
		int INITIAL_WEIGHT = 1; // начальное значение распространяемой волны "волнового алгоритма"
		int weight; // текущее значение волны
		int H = map.length; // "высота" лабиринта-массива
		int W = map[0].length; // "ширина" лабиринта-массива
		int[][] weights = new int[H][W]; // массив, содержащий все значения распространяемой волны
		// создать очередь для выполнения алгоритма
		Queue<Integer> q = new LinkedList<Integer>();
		// создать копию weights = map (map[][] должен оставаться неизменным)
		System.arraycopy(map, 0, weights, 0, map.length);
		// задать начальное значение начальной точки волны в массиве
		weights[sx][sy] = INITIAL_WEIGHT;
		// установить начальное значение конечной точки в лабиринте,
		// т.к. там сейчас значение -3 (точка Е == -3)
		weights[fx][fy] = 0;
		// добавить координаты начальной точки в очередь
		q.add(sx);
		q.add(sy);

		while (!q.isEmpty()) {
			// взять значение X и Y из очереди (с удалением из очереди)
			int currentX = q.poll();
			int currentY = q.poll();

			weight = weights[currentX][currentY] + 1; // увеличить значение распространяемой волны на единицу
			// выполнить проверки:
			// 1. координата X и Y должны лежать в пределах массива-карты
			// 2. координата X и Y не должны попадать в "стены"
			// добавить координаты узла, прошедшего проверку, в очередь
			if ((currentX - 1 >= 0) & (weights[currentX - 1][currentY] == BLANK)) {
				weights[currentX - 1][currentY] = weight;
				q.add(currentX - 1);
				q.add(currentY);
			}
			if ((currentY + 1 < W - 1) & (weights[currentX][currentY + 1] == BLANK)) {
				weights[currentX][currentY + 1] = weight;
				q.add(currentX);
				q.add(currentY + 1);
			}
			if ((currentX + 1 < H - 1) & (weights[currentX + 1][currentY] == BLANK)) {
				weights[currentX + 1][currentY] = weight;
				q.add(currentX + 1);
				q.add(currentY);
			}
			if ((currentY - 1 >= 0) & (weights[currentX][currentY - 1] == BLANK)) {
				weights[currentX][currentY - 1] = weight;
				q.add(currentX);
				q.add(currentY - 1);
			}
		}
		return weights;
	}

	// найти путь от конечной точки (fx,fy) к начальной точке (sx,sy)
	public static Object[] findPath(int[][] weights, int sx, int sy, int fx, int fy) {
		List<Integer> path = new ArrayList<Integer>();
		int H = weights.length;
		int W = weights[0].length;
		// так будут изменяться координаты x и y (т. е. задаются 4 направления перемещения)
		int[] movesOfX = {-1, 0, 1, 0};
		int[] movesOfY = {0, 1, 0, -1};
		// запись значения длины пути в конечной точке в переменную (т.е. записывается путь максимальной длины)
		int wayLength = weights[fx][fy];
		int x = fx;
		int y = fy;
		// по достижении начальной точки путь станет равен единице
		// (т.к. распространение волны начинается со значения "1")
		while(wayLength > 0) {
			path.add(x);
			path.add(y);
			wayLength--;

			for (int i = 0; i < movesOfX.length; i++) {
				// проверка:
				// 1. координата X и Y должны лежать в пределах массива-карты;
				// 2. путь (wayLength), уменьшенный на единицу, равняется по значению распространяемой волне
				// в проверяемом узле массива (лабиринта) weights[X][Y]
				// добавить координаты, прошедшие проверку, в ArrayList
				if ((x + movesOfX[i] >= 0) && (y + movesOfY[i] >= 0) && 
				    (x + movesOfX[i] < H - 1) && (y + movesOfY[i] < W - 1) && 
				    (weights[x + movesOfX[i]][y + movesOfY[i]] == wayLength))
					{
						// обновить X и Y
					x = x + movesOfX[i];
					y = y + movesOfY[i];
				}
			}
		}
		return path.toArray();
	}
	// напечатать полученный путь в консоль
	public static void printPath(Object[] outputPath, int sx, int sy, int fx, int fy) {	
		System.out.printf("Путь из точки (%d,%d) в точку (%d,%d):\n",sx, sy, fx, fy);	
		String delimiter = "->";
		for (int i = outputPath.length - 1; i >= 0; i -= 2) {
			System.out.printf("(%d,%d)", outputPath[i - 1], outputPath[i]);
			if (i > 2) {
				System.out.print(delimiter);
			}
		}
		System.out.printf("\nДлина пути составила: %d шага", outputPath.length/2 - 1);
	}
	// печатает лабиринт
	public static String mapColor(int[][] map, int sx, int sy, int fx, int fy) {
		map[sx][sy] = -2;
		map[fx][fy] = -3;
		StringBuilder sb = new StringBuilder();
	
		for (int row = 0; row < map.length; row++) {
		  for (int col = 0; col < map[row].length; col++) {
			switch (map[row][col]) {
			  case 0:
				sb.append("░");
				break;
			  case -1:
				sb.append("▓");
				break;
			  case -2:
				sb.append("К");
				break;
			  case -3:
				sb.append("E");
				break;
			  default:
				break;
			}
		  }
		  sb.append("\n");
		}
		for (int i = 0; i < 3; i++) {
		  sb.append("\n");
		}
		return sb.toString();
	  }

	public static void main(String[] args) {
		int[][] map = new int[][] {
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, 00, 00, 00, -1, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, 00, 00, 00, -1, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, -1, 00, 00, -1, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, -1, 00, -1, -1, -1, -1, 00, 00, 00, 00, -1, -1, -1, -1, -1, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, -1, 00, -1, 00, 00, -1, 00, 00, 00, 00, -1, 00, -1, 00, -1, 00, 00, 00, 00, -1 },
				{ -1, -1, -1, 00, -1, 00, -1, 00, 00, -1, 00, 00, 00, 00, -1, 00, -1, 00, -1, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, -1, 00, -1, 00, 00, -1, -1, -1, 00, 00, -1, 00, -1, 00, 00, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, -1, 00, 00, 00, 00, -1, 00, 00, 00, 00, -1, 00, 00, 00, -1, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, -1, 00, 00, 00, 00, -1, 00, 00, 00, 00, -1, 00, 00, 00, -1, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, -1, -1, -1, -1, -1, -1, 00, 00, 00, 00, -1, -1, -1, -1, -1, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, -1, -1, -1, -1, -1, -1, -1, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, -1 },
				{ -1, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }
		};
		//  !заданные начальная и конечная точки, а также сам массив не проверяются!
		// (они могут быть в стене лабиринта, за пределами лабиринта и т.д. и т.п., массив может быть пустым
		// или непроходимым)
		int sx = 1; // координата X начальной точки лабиринта
		int sy = 1; // координата Y начальной точки лабиринта
		int fx = 13; // координата X конечной точки лабиринта
		int fy = 21; // координата Y конечной точки лабиринта
		int WALL = -1; // "стена" в лабиринте
		int BLANK = 0; // свободный "путь" в лабиринте
		int[][] weights; // массив, содержащий результат (все значения) распространения волны волнового лабиринта
		System.out.println(mapColor(map, sx, sy, fx, fy));
		weights = enumerateMap(map, sx, sy, fx, fy, WALL, BLANK);

		// массив, содержащий построенный "путь" в лабиринте от начальной до конечной точки
		Object[] outputPath = findPath(weights, sx, sy, fx, fy);

		// печать найденного пути в консоль
		printPath(outputPath, sx, sy, fx, fy);
	}	
}

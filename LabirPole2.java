//Поле Лабиринта 11 х 10
// package Lee_Algorithm;

public class LabirPole2 extends LabirNumb {
    public static void main(String[] args) {
        System.out.println(mapColor(LabirNumb.getMap()));
        // возращает карту

        int[][] Pole = LabirNumb.getMap();
        int row = 4; // Начальная строка
        int col = 3; // Начальный столбец
        Pole[row][col] = 1;

        int[] Ochered = new int[1];
        int[] temp = new int[1];
        int iNumb;
        int j = -1;
        int iMaxNumb = 100;
        for (int i = 0; i <= iMaxNumb; i++) {
            iNumb = Pole[row][col] + 1;
            if (Pole[row - 1][col] == 0) {
                Pole[row - 1][col] = iNumb;
                j = j + 1;
                temp = new int[Ochered.length + 1];
                System.arraycopy(Ochered, 0, temp, 0, Ochered.length);
                Ochered = temp;
                Ochered[j] = (row - 1) * 1000 + col;
            } else if (Pole[row - 1][col] == -2) {
                row = row - 1;
                break;
            }
            if (Pole[row][col + 1] == 0) {
                Pole[row][col + 1] = iNumb;
                j = j + 1;
                temp = new int[Ochered.length + 1];
                System.arraycopy(Ochered, 0, temp, 0, Ochered.length);
                Ochered = temp;
                Ochered[j] = row * 1000 + (col + 1);
            } else if (Pole[row][col + 1] == -2) {
                col = col + 1;
                break;
            }
            if (Pole[row + 1][col] == 0) {
                Pole[row + 1][col] = iNumb;
                j = j + 1;
                temp = new int[Ochered.length + 1];
                System.arraycopy(Ochered, 0, temp, 0, Ochered.length);
                Ochered = temp;
                Ochered[j] = (row + 1) * 1000 + col;
            } else if (Pole[row + 1][col] == -2) {
                row = row + 1;
                break;
            }
            if (Pole[row][col - 1] == 0) {
                Pole[row][col - 1] = iNumb;
                j = j + 1;
                temp = new int[Ochered.length + 1];
                System.arraycopy(Ochered, 0, temp, 0, Ochered.length);
                Ochered = temp;
                Ochered[j] = row * 1000 + (col - 1);
            } else if (Pole[row][col - 1] == -2) {
                col = col - 1;
                break;
            }
            row = Ochered[0] / 1000;
            col = Ochered[0] % 1000;
            if (Ochered.length > 0) {
                temp = new int[Ochered.length - 1];
                System.arraycopy(Ochered, 1, temp, 0, Ochered.length - 1);
                Ochered = temp;
                j = j - 1;
            }
        }

        int iMax = 0;
        int row2 = 0;
        int col2 = 0;
        int iPole = Pole[row - 1][col];
          if (iPole > 0) {
              if (iPole > iMax){
                  iMax = iPole;
                  row2 = row - 1; col2 = col;
              }
          }
          iPole = Pole[row][col + 1];
          if (iPole > 0){
              if (iPole > iMax){
                  iMax = iPole;
                  row2 = row; col2 = col + 1;
              }
          }
          iPole = Pole[row + 1][col];
          if (iPole > 0){
              if (iPole > iMax){
                  iMax = iPole;
                  row2 = row + 1; col2 = col;
              }
          }
          iPole = Pole[row][col - 1];
          if (iPole > 0){
              if (iPole > iMax){
                  iMax = iPole;
                  row2 = row; col2 = col - 1;
              }
          }
        Pole[row2][col2] = 88;
        row = row2; col = col2;
        for (int i = 0; i <= iMaxNumb; i++) {
            if (iMax == 1) break;
            iMax = iMax - 1;
            if (Pole[row - 1][col] > 0){
                if (Pole[row - 1][col] == iMax){
                    row2 = row - 1; col2 = col;
                }
            }
            if (Pole[row][col + 1] > 0){
                if (Pole[row][col + 1] == iMax){
                    row2 = row; col2 = col + 1;
                }
            }
            if (Pole[row + 1][col] > 0){
                if (Pole[row + 1][col] == iMax) {
                    row2 = row + 1; col2 = col;
                }
            }
            if (Pole[row][col - 1] > 0){
                if (Pole[row][col - 1] == iMax){
                  row2 = row; col2 = col - 1;
                }
            }
            Pole[row2][col2] = 88;
            row = row2; col = col2;
        }
        System.out.println(mapColor(Pole));
    }

    static String mapColor(int[][] map) {
        StringBuilder sb = new StringBuilder();

        String sRowCol = "";
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                sRowCol = Integer.toString(map[row][col]);
                if (map[row][col] > 0 && map[row][col] != 88) {
                    if (sRowCol.length() == 1) {
                        sb.append("░" + sRowCol + "░");
                    } else {
                        sb.append("░" + sRowCol + ""); // "░");
                    }
                } else if(map[row][col] == 88) {
                    sb.append("" + sRowCol + "▄");
                }
                switch (map[row][col]) {
                    case 0:
                        sb.append("░░░"); // проход 176
                        break;
                    case -1:
                        sb.append("███"); // стена 178-219
                        break;
                    case -2:
                        sb.append("Вых"); // буква K 176
                        break;
                    default:
                        break;
                }
            } // col
            sb.append("\n");
        } // row
        for (int i = 0; i < 3; i++) { // доб. внизу интервал в 3 строки
            sb.append("\n");
        }
        return sb.toString();
    }
}

package ua.com.alevel.level1.task1;

public class HorseMove {

    public static boolean isPossibleMove(int x1, int y1, int x2, int y2) {
        if (Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 2)
            return true;
        else if (Math.abs(x2 - x1) == 2 && Math.abs(y2 - y1) == 1)
            return true;
        else
            return false;
    }

    public static void printChessBoard() {
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " |");
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0)
                    System.out.print("0 ");
                else
                    System.out.print("# ");
            }
            System.out.println();
        }
        System.out.println("   - - - - - - - -");
        System.out.print("   1 2 3 4 5 6 7 8");
    }
}

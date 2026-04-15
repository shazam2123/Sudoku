import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SudokuGenerator {
    private static final int SIZE = 9;
    private static final int BOX = 3;

    public static void main(String[] args) {
        int[][] board = new int[SIZE][SIZE];
        ArrayList<Integer> numbers = createNumbers();
        generateSolvedBoard(board, numbers);
        printBoard(board);
    }

    private static ArrayList<Integer> createNumbers() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) numbers.add(i);
        return numbers;
    }

    private static void generateSolvedBoard(int[][] board, ArrayList<Integer> numbers) {
        Random random = new Random();
        Collections.shuffle(numbers, random);

        for (int r = 0; r < SIZE; r++) {
            int shift = (r * BOX + r / BOX) % SIZE;
            for (int c = 0; c < SIZE; c++) {
                board[r][c] = numbers.get((c + shift) % SIZE);
            }
        }

        shuffleRowsWithinBands(board, random);
        shuffleRowBands(board, random);
        shuffleColumnsWithinStacks(board, random);
        shuffleColumnStacks(board, random);
    }

    private static void shuffleRowsWithinBands(int[][] board, Random random) {
        for (int band = 0; band < BOX; band++) {
            int start = band * BOX;
            int a = start + random.nextInt(BOX);
            int b = start + random.nextInt(BOX);
            swapRows(board, a, b);
        }
    }

    private static void shuffleRowBands(int[][] board, Random random) {
        int band1 = random.nextInt(BOX);
        int band2 = random.nextInt(BOX);
        for (int i = 0; i < BOX; i++) swapRows(board, band1 * BOX + i, band2 * BOX + i);
    }

    private static void shuffleColumnsWithinStacks(int[][] board, Random random) {
        for (int stack = 0; stack < BOX; stack++) {
            int start = stack * BOX;
            int a = start + random.nextInt(BOX);
            int b = start + random.nextInt(BOX);
            swapColumns(board, a, b);
        }
    }

    private static void shuffleColumnStacks(int[][] board, Random random) {
        int stack1 = random.nextInt(BOX);
        int stack2 = random.nextInt(BOX);
        for (int i = 0; i < BOX; i++) swapColumns(board, stack1 * BOX + i, stack2 * BOX + i);
    }

    private static void swapRows(int[][] board, int row1, int row2) {
        if (row1 == row2) return;
        for (int c = 0; c < SIZE; c++) {
            int temp = board[row1][c];
            board[row1][c] = board[row2][c];
            board[row2][c] = temp;
        }
    }

    private static void swapColumns(int[][] board, int col1, int col2) {
        if (col1 == col2) return;
        for (int r = 0; r < SIZE; r++) {
            int temp = board[r][col1];
            board[r][col1] = board[r][col2];
            board[r][col2] = temp;
        }
    }

    private static void printBoard(int[][] board) {
        for (int r = 0; r < SIZE; r++) {
            if (r % BOX == 0) System.out.println("|-------|-------|-------|");
            for (int c = 0; c < SIZE; c++) {
                if (c % BOX == 0) System.out.print("| ");
                System.out.print(board[r][c] + " ");
            }
            System.out.println("|");
        }
        System.out.println("|-------|-------|-------|");
    }
}
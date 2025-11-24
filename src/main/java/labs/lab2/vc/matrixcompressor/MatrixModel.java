package labs.lab2.vc.matrixcompressor;

import java.util.Random;

public class MatrixModel {
    public static int[][] generateMatrix(int n) {
        int[][] a = new int[n][n];
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = rnd.nextBoolean() ? rnd.nextInt(-n, n + 1) : 0;
            }
        }
        return a;
    }

    public static int[][] compressMatrix(int[][] a) {
        if (a == null || a.length == 0) return new int[0][0];
        int n = a.length;
        boolean[] keepRow = new boolean[n];
        boolean[] keepCol = new boolean[n];
        int rowsKept = 0, colsKept = 0;

        for (int i = 0; i < n; i++) {
            boolean has = false;
            for (int j = 0; j < n; j++) {
                if (a[i][j] != 0) { has = true; break; }
            }
            keepRow[i] = has;
            if (has) rowsKept++;
        }

        for (int j = 0; j < n; j++) {
            boolean has = false;
            for (int i = 0; i < n; i++) {
                if (a[i][j] != 0) { has = true; break; }
            }
            keepCol[j] = has;
            if (has) colsKept++;
        }

        if (rowsKept == 0 || colsKept == 0) return new int[0][0];

        int[][] b = new int[rowsKept][colsKept];
        int ii = 0;
        for (int i = 0; i < n; i++) {
            if (!keepRow[i]) continue;
            int jj = 0;
            for (int j = 0; j < n; j++) {
                if (!keepCol[j]) continue;
                b[ii][jj++] = a[i][j];
            }
            ii++;
        }
        return b;
    }

    public static String matrixToString(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) return "(empty)";
        StringBuilder sb = new StringBuilder();
        for (int[] row : m) {
            for (int v : row) {
                sb.append(String.format("%4d", v));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

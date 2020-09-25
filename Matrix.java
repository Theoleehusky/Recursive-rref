/**
 * @author Theo Lee
 * September 23d 2020
 * Ms. Khan
 * 
 * Recursive Reduced Row Echelon Form Calculator
 * Solves linear equations, inverses matrices
 */
public class Matrix {
    /**
     * @param matrix Matrix is filled.
     * @return Reduced Row Echelon Form.
     */
    public static double[][] rref(double[][] matrix) {
        return backTrack(solve(matrix), 0);
    }
    /**
     * Looks for a pivot in the first column.
     * If there is none, calls solve() on the matrix without the first column.
     * 
     * If there is a pivot, divides the row by the pivot and reduces all other values in the same column to zero.
     * Calls solve() on the matrix without the first column and the row with the pivot.
     * 
     * @param matrix Matrix is filled.
     * @return Matrix in Row Echelon Form.
     */
    private static double[][] solve(double[][] matrix) {
        /**
         * Base case: Matrix is empty.
         */
        if (matrix.length == 0 || matrix[0].length == 0)
            return matrix;
        
        int rows = matrix.length;
        int columns = matrix[0].length;
        
        int row = 0;
        double pivot = 0;
        for (int r = 0; r < rows; r++)
            if (matrix[r][0] != 0) {
                row = r;
                pivot = matrix[r][0];
                break;
            }
        
        if (pivot == 0)
            return joinRectangle(solve(nextRectangle(matrix)));
        
        for (int c = 0; c < columns; c++)
            matrix[row][c] /= pivot;
        for (int r = 0; r < rows; r++)
            if (matrix[r][0] != 0 && r != row)
                matrix[r] = add(matrix[r], matrix[row], -matrix[r][0]);
        return joinSquare(solve(nextSquare(matrix, row)), matrix[row]);
    }
    /**
     * @param matrix in Row Echelon Form.
     * @param row Index of row with pivot.
     * @return Reduced Row Echelon Form.
     */
    private static double[][] backTrack(double[][] matrix, int row) {
        int columns = matrix[0].length;
        
        /**
         * Base case: Row is greater than number of rows in matrix.
         */
        if (row == matrix.length)
            return matrix;
        
        matrix = joinRectangle(matrix, backTrack(nextRectangle(matrix), row+1));
        
        int column = -1;
        for (int c = 0; c < columns; c++)
            if (matrix[row][c] != 0) {
                column = c;
                break;
            }
        
        if (column == -1)
            return matrix;
        
        for (int r = 0; r < row; r++)
            matrix[r] = add(matrix[r], matrix[row], -matrix[r][column]);
        
        return matrix;
    }
    /**
     * @param matrix
     * @return Matrix without the first column.
     */
    private static double[][] nextRectangle(double[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        
        double[][] ans = new double[rows][columns-1];
        for (int r = 0; r < rows; r++)
            for (int c = 1; c < columns; c++)
                ans[r][c-1] = matrix[r][c];
        return ans;
    }
    /**
     * @param matrix
     * @return Adds a column of zeros to the matrix.
     */
    private static double[][] joinRectangle(double[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length+1;
        double[][] ans = new double[rows][columns];
        
        for (int r = 0; r < rows; r++)
            for (int c = 1; c < columns; c++)
               ans[r][c] = matrix[r][c-1];

        return ans;
    }
    /**
     * @param matrix
     * @param replace
     * @return 'Replace' matrix with first column of 'matrix' added in the front.
     */
    private static double[][] joinRectangle(double[][] matrix, double[][] replace) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        
        for (int r = 0; r < rows; r++)
            for (int c = 1; c < columns; c++)
               matrix[r][c] = replace[r][c-1];

        return matrix;
    }
    /**
     * @param to Row to be added to.
     * @param from Row to be added from.
     * @param multiple Number of additions.
     * @return Adds row 'from' to row 'to' 'multiple' times.
     */
    private static double[] add(double[] to, double[] from, double multiple) {
        for (int c = 0; c < to.length; c++)
            to[c] += from[c] * multiple;
        return to;
    }
    /**
     * @param matrix
     * @param skip Row to skip.
     * @return Removes first column and row in index 'skip'.
     */
    private static double[][] nextSquare(double[][] matrix, int skip) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int row = 0;
        double[][] ans = new double[rows-1][columns-1];
        
        for (int r = 0; r < rows; r++, row++) {
            if (skip == r) {
                row--;
                continue;
            }
            for (int c = 1; c < columns; c++)
                ans[row][c-1] = matrix[r][c];
        }
        
        return ans;
    }
    /**
     * @param matrix
     * @param row
     * @return Joins the matrix with a column of zeros and the 'row' row.
     */
    private static double[][] joinSquare(double[][] matrix, double[] row) {
        int rows = 1+matrix.length;
        int columns = row.length;
        double[][] ans = new double[rows][columns];
        
        ans[0] = row;
        for (int i = 1; i < rows; i++)
            for (int j = 1; j < columns; j++)
               ans[i][j] = matrix[i-1][j-1];
        
        return ans;
    }
}

import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

public class Board {
    private int[][] board;
    private int dimension;
    public Board(int[][] blocks) {           // construct a board from an n-by-n array of blocks
        dimension = blocks.length;
        board = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
            board[i][j] = blocks[i][j];
            }
        }
    }                                           // (where blocks[i][j] = block in row i, column j
    public int dimension() {                 // board dimension n
        return dimension;
    }
    public int hamming() {                 // number of blocks out of place
        int sum = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == dimension - 1 && j == dimension - 1) continue;
                if (board[i][j] != dimension  * (i) + j + 1) sum++;
            }
        } 
        return sum;
    }
    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int sum = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                int block = board[i][j];
                if (block == 0) continue;
                int blockrow = block / dimension;
                int blockcolumn = block % dimension - 1;
                if (blockcolumn == -1) {
                    blockrow--;
                    blockcolumn = dimension - 1; 
                }
                if(block == 1) {
                    blockrow = 0;
                    blockcolumn = 0;
                }
                int manhattan = Math.abs(blockrow - i) + Math.abs(blockcolumn - j);
                sum = sum + manhattan;
            }
        }
        return sum;
    }
    public boolean isGoal() {                // is this board the goal board?
        return (hamming() == 0);
    }
    public Board twin() {                    // a board that is obtained by exchanging any pair of blocks
        int[][] blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                blocks[i][j] = board[i][j];
            }
        }
        int temp = blocks[0][0];
        if (temp == 0) {
            temp = blocks[0][1];
            blocks[0][1] = blocks[1][1];
            blocks[1][1] = temp;
        }
        else {
            if(blocks[1][1] == 0) {
                blocks[0][0] = blocks[1][0];
                blocks[1][0] = temp;
            }
            else {
                blocks[0][0] = blocks[1][1];
                blocks[1][1] = temp;
            }         
        }
    return new Board(blocks);
    }
    public boolean equals(Object y) {        // does this board equal y?
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        if (this.hamming() != that.hamming() || this.manhattan() != that.manhattan()) return false;
        if (this.twin().hamming() != that.twin().hamming() || this.twin().manhattan() != that.twin().manhattan()) return false;
        return true;
    }
    public Iterable<Board> neighbors() {    // all neighboring boards
        Stack<Board> neighbor = new Stack<Board>();
        int[][] blocks = new int[dimension][dimension];
        int row = 0;
        int col = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                blocks[i][j] = board[i][j];
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }
        if (row != 0){
           int temp = blocks[row][col];
           blocks[row][col] = blocks[row -1][col];
           blocks[row -1][col] = temp;
           neighbor.push(new Board(blocks));
           temp = blocks[row][col];
           blocks[row][col] = blocks[row -1][col];
           blocks[row -1][col] = temp;
        }
        if (row != dimension - 1) {
            int temp = blocks[row][col];
            blocks[row][col] = blocks[row + 1][col];
            blocks[row + 1][col] = temp;
            neighbor.push(new Board(blocks));
            temp = blocks[row][col];
            blocks[row][col] = blocks[row + 1][col];
            blocks[row + 1][col] = temp;  
        }
        if (col != 0){
           int temp = blocks[row][col];
           blocks[row][col] = blocks[row][col - 1];
           blocks[row][col - 1] = temp;
           neighbor.push(new Board(blocks));
           temp = blocks[row][col];
           blocks[row][col] = blocks[row][col - 1];
           blocks[row][col - 1] = temp;
        }
        if (col != dimension - 1) {
            int temp = blocks[row][col];
            blocks[row][col] = blocks[row][col + 1];
            blocks[row][col + 1] = temp;
            neighbor.push(new Board(blocks));
            temp = blocks[row][col];
            blocks[row][col] = blocks[row][col + 1];
            blocks[row][col + 1] = temp;  
        }  
        return neighbor;
        
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) { // unit tests (not graded)
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        int[][] blocks1 = new int[n][n];
        int[][] blocks2 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int a = in.readInt();
                blocks[i][j] = a;
                blocks1[i][j] = a;
                blocks2[i][j] = a;
                if (a == 3) blocks2[i][j] = 4;
                if (a == 4) blocks2[i][j] = 3;
                    
            }
        }
        Board initial = new Board(blocks);
        Board initial1 = new Board(blocks1);
        Board initial2 = new Board(blocks2);
        
        
        
        
        StdOut.println(initial);
        for (Board board : initial.neighbors())
            StdOut.println(board);
        StdOut.println(initial.twin());
        Board twin = initial.twin();
        StdOut.println(initial.manhattan());
        StdOut.println(initial.hamming());
        StdOut.println(initial.isGoal());
        StdOut.println(initial.dimension());
        
        StdOut.println(initial.equals(initial));
        StdOut.println(initial.equals(initial1));
        StdOut.println(initial.equals(initial2));
        
        StdOut.println(initial.equals(twin));
        StdOut.println(initial2.equals(initial1));
        
    }
}
      
import edu.princeton.cs.algs4.WeightedQuickUnionUF; 
public class Percolation {
    private WeightedQuickUnionUF w;
    private int opensites = 0;
    private boolean percolate;
    private boolean[][] grid;
    private WeightedQuickUnionUF backwash;
    private int n;
    public Percolation(int size){
        // create n-by-n grid, with all sites blocked
        if (size <= 0) throw new IllegalArgumentException("n is out of bounds");
        n = size;
        w = new WeightedQuickUnionUF(n*n + 2);
        backwash = new WeightedQuickUnionUF(n + 1);
        grid = new boolean[n][n];
    }     
    public void open(int row, int col) {
        // open site (row, col) if it is not open already 
        if (row <= 0 || row > n) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col > n) throw new IndexOutOfBoundsException("col index i out of bounds");

        if ( ! isOpen(row,col) ){
            grid[row-1][col-1] = true;
            opensites = opensites+1;
            if( col+1 <= n && isOpen(row,col+1) ) w.union((row-1)*n+col , (row-1)*n+col+1 );
            if( col-1 >= 1 && isOpen(row,col-1) ) w.union((row-1)*n+col , (row-1)*n+col-1 );
            if( row+1 <= n && isOpen(row+1,col) ) w.union((row-1)*n+col , (row)*n + col );
            if( row-1 >= 1 && isOpen(row-1,col) ) w.union((row-1)*n+col , (row-2)*n+ col );
            if( row == n){
                if(row == 1) backwash.union(col,0);
                w.union((n-1) * n + col, n*n+1);
                if( row-1 >= 1 && isFull(row-1,col) ) backwash.union(col, 0);
                if( col+1 <= n && isOpen(row,col+1) ) backwash.union(col, col+1 );
                if( col-1 >= 1 && isOpen(row,col-1) ) backwash.union(col, col-1 );
            }
            if( row == 1) w.union(col, 0);
            if( w.connected( 0, n*n+1) ) percolate = true;              
        }
        
    }
    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        if (row <= 0 || row > n) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col > n) throw new IndexOutOfBoundsException("col index i out of bounds");
        else return grid[row-1][col-1];
    }
    public boolean isFull(int row, int col){
        // is site (row, col) full?
        if (row <= 0 || row > n) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col > n) throw new IndexOutOfBoundsException("col index i out of bounds");
        if (row == n) {
            return backwash.connected(col, 0);
        }
        return (isOpen(row,col) && w.find(0) == w.find((row-1)*n+col ));
    }
    public     int numberOfOpenSites() {
        // number of open sites
        return opensites;
    }
    public boolean percolates() {
        // does the system percolate?
        return percolate;
    }
    public static void main(String[] args) {

    }
}
    
    
    
    
    
    
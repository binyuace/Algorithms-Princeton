

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;
import java.lang.NullPointerException;

public class Solver {
    private MinPQ<Node> solve;
    private int move;
    private LinkedList<Node> solution = new LinkedList<Node>();
    private MinPQ<Node> unsolve;
    private boolean solveable;
    private class Node{
        private Board thisboard;
        private Node prevboard;
        private int moves;
        public Node(Board board, Node board1, int m) {
            thisboard = board;
            prevboard = board1;
            moves = m;
        }
            public int ham() {
               return thisboard.hamming() + this.moves;
        }
            public int man(){
                return thisboard.manhattan() + thisboard.hamming() + *this.moves;
            }
    }
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new NullPointerException();
        Node initialnode = new Node(initial, null, 0);
        Board twin = initial.twin();
        Node uninitialnode = new Node(twin, null, 0);
        solve = new MinPQ<Node>( (a,b) -> a.man() - b.man());
        unsolve =  new MinPQ<Node>( (a,b) -> a.man() - b.man());
        solve.insert(initialnode);
        unsolve.insert(uninitialnode);
        while (true) {
            Node del = solve.delMin();
            Node undel = unsolve.delMin();
           
            if (del.thisboard.isGoal()) {
                solution.add(del);
                solveable = true;
                move = del.moves;
                break;
            }
            if (undel.thisboard.isGoal()) {
                solution = null;
                solveable = false;
                break;
            }
            for (Board board : del.thisboard.neighbors()) {
                
                Node newnode = new Node(board, del, del.moves + 1);
                if ( del.moves == 0 || ! board.equals(del.prevboard.thisboard)) solve.insert(newnode);
//                StdOut.println(del.thisboard);
            }
            for (Board unboard : undel.thisboard.neighbors()) {
                
                Node unnewnode = new Node(unboard, undel, undel.moves + 1);
                if ( undel.moves == 0 || ! unboard.equals(undel.prevboard.thisboard)) unsolve.insert(unnewnode);    
            }
            

            solution.add(del);
//            StdOut.println(del.thisboard);
        }
    }
    public boolean isSolvable() {           // is the initial board solvable?
        return solveable;
    }
    public int moves() {                   // min number of moves to solve initial board; -1 if unsolvable
        return move;
    }
    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
        LinkedList<Board> newsol = new LinkedList<Board>();
        Node sol = solution.getLast();
        newsol.add(sol.thisboard);
        while(sol.moves != 0){
            sol = sol.prevboard;
            newsol.addFirst(sol.thisboard);
        }
        return newsol;
    }
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
            StdOut.println("Minimum number of moves = " + solver.moves());
        }
    }                                             
}

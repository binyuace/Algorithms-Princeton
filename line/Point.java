/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if(this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        if(this.x == that.x) return Double.POSITIVE_INFINITY;
        if(this.y == that.y) return 0.0;
        return ( (that.y - this.y + 0.0) / (that.x - this.x));
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if( this.y == that.y){
            if(this.x > that.x) return 1;
            if(this.x == that.x){return 0; }
            return -1;
        }
        if(this.y > that.y) { return 1;}
        return -1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        
        return new Slopeorder();
       
    }
    private class Slopeorder implements Comparator<Point> {
        private double slopeto(Point that) {
        /* YOUR CODE HERE */
                 if(x == that.x && y == that.y) return Double.NEGATIVE_INFINITY;
                 if(x == that.x) return Double.POSITIVE_INFINITY;
                 if(y == that.y) return 0.0;
                 return ( (that.y - y + 0.0) / (that.x - x));
             }
        public int compare(Point q1, Point q2) {
            double slope1 = this.slopeto(q1);
            double slope2 = this.slopeto(q2);

//            if (this.x == q1.x && this.y == q1.y) slope1 =  Double.NEGATIVE_INFINITY;
//            else if (this.x == q1.x) slope1 = Double.POSITIVE_INFINITY;
//            else if (this.y == q1.y) slope1 =  0.0;
//            else slope1 =  ( (q1.y - this.y + 0.0) / (q1.x - this.x));
//            if (this.x == q2.x && this.y == q2.y) slope2 Double.NEGATIVE_INFINITY;
//            else if (this.x == q2.x) slope2 = Double.POSITIVE_INFINITY;
//            else if (this.y == q2.y) slope2 =  0.0;
//            else slope2 =  ( (q2.y - this.y + 0.0) / (q2.x - this.x));
//                
            if ( slope1 > slope2) return 1;
            if ( slope1 == slope2) return 0;
            else return -1;
            // Note: ccw() recomputes dx1, dy1, dx2, and dy2
        
        }
    }



    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point o = new Point(0, 0);
        Point a = new Point(0, 1);
        Point v = new Point(0, 1);
        Point b = new Point(1, 1);
        Point c = new Point(1, 0);
        
        LineSegment seg = new LineSegment(a,b);
        LineSegment seg1 = new LineSegment(b,c);
        LineSegment seg12 = new LineSegment(b,c);
        LineSegment seg2 = new LineSegment(b,a);
        StdOut.println(a .compareTo(v));
        StdOut.println(seg == seg2);
        StdOut.println(seg1 == seg2);
        StdOut.println(seg1 == seg12);
//        StdOut.println(a.slopeOrder().compare(b,b)+""+seg == seg1+" "+seg == seg2 +" " +seg1 == seg2);
        Point[] list = new Point[4];
        list[0] = o;
        list[1] = a;
        list[2] = b;
        list[3] = c;
        Arrays.sort(list, o.slopeOrder()  );
        StdOut.println( list [0] +""+list[1]+""+list[2]);    
    }
}

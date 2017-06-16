
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
public class BruteCollinearPoints {   
    private int count = 0;
    private LineSegment[] s;
    private int size = 10;
    private boolean isCollinear(Point a, Point b, Point c, Point d){
        return ( a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(c) == a.slopeTo(d) );
    }
    private LineSegment segment(Point a, Point b, Point c, Point d){
        if ( a == null || b == null || c == null || d == null) throw new NullPointerException();
        Point[] po = new Point[4];
        po[0] = a;
        po[1] = b;
        po[2] = c;
        po[3] = d;
        Arrays.sort(po);
        LineSegment temp = new LineSegment(po[0],po[3]);    
        return temp;
    }
    private void resize(){
        LineSegment[] news = new LineSegment[size*2];
        for(int i = 0; i < count; i++){
            news[i] = s[i];   
        }
        size = size*2;
        s = news;
    }
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        int n = points.length;
        
        for (int i = 0; i < n -1; i++) {
            for (int j = i+1; j < n; j++ ) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
        if (n < 4) return;
        s = new LineSegment[n];
        for (int i = 0; i < n-3; i++) {
            for (int j = i+1; j < n-2; j++ ) {
                for (int k = j+1; k < n-1; k++) {
                    for (int l = k+1; l < n; l++) {
                        Point a = points[i];
                        Point b = points[j];
                        Point c = points[k];
                        Point d = points[l];
                        if(isCollinear(a, b, c, d) ) { 
                            count++;
                            if(count == size){
                                resize();
                            }
                            s[count-1] = segment(a, b, c, d);
                        }
                    }
                }
            }
        }
    }
        
    
        
    public           int numberOfSegments() {
        return count;
    
    }
            
    public LineSegment[] segments() {
        
        LineSegment[] temp = new LineSegment[count];  
        for (int i = 0; i < count; i++) {
            temp[i] = s[i];
        }
        return temp;
    }
    public static void main(String[] args) {

    // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}




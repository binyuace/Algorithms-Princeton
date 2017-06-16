
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
    
    private int count = 0;
    private LineSegment[] s = new LineSegment[10];
    private int size = 10;

    private void resize() {
        LineSegment[] news = new LineSegment[size*2];
        
        for (int i = 0; i < count; i++) { 
            news[i] = s[i]; 
           
        }
        size = size*2;
        s = news;
 
    }

    private void findsegments(Point[] points, Point origion) {
        int i = 1;
        int j = 2;
        int n = points.length;
        while (i < n-1) {
////            StdOut.println("po[i]"+points[i]); 
            while (j < n  && origion.slopeTo(points[i]) == origion.slopeTo(points[j])) {
//                StdOut.println("po[j]"+points[j]);            ///delete
                j++;
            }
//            StdOut.println();                                  ///delete
            if (j - i >= 3) {
                Point[] po = new Point[ j - i + 1 ];
                po[0] = origion;
                int a = 1;
                for (int t = i; t < j; t++) {
                    po[a] = points[t];    
                    a++;
                }
                assert po.length == j - i +1;
                Arrays.sort(po);
//                StdOut.println(origion+""+po[po.length-1]);
                if(origion.compareTo( po[0] ) == 0) {
//                    StdOut.println(po[0]);
                    LineSegment temp = new LineSegment(origion, po[po.length-1]);    
                    s[count] =  temp;
                    count++;
                    if (count == size) resize();    
              
                }
                
            }
            i = j;
            j++;
        }

    }
        

    private void sortpoints(Point[] points, Point origion) {
        int n = points.length;
        Point[] temp = new Point[n]; 
        for (int i = 0; i < n; i++) {
        temp[i] = points[i];
        }
        Arrays.sort(temp, origion.slopeOrder()); 
        findsegments(temp, origion);   
    }
    
    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        int pointnumber = points.length;
        if (pointnumber == 0) throw new NullPointerException("NO POINTS");
        for (int i = 0; i < pointnumber; i++) {
            if (points[i] == null) throw new NullPointerException("Null point");
        }
        for (int i = 0; i < pointnumber-1; i++) {
            for (int j = i+1; j < pointnumber; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
       
        for (int i = 0; i < pointnumber; i++) {
            Point origion = points[i];
//            StdOut.println(origion);
            sortpoints(points, origion);
        }
    
        
    }
    public           int numberOfSegments() {        // the number of line segments
    
        return count;
    }
    public LineSegment[] segments() {                // the line segments
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println(collinear.numberOfSegments());
        StdDraw.show();
    }
}
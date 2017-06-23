import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
public class PointSET {
    private SET<Point2D> pointset;
    public         PointSET() {                              // construct an empty set of points 
        pointset = new SET<Point2D>();
        
    }
    public           boolean isEmpty() {                      // is the set empty? 
        return pointset.isEmpty();
    }
    public               int size() {                        // number of points in the set 
        return pointset.size();
    }
    public              void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException();
        pointset.add(p);
    }
    public           boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) throw new NullPointerException();
        return pointset.contains(p);
    }
    public              void draw() {                        // draw all points to standard draw     
        StdDraw.setPenRadius(0.02);
        for (Point2D points : pointset) {
            points.draw();
        }
        
        StdDraw.show();
    }
    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle 
        if (rect == null) throw new NullPointerException();
        SET<Point2D> ans = new SET<Point2D>();
        for (Point2D points : pointset) {
            if (rect.contains(points)) { 
                ans.add(points);
            }
        }   
        return ans;
    }
    
    public           Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new NullPointerException();
        double min = Double.POSITIVE_INFINITY;
        Point2D minpoint = null;
        for (Point2D points : pointset) {
            double distance = p.distanceSquaredTo(points);
            if ( distance < min) {
                min = distance;
                minpoint = points;
            }
        }
        return minpoint;
    }
    
    public static void main(String[] args) {                 // unit testing of the methods (optional) 
//        Point2D a = new Point2D(0 , 0);
//        Point2D b = new Point2D(0 , 1);
//        Point2D c = new Point2D(1 , 0);
//        Point2D d = new Point2D(1 , 1);
//        PointSET set =  new PointSET();
//        set.insert(a);
//        set.insert(b);
//        set.insert(c);
//        set.insert(d);
////        for (Point2D points : set) {
////            System.out.println(points);
////        }
//        int m = set.size();
//        boolean n = set.isEmpty();
//        
//        System.out.println(m + "" + n);
//        Point2D e = new Point2D(0.9 , 0.9);
//        Point2D f = set.nearest(e);
//        System.out.println(f);
//        Point2D g = new Point2D(1 , 1);
//        System.out.println(set.contains(e)+""+set.contains(g) ); 
////        set.draw();
//        RectHV rec = new RectHV( 0.5, - 0.5, 1.5, 1.5);
//        Iterable<Point2D> newset = set.range(rec);
//        for(Point2D points : newset) {
//            System.out.println(points);
//        }
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(-2, 2);
//        StdDraw.setYscale(-2, 2);
//        
//        rec.draw();
//        set.draw();
//        
        
        
    }
}
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
public class KdTree {
    private int count;
    private Node root;
    private static final boolean x = true;
    private static final boolean y = true;
    private SET<Point2D> ans;
    private static class Node {
        private Point2D point;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree
        
        public Node(Point2D p, RectHV rect) {
            this.point = p;
            this.rect = rect;
        }
    }
    
    public         KdTree() {                              // construct an empty set of points 
        count = 0;
        
    }
    public           boolean isEmpty() {                      // is the set empty? 
        return count == 0;
    }
    public               int size() {                        // number of points in the set 
        return count;
    }
    public              void insert(Point2D p) {
        if (p == null) throw new NullPointerException("first argument to inseart() is null");
        
        RectHV rect =  new RectHV(0.0, 0.0, 1.0, 1.0);
        root = put(root, p, x, rect);
        count++;
        
    }
    
    
    private Node put(Node h, Point2D p, boolean orient, RectHV rect) {              // add the point to the set (if it is not already in the set)
        if (h == null) return new Node(p, rect);
        double cmp;
        if (orient) {
            cmp = p.x() - h.point.x();
            
            if      (cmp < 0) {
                RectHV newrect = new RectHV(rect.xmin(), rect.ymin(), h.point.x(), rect.ymax());
                h.left  = put(h.left,  p, !orient, newrect); 
            }
            
            else if (cmp > 0) {
                RectHV newrect = new RectHV(h.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                h.right = put(h.right, p, !orient, newrect);
            }
            else if (cmp == 0 && p.y() != h.point.y()) {
                RectHV newrect = new RectHV(h.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                h.right = put(h.right, p, !orient, newrect);
            }
            else count--;
        }
        if (!orient) {
            cmp = p.y() - h.point.y();
            if      (cmp < 0) {
                RectHV newrect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), h.point.y());
                h.left  = put(h.left,  p, !orient, newrect); 
            }
            
            else if (cmp > 0) {
                RectHV newrect = new RectHV(rect.xmin(), h.point.y(), rect.xmax(), rect.ymax());
                h.right = put(h.right, p, !orient, newrect);
            }
            else if (cmp == 0 && p.x() != h.point.x()) {
                RectHV newrect = new RectHV(rect.xmin(), h.point.y(), rect.xmax(), rect.ymax());
                h.right = put(h.right, p, !orient, newrect);
            }
            else count--;
            
        }
        
        return h;
    }
    
    public           boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) throw new NullPointerException();
        if (root == null) return false;
        return contains(root, p, x);
    }
    private boolean contains(Node h, Point2D p, boolean orient) {
        if (h == null) return false;
        double cmp;
        if (orient) cmp = p.x() - h.point.x();
        else cmp = p.y() - h.point.y();
        if      (cmp < 0) return contains(h.left,  p, !orient); 
        else if (cmp > 0) return contains(h.right, p, !orient); 
        else if (p.x() == h.point.x() && p.y() == h.point.y()) return true;
        else return contains(h.right,  p, !orient);
    }
    public              void draw() {                        // draw all points to standard draw     
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        RectHV newrect = new RectHV(root.point.x(), 0.0, root.point.x(), 1.0); 
        newrect.draw();
        
        draw(root.left, root.right, x);
        StdDraw.show();
    }
    private void draw(Node left, Node right, boolean orient) {
        if (left != null) {
            if (orient) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.01);
                RectHV newrect = new RectHV(left.rect.xmin(), left.point.y(), left.rect.xmax(), left.point.y()); 
                newrect.draw();
            }
            if (!orient) {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                RectHV newrect = new RectHV( left.point.x(), left.rect.ymin(), left.point.x(), left.rect.ymax() );
                newrect.draw();    
            }
            draw(left.left, left.right, !orient);
        }
        if (right != null) {
            if (orient) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.01);
                RectHV newrect = new RectHV(right.rect.xmin(), right.point.y(), right.rect.xmax(), right.point.y()); 
                newrect.draw();
            }
            if (!orient) {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                RectHV newrect = new RectHV( right.point.x(), right.rect.ymin(), right.point.x(), right.rect.ymax() );
                newrect.draw();    
            }
            draw(right.left, right.right, !orient);
        }
        
    }
    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle 
        ans = new SET<Point2D>();
        if (rect == null) throw new NullPointerException();
        if (root != null && rect.contains(root.point)) ans.add(root.point);
        if (root != null) search(root, rect);
        return ans;
    }
    private void search(Node subtree, RectHV rect) {
        if (subtree.left != null && subtree.left.rect.intersects(rect)) {
            if (rect.contains(subtree.left.point)) ans.add(subtree.left.point);
            search(subtree.left, rect);
        }
        if (subtree.right != null && subtree.right.rect.intersects(rect)) {
            if (rect.contains(subtree.right.point)) ans.add(subtree.right.point);
            search(subtree.right, rect);
        }
        
    }
    private double min;
    private Point2D minpoint;
    public           Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new NullPointerException();
        min = Double.POSITIVE_INFINITY;
        minpoint = null;
        find(p, root, x);
        
        return minpoint;  
    }
    private void find(Point2D p, Node subtree, boolean orient) {
        if (subtree != null) {
            if (subtree.rect.contains(p) || subtree.rect.distanceSquaredTo(p) < min) {
                double temp = subtree.point.distanceSquaredTo(p);
                if (temp < min) {
                    min = temp;
                    minpoint = subtree.point;
                }
                double cmp;
                if (orient) cmp = p.x() - subtree.point.x();
                else cmp = p.y() - subtree.point.y();
                if      (cmp < 0) {
                    find(p, subtree.left, !orient); 
                    find(p, subtree.right, !orient);
                }
                else if (cmp >= 0) {
                    find(p, subtree.right, !orient);
                    find(p, subtree.left, !orient);
                }
            }
        }    
       
        
    }
    
    
    
    public static void main(String[] args) {                 // unit testing of the methods (optional) 
//        Point2D a = new Point2D(0.7 , 0.7);
//        Point2D b = new Point2D(0.6 , 0.6);
//        Point2D c = new Point2D(0.5 , 0.5);
//        Point2D d = new Point2D(0.8 , 0.9);
//        KdTree set =  new KdTree();
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
//        System.out.println(m + " " + n);
//        Point2D e = new Point2D(0.9 , 0.9);
////        Point2D f = set.nearest(e);
////        System.out.println("f = "+f);
//        Point2D g = new Point2D(1 , 1);
//        System.out.println(set.contains(e)+" "+set.contains(g) ); 
////        set.draw();
//        RectHV rec = new RectHV( 0.5, 0.5, 0.8, 0.8);
//        Iterable<Point2D> newset = set.range(rec);
//        for(Point2D points : newset) {
//            System.out.println(points);
//        }
//        StdDraw.enableDoubleBuffering();
////        StdDraw.setXscale(-2, 2);
////        StdDraw.setYscale(-2, 2);
//        
//        rec.draw();
//        set.draw();
        
        
        
    }
}

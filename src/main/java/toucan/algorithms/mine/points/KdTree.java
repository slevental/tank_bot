package toucan.algorithms.mine.points;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Entry head;
    private int size;

    private static final double XMIN = 0;
    private static final double XMAX = 1;

    private static final double YMIN = 0;
    private static final double YMAX = 1;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!contains(p)) size++;
        head = insert(head, p, true);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(head, p);
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(final RectHV rect) {
        List<Point2D> res = new ArrayList<Point2D>();
        range(res, head, rect);
        return res;
    }

    private void range(List<Point2D> res, Entry node, RectHV rect) {
        if (node == null)                     { return; }
        if (rect.contains(node.val))          { res.add(node.val); }
        if (leftRect(node).intersects(rect))  { range(res, node.left,  rect); }
        if (rightRect(node).intersects(rect)) { range(res, node.right, rect); }
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        Entry nearest = nearest(head, null, p.x(), p.y());
        return nearest == null ? null : nearest.val;
    }

    private RectHV leftRect(Entry node){
        double xmax = node.vertical ? node.val.x() : XMAX;
        double ymax = node.vertical ? YMAX         : node.val.y();
        return new RectHV(XMIN, YMIN, xmax, ymax);
    }

    private RectHV rightRect(Entry node){
        double xmin = node.vertical ? node.val.x() : XMIN;
        double ymin = node.vertical ? YMIN         : node.val.y();
        return new RectHV(xmin, ymin, XMAX, YMAX);
    }

    private Entry nearest(Entry node, Entry nearest, double x, double y) {
        if (node == null)                     { return nearest; }
        if (closerPoint(node, nearest, x, y)) { nearest = node; }

        if (cmp(node, x,y) > 0)                { nearest = nearest(node.left,  nearest, x, y); }
        if (cmp(node, x,y) < 0)                { nearest = nearest(node.right, nearest, x, y); }
        if (closerRect (node, nearest, x, y))  { nearest = nearest(node.left,  nearest, x, y); }
        if (closerRect (node, nearest, x, y))  { nearest = nearest(node.right, nearest, x, y); }

        return nearest;
    }



    private boolean closerRect(Entry node, Entry nearest, double x, double y) {
        double min = distance(nearest.val.x(), nearest.val.y(), x, y);
        return min > (node.vertical ? Math.abs(node.val.x() - x) : Math.abs(node.val.y() - y));
    }

    private boolean closerPoint(Entry node, Entry nearest, double x, double y) {
//        System.out.println("Min = " + distance(nearest.val.x(), nearest.val.y(), x, y));
        return nearest == null || distance(nearest.val.x(), nearest.val.y(), x, y) >
                                  distance(node.val.x(), node.val.y(), x, y);
    }

    private double distance(double x1, double y1, double x2, double y2){
//        System.out.println(String.format("CMP <%s,%s>, <%s,%s>",x1,y1,x2,y2));
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dy * dy + dx * dx);
    }

    // draw all of the points to standard draw
    public void draw() {

    }

    private double cmp(Entry node, Point2D val) {
        return cmp(node, val.x(), val.y());
    }

    private double cmp(Entry node, double x, double y) {
        double dx = node.val.x() - x;
        double dy = node.val.y() - y;

        if      (node.vertical  && dx != 0) { return dx; }
        else if (!node.vertical && dy != 0) { return dy; }
        else if (node.vertical)             { return dy; }
        else                                { return dx; }
    }

    private Entry insert(Entry node, Point2D val, boolean vertical) {
        if      (node == null)       { return new Entry(vertical, val); }

        double cmp = cmp(node, val);
        if      (cmp > 0) { node.left  = insert(node.left,  val, !vertical); }
        else if (cmp < 0) { node.right = insert(node.right, val, !vertical); }
        else              { node.val   = val; }

        return node;
    }

    private boolean contains(Entry node, Point2D val) {
        while (node != null){
            double cmp = cmp(node, val);
            if      (cmp > 0) { node = node.left;  }
            else if (cmp < 0) { node = node.right; }
            else              { return true; }
        }
        return false;
    }

    private static class Entry {
        Entry left;
        Entry right;
        Point2D val;
        boolean vertical;

        public Entry(boolean vertical, Point2D v) {
            this.vertical = vertical;
            this.val = v;
        }
    }
}
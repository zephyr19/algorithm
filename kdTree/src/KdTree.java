import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static class Node implements Comparable<Node> {
        Point2D point;
        boolean isVertical;
        Node left;
        Node right;

        private Node(Point2D point, boolean isVertical, Node left, Node right) {
            this.point = point;
            this.isVertical = isVertical;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node o) {
            o.isVertical = !isVertical;
            if (isVertical) {
                if (point.x() > o.point.x()) return +1;
                if (point.x() < o.point.x()) return -1;
            } else {
                if (point.y() > o.point.y()) return +1;
                if (point.y() < o.point.y()) return -1;
            }
            return point.compareTo(o.point);
        }
    }

    private Node root;
    private int size;
    private double closest;
    private Point2D best;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("The argument can't be null!");
        root = insert(new Node(p, true, null, null), root);
        size++;
    }

    private Node insert(Node p, Node node) {
        if (node == null) return p;
        int cmp = node.compareTo(p);
        if (cmp == 0) {
            size--;
            return node;
        }
        if (cmp > 0) node.left = insert(p, node.left);
        else          node.right = insert(p, node.right);
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("The argument can't be null!");
        return contains(new Node(p, true, null, null), root);
    }

    private boolean contains(Node p, Node node) {
        if (node == null) return false;
        int cmp = node.compareTo(p);
        if (cmp == 0) return true;
        if (cmp > 0)  return contains(p, node.left);
        else          return contains(p, node.right);
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        StdDraw.setPenRadius(0.03);
        if (node.isVertical) StdDraw.setPenColor(StdDraw.RED);
        else                 StdDraw.setPenColor(StdDraw.BLUE);
        node.point.draw();
        draw(node.left);
        draw(node.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("The argument can't be null!");
        Queue<Point2D> queue = new Queue<>();
        range(rect, queue, root);
        return queue;
    }

    private void range(RectHV rect, Queue<Point2D> queue, Node node) {
        if (node == null) return;
        if (rect.contains(node.point)) queue.enqueue(node.point);
        if (node.isVertical) {
            if      (node.point.x() < rect.xmin()) range(rect, queue, node.right);
            else if (node.point.x() > rect.xmax()) range(rect, queue, node.left);
            else {
                range(rect, queue, node.left);
                range(rect, queue, node.right);
            }
        } else {
            if      (node.point.y() < rect.ymin()) range(rect, queue, node.right);
            else if (node.point.y() > rect.ymax()) range(rect, queue, node.left);
            else {
                range(rect, queue, node.left);
                range(rect, queue, node.right);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("The argument can't be null!");
        closest = Double.POSITIVE_INFINITY;
        best = null;
        nearest(p, root);
        return best;
    }

    private void nearest(Point2D p, Node node) {
        if (node == null) return;
        double d = node.point.distanceTo(p);
        if (d < closest) {
            closest = d;
            best = node.point;
        }
        Node goodSide = node.left;
        Node badSide = node.right;
        if (node.isVertical) {
            if (p.x() - node.point.x() > 0) {
                goodSide = node.right;
                badSide = node.left;
            }
        } else {
            if (p.y() - node.point.y() > 0) {
                goodSide = node.right;
                badSide = node.left;
            }
        }
        nearest(p, goodSide);
        if (node.isVertical) {
            if (closest > Math.abs(p.x() - node.point.x())) nearest(p, badSide);
        } else if (closest > Math.abs(p.y() - node.point.y())) nearest(p, badSide);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.372, 0.497));
        kdTree.insert(new Point2D(0.564, 0.413));
        kdTree.insert(new Point2D(0.226, 0.577));
        kdTree.insert(new Point2D(0.144, 0.179));
        kdTree.insert(new Point2D(0.083, 0.51));
        kdTree.insert(new Point2D(0.32, 0.708));
        kdTree.insert(new Point2D(0.417, 0.362));
        kdTree.insert(new Point2D(0.862, 0.825));
        kdTree.insert(new Point2D(0.785, 0.725));
        kdTree.insert(new Point2D(0.499, 0.208));
        kdTree.draw();
    }
}

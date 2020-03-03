import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // check if the argument contains null
        if (points == null) throw new IllegalArgumentException("the argument is null!");
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("the points contain a null object!");
        }
        // sort array based on the compareTo method
        Point[] newPoints = points.clone();
        Arrays.sort(newPoints, Point::compareTo);
        // check if the array contains two same points
        for (int i = 1; i < newPoints.length; i++) {
            if (newPoints[i - 1].equals(newPoints[i])) throw new IllegalArgumentException("the Points contain two same point");
        }
        lineSegments = new ArrayList<>();
        for (int i = 1; i < newPoints.length; i++) {
            Point base = newPoints[i - 1];  // sort array based on their slops to points[i-1]
            Arrays.sort(newPoints, i, newPoints.length, base.slopeOrder());
            int count = 0;
            double slop = Double.NEGATIVE_INFINITY;
            double newSlop;
            for (int j = i; j < newPoints.length; j++) {
                newSlop = base.slopeTo(newPoints[j]);
                if (slop == newSlop) {
                    count++;
                } else {
                    // when the slop changes check if the previous slop has 4 slop
                    if (count >= 2) {
                        boolean duplicate = false;
                        // check if the base point is the least point
                        for (int k = 0; k < i; k++) {
                            if (slop == base.slopeTo(newPoints[k])) {
                                duplicate = true;
                                break;
                            }
                        }
                        if (!duplicate) {
                            lineSegments.add(new LineSegment(base, newPoints[j - 1]));
                        }
                    }
                    slop = newSlop;
                    count = 0;
                }
            }
            // maybe the last collinear point at the end of array
            if (count >= 2) {
                boolean duplicate = false;
                for (int k = 0; k < i; k++) {
                    if (slop == base.slopeTo(newPoints[k])) {
                        duplicate = true;
                        break;
                    }
                }
                if (!duplicate) {
                    lineSegments.add(new LineSegment(base, newPoints[newPoints.length - 1]));
                }
            }
            Arrays.sort(newPoints, i, newPoints.length, Point::compareTo);  // sort the array base on the points' position
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        Point[] points = {new Point(1, 2), new Point(2, 4), new Point(0, 0), new Point(3, 6), new Point(4, 8),
        new Point(1, 1), new Point(2, 2), new Point(3, 3)};
        FastCollinearPoints bruteCollinearPoints = new FastCollinearPoints(points);
        for (LineSegment lineSegment : bruteCollinearPoints.segments()) {
            StdOut.println(lineSegment.toString());
        }
        StdOut.println(bruteCollinearPoints.numberOfSegments());
    }
}
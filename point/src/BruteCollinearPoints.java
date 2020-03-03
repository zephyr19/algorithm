import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // check points argument
        if (points == null) throw new IllegalArgumentException("the argument is null!");
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("the points contain a null object!");
        }
        // sort array based on the compareTo method
        Point[] newPoints = points.clone();
        Arrays.sort(newPoints, Point::compareTo);
        // check if the array contains two same points
        for (int i = 1; i < newPoints.length; i++) {
            if (newPoints[i - 1].equals(newPoints[i])) throw new IllegalArgumentException("the newPoints contain two same point");
        }
        lineSegments = new ArrayList<>();
        // solve the problem
        for (int i = 0; i < newPoints.length; i++) {
            for (int j = i + 1; j < newPoints.length; j++) {
                for (int k = j + 1; k < newPoints.length; k++) {
                    for (int m = k + 1; m < newPoints.length; m++) {
                        if (newPoints[i].slopeTo(newPoints[j]) == newPoints[i].slopeTo(newPoints[k]) &&
                                newPoints[i].slopeTo(newPoints[k]) == newPoints[i].slopeTo(newPoints[m])) {
                            lineSegments.add(new LineSegment(newPoints[i], newPoints[m]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments"
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        Point[] points = {new Point(0, 0), new Point(1, 2), new Point(2, 4), new Point(3, 6)};
        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        for (LineSegment lineSegment : bruteCollinearPoints.segments()) {
            StdOut.println(lineSegment.toString());
        }
        StdOut.println(bruteCollinearPoints.numberOfSegments());
    }
}
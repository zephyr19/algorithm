import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("The picture is null");
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) throw new IllegalArgumentException();
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) return 1000.00;
        Color left = picture.get(x + 1, y);
        Color right = picture.get(x - 1, y);
        Color up = picture.get(x, y + 1);
        Color bottom = picture.get(x, y - 1);
        double a = Math.pow(left.getRed() - right.getRed(), 2);
        a += Math.pow(left.getGreen() - right.getGreen(), 2);
        a += Math.pow(left.getBlue() - right.getBlue(), 2);
        double b = Math.pow(up.getRed() - bottom.getRed(), 2);
        b += Math.pow(up.getGreen() - bottom.getGreen(), 2);
        b += Math.pow(up.getBlue() - bottom.getBlue(), 2);
        return Math.sqrt(a + b);
    }

    private double[][] energyMatrix() {
        double[][] matrix = new double[width()][height()];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = energy(i, j);
            }
        }
        return matrix;
    }

    private int[] hChild(int i) {
        int next = i + 1, width = width();
        if (height() == 1) return new int[] {next};
        int y = i / width;
        if (y == 0) return new int[] {next, next + width};
        if (y == height() - 1) return new int[] {next, next - width};
        return new int[] {next, next - width, next + width};
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] energyMatrix = energyMatrix();
        int len = width() * height();
        IndexMinPQ<Double> pq = new IndexMinPQ<>(len);
        double[] distTo = new double[len];
        int[] edgeTo = new int[len];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        for (int i = 0; i < height(); i++) {
            int index = i * width();
            double energy = energyMatrix[0][i];
            pq.insert(index, energy);
            distTo[index] = energy;
            edgeTo[index] = index;
        }
        while (!pq.isEmpty()) {
            int i = pq.delMin();
            if (i % width() == width() - 1) {
                int[] seam = new int[width()];
                seam[seam.length - 1] = i / width();
                for (int j = seam.length - 2; j >= 0; j--) {
                    seam[j] = edgeTo[seam[j + 1] * width() + (j + 1)] / width();
                }
                return seam;
            }
            for (int child : hChild(i)) {
                double dist = distTo[i] + energyMatrix[child % width()][child / width()];
                if (distTo[child] > dist) {
                    distTo[child] = dist;
                    edgeTo[child] = i;
                    if (pq.contains(child)) pq.decreaseKey(child, dist);
                    else                    pq.insert(child, dist);
                }
            }

        }
        throw new IllegalArgumentException("The method has an error");
    }

    private int[] vChild(int i) {
        int next = i + 1, height = height();
        if (width() == 1) return new int[] {next};
        int x = i / height;
        if (x == 0) return new int[] {next, next + height};
        if (x == width() - 1) return new int[] {next, next - height};
        return new int[] {next, next - height, next + height};
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energyMatrix = energyMatrix();
        int width = width(), height = height();
        int len = width * height;
        IndexMinPQ<Double> pq = new IndexMinPQ<>(len);
        double[] distTo = new double[len];
        int[] edgeTo = new int[len];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        for (int i = 0; i < width; i++) {
            int index = i * height;
            double energy = energyMatrix[i][0];
            pq.insert(index, energy);
            distTo[index] = energy;
            edgeTo[index] = index;
        }
        while (!pq.isEmpty()) {
            int i = pq.delMin();
            if (i % height == height - 1) {
                int[] seam = new int[height];
                seam[seam.length - 1] = i / height;
                for (int j = seam.length - 2; j >= 0; j--) {
                    seam[j] = edgeTo[seam[j + 1] * height + (j + 1)] / height;
                }
                return seam;
            }
            for (int child : vChild(i)) {
                double dist = distTo[i] + energyMatrix[child / height][child % height];
                if (distTo[child] > dist) {
                    distTo[child] = dist;
                    edgeTo[child] = i;
                    if (pq.contains(child)) pq.decreaseKey(child, dist);
                    else                    pq.insert(child, dist);
                }
            }

        }
        throw new IllegalArgumentException("The method has an error");
    }

    private void validate(int[] seam, int len, int range) {
        if (seam == null) throw new IllegalArgumentException("The seam is null");
        if (seam.length != len || range <= 1) throw new IllegalArgumentException("The seam's length is not match");
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] < 0 || seam[i] >= range) throw new IllegalArgumentException();
            if (Math.abs(seam[i+1] - seam[i]) > 1) throw new IllegalArgumentException("Pixels are not continuous");
        }
        if (seam[seam.length - 1] < 0 || seam[seam.length - 1] >= range) throw new IllegalArgumentException();
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validate(seam, width(), height());
        Picture res = new Picture(width(), height() - 1);
        for (int i = 0; i < res.width(); i++) {
            for (int j = 0; j < res.height(); j++) {
                int y = seam[i];
                if (j < y) res.set(i, j, picture.get(i, j));
                else       res.set(i, j, picture.get(i, j + 1));
            }
        }
        picture = res;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validate(seam, height(), width());
        Picture res = new Picture(width() - 1, height());
        for (int i = 0; i < res.width(); i++) {
            for (int j = 0; j < res.height(); j++) {
                int x = seam[j];
                if (i < x) res.set(i, j, picture.get(i, j));
                else       res.set(i, j, picture.get(i + 1, j));
            }
        }
        picture = res;
    }

    //  unit testing (optional)
    public static void main(String[] args) {
//        Picture pic = SCUtility.randomPicture(8, 1);
//        SeamCarver seam = new SeamCarver(pic);
//        seam.findHorizontalSeam();
//        pic = SCUtility.randomPicture(1, 8);
//        seam = new SeamCarver(pic);
//        seam.findVerticalSeam();
//        Picture picture = new Picture("test.png");
        Picture picture = new Picture("HJoceanSmall.png");
        picture.show();
        SeamCarver seamCarver = new SeamCarver(picture);
        for (int i = 0; i < 100; i++) {
            seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
//            picture = SCUtility.seamOverlay(seamCarver.picture, false, seamCarver.findVerticalSeam());
            System.out.println(Arrays.toString(seamCarver.findVerticalSeam()));
        }
//        picture.show();
        seamCarver.picture.show();
    }

}
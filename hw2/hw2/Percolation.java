package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.Test;

public class Percolation {
    private int[][] arr;
    private WeightedQuickUnionUF weightedQuickUnionUF = null;
    private int virtualTopSite;
    private int virtualBottomSite;
    private int openSize = 0;
    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        arr = new int[N][N];
        virtualTopSite = N * N;
        virtualBottomSite = N * N + 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = 0;
            }
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N + 3);
    }

    private int xyTo1D(int r, int c) {
        return r * arr[0].length + c;
    }

    private boolean indexOutOfBounds(int row, int col) {
        if (!(row >= 0 && row < arr.length && col >= 0 && col < arr.length)) {
            return true;
        }
        return false;
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        if (indexOutOfBounds(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (arr[row][col] == 0) {
            arr[row][col] = 1;
            openSize += 1;
            if (row == 0) {
                weightedQuickUnionUF.union(virtualTopSite, xyTo1D(row, col));
            } else if (row == arr[0].length - 1 && !(percolates())) {
                weightedQuickUnionUF.union(virtualBottomSite, xyTo1D(row, col));
            }
            if (!(indexOutOfBounds(row - 1, col)) && isOpen(row - 1, col)) {
                weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (!(indexOutOfBounds(row + 1, col)) && isOpen(row + 1, col)) {
                weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (!(indexOutOfBounds(row, col - 1)) && isOpen(row, col - 1)) {
                weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (!(indexOutOfBounds(row, col + 1)) && isOpen(row, col + 1)) {
                weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }

    }
    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        if(arr[row][col] == 1) {
            return true;
        }
        return false;
    }
    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        return weightedQuickUnionUF.connected(xyTo1D(row, col), virtualTopSite);

    }
    public int numberOfOpenSites() {
        // number of open sites
        return openSize;
    }
    public boolean percolates() {
        // does the system percolate?
        return weightedQuickUnionUF.connected(virtualTopSite, virtualBottomSite);
    }
    public static void main(String[] args){

    }   // use for unit testing (not required)
}

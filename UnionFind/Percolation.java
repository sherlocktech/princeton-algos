import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation
 */
public class Percolation {
    private final WeightedQuickUnionUF  uf;
    private final int                   topId;
    private final int                   bottomId;
    private final int                   n;

    private boolean[]   sites;
    private int         openSites;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Argument must be greater than 0.");
        // create n-by-n grid, with all sites blocked
        this.n = n; 
        // create uf object with two extra spots for virtual top and bottom
        uf = new WeightedQuickUnionUF((n * n) + 2);
        topId = (n * n);
        bottomId = (n * n) + 1;
        sites = new boolean[n * n]; 
        openSites = 0;
    }
    
    public void open(int row, int col) {
        if (!((row >= 1 && row <= n) && (col >= 1 && col <= n)))
            throw new IllegalArgumentException("Arguments must be between 1 and n (inclusive).");
        // open site (row, col) if it is not open already
        int id = findId(row, col);

        if (idOpen(id)) return;
        else {
            // open site
            sites[id] = true;
            // connect all open sites adjacent
            connectNeighbors(row, col);
        }
        // increase num of open sites
        openSites++;
    }

    public boolean isOpen(int row, int col) {
        if (!((row >= 1 && row <= n) && (col >= 1 && col <= n)))
            throw new IllegalArgumentException("Arguments must be between 1 and n (inclusive).");
        // is site (row, col) open?
        int id = findId(row, col);
        return idOpen(id);
    }

    public boolean isFull(int row, int col) {
        if (!((row >= 1 && row <= n) && (col >= 1 && col <= n)))
            throw new IllegalArgumentException("Arguments must be between 1 and n (inclusive).");
        // is site (row, col) full?
        int id = findId(row, col);
        return uf.connected(id, topId);
    }

    public int numberOfOpenSites() {
        // number of open sites
        return openSites;
    }

    public boolean percolates() {
        // does the system percolate?
        return uf.connected(topId, bottomId);
    }

    private int findId(int row, int col) {
        return ((row - 1) * n) + (col - 1);
    }

    private boolean idOpen(int id) {
        return sites[id];
    }

    private void connectNeighbors(int row, int col) {
        /** Need to connect top row to topId and bottom
         *  row to bottomId if (row, col) is full
         */
        int id = findId(row, col);

        // if on top connect to topId, bottom connect to bottomId
        if (row == 1) uf.union(id, topId);
        //if (row == n && uf.connected(id, topId)) uf.union(id, bottomId);

        // loop over rows 
        int r = row - 1;
        int c = col;
        int i = findId(r, c);
        while (i <= findId(row + 1, col)) {
            if (r < 1 || r > n) {
                r++;
                i = findId(r, c);
            } 
            else {
                if (idOpen(i)) {
                    uf.union(i, id);
                    if (r == n && uf.connected(i, topId)) uf.union(i, bottomId);
                } 
                r++;
                i = findId(r, c);
            }
        }

        // loop over columns
        r = row;
        c = col - 1;
        i = findId(r, c);
        while (i <= findId(row, col + 1)) {
            if (c < 1 || c > n) {
                c++;
                i = findId(r, c);
            }
            else {
                if (idOpen(i)) {
                    uf.union(i, id);
                    if (r == n && uf.connected(i, topId)) uf.union(i, bottomId);
                }
                c++;
                i = findId(r, c);
            }
        }
    }
}
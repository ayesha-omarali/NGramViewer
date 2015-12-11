import java.util.*;

/* Most of this code is adopted from the Princeton algorithms textbook. */
public class TST {
    private TSTNode root;

    protected static class TSTNode {
        private char c;
        private TSTNode left, mid, right;
        private double val;
    }

    protected static class TSTNodeComparator implements Comparator<Object[]> {
        public int compare(Object[] x, Object[] y) {
            double d1 = (double) x[1];
            double d2 = (double) y[1];

            if (d1 < d2) {
                return 1;
            } else if (d1 > d2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public TST() {
        root = new TSTNode();
    }

    public void put(String s, double val) {
        root = put(root, s, val, 0);
    }

    private TSTNode put(TSTNode x, String key, double val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new TSTNode();
            x.c = c;
        }

        if (c < x.c) {
            x.left = put(x.left, key, val, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, val, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, val, d+1);
        } else  {
            x.val = val;
        }

        return x;
    }

    public PriorityQueue<Object[]> keysWithPrefix(String prefix) {
        PriorityQueue<Object[]> queue = new PriorityQueue<Object[]>(100, new TSTNodeComparator());
        TSTNode x = get(root, prefix, 0);

        if (x == null) {
            return queue;
        }

        if (x.val != 0.0) {
            queue.add(new Object[]{prefix, x.val});
        }

        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(TSTNode x, StringBuilder prefix, PriorityQueue<Object[]> queue) {
        if (x == null) {
            return;
        }

        collect(x.left,  prefix, queue);

        if (x.val != 0.0) {
            queue.add(new Object[]{prefix.toString() + x.c, x.val});
        }

        collect(x.mid,   prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }

    public double get(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        TSTNode x = get(root, key, 0);
        if (x == null) {
            return 0.0;
        }

        return x.val;
    }

    private TSTNode get(TSTNode x, String key, int d) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        if (x == null) {
            return null;
        }

        char c = key.charAt(d);
        if (c < x.c) {
            return get(x.left,  key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid,   key, d+1);
        } else {
            return x;
        }
    }
}

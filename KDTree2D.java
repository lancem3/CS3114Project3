import java.util.Arrays;
import java.util.Comparator;

public class KDTree2D {
    private KDNode head;

    /**
     *
     * @param orig
     *      array to be split
     * @param lower
     *      lower half of orig
     * @param upper
     *      upper half of orig
     *
     * Copies all values below or equal to the median of orig into lower,
     * copies all values above median into upper
     */
    private void splitByMedian(Point[] orig, Point[] lower, Point[] upper){
        int median = ((orig.length - 1) /2);
        upper = new Point[median + 1];
        lower = new Point[median];
        System.arraycopy(orig, 0, lower, 0, median + 1);
        System.arraycopy(orig, median + 1, upper, 0, median);
    }

    /**
     *
     * @param curr
     *      current node
     * @param depth
     *      depth of current node in tree
     *
     * Recursively builds kd tree
     */
    private void buildTree(KDNode curr, int depth){
        Point[] points = curr.regionPoints;
        if(points.length == 1){//if node is a leaf
            return;
        }
        else {
            Point[] upper = new Point[0];//points above median
            Point[] lower = new Point[0];//points below median
            if (depth % 2 == 0) {
                Arrays.sort(points, new SortPointsByX());//split region with vertical line
            }
            else{
                Arrays.sort(points, new SortPointsByY());//split region with horizontal line
            }
            splitByMedian(points, lower, upper);
            curr.left = new KDNode(lower);
            curr.right = new KDNode(upper);
            buildTree(curr.left, depth+1);
            buildTree(curr.right, depth+1);
        }
    }
    public KDTree2D(Point[] points){
        head = new KDNode(points);
    }

    class KDNode {
        Point[] regionPoints;
        KDNode left, right;
        public KDNode(Point[] points){
            regionPoints = points;
        }
    }

    //adapted from Piazza post by Nathaniel Lahn
    class SortPointsByX implements Comparator<Point> {
        public int compare(Point a, Point b){
            int compareX = Integer.compare(a.x, b.x);   //compare x values
            if(compareX == 0){  //if x values are equal
                return Integer.compare(a.y, b.y);   //use y value as tiebreaker
            }
            else{
                return compareX;
            }
        }
    }
    class SortPointsByY implements Comparator<Point>{
        public int compare(Point a, Point b){
            int compareX = Integer.compare(a.y, b.y);   //compare y values
            if(compareX == 0){  //if y values are equal
                return Integer.compare(a.x, b.x);   //use x value as tiebreaker
            }
            else{
                return compareX;
            }
        }
    }
}

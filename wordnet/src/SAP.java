public class SAP {
    private Digraph graph;

    public SAP(Digraph G) {
        graph = new Digraph(G);
    }

    public int length(int v, int w) {
        validate(v);
        validate(w);

        int ancestor = ancestor(v, w);

        if (ancestor == -1) {
            return -1;
        }

        BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(graph, w);

        return pathV.distTo(ancestor) + pathW.distTo(ancestor);
    }

    public int ancestor(int v, int w) {
        validate(v);
        validate(w);

        BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(graph, w);

        return minAncestor(pathV, pathW);
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int ancestor = ancestor(v, w);

        if (ancestor == -1) {
            return -1;
        }

        BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(graph, w);

        return pathV.distTo(ancestor) + pathW.distTo(ancestor);
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(graph, w);

        return minAncestor(pathV, pathW);
    }

    private int minAncestor(BreadthFirstDirectedPaths pathV, BreadthFirstDirectedPaths pathW) {
        int minCommonAncestor = -1;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < graph.V(); i++) {
            if (pathV.hasPathTo(i) && pathW.hasPathTo(i)) {
                int vDist = pathV.distTo(i);
                int wDist = pathW.distTo(i);
                int newDistance = vDist + wDist;
                if (newDistance < minDistance) {
                    minCommonAncestor = i;
                    minDistance = newDistance;
                }
            }
        }

        return minCommonAncestor;
    }

    private void validate(int v) {
        if (v < 0 || v > graph.V() - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}

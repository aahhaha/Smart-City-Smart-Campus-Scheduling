package graph.common;

import java.util.*;

public class Graph {
    public final boolean directed;
    public final int n;
    public final List<List<Edge>> adj;

    public Graph(boolean directed, int n) {
        this.directed = directed;
        this.n = n;
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(u, v, w));
        if (!directed) adj.get(v).add(new Edge(v, u, w));
    }
}

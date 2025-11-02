package graph.scc;

import graph.common.*;
import java.util.*;

public class TarjanSCC {
    private final Graph g;
    private final BasicMetrics m;

    private int time = 0, compCount = 0;
    private int[] disc, low, comp;
    private boolean[] inStack;
    private Deque<Integer> st = new ArrayDeque<>();

    public TarjanSCC(Graph g, BasicMetrics m){
        this.g = g; this.m = m;
    }

    public int[] run(){
        int n = g.n;
        disc = new int[n]; low = new int[n]; comp = new int[n];
        Arrays.fill(disc, -1); Arrays.fill(comp, -1);
        inStack = new boolean[n];
        for (int u = 0; u < n; u++)
            if (disc[u] == -1) dfs(u);
        return comp; // id компоненты для каждой вершины
    }

    private void dfs(int u){
        m.incDfsVisit();
        disc[u] = low[u] = time++;
        st.push(u); inStack[u] = true;

        for (Edge e : g.adj.get(u)) {
            m.incDfsEdge();
            int v = e.v();
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            while (true) {
                int w = st.pop(); inStack[w] = false; comp[w] = compCount;
                if (w == u) break;
            }
            compCount++;
        }
    }

    public int components(){ return compCount; }
}

package graph.dagsp;

import graph.common.*;
import java.util.*;

public class DagLongestPath {
    public static int[] run(Graph dag, List<Integer> topo, BasicMetrics m){
        int n = dag.n;
        int[] best = new int[n];
        Arrays.fill(best, Integer.MIN_VALUE/4);

        // стартуем с 0 на всех истоках (in-degree == 0)
        int[] indeg = new int[n];
        for (int u=0; u<n; u++) for (Edge e : dag.adj.get(u)) indeg[e.v()]++;
        for (int i=0; i<n; i++) if (indeg[i]==0) best[i] = 0;

        for (int u : topo){
            if (best[u] == Integer.MIN_VALUE/4) continue;
            for (Edge e : dag.adj.get(u)){
                best[e.v()] = Math.max(best[e.v()], best[u] + e.w());
                m.incRelax();
            }
        }
        return best;
    }
}

package graph.scc;

import graph.common.*;
import java.util.*;

public class CondensationBuilder {
    public static Graph build(Graph g, int[] comp){
        int c = Arrays.stream(comp).max().orElse(-1) + 1;
        Graph dag = new Graph(true, c);

        // Чтобы не дублировать ребра между компонентами:
        Set<Long> seen = new HashSet<>();

        for (int u = 0; u < g.n; u++) {
            for (Edge e : g.adj.get(u)) {
                int cu = comp[u], cv = comp[e.v()];
                if (cu != cv) {
                    long key = ((long)cu << 32) | (cv & 0xffffffffL);
                    if (seen.add(key)) dag.addEdge(cu, cv, e.w());
                }
            }
        }
        return dag;
    }
}

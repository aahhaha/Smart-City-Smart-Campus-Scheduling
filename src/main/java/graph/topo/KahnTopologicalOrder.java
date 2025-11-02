package graph.topo;

import graph.common.*;
import java.util.*;

public class KahnTopologicalOrder {
    public static List<Integer> order(Graph dag, BasicMetrics m){
        int n = dag.n;
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++)
            for (Edge e : dag.adj.get(u)) indeg[e.v()]++;

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) { q.add(i); m.incQueuePush(); }

        List<Integer> topo = new ArrayList<>();
        while (!q.isEmpty()){
            int u = q.remove(); m.incQueuePop(); topo.add(u);
            for (Edge e : dag.adj.get(u)){
                if (--indeg[e.v()] == 0) { q.add(e.v()); m.incQueuePush(); }
            }
        }
        if (topo.size() != n) throw new IllegalStateException("Condensation is not a DAG");
        return topo;
    }
}

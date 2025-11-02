package graph.dagsp;

import graph.common.*;
import java.util.*;

public class DagShortestPath {
    public static class Result {
        public final int src; public final int[] dist; public final int[] prev;
        Result(int s,int[]d,int[]p){ src=s; dist=d; prev=p; }
        public List<Integer> pathTo(int t){
            List<Integer> p = new ArrayList<>();
            for (int v=t; v!=-1; v=prev[v]) p.add(v);
            Collections.reverse(p); return p;
        }
    }

    public static Result run(Graph dag, List<Integer> topo, int src, BasicMetrics m){
        final int INF = 1_000_000_000;
        int n = dag.n;
        int[] dist = new int[n]; Arrays.fill(dist, INF);
        int[] prev = new int[n]; Arrays.fill(prev, -1);
        dist[src] = 0;

        for (int u : topo){
            if (dist[u] == INF) continue;
            for (Edge e : dag.adj.get(u)){
                int alt = dist[u] + e.w();
                if (alt < dist[e.v()]) { dist[e.v()] = alt; prev[e.v()] = u; m.incRelax(); }
            }
        }
        return new Result(src, dist, prev);
    }
}

package app;

import graph.common.*;
import graph.scc.*;
import graph.topo.*;
import graph.dagsp.*;

import java.io.FileWriter;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "data/small-03.json";
        if (!Files.exists(Path.of(file))) {
            System.err.println("File not found: " + file);
            System.exit(1);
        }

        var loaded = Loader.fromJsonFile(file);
        Graph g = loaded.g;
        int src = loaded.source;

        var m = new BasicMetrics();

        long tStart, tEnd;
        long tarjanTime, dagTime, spTime, totalTime;

        System.out.println("=======================================");
        System.out.println("▶ Running analysis for " + file);

        tStart = System.nanoTime();
        var tarjan = new TarjanSCC(g, m);
        int[] comp = tarjan.run();
        int comps = Arrays.stream(comp).max().orElse(-1) + 1;
        tEnd = System.nanoTime();
        tarjanTime = tEnd - tStart;

        tStart = System.nanoTime();
        Graph dag = CondensationBuilder.build(g, comp);
        var topo = KahnTopologicalOrder.order(dag, m);
        tEnd = System.nanoTime();
        dagTime = tEnd - tStart;

        tStart = System.nanoTime();
        int srcComp = comp[src];
        var sp = DagShortestPath.run(dag, topo, srcComp, m);
        var longest = DagLongestPath.run(dag, topo, m);
        tEnd = System.nanoTime();
        spTime = tEnd - tStart;

        totalTime = tarjanTime + dagTime + spTime;

        int crit = Arrays.stream(longest).max().orElse(Integer.MIN_VALUE / 4);

        System.out.println("Components count: " + comps);
        System.out.println("Topological order: " + topo);
        System.out.println("Critical (longest) path length: " + crit);
        System.out.println("\n--- Timing (ns) ---");
        System.out.printf("TarjanSCC: %d%n", tarjanTime);
        System.out.printf("Condensation + Topo: %d%n", dagTime);
        System.out.printf("DAG Shortest/Longest: %d%n", spTime);
        System.out.printf("TOTAL: %d%n", totalTime);
        System.out.println("\nMetrics: " + m);

        try (FileWriter fw = new FileWriter("metrics_log.txt", true)) {
            fw.write(String.format(
                    Locale.US,
                    "%s | V=%d | E=%d | SCC=%d | total=%d | Tarjan=%d | DAG=%d | SP=%d | dfsVisit=%d | dfsEdge=%d | qPush=%d | qPop=%d | relax=%d | CritPath=%d%n",
                    file,
                    g.n,
                    g.adj.stream().mapToInt(List::size).sum(),
                    comps,
                    totalTime, tarjanTime, dagTime, spTime,
                    m.getDfsVisit(), m.getDfsEdge(),
                    m.getQueuePush(), m.getQueuePop(), m.getRelax(),
                    crit
            ));
        }

        System.out.println("\n✅ Detailed metrics written to metrics_log.txt\n");
    }
}

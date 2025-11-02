import graph.common.*;
import graph.dagsp.*;
import graph.topo.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class DagSpSmallTests {

    @Test
    void testShortestPathDAG() {
        Graph dag = new Graph(true, 4);
        dag.addEdge(0, 1, 1);
        dag.addEdge(0, 2, 4);
        dag.addEdge(1, 2, 2);
        dag.addEdge(1, 3, 6);
        dag.addEdge(2, 3, 3);

        var m = new BasicMetrics();
        var topo = KahnTopologicalOrder.order(dag, m);
        var res = DagShortestPath.run(dag, topo, 0, m);

        assertEquals(0, res.dist[0]);
        assertEquals(1, res.dist[1]);
        assertEquals(3, res.dist[2]);
        assertEquals(6, res.dist[3]);
    }
}

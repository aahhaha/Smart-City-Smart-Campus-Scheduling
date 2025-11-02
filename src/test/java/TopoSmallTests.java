import graph.common.*;
import graph.topo.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TopoSmallTests {

    @Test
    void testSimpleDAGOrder() {
        Graph dag = new Graph(true, 4);
        dag.addEdge(0, 1, 1);
        dag.addEdge(0, 2, 1);
        dag.addEdge(1, 3, 1);
        dag.addEdge(2, 3, 1);

        var m = new BasicMetrics();
        var order = KahnTopologicalOrder.order(dag, m);

        assertEquals(4, order.size());
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(2));
        assertEquals(3, order.get(order.size() - 1));
    }
}

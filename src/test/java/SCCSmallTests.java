import graph.common.*;
import graph.scc.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class SCCSmallTests {

    @Test
    void testTriangleAndTail() {
        Graph g = new Graph(true, 4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);
        g.addEdge(2, 3, 1);

        var m = new BasicMetrics();
        var tarjan = new TarjanSCC(g, m);
        int[] comp = tarjan.run();

        assertEquals(comp[0], comp[1]);
        assertEquals(comp[1], comp[2]);
        assertNotEquals(comp[2], comp[3]);
    }
}

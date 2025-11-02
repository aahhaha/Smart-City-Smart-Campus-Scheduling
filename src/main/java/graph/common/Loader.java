package graph.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Iterator;

public class Loader {
    public static class Loaded {
        public final Graph g;
        public final int source;
        public Loaded(Graph g, int source){ this.g = g; this.source = source; }
    }

    public static Loaded fromJsonFile(String path) throws Exception {
        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(new File(path));

        boolean directed = root.path("directed").asBoolean(true);
        int n = root.path("n").asInt();
        Graph g = new Graph(directed, n);

        for (Iterator<JsonNode> it = root.path("edges").elements(); it.hasNext(); ){
            JsonNode e = it.next();
            int u = e.path("u").asInt();
            int v = e.path("v").asInt();
            int w = e.path("w").asInt(1);
            g.addEdge(u, v, w);
        }

        int source = root.path("source").asInt(0);
        return new Loaded(g, source);
    }
}

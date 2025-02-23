package network;

import java.util.HashMap;
import java.util.*;

public class NetworkGraph {
    private Map<String, NetworkNode> nodes;

    public NetworkGraph() {
        nodes = new HashMap<>();
    }

    public void addNode(NetworkNode node) {
        nodes.put(node.getId(), node);
    }
    public NetworkNode getNode(String id) {
        return nodes.get(id);
    }

    public void addEdge(String fromId, String toId, int latency){
        NetworkNode from = nodes.get(fromId);
        NetworkNode to = nodes.get(toId);
        if (from != null || to != null){
            Edge edge = new Edge(from, to, latency);
            from.addConnection(edge);

            // for an undirected graph, also add the reverse connection
            Edge reverseEdge = new Edge(to, from, latency);
            to.addConnection(reverseEdge);
        }
    }

}

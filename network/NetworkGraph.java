package network;

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

    public void addEdge(String fromId, String toId, int latency) {
        NetworkNode from = nodes.get(fromId);
        NetworkNode to = nodes.get(toId);
        if (from != null && to != null) {
            Edge edge = new Edge(from, to, latency);
            from.addConnection(edge);

            // For an undirected graph, also add the reverse connection
            Edge reverseEdge = new Edge(to, from, latency);
            to.addConnection(reverseEdge);
        }
    }

    // ✅ New Method to Get All Nodes
    public Collection<NetworkNode> getAllNodes() {
        return nodes.values();
    }

    public List<NetworkNode> getShortestPath(String sourceId, String targetId) {
        Map<NetworkNode, Integer> distances = new HashMap<>();
        Map<NetworkNode, NetworkNode> previous = new HashMap<>();
        PriorityQueue<NetworkNode> queue = new PriorityQueue<>(
                (n1, n2) -> Integer.compare(distances.getOrDefault(n1, Integer.MAX_VALUE), distances.getOrDefault(n2, Integer.MAX_VALUE))
        );
        NetworkNode source = nodes.get(sourceId);
        NetworkNode target = nodes.get(targetId);
        if (source == null || target == null) {
            return null;
        }

        for (NetworkNode node : nodes.values()) {
            distances.put(node, Integer.MAX_VALUE);
            previous.put(node, null);
            queue.add(node);
        }
        distances.put(source, 0);

        while (!queue.isEmpty()) {
            NetworkNode u = queue.poll();
            if (u.equals(target)) break;
            for (Edge edge : u.getConnections()) {
                NetworkNode v = edge.getTo();
                int alt = distances.get(u) + edge.getLatency();
                if (alt < distances.getOrDefault(v, Integer.MAX_VALUE)) {
                    distances.put(v, alt);
                    previous.put(v, u);
                    queue.remove(v);
                    queue.add(v);
                }
            }
        }

        List<NetworkNode> path = new ArrayList<>();
        for (NetworkNode at = target; at != null; at = previous.get(at)) {
            path.add(0, at);
        }
        if (!path.isEmpty() && path.get(0).equals(source)) {
            return path;
        }
        return null;
    }
}

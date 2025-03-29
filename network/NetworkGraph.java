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

    public Collection<NetworkNode> getAllNodes() {
        return nodes.values();
    }

    public void addEdge(String fromId, String toId, int latency) {
        NetworkNode from = nodes.get(fromId);
        NetworkNode to = nodes.get(toId);
        if (from != null && to != null) {
            // forward edge
            Edge edge = new Edge(from, to, latency);
            from.addConnection(edge);

            // reverse edge (undirected)
            Edge reverse = new Edge(to, from, latency);
            to.addConnection(reverse);

            System.out.println("[DEBUG] Created edge: " + fromId + " <--> " + toId + " (lat=" + latency + ")");
        } else {
            System.out.println("[DEBUG] WARNING: Could not create edge " + fromId + "->" + toId + " (One or both nodes not found!)");
        }
    }

    /**
     * Traditional Dijkstra’s algorithm, but simplified:
     *  - We only insert each node into the priority queue once (when we discover a better distance).
     *  - We use a visited set to avoid re-processing the same node multiple times.
     */
    public List<NetworkNode> getShortestPath(String sourceId, String targetId) {
        System.out.println("\n[DEBUG] getShortestPath() called with source=" + sourceId + ", target=" + targetId);

        NetworkNode source = nodes.get(sourceId);
        NetworkNode target = nodes.get(targetId);
        if (source == null || target == null) {
            System.out.println("[DEBUG] Source or target is null -> returning null");
            return null;
        }

        Map<NetworkNode, Integer> dist = new HashMap<>();
        Map<NetworkNode, NetworkNode> prev = new HashMap<>();
        Set<NetworkNode> visited = new HashSet<>();

        // Initialize distances
        for (NetworkNode n : nodes.values()) {
            dist.put(n, Integer.MAX_VALUE);
            prev.put(n, null);
        }
        dist.put(source, 0);

        // Priority queue holds nodes in order of their current best distance
        PriorityQueue<NetworkNode> queue = new PriorityQueue<>(
                Comparator.comparingInt(dist::get)
        );
        queue.add(source);

        while (!queue.isEmpty()) {
            NetworkNode u = queue.poll();
            if (!visited.add(u)) {
                // Already processed this node, skip
                continue;
            }
            if (u.equals(target)) {
                System.out.println("[DEBUG] Reached target node: " + targetId);
                break;
            }

            int distU = dist.get(u);
            for (Edge edge : u.getConnections()) {
                NetworkNode v = edge.getTo();
                if (visited.contains(v)) continue; // Already finalized distance

                int alt = distU + edge.getLatency();
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    // Insert v with updated distance
                    queue.add(v);
                }
            }
        }

        // Reconstruct the path
        List<NetworkNode> path = new ArrayList<>();
        for (NetworkNode at = target; at != null; at = prev.get(at)) {
            path.add(0, at);
        }

        if (!path.isEmpty() && path.get(0).equals(source)) {
            System.out.println("Shortest path found with length=" + dist.get(target));
            return path;
        }
        System.out.println("No path found from " + sourceId + " to " + targetId);
        return null;
    }
}

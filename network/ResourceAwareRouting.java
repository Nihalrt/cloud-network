package network;

import java.util.*;

public class ResourceAwareRouting {
    private Map<NetworkNode, Double> dist;
    private Map<NetworkNode, NetworkNode> prev;
    private PriorityQueue<NetworkNode> queue;

    private double alpha; // latency weight
    private double beta;  // CPU weight
    private double gamma; // MEM weight

    public ResourceAwareRouting(double alpha, double beta, double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        dist = new HashMap<>();
        prev = new HashMap<>();
        // queue is created in findPath()
    }

    public List<NetworkNode> findPath(NetworkGraph graph, String sourceId, String targetId) {
        System.out.println("ResourceAwareRouting.findPath: source=" + sourceId + ", target=" + targetId);

        NetworkNode source = graph.getNode(sourceId);
        NetworkNode target = graph.getNode(targetId);
        if (source == null || target == null) {
            System.out.println("source or target is null, returning null");
            return null;
        }

        // init dist
        for (NetworkNode n : graph.getAllNodes()) {
            dist.put(n, Double.POSITIVE_INFINITY);
            prev.put(n, null);
        }
        dist.put(source, 0.0);

        queue = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        queue.addAll(graph.getAllNodes());

        while (!queue.isEmpty()) {
            NetworkNode u = queue.poll();
            if (u.equals(target)) {
                System.out.println("Reached target in resourceAware");
                break;
            }

            for (Edge edge : u.getConnections()) {
                NetworkNode v = edge.getTo();
                // cost = alpha*latency + beta*CPU + gamma*MEM
                double edgeCost = alpha * edge.getLatency()
                        + beta * v.getCurrentCpuLoad()
                        + gamma * v.getCurrentMemLoad();
                double alt = dist.get(u) + edgeCost;

                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    queue.remove(v);
                    queue.offer(v);
                }
            }
        }

        // reconstruct
        List<NetworkNode> path = new ArrayList<>();
        for (NetworkNode at = target; at != null; at = prev.get(at)) {
            path.add(0, at);
        }
        if (!path.isEmpty() && path.get(0).equals(source)) {
            System.out.println("Resource-aware path found with cost=" + dist.get(target));
            return path;
        }
        System.out.println("No resource-aware path found from " + sourceId + " to " + targetId);
        return null;
    }

    public double getCostTo(NetworkNode node) {
        return dist.getOrDefault(node, Double.POSITIVE_INFINITY);
    }
}

package network;

import java.util.*;

public class ResourceAwareRouting {
    private Map<NetworkNode, Double> dist;
    private Map<NetworkNode, NetworkNode> previous;
    private PriorityQueue<NetworkNode> queue;

    // Weight Factors (CPU,Latency,Memory)
    private double alpha;
    private double beta;
    private double gamma;

    public ResourceAwareRouting(double alpha, double beta, double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.dist = new HashMap<>();
        this.previous = new HashMap<>();
        this.queue = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
    }

    public List<NetworkNode> findPath(NetworkGraph graph, String sourceId, String targetId){
        NetworkNode source = graph.getNode(sourceId);
        NetworkNode target = graph.getNode(targetId);
        if(source == null || target == null){
            return null;
        }

        // Initialize dist and previous for all nodes in the graph
        for (NetworkNode node: graph.getAllNodes()){
            dist.put(node, Double.POSITIVE_INFINITY);
            previous.put(node, source);
        }
        dist.put(source, 0.0);

        // Fill the queue with all the nodes in the graph
        queue.addAll(graph.getAllNodes());

        while (!queue.isEmpty()){
            NetworkNode u = queue.poll(); // get the node with the lowest known cost
            if(u.equals(target)) break;

            for (Edge edge: u.getConnections()){
                NetworkNode v = edge.getTo();

                // Cost(u--->v) = alpha*latency + CPU load*beta + memoryload*gamma
                double edgeCost = (alpha*edge.getLatency()) + (beta*v.getCurrentCpuLoad()) + (gamma*v.getCurrentMemLoad());
                double alt = dist.get(u) + edgeCost;

                if (alt < dist.get(v)){
                    dist.put(v, alt);
                    previous.put(v, u);

                    // Reinsert into the queue to update priority
                    queue.remove(v);
                    queue.offer(v);

                }

            }

        }

        List<NetworkNode> path = new ArrayList<>();
        for(NetworkNode at = target; at!=null; at=previous.get(at)){
            path.add(0, at);
        }

        if (path.isEmpty() || !path.get(0).equals(source)){
            return null;
        }

        return path;
    }

    public double getCostTo(NetworkNode node){
        return dist.getOrDefault(node,Double.POSITIVE_INFINITY);

    }

}

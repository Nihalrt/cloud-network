

import network.*;
import java.util.List;

public class main {
    public static void main(String[] args) {
        System.out.println("=== STARTING MAIN PROGRAM ===");

        // 1) Create the graph
        NetworkGraph graph = new NetworkGraph();

        // 2) Create 10 nodes: Node1..Node10
        NetworkNode[] nodes = new NetworkNode[11];
        for (int i = 1; i <= 10; i++) {
            nodes[i] = new NetworkNode("Node" + i);
            graph.addNode(nodes[i]);
        }

        // 3) Create ring edges: Node1->Node2->...->Node10
        // each with latency=1 => ring total is 9
        for (int i = 1; i < 10; i++) {
            graph.addEdge("Node" + i, "Node" + (i+1), 1);
        }

        // Add a direct edge from Node1->Node10 with latency=12
        // => Traditional sees ring=9 <12 => picks ring
        // => ResourceAware sees ring cost is huge (due to CPU=1, MEM=1) => picks direct
        graph.addEdge("Node1", "Node10", 12);

        // 4) Resource loads
        // Node1, Node10 => CPU=0, MEM=0
        nodes[1].setCurrentCpuLoad(0.0);
        nodes[1].setCurrentMemLoad(0.0);
        nodes[10].setCurrentCpuLoad(0.0);
        nodes[10].setCurrentMemLoad(0.0);

        // Node2..Node9 => CPU=1, MEM=1
        for (int i = 2; i < 10; i++) {
            nodes[i].setCurrentCpuLoad(1.0);
            nodes[i].setCurrentMemLoad(1.0);
        }

        // 5) ResourceAwareRouting
        ResourceAwareRouting router = new ResourceAwareRouting(1.0, 2.0, 1.5);
        System.out.println("\n=== RESOURCE-AWARE ROUTING ===");
        List<NetworkNode> resourcePath = router.findPath(graph, "Node1", "Node10");
        if (resourcePath != null) {
            System.out.println("ResourceAware Path: " + resourcePath);
            double totalCost = router.getCostTo(nodes[10]);
            System.out.println("ResourceAware Cost: " + totalCost);
        } else {
            System.out.println("No resource-aware path found.");
        }

        // 6) Traditional Shortest Path
        System.out.println("\n=== TRADITIONAL SHORTEST PATH ===");
        List<NetworkNode> shortestPath = graph.getShortestPath("Node1", "Node10");
        if (shortestPath != null) {
            System.out.println("Shortest Path: " + shortestPath);

            // Sum up latencies
            int totalLatency = 0;
            for (int i = 0; i < shortestPath.size() - 1; i++) {
                NetworkNode from = shortestPath.get(i);
                NetworkNode to = shortestPath.get(i + 1);
                for (Edge e : from.getConnections()) {
                    if (e.getTo().equals(to)) {
                        totalLatency += e.getLatency();
                        break;
                    }
                }
            }
            System.out.println("Total Latency: " + totalLatency);
        } else {
            System.out.println("No path found (Traditional).");
        }

        System.out.println("\n=== DONE! ===");
    }
}

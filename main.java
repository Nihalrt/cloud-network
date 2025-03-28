import core.*;
import network.*;
import simulation.*;
import storage.DataCenter;

import java.util.List;

public class main {

    public static void main(String[] args) {
        // Initialize DataCenter
        DataCenter dataCenter = new DataCenter();

        // Create Network Graph
        NetworkGraph graph = new NetworkGraph();

        // Create Compute Nodes as Network Nodes
        ComputeNode node1 = new ComputeNode("Node1", 16, 32768);
        ComputeNode node2 = new ComputeNode("Node2", 16, 32768);
        ComputeNode node3 = new ComputeNode("Node3", 16, 32768);

        // Create Virtual Machines and assign to Nodes
        VirtualMachine VM1 = new VirtualMachine("VM1", 4, 8192);
        VirtualMachine VM2 = new VirtualMachine("VM2", 4, 8192);
        VirtualMachine VM3 = new VirtualMachine("VM3", 4, 8192);
        VirtualMachine VM4 = new VirtualMachine("VM4", 4, 8192);

        node1.addVM(VM1);
        node1.addVM(VM2);
        node2.addVM(VM3);
        node3.addVM(VM4);

        // Add Compute Nodes to DataCenter and Graph
        dataCenter.addNode(node1);
        dataCenter.addNode(node2);
        dataCenter.addNode(node3);

        // Convert ComputeNode to NetworkNode
        NetworkNode networkNode1 = new NetworkNode("Node1");
        NetworkNode networkNode2 = new NetworkNode("Node2");
        NetworkNode networkNode3 = new NetworkNode("Node3");

        graph.addNode(networkNode1);
        graph.addNode(networkNode2);
        graph.addNode(networkNode3);

        // Create Edges (latency between nodes)
        graph.addEdge("Node1", "Node2", 2);
        graph.addEdge("Node2", "Node3", 3);
        graph.addEdge("Node1", "Node3", 4);

        // Set CPU and Memory Load Based on VM Utilization
        networkNode1.setCurrentCpuLoad(0.6); // 60% CPU usage
        networkNode1.setCurrentMemLoad(0.5); // 50% Memory usage

        networkNode2.setCurrentCpuLoad(0.2);
        networkNode2.setCurrentMemLoad(0.3);

        networkNode3.setCurrentCpuLoad(0.8); // High load → should avoid this node
        networkNode3.setCurrentMemLoad(0.7);

        // Define Weight Factors for Cost Function
        double alpha = 1.0; // Latency weight
        double beta = 2.0;  // CPU weight
        double gamma = 1.5; // Memory weight

        // Run ResourceAwareRouting
        ResourceAwareRouting router = new ResourceAwareRouting(alpha, beta, gamma);
        List<NetworkNode> resourceAwarePath = router.findPath(graph, "Node1", "Node3");

        System.out.println("\n=== Resource-Aware Routing Result ===");
        if (resourceAwarePath != null) {
            System.out.println("Path: " + resourceAwarePath);
            System.out.println("Total Cost: " + router.getCostTo(networkNode3));
        } else {
            System.out.println("No path found.");
        }

        // Compare with Traditional Shortest Path
        List<NetworkNode> shortestPath = graph.getShortestPath("Node1", "Node3");

        System.out.println("\n=== Traditional Shortest Path Result ===");
        if (shortestPath != null) {
            System.out.println("Path: " + shortestPath);
            int totalCost = 0;
            for (int i = 0; i < shortestPath.size() - 1; i++) {
                NetworkNode from = shortestPath.get(i);
                NetworkNode to = shortestPath.get(i + 1);
                for (Edge edge : from.getConnections()) {
                    if (edge.getTo().equals(to)) {
                        totalCost += edge.getLatency();
                        break;
                    }
                }
            }
            System.out.println("Total Latency: " + totalCost);
        } else {
            System.out.println("No path found.");
        }

        // Start Task Simulation
        EventSimulator simulator = new EventSimulator(dataCenter);
        simulator.runSimulation(10);
    }
}

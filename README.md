# Cloud Network Simulation with Resource-Aware Routing

This project is a simulation of a simplified cloud computing environment written in **Java**, focusing on **resource-aware communication between distributed nodes**. It demonstrates how an innovative routing algorithm can outperform traditional latency-based methods by considering not only **network latency** but also **CPU and memory load** of each node.

##  Project Objectives

1. Simulate a cloud network with multiple compute nodes and virtual machines.
2. Mimic real-world conditions where some nodes are heavily loaded.
3. Implement traditional Dijkstra's algorithm for shortest path based on latency.
4. Propose and implement a new DSA-based solution: Resource-Aware Routing (RAR).
5. Compare traditional vs resource-aware routing.
6. Demonstrate results clearly via a runnable Java program.

##  Concept and Innovation

### Problem:
In real-world cloud environments, choosing routes based solely on network latency can lead to overloading already-busy nodes. This causes bottlenecks, latency spikes, or even data loss.

### Proposed Solution:
We introduce a **Resource-Aware Routing Algorithm (RAR)** that extends Dijkstra's algorithm by including **node resource usage (CPU + Memory)** in the cost function. This ensures routing avoids over-utilized nodes, balancing traffic and improving reliability.

### Cost Function:
 - cost = α * latency + β * CPU_load + γ * MEM_load


Where:
- α: weight for latency (network delay)
- β: weight for CPU utilization
- γ: weight for memory utilization

By tuning these parameters, the system can balance between speed and load distribution.

## ⚙️ Components

### 1. `core/`
- `VirtualMachine.java`: Models a VM with limited CPU/memory and a task queue.
- `ComputeNode.java`: Hosts multiple VMs and simulates failures.
- `Task.java`: Represents jobs/tasks to be executed by VMs.

### 2. `network/`
- `NetworkNode.java`: Represents a compute node in the network, with current CPU and memory load.
- `Edge.java`: Represents connections (edges) between nodes with latency.
- `NetworkGraph.java`: Holds the graph of nodes and their connections.
- `ResourceAwareRouting.java`: Implements the custom DSA algorithm.

### 3. `scheduler/`
- `Scheduler.java`: Assigns tasks to the least-loaded VM.
- `LoadBalancer.java`: Supports the scheduler in finding optimal VMs.

### 4. `simulation/`
- `EventSimulator.java`: Simulates time ticks, generating and scheduling tasks over time.

### 5. `main/`
- `Main.java`: The entry point where we build the network, set up node loads, run both routing algorithms, and launch the simulation.

## Comparison of Routing Strategies

We simulate a network of **10 nodes** arranged in a ring:
- `Node1 -> Node2 -> ... -> Node10`
- Each ring edge has latency = 1
- `Node1 -> Node10` is a direct edge with latency = 10

We set `Node2` to `Node9` to be heavily loaded (CPU=1.0, MEM=1.0), and `Node1`, `Node10` to be lightly loaded (CPU=0.0, MEM=0.0).

### Traditional Shortest Path
- Chooses the ring path (`Node1 -> Node2 -> ... -> Node10`) because 9 < 10 (latency).
- Ignores CPU/memory usage, causing potential congestion.

### Resource-Aware Routing
- Chooses the direct edge (`Node1 -> Node10`) because total cost through ring is:
  - ~4.5 per overloaded node * 8 + final step = ~36
  - Direct = 10 + 0 + 0 = 10
- **Avoids overloaded nodes**, ensuring faster and safer communication.

##  Results and Observations

=== RESOURCE-AWARE ROUTING === Path: [Node1, Node10] Cost: 10.0

=== TRADITIONAL SHORTEST PATH === Path: [Node1, Node2, ..., Node10] Total Latency: 9

=== DONE! ===


### What This Proves:
- Resource-aware routing can outperform latency-only routing in cloud-like conditions.
- Our algorithm prevents overloading, improving system reliability.
- The cost model is flexible and tunable per use-case.

## 🌐 Real-World Relevance

- Cloud Providers (AWS, GCP, Azure) could benefit from this strategy to route traffic around busy zones.
- Edge computing, mesh networks, CDNs, and distributed systems can adopt similar routing intelligence.
- Extensible to include bandwidth, disk I/O, or security parameters in future versions.

## 🎓 Future Enhancements

- Dynamic load adjustment during runtime
- Graph visualization of path differences
- Support for unidirectional weighted links
- Machine-learning assisted weight tuning

##  How to Run

1. Clone the repo:
```bash
git clone https://github.com/<your-username>/cloud-network.git
```
2. Run
```bash
java main.Main
```







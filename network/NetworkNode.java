package network;

import java.util.*;

public class NetworkNode {
    private String id;
    private List<Edge> connections;
    private double currentCpuLoad;
    private double currentMemLoad;

    public NetworkNode(String id) {
        this.id = id;
        this.connections = new ArrayList<>();
        this.currentCpuLoad = 0.0;
        this.currentMemLoad = 0.0;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public List<Edge> getConnections() {
        return connections;
    }

    public void addConnection(Edge edge) {
        this.connections.add(edge);
    }

    public double getCurrentCpuLoad() {
        return currentCpuLoad;
    }

    public void setCurrentCpuLoad(double currentCpuLoad) {
        this.currentCpuLoad = Math.max(0.0, Math.min(1.0, currentCpuLoad));
    }

    public double getCurrentMemLoad() {
        return currentMemLoad;
    }

    public void setCurrentMemLoad(double currentMemLoad) {
        this.currentMemLoad = Math.max(0.0, Math.min(1.0, currentMemLoad));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NetworkNode that = (NetworkNode) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NetworkNode{" +
                "id='" + id + '\'' +
                ", cpuLoad=" + currentCpuLoad +
                ", memLoad=" + currentMemLoad +
                '}';
    }
}

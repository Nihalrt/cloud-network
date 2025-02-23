package network;


public class Edge {
    private NetworkNode from;
    private NetworkNode to;
    private int latency;

    public Edge(NetworkNode from, NetworkNode to, int latency) {
        this.from = from;
        this.to = to;
        this.latency = latency;
    }

    // getters & setters
    public NetworkNode getFrom() {
        return from;
    }
    public NetworkNode getTo() {
        return to;
    }
    public int getLatency() {
        return latency;
    }

    @Override
    public String toString() {
        return "Edge [from=" + from + ", to=" + to + ", latency=" + latency + "]";
    }


}

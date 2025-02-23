package network;

import java.util.*;

public class NetworkNode {

    private String Id;
    private List<Edge> connections;

    public NetworkNode(String id) {
        this.Id = id;
        this.connections = new ArrayList<>();
    }

    // getters and setters
    public String getId() {
        return Id;
    }
    public List<Edge> getConnections(){
        return connections;
    }
    public void addConnection(Edge edge) {
        this.connections.add(edge);
    }



}

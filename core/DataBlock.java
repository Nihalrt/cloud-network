package core;

import java.util.ArrayList;
import java.util.List;

public class DataBlock {
    private String blockId;
    private List<String> replicaNodeIds;

    public DataBlock(String blockId){
        this.blockId = blockId;
        this.replicaNodeIds = new ArrayList<>();
    }
    public String getBlockId() {
        return blockId;
    }
    public List<String> getReplicaNodeIds() {
        return replicaNodeIds;
    }
    public void addReplicaNodeId(String replicaNodeId) {
        replicaNodeIds.add(replicaNodeId);
    }
    @Override
    public String toString() {
        return "DataBlock [blockId=" + blockId + "]";
    }

}


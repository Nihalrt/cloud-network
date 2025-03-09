package storage;

import java.util.*;
public class DataBlock {

    private String blockId;
    private List<String> replicaNodeIds;

    public DataBlock(String blockId) {
        this.blockId = blockId;
        this.replicaNodeIds = new ArrayList<>();
    }
    public String getBlockId() {
        return blockId;
    }
    public List<String> getReplicaNodeIds(){
        return replicaNodeIds;
    }
    public void addReplica(String nodeId) {
        replicaNodeIds.add(nodeId);
    }

    @Override
    public String toString(){
        return "DataBlock{" + "blockId=" + blockId + ", replicaNodes=" + replicaNodeIds + '}';
    }


}

package storage;

import java.util.HashMap;
import java.util.Map;
public class DistributedStorage {
    private Map<String, DataBlock> storageBlocks;

    public DistributedStorage() {
        storageBlocks = new HashMap<>();
    }

    public void storeData(String blockId, String nodeId){
        DataBlock block = storageBlocks.get(blockId);
        if(block == null){
            block = new DataBlock(blockId);
            storageBlocks.put(blockId, block);
        }
        block.addReplica(nodeId);
        System.out.println("Stored data block " + blockId + " to " + nodeId);

    }
    public DataBlock retrieveData(String blockId) {
        return storageBlocks.get(blockId);
    }
}

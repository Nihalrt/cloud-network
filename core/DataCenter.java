package core;

import java.util.ArrayList;
import java.util.List;

public class DataCenter {
    private List<ComputeNode> nodes;


    public DataCenter(){
        nodes = new ArrayList<>();
    }

    public void addNode(ComputeNode node){
        nodes.add(node);
        System.out.println("Added node: " + node.getId());

    }

    public List<ComputeNode> getNodes(){
        return nodes;
    }

    public void scheduleTask(Task task){
        for (ComputeNode node: nodes){
            if (node.isActive() && !node.getVMs().isEmpty()){
                VirtualMachine vm = node.getVMs().get(0);
                vm.addTask(task);
                return;

            }
        }
        System.out.println("No task found");
    }


}

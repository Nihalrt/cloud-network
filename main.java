import core.*;
import simulation.*;
import storage.DataCenter;

public  class main{
    public static void main (String[] args){
        // Initialize DataCenter
        DataCenter dataCenter = new DataCenter();

        // Create and add a ComputeNode
        ComputeNode node1 = new ComputeNode("Node1", 16, 32768);
        dataCenter.addNode(node1);

        //Create some VMs and add them to the nodes
        VirtualMachine VM1 = new VirtualMachine("VM1", 4, 8192);
        VirtualMachine VM2 = new VirtualMachine("VM2", 4, 8192);
        node1.addVM(VM1);
        node1.addVM(VM2);

        //Start the simulation
        EventSimulator simulator = new EventSimulator(dataCenter);
        simulator.runSimulation(10);

    }
}

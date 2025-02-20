package core;

import java.util.*;

public class ComputeNode {
    private String id;
    private int totalcpu;
    private int totalmem;
    private List<VirtualMachine> vms;
    private boolean isActive;

    public ComputeNode(String id, int totalCpu, int totalMemory){
        this.id = id;
        this.totalcpu = totalCpu;
        this.totalmem = totalMemory;
        this.isActive = true;
        this.vms = new ArrayList<>();
    }

    //getters & setters
    public String getId(){
        return id;
    }
    public int getTotalcpu(){
        return totalcpu;
    }
    public int getTotalmem(){
        return totalmem;

    }
    public boolean isActive(){
        return isActive;
    }
    public void addVM(VirtualMachine vm){
        vms.add(vm);
    }
    public List<VirtualMachine> getVMs(){
        return vms;
    }
    public void simulateFailure(){
        isActive = false;
        System.out.println("Computer Node" + id + "has failed");

    }
    public void recover(){
        isActive = true;
        System.out.println("Computer Node" + id + "has recovered");
    }
    

}

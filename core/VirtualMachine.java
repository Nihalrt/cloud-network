package core;

import java.util.*;


public class VirtualMachine {
    private String id;
    private int availableCpu;
    private int availableMemory;
    private Queue<Task> taskQueue;

    public VirtualMachine(String id, int availableCpu, int availableMemory) {
        this.id = id;
        this.availableCpu = availableCpu;
        this.availableMemory = availableMemory;
        this.taskQueue = new LinkedList<>();
    }

    //getters & Setters

    public String getId(){
        return id;
    }
    public int getAvailableCpu(){
        return availableCpu;
    }
    public int getAvailableMemory(){
        return availableMemory;
    }

    public void addTask(Task task){
        taskQueue.offer(task);
        System.out.println("VM" + id + " received task " + task);
    }

    public void processTasks(){
        if (!taskQueue.isEmpty()){
            Task currentTask = taskQueue.peek();

            int newtime = currentTask.getExecutionTime()-1;
            if(newtime<=0){
                System.out.println("VM" + id + "finished processing task " + currentTask);
                taskQueue.poll();
            } else{

                System.out.println("VM" + id + "processing task: " + currentTask.getTaskId() + " (remaining time: " + newtime + ")");
            }


        }
    }

    public int getLoad(){
        return taskQueue.size();
    }




}

package scheduler;

import core.*;
import java.util.*;

public class Scheduler {
    private PriorityQueue<Task> taskQueue;

    public Scheduler() {
        taskQueue = new PriorityQueue<>();

    }
    public void addTask(Task task) {
        taskQueue.offer(task);
        System.out.println("Scheduler added task: " + task);

    }
    public void scheduleTasks(List<VirtualMachine> vms){
        while (!taskQueue.isEmpty()){
            Task task = taskQueue.poll();
            VirtualMachine selectedVM = LoadBalancer.getLeastLoadedVM(vms);
            if (selectedVM != null){
                selectedVM.addTask(task);
            }
            else{
                System.out.println("No VM available for task: " + task.getTaskId());
            }
        }
    }

}

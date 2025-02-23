package simulation;

import core.*;
import storage.DataCenter;
import java.util.*;

public class EventSimulator {
    private DataCenter dataCenter;
    private int tick = 0;
    private Random random;

    public EventSimulator(DataCenter dataCenter) {
        this.dataCenter = dataCenter;
        this.random = new Random();
    }
    public void runSimulation(int maxTicks) {
        for (tick = 0; tick < maxTicks; tick++) {
            System.out.println("Tick: " + tick);
            generateTaskEvent();
            processTasks();

            try {
                Thread.sleep(500); //pause half a second between the ticks

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();


            }
        }
    }
    private void generateTaskEvent() {
        if (random.nextBoolean()){
            String taskId = "T" + tick + "-" + random.nextInt(100);
            Task task = new Task(taskId, 1,512, random.nextInt(3) + 1, random.nextInt(10) );
            System.out.println("generateTaskEvent: " + taskId);
            dataCenter.scheduleTask(task);
        }
    }
    private void processTasks() {
        for (ComputeNode node: dataCenter.getNodes()) {
            if (node.isActive()){
                for(VirtualMachine vm: node.getVMs()){
                    vm.processTasks();
                }
            }

        }
    }

}

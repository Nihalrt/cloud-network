package core;


public class Task implements Comparable<Task> {
    private String taskId;
    private int requiredCpu;
    private int requiredMemory;
    private int executionTime; // in simulation ticks
    private int priority;

    public Task(String taskId, int requiredCpu, int requiredMemory, int executionTime, int priority) {
        this.taskId = taskId;
        this.requiredCpu = requiredCpu;
        this.requiredMemory = requiredMemory;
        this.executionTime = executionTime;
        this.priority = priority;

    }

    public String getTaskId(){
        return taskId;
    }
    public int getRequiredCpu(){
        return requiredCpu;

    }
    public int getRequiredMemory(){
        return requiredMemory;

    }
    public int getExecutionTime(){
        return executionTime;

    }
    public int getPriority(){
        return priority;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString(){
        return "Task[" + taskId + ", priority=" + priority + "]";
    }



}



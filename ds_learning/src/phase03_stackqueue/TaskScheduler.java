package phase03_stackqueue;
import java.util.Queue;
import java.util.LinkedList;
public class TaskScheduler {
    private Queue<String> queue = new LinkedList<>();

    void addTask(String task) {
        queue.offer(task);
    }
    void processNext() {
        System.out.println("处理任务: " + queue.poll());
    }
    void processAll() {
        while (!queue.isEmpty()) {
            processNext();
        }
    }
    public static void main(String[] args) {
        TaskScheduler ts = new TaskScheduler();
        ts.addTask("下载文件");
        ts.addTask("扫描病毒");
        ts.addTask("保存报告");
        ts.processAll();
    }



}

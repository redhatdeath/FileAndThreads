import data.Data;
import storage.Storage;
import work.WorkerInserter;
import work.WorkerRemover;

public class Main {

    private static final int size = 8;
    public static void main(String[] args) throws InterruptedException {
        Storage<Data> storage = Storage.getInstance();
        WorkerRemover prorab = new WorkerRemover(storage);
        prorab.startWork();
        Thread[] myThreads = new Thread[size];
        WorkerInserter[] works = new WorkerInserter[size];
        for (int i = 0; i < size; i++) {
            works[i] = new WorkerInserter("work-" + (i % 8) + ".txt", storage);
            myThreads[i] = new Thread(works[i]);
        }
        for (int i = 0; i < size; i++) {
            myThreads[i].start();
        }

        while (prorab.isRunning()) {
            Thread.currentThread().join(50);
        }
        System.out.println("Work is done!!!");
    }
}
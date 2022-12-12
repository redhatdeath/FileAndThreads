package work;

import data.Data;
import storage.Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class WorkerInserter implements Runnable {
    private static int threadId = 0;
    private final String fileName;
    private String workerName;
    private final Storage<Data> storage;

    public WorkerInserter(String fileName, Storage<Data> storage) {
        workerName = this.getClass().getSimpleName();
        Thread.currentThread().setName(workerName + (threadId++));
        this.fileName = fileName;
        this.storage = storage;
    }

    @Override
    public void run() {
        System.err.println(Thread.currentThread().getName() + " start work!!!");
        File file = new File(fileName);
        String readLine;
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            while ((readLine = bufferedReader.readLine()) != null) {
                storage.addData(new Data(fileName, readLine));
                Thread.sleep((long) (Math.random() * 251 + 50));
            }
        } catch (Exception ignored) {
        }
        System.err.println(Thread.currentThread().getName() + " stop work!");
    }
}

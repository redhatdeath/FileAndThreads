package storage;

import java.util.ArrayDeque;

public class Storage<T> {
    private final ArrayDeque<T> queue;
    // Singleton
    private static Storage instance;

    public static Storage getInstance() {
        if (instance == null)
            instance = new Storage();
        return instance;
    }

    // Constructor
    private Storage() {
        queue = new ArrayDeque<>();
    }

    // public methods
    public int getQueueSize() {
        return queue.size();
    }

    public void addData(T data) {
        synchronized (queue) {
            queue.add(data);
        }
    }

    public T removeData() {
        return queue.poll();
    }
}
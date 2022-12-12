package work;

import data.Data;
import storage.Storage;

import java.util.Timer;
import java.util.TimerTask;

public class WorkerRemover {
    private final Storage<Data> storage;
    private boolean running;

    public boolean isRunning() {
        return running;
    }

    private Timer myTimer;
    private WorkBySchedule work;

    private final int PERIOD_LONG = 1000;//mc
    private final int PERIOD_SHORT = 25;//mc
    private int period;
    private int period_old;
    private int emptyCounterOfQueueSize = 0;


    public WorkerRemover(Storage<Data> storage) {
        this.storage = storage;
        myTimer = new Timer();
        work = new WorkBySchedule();
        period = PERIOD_SHORT;
        period_old = PERIOD_LONG;
    }

    private class WorkBySchedule extends TimerTask {

        @Override
        public void run() {
            if (running) {
                workWithDataInQueue();
                updateQueueInfo(storage.getQueueSize());
                if (period == PERIOD_LONG && (++emptyCounterOfQueueSize) > 5)
                    stopWork();
            }
        }
    }

    private void updateQueueInfo(int queueSize) {
        period = queueSize == 0 ? PERIOD_LONG : PERIOD_SHORT;
        if (period != period_old)
            restartWork();
        period_old = period;
    }

    private void workWithDataInQueue() {
        if (storage.getQueueSize() > 0)
            System.out.println(storage.removeData());
        else System.err.println("Empty Queue!!!");
    }

    private void restartWork() {
        if (myTimer != null)
            myTimer.cancel();
        myTimer = new Timer();
        work = new WorkBySchedule();
        myTimer.scheduleAtFixedRate(work, 0, period);
        emptyCounterOfQueueSize = 0;
    }

    public void startWork() {
        myTimer.scheduleAtFixedRate(work, 0, period);
        running = true;
    }

    public void stopWork() {
        myTimer.cancel();
        running = false;
    }
}
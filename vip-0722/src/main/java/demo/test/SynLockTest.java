package demo.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class SynLockTest {
    static class SynRunner implements Runnable {
        private long v = 0;
        @Override
        public synchronized void run() {
            v = v + 1;
        }
    }
    static class LockRunner implements Runnable {
        private ReentrantLock lock = new ReentrantLock();
        private long v = 0;
        @Override
        public void run() {
            lock.lock();
            try {
                v = v + 1;
            } finally {
                lock.unlock();
            }
        }
    }
    static class Tester {
        private AtomicLong runCount = new AtomicLong(0);
        private AtomicLong start = new AtomicLong();
        private AtomicLong end = new AtomicLong();
        public Tester(final Runnable runner, int threadCount) {
            final ExecutorService pool = Executors.newFixedThreadPool(threadCount);
            Runnable task = new  Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                runner.run();
                                long count = runCount.incrementAndGet();
                                if (count == 1) {
                                    start.set(System.nanoTime());
                                } else if (count >= 10000000) {
                                    if (count == 10000000) {
                                        end.set(System.nanoTime());
                                        System.out.println(runner.getClass().getSimpleName() + ", cost: " + (end.longValue()- start.longValue())/1000000 + "ms");
                                    }
                                    pool.shutdown();
                                    return;
                                }
                            }
                        }
                    };
            for (int i = 0; i < threadCount; i++) {
                pool.submit(task);
            }
        }
    }
    public static void main(String[] args) {
        new Tester(new SynRunner(), 5000);
        new Tester(new LockRunner(), 5000);
    }
}

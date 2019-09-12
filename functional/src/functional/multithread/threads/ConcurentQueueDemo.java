package functional.multithread.threads;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConcurentQueueDemo {

    private static class QueueElement {
        private String value;
        public QueueElement(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    private static class QueueProducer implements Runnable {
        int intervalMs;
        int consumerCount;
        private BlockingQueue<QueueElement> queue;
        public QueueProducer(int intervalMs, int consumerCount, BlockingQueue<QueueElement> queue) {
            this.intervalMs = intervalMs;
            this.consumerCount = consumerCount;
            this.queue = queue;
        }

        public void run() {
            List<String> list = List.of("One", "Two", "Three", "Four", "Five");
            try {
                for (String e : list) {
                    Thread.sleep(intervalMs);
                    queue.put(new QueueElement(e));
                    System.out.println(e + " produced");
                }
                for (int i = 0; i < consumerCount; i++) {
                    queue.put(new QueueElement("Stop"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class QueueConsumer implements Runnable {
        private String name;
        private int intevalMs;
        private BlockingQueue<QueueElement> queue;
        public QueueConsumer(String name, int intevalMs, BlockingQueue<QueueElement> queue) {
            this.name = name;
            this.intevalMs = intevalMs;
            this.queue = queue;
        }
        public void run() {
            try {
                while(true) {
                    String value = queue.take().getValue();
                    if ("Stop".equals(value)) {
                        break;
                    }
                    System.out.println(value + " consumed by " + name);
                    Thread.sleep(intevalMs);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String...args) {
        BlockingQueue<QueueElement> queue = new ArrayBlockingQueue<>(5);
        QueueProducer producer = new QueueProducer(10,5, queue);
        QueueConsumer consumer = new QueueConsumer("MyQueue", 12, queue);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

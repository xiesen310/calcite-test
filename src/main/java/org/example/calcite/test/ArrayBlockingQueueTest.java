package org.example.calcite.test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueTest {
    public static void main(String[] args) {
        Queue queue = new ArrayBlockingQueue(10);
        for (int i = 0; i < 10; i++) {
            queue.add(i);
        }
        System.out.println(queue.size());
        queue.poll();
        queue.poll();
        System.out.println(queue.size());

    }
}

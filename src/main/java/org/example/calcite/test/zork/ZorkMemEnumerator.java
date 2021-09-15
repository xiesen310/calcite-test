package org.example.calcite.test.zork;

import org.apache.calcite.linq4j.Enumerator;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ZorkMemEnumerator<E> implements Enumerator<E> {
    private E current;

    public static Queue queue = new ArrayBlockingQueue(10);

    public ZorkMemEnumerator() {

    }
    
    public static void add(List<String> list) {
        queue.add(list);
    }

    @Override
    public E current() {
        return current;
    }

    @Override
    public boolean moveNext() {
        try {
            if (queue.size() == 0) {
                return false;
            }
            Object poll = queue.poll();

            // 如果是多列，这里要多个值
            current = (E) poll;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 出现异常走这里
     */
    @Override
    public void reset() {
        System.out.println("报错了兄弟，不支持此操作");
    }

    /**
     * InputStream流在这里关闭
     */
    @Override
    public void close() {

    }
}

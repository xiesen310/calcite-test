package org.example.calcite.test.tutorial;

import org.apache.calcite.linq4j.Enumerator;

public class TutorialEnumerator<E> implements Enumerator<E> {
    private E current;
    @Override
    public E current() {
        return current;
    }

    @Override
    public boolean moveNext() {

        return false;
    }

    @Override
    public void reset() {
        new UnsupportedOperationException();
    }

    @Override
    public void close() {

    }
}

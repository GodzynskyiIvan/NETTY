package com.godzynskyi.StatusObserver;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class FixedSizeStack<E> {

    private final Deque<E> deque;
    private final int size;

    public FixedSizeStack(int size) {
        this.deque = new LinkedList<E>();
        this.size = size;
    }

    public synchronized void add(E element) {
        if (deque.size() == size) {
            deque.removeLast();
        }
        deque.addFirst(element);
    }

    public synchronized List<E> getAll() {
        return new ArrayList<E>(deque);
    }
}

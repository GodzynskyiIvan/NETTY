package com.godzynskyi;

import com.godzynskyi.StatusObserver.FixedSizeStack;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class FixedSizeStackTest {
    public static void main(String[] args) {
        FixedSizeStack<Integer> list = new FixedSizeStack<Integer>(16);
        for(int i=1; i<=20; i++) {
            list.add(i);
        }

        System.out.println(list.getAll());
    }

}

package com.mitchellmebane.linkedlist;

import java.util.Random;

public class App {

    public static void main(final String[] args) {
        final LinkedList<Integer> il = new Random()
                .ints(20, 1, 100)
                .boxed()
                .sorted()
                .collect(LinkedList.collector());

        final StringBuilder sb = new StringBuilder(64);
        boolean first = true;
        for(int i : il) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(i);
            first = false;
        }

        System.out.println(String.format("# ints: %d", il.size()));
        System.out.println("Ints:");
        System.out.println(sb);
    }
}

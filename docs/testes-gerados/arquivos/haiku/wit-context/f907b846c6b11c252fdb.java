/*
 * @test
 * @summary LinkedList spliterator forEachRemaining: null action must throw NullPointerException and normal traversal must visit remaining elements in order
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.Spliterator;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachRemainingVisitsInOrder();
        testForEachRemainingOnEmptyList();
    }

    static void testNullActionThrowsNPE() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        Spliterator<Integer> s = list.spliterator();
        boolean thrown = false;
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
    }

    static void testForEachRemainingVisitsInOrder() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        StringBuilder sb = new StringBuilder();
        Spliterator<Integer> s = list.spliterator();
        s.forEachRemaining(x -> sb.append(x));
        if (!"123".equals(sb.toString())) {
            throw new RuntimeException("unexpected traversal: " + sb);
        }
        StringBuilder sb2 = new StringBuilder();
        s.forEachRemaining(x -> sb2.append(x));
        if (sb2.length() != 0) {
            throw new RuntimeException("elements visited twice: " + sb2);
        }
    }

    static void testForEachRemainingOnEmptyList() {
        LinkedList<Integer> list = new LinkedList<>();
        int[] count = new int[1];
        list.spliterator().forEachRemaining(x -> count[0]++);
        if (count[0] != 0) {
            throw new RuntimeException("action invoked on empty list");
        }
    }
}

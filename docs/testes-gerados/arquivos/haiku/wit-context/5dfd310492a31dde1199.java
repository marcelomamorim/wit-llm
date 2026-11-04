/*
 * @test
 * @summary Witup-generated checks for ConcurrentLinkedQueue.retainAll(Collection)
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<Integer> contents(ConcurrentLinkedQueue<Integer> q) {
        return new ArrayList<>(q);
    }

    public static void main(String[] args) {
        ConcurrentLinkedQueue<Integer> q1 = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2, 3, 4));
        boolean changed1 = q1.retainAll(Arrays.asList(2, 4, 6));
        check(changed1, "retainAll should report modification");
        check(contents(q1).equals(Arrays.asList(2, 4)), "retainAll should keep only elements present in c");

        ConcurrentLinkedQueue<Integer> q2 = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2));
        boolean changed2 = q2.retainAll(Arrays.asList(1, 2, 3));
        check(!changed2, "retainAll should return false when nothing is removed");
        check(contents(q2).equals(Arrays.asList(1, 2)), "queue content should be unchanged");

        ConcurrentLinkedQueue<Integer> q3 = new ConcurrentLinkedQueue<>(Arrays.asList(5, 6));
        boolean changed3 = q3.retainAll(Collections.<Integer>emptyList());
        check(changed3, "retainAll with empty collection should report modification");
        check(q3.isEmpty(), "retainAll with empty collection should clear the queue");

        ConcurrentLinkedQueue<Integer> q4 = new ConcurrentLinkedQueue<>();
        boolean changed4 = q4.retainAll(Collections.<Integer>emptyList());
        check(!changed4, "retainAll on empty queue should return false");
        check(q4.isEmpty(), "empty queue should remain empty");

        ConcurrentLinkedQueue<Integer> q5 = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2));
        boolean npe = false;
        try {
            q5.retainAll(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "retainAll(null) should throw NullPointerException");
        check(contents(q5).equals(Arrays.asList(1, 2)), "queue should be unchanged after NPE");
    }
}

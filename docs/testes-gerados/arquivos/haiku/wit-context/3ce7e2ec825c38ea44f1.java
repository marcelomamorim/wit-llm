/*
 * @test
 * @summary Tests LinkedBlockingDeque iterator forEachRemaining: null action, empty deque, ordering, partial consumption and batch boundary
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyDequeNoActions();
        testVisitsAllElementsInOrder();
        testVisitsRemainingAfterNext();
        testLargerThanBatchSize();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static void testNullActionThrowsNPE() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.addLast(1);
        Iterator<Integer> it = d.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) should throw NullPointerException");
    }

    private static void testEmptyDequeNoActions() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        Iterator<Integer> it = d.iterator();
        List<Integer> out = new ArrayList<>();
        it.forEachRemaining(out::add);
        check(out.isEmpty(), "empty deque should produce no actions");
    }

    private static void testVisitsAllElementsInOrder() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        for (int i = 1; i <= 5; i++) {
            d.addLast(i);
        }
        List<Integer> out = new ArrayList<>();
        d.iterator().forEachRemaining(out::add);
        List<Integer> expected = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            expected.add(i);
        }
        check(expected.equals(out), "expected " + expected + " but was " + out);
    }

    private static void testVisitsRemainingAfterNext() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        for (int i = 1; i <= 5; i++) {
            d.addLast(i);
        }
        Iterator<Integer> it = d.iterator();
        int first = it.next();
        check(first == 1, "first element should be 1");
        List<Integer> out = new ArrayList<>();
        it.forEachRemaining(out::add);
        List<Integer> expected = new ArrayList<>();
        for (int i = 2; i <= 5; i++) {
            expected.add(i);
        }
        check(expected.equals(out), "remaining elements expected " + expected + " but was " + out);
    }

    private static void testLargerThanBatchSize() {
        final int n = 200;
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        for (int i = 0; i < n; i++) {
            d.addLast(i);
        }
        List<Integer> out = new ArrayList<>();
        d.iterator().forEachRemaining(out::add);
        check(out.size() == n, "expected " + n + " elements but got " + out.size());
        for (int i = 0; i < out.size(); i++) {
            if (out.get(i) != i) {
                check(false, "element at index " + i + " should be " + i + " but was " + out.get(i));
                break;
            }
        }
    }
}

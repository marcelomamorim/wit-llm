/*
 * @test
 * @summary Witup-derived tests for LinkedTransferQueue.forEach null handling and element traversal
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testForEachNullActionThrowsNPE() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        boolean thrown = false;
        try {
            q.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testForEachNullActionOnEmptyQueueThrowsNPE() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        boolean thrown = false;
        try {
            q.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) on empty queue must throw NullPointerException");
    }

    static void testForEachVisitsElementsInFifoOrder() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>(Arrays.asList("x", "y", "z"));
        List<String> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.equals(Arrays.asList("x", "y", "z")),
              "forEach must visit elements in FIFO order, got " + seen);
    }

    static void testForEachOnEmptyQueueDoesNotInvokeAction() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        int[] calls = {0};
        q.forEach(s -> calls[0]++);
        check(calls[0] == 0, "forEach on empty queue must not invoke action");
    }

    static void testForEachDoesNotRemoveElements() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>(Arrays.asList("1", "2"));
        q.forEach(s -> { });
        check(q.size() == 2, "forEach must not remove elements");
        check("1".equals(q.peek()), "head must remain unchanged after forEach");
    }

    public static void main(String[] args) {
        testForEachNullActionThrowsNPE();
        testForEachNullActionOnEmptyQueueThrowsNPE();
        testForEachVisitsElementsInFifoOrder();
        testForEachOnEmptyQueueDoesNotInvokeAction();
        testForEachDoesNotRemoveElements();
    }
}

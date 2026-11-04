/*
 * @test
 * @summary Verifies that LinkedTransferQueue.add returns true and enqueues elements in FIFO order
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testAddReturnsTrue();
        testAddPreservesFifoOrder();
        testAddAfterCollectionConstructor();
        testAddUpdatesSizeAndPeek();
        testAddAllowsDuplicates();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddReturnsTrue() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        check(q.add(1) == true, "add(1) must return true");
        check(q.add(2) == true, "add(2) must return true");
        check(q.size() == 2, "size must be 2 after two adds");
    }

    static void testAddPreservesFifoOrder() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        check(q.poll() == 1, "first polled element must be 1");
        check(q.poll() == 2, "second polled element must be 2");
        check(q.poll() == 3, "third polled element must be 3");
        check(q.poll() == null, "queue must be empty after polling all elements");
    }

    static void testAddAfterCollectionConstructor() {
        List<Integer> initial = Arrays.asList(10, 20);
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(initial);
        check(q.add(30) == true, "add must return true on queue built from collection");
        check(q.size() == 3, "size must be 3 after adding to queue of 2");
        check(q.poll() == 10, "constructor elements must precede added element");
        check(q.poll() == 20, "second constructor element must be next");
        check(q.poll() == 30, "added element must be last");
    }

    static void testAddUpdatesSizeAndPeek() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        check(q.isEmpty(), "new queue must be empty");
        q.add(7);
        check(q.size() == 1, "size must be 1 after one add");
        check(q.peek() == 7, "peek must return the added element");
        check(!q.isEmpty(), "queue must not be empty after add");
    }

    static void testAddAllowsDuplicates() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        check(q.add(5) == true, "first add of 5 must return true");
        check(q.add(5) == true, "second add of 5 must return true");
        check(q.size() == 2, "duplicate elements must both be stored");
        check(q.poll() == 5, "first duplicate must be polled");
        check(q.poll() == 5, "second duplicate must be polled");
        check(q.poll() == null, "queue must be empty after polling duplicates");
    }
}

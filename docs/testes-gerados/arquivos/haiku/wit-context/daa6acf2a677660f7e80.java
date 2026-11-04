/*
 * @test
 * @summary Witup-derived checks for LinkedTransferQueue.add(E)
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        addNullThrowsNullPointerException();
        addNullLeavesQueueEmpty();
        addReturnsTrueAndMakesElementAvailable();
        addPreservesFifoOrder();
        addNullAfterElementsKeepsExistingContents();
    }

    static void addNullThrowsNullPointerException() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        try {
            q.add(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("add(null) did not throw NullPointerException");
    }

    static void addNullLeavesQueueEmpty() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        try {
            q.add(null);
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.isEmpty(), "queue not empty after failed add(null)");
        check(q.size() == 0, "size not 0 after failed add(null)");
        check(q.poll() == null, "poll not null after failed add(null)");
    }

    static void addReturnsTrueAndMakesElementAvailable() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        boolean result = q.add("a");
        check(result, "add(non-null) did not return true");
        check(q.size() == 1, "size not 1 after add");
        check("a".equals(q.peek()), "peek did not return added element");
    }

    static void addPreservesFifoOrder() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        check(q.add("x"), "add x returned false");
        check(q.add("y"), "add y returned false");
        check(q.add("z"), "add z returned false");
        check("x".equals(q.poll()), "FIFO violated: expected x");
        check("y".equals(q.poll()), "FIFO violated: expected y");
        check("z".equals(q.poll()), "FIFO violated: expected z");
        check(q.poll() == null, "queue should be empty");
    }

    static void addNullAfterElementsKeepsExistingContents() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        try {
            q.add(null);
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 1, "size changed after failed add(null)");
        check("a".equals(q.peek()), "head changed after failed add(null)");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

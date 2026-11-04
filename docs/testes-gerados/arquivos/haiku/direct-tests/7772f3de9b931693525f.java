/*
 * @test
 * @summary toArray returns a FIFO snapshot, including after wrap-around, and is independent of the queue
 * @run main ArrayBlockingQueueWitupTest
 */
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testEmpty();
        testFifoOrder();
        testWrapAround();
        testCopyIsIndependent();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmpty() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        Object[] a = q.toArray();
        check(a.length == 0, "empty queue must yield empty array");
    }

    static void testFifoOrder() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(5);
        q.offer("a");
        q.offer("b");
        q.offer("c");
        check(Arrays.equals(q.toArray(), new Object[]{"a", "b", "c"}),
              "toArray must return elements in FIFO order");
        check(q.size() == 3, "toArray must not remove elements");
    }

    static void testWrapAround() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        check(q.poll() == 1, "first poll must return 1");
        check(q.poll() == 2, "second poll must return 2");
        q.add(4);
        check(Arrays.equals(q.toArray(), new Object[]{3, 4}),
              "toArray must be correct when the contents wrap around the array");
    }

    static void testCopyIsIndependent() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.add("x");
        q.add("y");
        Object[] a = q.toArray();
        a[0] = "changed";
        check(q.peek().equals("x"), "modifying returned array must not affect queue");
        check(Arrays.equals(q.toArray(), new Object[]{"x", "y"}),
              "subsequent toArray must still reflect queue contents");
    }
}

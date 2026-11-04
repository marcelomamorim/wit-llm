/*
 * @test
 * @summary Witup-generated behavioral checks for ArrayBlockingQueue iterator hasNext
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> empty = new ArrayBlockingQueue<>(2);
        Iterator<Integer> emptyIt = empty.iterator();
        check(!emptyIt.hasNext(), "empty queue iterator should report no next element");
        check(!emptyIt.hasNext(), "repeated hasNext on exhausted empty iterator should return false");

        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.add(1);
        q.add(2);
        Iterator<Integer> it = q.iterator();
        check(it.hasNext(), "hasNext should be true when first element is available");
        check(it.hasNext(), "hasNext should be idempotent before next()");
        check(it.next().intValue() == 1, "first element should be 1");
        check(it.hasNext(), "hasNext should be true when second element is available");
        check(it.next().intValue() == 2, "second element should be 2");
        check(!it.hasNext(), "hasNext should be false after last element");
        check(!it.hasNext(), "repeated hasNext after exhaustion should return false");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

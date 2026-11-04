/* @test    @summary Test for DelayQueue(Collection<? extends E> c) constructor with null collection    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.Collection;
import java.util.NoSuchElementException;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullCollection();
        testConstructorWithEmptyCollection();
    }

    private static void testConstructorWithNullCollection() {
        try {
            new DelayQueue<Object>(null);
            System.err.println("Failed: Expected NullPointerException for null collection");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testConstructorWithEmptyCollection() {
        Collection<Object> emptyCollection = new java.util.ArrayList<>();
        DelayQueue<Object> dq = new DelayQueue<>(emptyCollection);
        if (dq.size() != 0) {
            System.err.println("Failed: DelayQueue should be empty when constructed with empty collection");
        }
    }
}

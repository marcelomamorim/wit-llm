/*
 * @test
 * @summary Tests for ArrayBlockingQueue class methods
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNextWithNullItem();
        testNextWithItem();
    }

    private static void testNextWithNullItem() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        // Simulate internal state: nextItem == null to trigger NoSuchElementException
        // Since nextItem is private, we simulate the behavior by calling next() on an empty queue
        try {
            ArrayBlockingQueue<String>.Itr iterator = queue.new Itr();
            // forcibly set nextItem to null via reflection if needed, but avoiding reflection, so just test empty
            iterator.next();
            // Expect NoSuchElementException
            System.out.println("testNextWithNullItem: Failed - exception not thrown");
        } catch (NoSuchElementException e) {
            System.out.println("testNextWithNullItem: Passed");
        } catch (Exception e) {
            System.out.println("testNextWithNullItem: Failed - unexpected exception: " + e);
        }
    }

    private static void testNextWithItem() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("element");
        try {
            ArrayBlockingQueue<String>.Itr iterator = queue.new Itr();
            String item = iterator.next();
            if ("element".equals(item)) {
                System.out.println("testNextWithItem: Passed");
            } else {
                System.out.println("testNextWithItem: Failed - unexpected item: " + item);
            }
        } catch (Exception e) {
            System.out.println("testNextWithItem: Failed - exception: " + e);
        }
    }
}

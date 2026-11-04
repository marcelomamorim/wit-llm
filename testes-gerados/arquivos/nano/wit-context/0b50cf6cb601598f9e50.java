/*
 * @test
 * @summary Tests for ArrayBlockingQueue behavior and exception paths
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.forEach(null);
            System.out.println("FAIL: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null action");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testForEachWithValidAction() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i).append(",");
        try {
            queue.forEach(consumer);
            String result = sb.toString();
            if (result.contains("1,") && result.contains("2,") && result.contains("3,")) {
                System.out.println("PASS: forEach executed successfully with consumer");
            } else {
                System.out.println("FAIL: forEach did not process all elements correctly");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception during forEach: " + t);
        }
    }
}

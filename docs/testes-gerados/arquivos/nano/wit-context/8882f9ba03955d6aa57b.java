/* @test    @summary Test for PriorityBlockingQueue.forEach method to verify behavior with null and non-null Consumers    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithValidConsumer();
    }

    static void testForEachWithNullConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.forEach(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testForEachWithValidConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i).append(",");
        try {
            queue.forEach(consumer);
            String result = sb.toString();
            if (result.contains("1,") && result.contains("2,") && result.contains("3,")) {
                System.out.println("PASS: Consumer applied correctly, output: " + result);
            } else {
                System.out.println("FAIL: Consumer did not process all elements, output: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during forEach with valid consumer: " + e);
        }
    }
}

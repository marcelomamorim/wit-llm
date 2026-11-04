/*
 * @test
 * @summary Tests for LinkedBlockingQueue.forEach method behavior and exception paths
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithNonNullAction();
    }

    public static void testForEachWithNullAction() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        try {
            queue.forEach(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testForEachWithNonNullAction() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e).append(",");
        try {
            queue.forEach(consumer);
            String result = sb.toString();
            if ("1,2,3,".equals(result)) {
                System.out.println("PASS: forEach executed successfully with output: " + result);
            } else {
                System.out.println("FAIL: Unexpected output: " + result);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception during forEach: " + t);
        }
    }
}

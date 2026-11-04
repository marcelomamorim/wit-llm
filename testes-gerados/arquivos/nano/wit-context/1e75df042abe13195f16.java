/*
 * @test
 * @summary Test for LinkedTransferQueue.forEach method, focusing on null input handling.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithValidConsumer();
    }

    public static void testForEachWithNullConsumer() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        try {
            queue.forEach(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testForEachWithValidConsumer() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e).append(",");
        queue.forEach(consumer);
        String result = sb.toString();
        if (result.equals("1,2,3,")) {
            System.out.println("PASS: forEach applied consumer correctly");
        } else {
            System.out.println("FAIL: Unexpected result: " + result);
        }
    }
}

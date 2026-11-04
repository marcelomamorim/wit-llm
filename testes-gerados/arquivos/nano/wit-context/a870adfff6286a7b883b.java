/* @test    @summary Test DelayQueue.put method for normal and exceptional behavior    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testPutWithNullElement();
        testPutNormalElement();
    }

    private static void testPutWithNullElement() {
        DelayQueue<Object> dq = new DelayQueue<>();
        try {
            dq.put(null);
            // Expecting NullPointerException as DelayQueue does not accept nulls
            System.out.println("FAIL: Expected NullPointerException for null element in put()");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null element in put()");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null element in put(): " + e);
        }
    }

    private static void testPutNormalElement() {
        DelayQueue<String> dq = new DelayQueue<>();
        String element = "testElement";
        try {
            dq.put(element);
            if (dq.size() == 1 && dq.peek() != null && dq.peek().equals(element)) {
                System.out.println("PASS: Element successfully added via put()");
            } else {
                System.out.println("FAIL: Element not found after put()");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during put(): " + e);
        }
    }
}

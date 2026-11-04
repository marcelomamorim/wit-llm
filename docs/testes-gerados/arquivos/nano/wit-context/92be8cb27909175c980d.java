/* @test    @summary Test for DelayQueue.add(E e) method to observe behavior with null element and normal element    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testAddNullElement();
        testAddNormalElement();
    }

    private static void testAddNullElement() {
        DelayQueue<Object> dq = new DelayQueue<>();
        try {
            dq.add(null);
            System.out.println("FAIL: Expected NullPointerException when adding null");
        } catch (NullPointerException expected) {
            System.out.println("PASS: NullPointerException thrown as expected when adding null");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception when adding null: " + e);
        }
    }

    private static void testAddNormalElement() {
        DelayQueue<String> dq = new DelayQueue<>();
        boolean result = dq.add("testElement");
        if (result && dq.size() == 1 && dq.peek().equals("testElement")) {
            System.out.println("PASS: Successfully added normal element");
        } else {
            System.out.println("FAIL: Adding normal element did not behave as expected");
        }
    }
}

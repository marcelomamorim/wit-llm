/* @test    @summary Test putLast method of LinkedBlockingDeque for null input and normal operation    @run main LinkedBlockingDequeWitupTest_P18db1c110db5 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeWitupTest_P18db1c110db5 {

    public static void main(String[] args) throws InterruptedException {
        testPutLastNull();
        testPutLastNormal();
    }

    private static void testPutLastNull() throws InterruptedException {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.putLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when putting null element");
        }
    }

    private static void testPutLastNormal() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        deque.putLast(element);
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after putLast");
        }
        String retrieved = deque.takeFirst();
        if (!element.equals(retrieved)) {
            throw new AssertionError("Element retrieved does not match element inserted");
        }
    }
}

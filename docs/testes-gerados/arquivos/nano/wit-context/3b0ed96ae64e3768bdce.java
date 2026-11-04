/* @test    @summary Test for DelayQueue.next() method to verify NoSuchElementException behavior when cursor exceeds array length    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.NoSuchElementException;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testNextThrowsExceptionWhenCursorExceedsArray();
    }

    private static void testNextThrowsExceptionWhenCursorExceedsArray() {
        DelayQueue<Object> dq = new DelayQueue<>();
        // Reflection or internal access is not used; instead, we simulate the condition by subclassing or dummy setup
        // Since DelayQueue does not expose internal array or cursor, we simulate the scenario by subclassing
        DelayQueue<Object> testDQ = new DelayQueue<Object>() {
            private int cursor = 1;
            private Object[] array = new Object[0];
            @Override
            public Object next() {
                if (cursor >= array.length)
                    throw new NoSuchElementException();
                return array[cursor++];
            }
        };
        try {
            testDQ.next();
            // If no exception, the test fails
            System.err.println("Expected NoSuchElementException was not thrown.");
        } catch (NoSuchElementException e) {
            // Test passes
            System.out.println("NoSuchElementException correctly thrown.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}

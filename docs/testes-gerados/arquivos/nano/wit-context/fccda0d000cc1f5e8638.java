/*
 * @test
 * @summary Test for LinkedBlockingDeque.push method, covering null and non-null inputs.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPushWithNull();
        testPushWithObject();
    }

    private static void testPushWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        try {
            deque.push(null);
            // Expecting NullPointerException, test passes if exception is thrown
            System.out.println("testPushWithNull: Failed - no exception thrown");
        } catch (NullPointerException expected) {
            System.out.println("testPushWithNull: Passed");
        } catch (Throwable t) {
            System.out.println("testPushWithNull: Failed - unexpected exception: " + t);
        }
    }

    private static void testPushWithObject() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "element";
        try {
            deque.push(element);
            if (deque.peekFirst().equals(element)) {
                System.out.println("testPushWithObject: Passed");
            } else {
                System.out.println("testPushWithObject: Failed - element not at front");
            }
        } catch (Throwable t) {
            System.out.println("testPushWithObject: Failed - exception: " + t);
        }
    }
}

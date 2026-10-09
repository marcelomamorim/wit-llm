/*
 * @test
 * @summary Tests the putLast method of LinkedBlockingDeque for expected behaviors and exceptions.
 * @run main LinkedBlockingDequeWitupTest_Pf4f90145f75d
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeWitupTest_Pf4f90145f75d {

    public static void main(String[] args) throws Exception {
        testPutLastWithNull();
        testPutLastWithObject();
    }

    static void testPutLastWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        try {
            deque.putLast(null);
            // Expect NullPointerException
            System.err.println("Failed: Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            System.out.println("Passed: NullPointerException thrown as expected when argument is null");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    static void testPutLastWithObject() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        String testElement = "test";
        deque.putLast(testElement);
        Object retrieved = deque.takeFirst();
        if (testElement.equals(retrieved)) {
            System.out.println("Passed: Element correctly added and retrieved from deque");
        } else {
            System.err.println("Failed: Retrieved element does not match added element");
        }
    }
}

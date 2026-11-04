/*
 * @test
 * @summary Test for Collections.putIfAbsent method to observe exception behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testPutIfAbsentThrowsException();
    }

    public static void testPutIfAbsentThrowsException() {
        try {
            Collections.putIfAbsent("key", "value");
            System.out.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception type: " + t.getClass().getName());
        }
    }
}

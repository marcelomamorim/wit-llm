/*
 * @test
 * @summary Test for Collections.remove method to observe exception behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        Object key = new Object();
        try {
            Collections.remove(key);
            System.out.println("Test failed: no exception thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException caught as expected.");
        } catch (Exception e) {
            System.out.println("Test failed: unexpected exception: " + e);
        }
    }
}

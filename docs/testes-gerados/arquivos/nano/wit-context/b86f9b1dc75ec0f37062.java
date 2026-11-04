/*
 * @test
 * @summary Test for Collections.remove(Object, Object) method.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsUnsupportedOperationException();
    }

    public static void testRemoveThrowsUnsupportedOperationException() {
        Object key = new Object();
        Object value = new Object();
        try {
            Collections.remove(key, value);
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}

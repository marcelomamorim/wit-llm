/* @test    @summary Test Collections.remove method for UnsupportedOperationException    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsUnsupported();
    }

    public static void testRemoveThrowsUnsupported() {
        Object key = new Object();
        Object value = new Object();
        try {
            Collections.remove(key, value);
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException was correctly thrown.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}

/* @test    @summary Test for Collections.remove(Object, Object) method to ensure it throws UnsupportedOperationException.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Object key = new Object();
        Object value = new Object();
        try {
            boolean result = Collections.remove(key, value);
            // Since the method always throws, this line should not be reached.
            System.out.println("Unexpected success: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        }
    }
}

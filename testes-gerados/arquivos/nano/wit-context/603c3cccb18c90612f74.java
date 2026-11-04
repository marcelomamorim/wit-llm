/*
 * @test
 * @summary Test for KeyValueHolder.setValue(V) method to verify it throws UnsupportedOperationException.
 * @run main KeyValueHolderWitupTest
 */

import java.util.KeyValueHolder;

public class KeyValueHolderWitupTest {

    public static void main(String[] args) {
        testSetValueThrowsException();
    }

    public static void testSetValueThrowsException() {
        java.util.Map.Entry<String, Integer> entry = new KeyValueHolder<>("key", 1);
        try {
            entry.setValue(2);
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: UnsupportedOperationException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}

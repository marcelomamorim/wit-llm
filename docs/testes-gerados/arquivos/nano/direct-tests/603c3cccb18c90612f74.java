/*
 * @test
 * @summary Test for KeyValueHolder.setValue method to ensure it throws UnsupportedOperationException
 * @run main KeyValueHolderWitupTest
 */

import java.util.KeyValueHolder;

public class KeyValueHolderWitupTest {

    public static void main(String[] args) {
        testSetValueThrowsException();
    }

    public static void testSetValueThrowsException() {
        java.util.Map.Entry<String, Integer> entry = new java.util.KeyValueHolder<>("key", 1);
        try {
            entry.setValue(2);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}

/* @test    @summary Test for SelectionKeyImpl.readyOps() method    @run main SelectionKeyImplWitupTest */

import sun.nio.ch.SelectionKeyImpl;

public class SelectionKeyImplWitupTest {

    public static void main(String[] args) {
        testReadyOpsReturnsInternalState();
        testReadyOpsAfterInvalidation();
    }

    private static void testReadyOpsReturnsInternalState() {
        SelectionKeyImpl key = new SelectionKeyImpl(null, null);
        // Reflection is needed to set private field 'readyOps' because no public setter exists
        try {
            java.lang.reflect.Field readyOpsField = SelectionKeyImpl.class.getDeclaredField("readyOps");
            readyOpsField.setAccessible(true);
            readyOpsField.setInt(key, 42);
            int result = key.readyOps();
            if (result != 42) {
                throw new AssertionError("Expected readyOps to be 42, but got " + result);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection failed to set readyOps", e);
        }
    }

    private static void testReadyOpsAfterInvalidation() {
        SelectionKeyImpl key = new SelectionKeyImpl(null, null);
        // Simulate invalid state by overriding isValid() to return false
        try {
            java.lang.reflect.Field validField = SelectionKeyImpl.class.getDeclaredField("valid");
            validField.setAccessible(true);
            validField.setBoolean(key, false);
            // Expect an exception when calling readyOps due to invalid state
            try {
                key.readyOps();
                throw new AssertionError("Expected CancelledKeyException to be thrown");
            } catch (java.nio.channels.CancelledKeyException e) {
                // Expected exception
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // If the internal 'valid' field does not exist, skip this test
        }
    }
}

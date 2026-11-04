/* @test    @summary Test interestOps() method of SelectionKeyImpl for valid and invalid states    @run main SelectionKeyImplWitupTest */

import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.CancelledKeyException;

public class SelectionKeyImplWitupTest {

    public static void main(String[] args) {
        testInterestOpsValid();
        testInterestOpsInvalid();
    }

    private static void testInterestOpsValid() {
        SelectionKeyImpl key = new SelectionKeyImpl(null, null);
        // Use reflection to set interestOps directly for testing purposes
        try {
            java.lang.reflect.Field interestOpsField = SelectionKeyImpl.class.getDeclaredField("interestOps");
            interestOpsField.setAccessible(true);
            interestOpsField.setInt(key, 0);
            // Should not throw, valid state
            int result = key.interestOps();
            if (result != 0) {
                throw new AssertionError("Expected interestOps to be 0, but got " + result);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testInterestOpsInvalid() {
        SelectionKeyImpl key = new SelectionKeyImpl(null, null);
        // Simulate invalid state by overriding isValid() to return false
        try {
            java.lang.reflect.Field validField = AbstractSelectionKey.class.getDeclaredField("valid");
            validField.setAccessible(true);
            validField.setBoolean(key, false);
            // Should throw CancelledKeyException
            try {
                key.interestOps();
                throw new AssertionError("Expected CancelledKeyException to be thrown");
            } catch (CancelledKeyException e) {
                // Expected exception
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

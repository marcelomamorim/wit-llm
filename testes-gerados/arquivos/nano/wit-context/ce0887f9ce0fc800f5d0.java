/*
 * @test
 * @summary Tests the constructor of TextOutputCallback for valid and invalid inputs.
 * @run main TextOutputCallbackWitupTest
 */

import javax.security.auth.callback.TextOutputCallback;

public class TextOutputCallbackWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidConstructor();
    }

    static void testValidConstructor() {
        String message = "Test message";
        int[] validTypes = {0, 1, 2};
        for (int type : validTypes) {
            try {
                TextOutputCallback callback = new TextOutputCallback(type, message);
                assert callback.getMessageType() == type : "Message type mismatch";
                assert callback.getMessage().equals(message) : "Message content mismatch";
            } catch (Exception e) {
                throw new AssertionError("Valid constructor threw exception for type: " + type, e);
            }
        }
    }

    static void testInvalidConstructor() {
        String message = "Test message";
        int[] invalidTypes = {-1, 3, 999};
        for (int type : invalidTypes) {
            try {
                new TextOutputCallback(type, message);
                throw new AssertionError("Expected IllegalArgumentException for messageType: " + type);
            } catch (IllegalArgumentException e) {
                // Expected exception
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception for messageType: " + type, e);
            }
        }
        // Test null message
        try {
            new TextOutputCallback(0, null);
            throw new AssertionError("Expected IllegalArgumentException for null message");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null message", e);
        }
        // Test empty message
        try {
            new TextOutputCallback(0, "");
            throw new AssertionError("Expected IllegalArgumentException for empty message");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty message", e);
        }
    }
}

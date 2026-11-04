/*
 * @test
 * @summary Test TextOutputCallback constructor with valid and invalid inputs
 * @run main javax.security.auth.callback.TextOutputCallbackWitupTest
 */

public class TextOutputCallbackWitupTest {
    public static void main(String[] args) {
        testValidMessageTypes();
        testInvalidMessageType();
        testNullMessage();
        testEmptyMessage();
    }

    private static void testValidMessageTypes() {
        javax.security.auth.callback.TextOutputCallback toc;

        toc = new javax.security.auth.callback.TextOutputCallback(javax.security.auth.callback.TextOutputCallback.INFORMATION, "Info message");
        if (toc.getMessageType() != javax.security.auth.callback.TextOutputCallback.INFORMATION)
            throw new RuntimeException("Expected INFORMATION message type");
        if (!"Info message".equals(toc.getMessage()))
            throw new RuntimeException("Expected message 'Info message'");

        toc = new javax.security.auth.callback.TextOutputCallback(javax.security.auth.callback.TextOutputCallback.WARNING, "Warning message");
        if (toc.getMessageType() != javax.security.auth.callback.TextOutputCallback.WARNING)
            throw new RuntimeException("Expected WARNING message type");
        if (!"Warning message".equals(toc.getMessage()))
            throw new RuntimeException("Expected message 'Warning message'");

        toc = new javax.security.auth.callback.TextOutputCallback(javax.security.auth.callback.TextOutputCallback.ERROR, "Error message");
        if (toc.getMessageType() != javax.security.auth.callback.TextOutputCallback.ERROR)
            throw new RuntimeException("Expected ERROR message type");
        if (!"Error message".equals(toc.getMessage()))
            throw new RuntimeException("Expected message 'Error message'");
    }

    private static void testInvalidMessageType() {
        try {
            new javax.security.auth.callback.TextOutputCallback(-1, "Invalid");
            throw new RuntimeException("Expected IllegalArgumentException for invalid messageType");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        try {
            new javax.security.auth.callback.TextOutputCallback(3, "Invalid");
            throw new RuntimeException("Expected IllegalArgumentException for invalid messageType");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testNullMessage() {
        try {
            new javax.security.auth.callback.TextOutputCallback(javax.security.auth.callback.TextOutputCallback.INFORMATION, null);
            throw new RuntimeException("Expected IllegalArgumentException for null message");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEmptyMessage() {
        try {
            new javax.security.auth.callback.TextOutputCallback(javax.security.auth.callback.TextOutputCallback.INFORMATION, "");
            throw new RuntimeException("Expected IllegalArgumentException for empty message");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test TextOutputCallback constructor for valid and invalid inputs
 * @run main TextOutputCallbackWitupTest
 */

import javax.security.auth.callback.TextOutputCallback;

public class TextOutputCallbackWitupTest {
    public static void main(String[] args) {
        testValidMessageTypes();
        testInvalidMessageTypes();
        testNullMessage();
        testEmptyMessage();
    }

    private static void testValidMessageTypes() {
        int[] validTypes = {
            TextOutputCallback.INFORMATION,
            TextOutputCallback.WARNING,
            TextOutputCallback.ERROR
        };
        String testMessage = "Test message";

        for (int type : validTypes) {
            TextOutputCallback toc = new TextOutputCallback(type, testMessage);
            if (toc.getMessageType() != type) {
                throw new RuntimeException("getMessageType() returned unexpected value");
            }
            if (!testMessage.equals(toc.getMessage())) {
                throw new RuntimeException("getMessage() returned unexpected value");
            }
        }
    }

    private static void testInvalidMessageTypes() {
        int[] invalidTypes = { -1, 3, Integer.MIN_VALUE, Integer.MAX_VALUE };
        String testMessage = "Valid message";

        for (int type : invalidTypes) {
            boolean thrown = false;
            try {
                new TextOutputCallback(type, testMessage);
            } catch (IllegalArgumentException iae) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for messageType: " + type);
            }
        }
    }

    private static void testNullMessage() {
        boolean thrown = false;
        try {
            new TextOutputCallback(TextOutputCallback.INFORMATION, null);
        } catch (IllegalArgumentException iae) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for null message");
        }
    }

    private static void testEmptyMessage() {
        boolean thrown = false;
        try {
            new TextOutputCallback(TextOutputCallback.WARNING, "");
        } catch (IllegalArgumentException iae) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for empty message");
        }
    }
}

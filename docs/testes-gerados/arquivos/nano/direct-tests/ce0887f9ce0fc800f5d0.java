/* @test    @summary Test constructor of TextOutputCallback for valid and invalid inputs    @run main TextOutputCallbackWitupTest */

import javax.security.auth.callback.TextOutputCallback;

public class TextOutputCallbackWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidConstructorNullMessage();
        testInvalidConstructorEmptyMessage();
        testInvalidConstructorInvalidType();
    }

    static void testValidConstructor() {
        int[] validTypes = {TextOutputCallback.INFORMATION, TextOutputCallback.WARNING, TextOutputCallback.ERROR};
        String message = "Test message";
        for (int type : validTypes) {
            TextOutputCallback callback = new TextOutputCallback(type, message);
            assert callback.getMessageType() == type : "Message type mismatch";
            assert callback.getMessage().equals(message) : "Message content mismatch";
        }
    }

    static void testInvalidConstructorNullMessage() {
        try {
            new TextOutputCallback(TextOutputCallback.INFORMATION, null);
            assert false : "Expected IllegalArgumentException for null message";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidConstructorEmptyMessage() {
        try {
            new TextOutputCallback(TextOutputCallback.WARNING, "");
            assert false : "Expected IllegalArgumentException for empty message";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidConstructorInvalidType() {
        int invalidType = 99;
        try {
            new TextOutputCallback(invalidType, "Invalid type test");
            assert false : "Expected IllegalArgumentException for invalid messageType";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

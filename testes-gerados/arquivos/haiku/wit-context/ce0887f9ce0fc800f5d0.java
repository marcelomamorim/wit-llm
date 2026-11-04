/*
 * @test
 * @summary Witup-derived tests for TextOutputCallback constructor argument validation
 * @run main TextOutputCallbackWitupTest
 */

import javax.security.auth.callback.TextOutputCallback;

public class TextOutputCallbackWitupTest {

    public static void main(String[] args) {
        testValidArguments();
        testInvalidMessageTypeRejected();
        testNullMessageRejected();
        testEmptyMessageRejected();
        testGetters();
    }

    static void testValidArguments() {
        int[] types = {TextOutputCallback.INFORMATION, TextOutputCallback.WARNING, TextOutputCallback.ERROR};
        for (int type : types) {
            TextOutputCallback cb = new TextOutputCallback(type, "msg");
            if (cb.getMessageType() != type) {
                throw new RuntimeException("messageType mismatch for " + type);
            }
            if (!"msg".equals(cb.getMessage())) {
                throw new RuntimeException("message mismatch for " + type);
            }
        }
    }

    static void testInvalidMessageTypeRejected() {
        int[] invalid = {-1, 3, 100, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int type : invalid) {
            try {
                new TextOutputCallback(type, "msg");
                throw new RuntimeException("expected IllegalArgumentException for type " + type);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
    }

    static void testNullMessageRejected() {
        try {
            new TextOutputCallback(TextOutputCallback.INFORMATION, null);
            throw new RuntimeException("expected IllegalArgumentException for null message");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testEmptyMessageRejected() {
        try {
            new TextOutputCallback(TextOutputCallback.WARNING, "");
            throw new RuntimeException("expected IllegalArgumentException for empty message");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testGetters() {
        TextOutputCallback cb = new TextOutputCallback(TextOutputCallback.ERROR, "failure");
        if (cb.getMessageType() != TextOutputCallback.ERROR) {
            throw new RuntimeException("getMessageType mismatch");
        }
        if (!"failure".equals(cb.getMessage())) {
            throw new RuntimeException("getMessage mismatch");
        }
    }
}

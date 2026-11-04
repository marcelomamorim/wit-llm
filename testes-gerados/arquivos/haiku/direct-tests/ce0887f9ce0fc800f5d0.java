/*
 * @test
 * @summary Tests TextOutputCallback constructor validation and getters
 * @run main TextOutputCallbackWitupTest
 */

import javax.security.auth.callback.TextOutputCallback;

public class TextOutputCallbackWitupTest {

    public static void main(String[] args) {
        testValidTypesAreStored();
        testMessageIsStored();
        testInvalidMessageTypeThrows();
        testNullMessageThrows();
        testEmptyMessageThrows();
    }

    static void testValidTypesAreStored() {
        int[] types = { TextOutputCallback.INFORMATION, TextOutputCallback.WARNING, TextOutputCallback.ERROR };
        for (int type : types) {
            TextOutputCallback cb = new TextOutputCallback(type, "msg");
            check(cb.getMessageType() == type, "messageType mismatch for " + type);
        }
    }

    static void testMessageIsStored() {
        String msg = "hello";
        TextOutputCallback cb = new TextOutputCallback(TextOutputCallback.WARNING, msg);
        check(msg.equals(cb.getMessage()), "message mismatch");
    }

    static void testInvalidMessageTypeThrows() {
        expectIllegalArgument(-1, "x");
        expectIllegalArgument(3, "x");
        expectIllegalArgument(Integer.MAX_VALUE, "x");
    }

    static void testNullMessageThrows() {
        expectIllegalArgument(TextOutputCallback.INFORMATION, null);
        expectIllegalArgument(TextOutputCallback.ERROR, null);
    }

    static void testEmptyMessageThrows() {
        expectIllegalArgument(TextOutputCallback.INFORMATION, "");
        expectIllegalArgument(TextOutputCallback.WARNING, "");
    }

    static void expectIllegalArgument(int type, String msg) {
        try {
            new TextOutputCallback(type, msg);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for type=" + type + " msg=" + msg);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

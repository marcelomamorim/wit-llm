/*
 * @test
 * @summary Tests Objects.requireNonNull(T, String) null check and message propagation
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testReturnsSameNonNullReference();
        testNullThrowsNpeWithMessage();
        testNullThrowsNpeWithNullMessage();
        testNonNullIgnoresMessage();
    }

    static void testReturnsSameNonNullReference() {
        String s = "value";
        String r = Objects.requireNonNull(s, "msg");
        if (r != s) {
            throw new AssertionError("expected same reference returned");
        }
    }

    static void testNullThrowsNpeWithMessage() {
        try {
            Objects.requireNonNull(null, "custom message");
        } catch (NullPointerException e) {
            if (!"custom message".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new AssertionError("NullPointerException not thrown");
    }

    static void testNullThrowsNpeWithNullMessage() {
        try {
            Objects.requireNonNull(null, (String) null);
        } catch (NullPointerException e) {
            if (e.getMessage() != null) {
                throw new AssertionError("expected null message, got: " + e.getMessage());
            }
            return;
        }
        throw new AssertionError("NullPointerException not thrown");
    }

    static void testNonNullIgnoresMessage() {
        Integer v = Integer.valueOf(42);
        Integer r = Objects.requireNonNull(v, null);
        if (r != v) {
            throw new AssertionError("expected same reference returned");
        }
    }
}

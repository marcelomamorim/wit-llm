/*
 * @test
 * @summary Tests observable behavior of Objects.requireNonNull(T, String)
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testReturnsSameReferenceForNonNullString();
        testReturnsSameReferenceForNonNullObject();
        testThrowsNpeWithMessageForNull();
        testThrowsNpeWithNullMessageForNull();
        testMessageIgnoredForNonNull();
    }

    static void testReturnsSameReferenceForNonNullString() {
        String s = "value";
        String r = Objects.requireNonNull(s, "msg");
        if (r != s) {
            throw new RuntimeException("Expected same reference for non-null String");
        }
    }

    static void testReturnsSameReferenceForNonNullObject() {
        Object o = new Object();
        Object r = Objects.requireNonNull(o, "msg");
        if (r != o) {
            throw new RuntimeException("Expected same reference for non-null Object");
        }
    }

    static void testThrowsNpeWithMessageForNull() {
        String expected = "expected-message";
        try {
            Objects.requireNonNull(null, expected);
        } catch (NullPointerException e) {
            if (!expected.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NPE message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }

    static void testThrowsNpeWithNullMessageForNull() {
        try {
            Objects.requireNonNull(null, (String) null);
        } catch (NullPointerException e) {
            if (e.getMessage() != null) {
                throw new RuntimeException("Expected null NPE message, got: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }

    static void testMessageIgnoredForNonNull() {
        String s = "x";
        try {
            String r = Objects.requireNonNull(s, null);
            if (r != s) {
                throw new RuntimeException("Expected same reference when message is null");
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("NPE must not be thrown for non-null obj", e);
        }
    }
}

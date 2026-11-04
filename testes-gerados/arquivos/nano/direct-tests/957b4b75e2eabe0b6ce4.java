/*
 * @test
 * @summary Test String.join with multiple CharSequence elements
 * @run main StringWitupTest
 */

import java.lang.String;
import java.util.Objects;

public class StringWitupTest {

    public static void main(String[] args) {
        testJoinWithMultipleElements();
        testJoinWithSingleElement();
        testJoinWithEmptyElements();
        testJoinWithNullDelimiter();
        testJoinWithNullElements();
    }

    static void testJoinWithMultipleElements() {
        String result = String.join(", ", "apple", "banana", "cherry");
        if (!"apple, banana, cherry".equals(result)) {
            throw new AssertionError("Expected 'apple, banana, cherry' but got: " + result);
        }
    }

    static void testJoinWithSingleElement() {
        String result = String.join(", ", "single");
        if (!"single".equals(result)) {
            throw new AssertionError("Expected 'single' but got: " + result);
        }
    }

    static void testJoinWithEmptyElements() {
        String result = String.join(", ");
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string but got: " + result);
        }
    }

    static void testJoinWithNullDelimiter() {
        try {
            String.join(null, "a", "b");
            throw new AssertionError("Expected NullPointerException for null delimiter");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testJoinWithNullElements() {
        try {
            String.join(", ", (CharSequence[]) null);
            throw new AssertionError("Expected NullPointerException for null elements array");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            String.join(", ", "a", null, "c");
            // Should not throw, as null is a valid CharSequence, treated as "null"
            String result = String.join(", ", "a", null, "c");
            if (!"a, null, c".equals(result)) {
                throw new AssertionError("Expected 'a, null, c' but got: " + result);
            }
        } catch (NullPointerException e) {
            throw new AssertionError("Did not expect NullPointerException here", e);
        }
    }
}

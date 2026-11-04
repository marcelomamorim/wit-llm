/*
 * @test
 * @summary Test java.lang.String.join(CharSequence, CharSequence...) method
 * @run main StringWitupTest
 */

import java.lang.String;
import java.util.Objects;
import java.util.StringJoiner;

public class StringWitupTest {
    public static void main(String[] args) {
        testJoinWithNormalInputs();
        testJoinWithEmptyElements();
        testJoinWithEmptyDelimiter();
        testJoinWithNullDelimiter();
        testJoinWithNullElements();
        testJoinWithNullElementInside();
    }

    private static void testJoinWithNormalInputs() {
        String result = String.join(",", "a", "b", "c");
        if (!"a,b,c".equals(result)) {
            throw new RuntimeException("Failed: join with normal inputs");
        }
    }

    private static void testJoinWithEmptyElements() {
        String result = String.join(",");
        if (!"".equals(result)) {
            throw new RuntimeException("Failed: join with empty elements");
        }
    }

    private static void testJoinWithEmptyDelimiter() {
        String result = String.join("", "a", "b", "c");
        if (!"abc".equals(result)) {
            throw new RuntimeException("Failed: join with empty delimiter");
        }
    }

    private static void testJoinWithNullDelimiter() {
        try {
            String.join(null, "a", "b");
            throw new RuntimeException("Failed: join with null delimiter did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testJoinWithNullElements() {
        try {
            String.join(",", (CharSequence[])null);
            throw new RuntimeException("Failed: join with null elements did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testJoinWithNullElementInside() {
        String result = String.join(",", "a", null, "c");
        if (!"a,null,c".equals(result)) {
            throw new RuntimeException("Failed: join with null element inside");
        }
    }
}

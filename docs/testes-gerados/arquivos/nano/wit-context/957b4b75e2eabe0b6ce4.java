/*
 * @test
 * @summary Tests for String.join method to verify exception paths and behavior.
 * @run main StringWitupTest
 */

import java.lang.String;
import java.util.Objects;

public class StringWitupTest {

    public static void main(String[] args) {
        testJoin_NullDelimiter();
        testJoin_NullElements();
    }

    public static void testJoin_NullDelimiter() {
        try {
            String result = String.join(null, "a", "b");
            // If no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null delimiter");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown for null delimiter");
        }
    }

    public static void testJoin_NullElements() {
        try {
            String result = String.join(",", (CharSequence[]) null);
            System.err.println("Failed: Expected NullPointerException for null elements array");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null elements array");
        }
        // Also test with null elements and non-null delimiter
        try {
            String result = String.join(",", (CharSequence[]) null);
            System.err.println("Failed: Expected NullPointerException for null elements with non-null delimiter");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null elements with non-null delimiter");
        }
    }
}

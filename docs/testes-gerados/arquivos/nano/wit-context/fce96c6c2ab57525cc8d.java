/*
 * @test
 * @summary Tests for java.util.StringJoiner constructor with null parameters.
 * @run main StringJoinerWitupTest
 */

import java.util.StringJoiner;

public class StringJoinerWitupTest {

    public static void main(String[] args) {
        testNullPrefix();
        testNullDelimiterWithNonNullPrefix();
        testNullSuffixWithNonNullDelimiterAndPrefix();
    }

    private static void testNullPrefix() {
        try {
            new StringJoiner(
                "-" // delimiter
            );
            // No exception expected here, as constructor with only delimiter does not throw for null prefix
        } catch (NullPointerException e) {
            throw new AssertionError("Unexpected NullPointerException for null prefix", e);
        }
    }

    private static void testNullDelimiterWithNonNullPrefix() {
        try {
            new StringJoiner(
                null // delimiter
            );
            // Expecting NullPointerException due to null delimiter
            throw new AssertionError("Expected NullPointerException for null delimiter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testNullSuffixWithNonNullDelimiterAndPrefix() {
        try {
            new StringJoiner(
                ",", // delimiter
                "prefix", // prefix
                null // suffix
            );
            // Expecting NullPointerException due to null suffix
            throw new AssertionError("Expected NullPointerException for null suffix");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}

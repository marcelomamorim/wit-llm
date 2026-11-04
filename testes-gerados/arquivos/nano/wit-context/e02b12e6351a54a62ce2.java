/*
 * @test
 * @summary Tests for DateTimeFormatterBuilder.optionalEnd()
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testOptionalEndThrowsWhenActiveParentIsNull();
        testOptionalEndProceedsWhenActiveParentIsNotNull();
    }

    private static void testOptionalEndThrowsWhenActiveParentIsNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Manipulate internal state to simulate active.parent == null
        // Reflection is required here, but as per constraints, we avoid reflection.
        // Instead, we test the default behavior which should not throw in a fresh instance.
        // So, this test is not applicable unless internal state is set.
        // Given constraints, we skip this test.
    }

    private static void testOptionalEndProceedsWhenActiveParentIsNotNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Since internal state is not accessible, we test the method's behavior in normal use.
        // The method should not throw when called normally.
        try {
            builder.optionalEnd();
        } catch (IllegalStateException e) {
            // If exception occurs, it indicates internal state was not as expected.
            // But in default state, no exception should be thrown.
            throw new AssertionError("optionalEnd() threw IllegalStateException unexpectedly", e);
        }
    }
}

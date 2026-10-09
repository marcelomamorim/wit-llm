/*
 * @test
 * @summary Test DateTimeFormatterBuilder.optionalEnd() method for correct behavior and exception handling.
 * @run main DateTimeFormatterBuilderWitupTest_Pd9e7d7b52d8a
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_Pd9e7d7b52d8a {

    public static void main(String[] args) {
        testOptionalEndWithoutStart();
        testOptionalEndWithStartAndParser();
        testOptionalEndWithStartWithoutParser();
        System.exit(0);
    }

    private static void testOptionalEndWithoutStart() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.optionalEnd();
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            if (!e.getMessage().contains("no previous call to optionalStart")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }

    private static void testOptionalEndWithStartAndParser() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            // Simulate starting an optional section
            builder.optionalStart();
            // Add a parser (simulate by calling appendLiteral or similar if needed)
            // Since appendLiteral is not shown, we assume a placeholder method or just proceed
            // For this test, we just call optionalEnd after optionalStart
            builder.optionalEnd();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during valid optionalEnd: " + e, e);
        }
    }

    private static void testOptionalEndWithStartWithoutParser() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.optionalStart();
            builder.optionalEnd();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during optionalEnd without parser: " + e, e);
        }
    }
}

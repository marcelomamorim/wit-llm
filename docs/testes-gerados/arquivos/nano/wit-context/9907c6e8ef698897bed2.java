/*
 * @test
 * @summary Tests for DateTimeFormatterBuilder.appendOptional method.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendOptionalWithNullFormatter();
        testAppendOptionalWithValidFormatter();
    }

    private static void testAppendOptionalWithNullFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendOptional(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
            assert e.getMessage().equals("formatter") : "Unexpected message: " + e.getMessage();
        }
    }

    private static void testAppendOptionalWithValidFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            builder.appendOptional(formatter);
            // No exception expected, verify builder is not null
            if (builder == null) {
                throw new AssertionError("Builder is null after appendOptional");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}

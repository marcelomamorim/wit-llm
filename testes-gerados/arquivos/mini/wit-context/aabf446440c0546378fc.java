/*
 * @test
 * @summary Test appendChronologyText(TextStyle) method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendChronologyTextNull();
        testAppendChronologyTextValid();
        System.out.println("All tests passed.");
    }

    private static void testAppendChronologyTextNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendChronologyText(null);
            throw new AssertionError("Expected NullPointerException for null textStyle");
        } catch (NullPointerException e) {
            // Expected exception
            if (!"textStyle".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testAppendChronologyTextValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Use a valid TextStyle enum constant
        TextStyle style = TextStyle.FULL;
        DateTimeFormatterBuilder result = builder.appendChronologyText(style);
        if (result != builder) {
            throw new AssertionError("appendChronologyText should return the same builder instance");
        }
    }
}

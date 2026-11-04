/*
 * @test
 * @summary Test appendChronologyText method of DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendChronologyTextReturnsSameBuilder();
        testAppendChronologyTextRejectsNull();
    }

    private static void testAppendChronologyTextReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendChronologyText(TextStyle.FULL);
        if (result != builder) {
            throw new AssertionError("appendChronologyText should return the same builder instance");
        }
    }

    private static void testAppendChronologyTextRejectsNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendChronologyText(null);
            throw new AssertionError("appendChronologyText should throw NullPointerException when textStyle is null");
        } catch (NullPointerException ex) {
            // expected
            if (!"textStyle".equals(ex.getMessage())) {
                throw new AssertionError("NullPointerException message should be 'textStyle'");
            }
        }
    }
}

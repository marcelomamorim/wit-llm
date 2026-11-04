/*
 * @test
 * @summary Test appendChronologyText with null TextStyle to verify NullPointerException
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendChronologyTextNull();
    }

    public static void testAppendChronologyTextNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendChronologyText(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}

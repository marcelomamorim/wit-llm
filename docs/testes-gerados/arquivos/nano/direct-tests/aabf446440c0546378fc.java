/*
 * @test
 * @summary Test appendChronologyText method of DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendChronologyText();
    }

    public static void testAppendChronologyText() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendChronologyText(TextStyle.FULL);
            builder.appendChronologyText(TextStyle.SHORT);
            builder.appendChronologyText(TextStyle.NARROW);
            System.out.println("appendChronologyText executed without exceptions.");
        } catch (Exception e) {
            throw new AssertionError("Exception thrown during appendChronologyText", e);
        }
    }
}

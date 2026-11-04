/*
 * @test
 * @summary Tests appendChronologyText(TextStyle) null check and fluent return value
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullTextStyleThrowsNpe();
        testReturnsSameBuilderForFull();
        testReturnsSameBuilderForShort();
    }

    static void testNullTextStyleThrowsNpe() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean thrown = false;
        try {
            builder.appendChronologyText(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null textStyle");
        }
    }

    static void testReturnsSameBuilderForFull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendChronologyText(TextStyle.FULL);
        if (result != builder) {
            throw new RuntimeException("appendChronologyText should return this builder");
        }
    }

    static void testReturnsSameBuilderForShort() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendChronologyText(TextStyle.SHORT);
        if (result != builder) {
            throw new RuntimeException("appendChronologyText should return this builder for SHORT");
        }
    }
}

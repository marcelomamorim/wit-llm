/*
 * @test
 * @summary Test appendLocalized method of DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest_P1d6477880d49
 */
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

public class DateTimeFormatterBuilderWitupTest_P1d6477880d49 {
    public static void main(String[] args) {
        testAppendLocalizedWithDateStyle();
        testAppendLocalizedWithTimeStyle();
        testAppendLocalizedWithBothStyles();
        testAppendLocalizedWithNullStyles();
    }

    static void testAppendLocalizedWithDateStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLocalized(FormatStyle.SHORT, null);
        System.out.println("appendLocalized with dateStyle only passed");
    }

    static void testAppendLocalizedWithTimeStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLocalized(null, FormatStyle.MEDIUM);
        System.out.println("appendLocalized with timeStyle only passed");
    }

    static void testAppendLocalizedWithBothStyles() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLocalized(FormatStyle.FULL, FormatStyle.LONG);
        System.out.println("appendLocalized with both styles passed");
    }

    static void testAppendLocalizedWithNullStyles() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(null, null);
            System.out.println("Error: Expected exception for null styles not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly caught exception for null styles");
        }
    }
}

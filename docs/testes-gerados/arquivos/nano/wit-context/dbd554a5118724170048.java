/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.toFormatter(Locale) method.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testToFormatterWithNullLocale();
        testToFormatterWithValidLocale();
    }

    private static void testToFormatterWithNullLocale() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.toFormatter(null);
            System.out.println("FAIL: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null locale");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testToFormatterWithValidLocale() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            Locale locale = Locale.US;
            builder.toFormatter(locale);
            System.out.println("PASS: toFormatter with valid locale executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid locale: " + e);
        }
    }
}

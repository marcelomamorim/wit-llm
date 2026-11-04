/*
 * @test
 * @summary Test for DateTimeFormatter.ofLocalizedTime method.
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testOfLocalizedTime_NullInput();
        testOfLocalizedTime_ValidInput();
    }

    public static void testOfLocalizedTime_NullInput() {
        try {
            DateTimeFormatter.ofLocalizedTime(null);
            System.err.println("FAIL: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testOfLocalizedTime_ValidInput() {
        try {
            FormatStyle style = FormatStyle.SHORT;
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(style);
            if (formatter != null) {
                System.out.println("PASS: Formatter created successfully with style: " + style);
            } else {
                System.err.println("FAIL: Formatter is null for style: " + style);
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}

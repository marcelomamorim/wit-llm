/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.append(DateTimeFormatter)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendWithNullFormatter();
        testAppendWithValidFormatter();
    }

    private static void testAppendWithNullFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.append((DateTimeFormatter) null);
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            if ("formatter".equals(e.getMessage())) {
                System.out.println("Passed: NullPointerException thrown as expected");
            } else {
                System.err.println("Failed: NullPointerException message mismatch");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
            System.exit(1);
        }
    }

    private static void testAppendWithValidFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            builder.append(formatter);
            System.out.println("Passed: append with valid formatter");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
            System.exit(1);
        }
    }
}

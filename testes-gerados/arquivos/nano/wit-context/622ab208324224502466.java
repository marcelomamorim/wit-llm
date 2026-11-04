/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.appendPattern(String pattern)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendPatternWithNull();
        testAppendPatternWithValidString();
    }

    private static void testAppendPatternWithNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendPattern(null);
            System.out.println("FAIL: Expected NullPointerException for null pattern");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null pattern");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testAppendPatternWithValidString() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        String pattern = "yyyy-MM-dd";
        try {
            DateTimeFormatterBuilder result = builder.appendPattern(pattern);
            if (result == builder) {
                System.out.println("PASS: appendPattern returned the same builder instance");
            } else {
                System.out.println("FAIL: appendPattern did not return the same builder instance");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}

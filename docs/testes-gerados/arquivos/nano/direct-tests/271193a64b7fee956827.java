/*
 * @test
 * @summary Test parse method of DateTimeFormatterBuilder for normal and edge cases
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseContext;
import java.lang.reflect.Method;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        testParseNormal();
        testParseWithNullContext();
        testParseWithInvalidPosition();
    }

    private static void testParseNormal() throws Exception {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Method parseMethod = DateTimeFormatterBuilder.class.getDeclaredMethod("parse", DateTimeParseContext.class, CharSequence.class, int.class);
        parseMethod.setAccessible(true);
        // Create a dummy context with minimal implementation
        DateTimeParseContext context = new DateTimeParseContext();
        CharSequence text = "2021-12-25";
        int position = 0;
        int result = (int) parseMethod.invoke(builder, context, text, position);
        // Since the method returns an int, check if it is non-negative
        if (result < 0) {
            throw new AssertionError("Expected non-negative parse result, got: " + result);
        }
        System.out.println("testParseNormal passed with result: " + result);
    }

    private static void testParseWithNullContext() throws Exception {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Method parseMethod = DateTimeFormatterBuilder.class.getDeclaredMethod("parse", DateTimeParseContext.class, CharSequence.class, int.class);
        parseMethod.setAccessible(true);
        try {
            parseMethod.invoke(builder, null, "text", 0);
            throw new AssertionError("Expected NullPointerException for null context");
        } catch (Exception e) {
            if (!(e.getCause() instanceof NullPointerException)) {
                throw new AssertionError("Expected NullPointerException, got: " + e, e);
            }
        }
        System.out.println("testParseWithNullContext passed");
    }

    private static void testParseWithInvalidPosition() throws Exception {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Method parseMethod = DateTimeFormatterBuilder.class.getDeclaredMethod("parse", DateTimeParseContext.class, CharSequence.class, int.class);
        parseMethod.setAccessible(true);
        DateTimeParseContext context = new DateTimeParseContext();
        CharSequence text = "2021-12-25";
        int invalidPosition = -1;
        int result = (int) parseMethod.invoke(builder, context, text, invalidPosition);
        if (result != invalidPosition) {
            throw new AssertionError("Expected parse to return input position for invalid position, got: " + result);
        }
        System.out.println("testParseWithInvalidPosition passed with result: " + result);
    }
}

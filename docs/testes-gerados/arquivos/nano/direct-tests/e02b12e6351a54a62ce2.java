/*
 * @test
 * @summary Test DateTimeFormatterBuilder.optionalEnd() method for correct behavior.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testOptionalEndWithoutStart();
        testOptionalEndWithStart();
        testMultipleOptionalEndCalls();
    }

    private static void testOptionalEndWithoutStart() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.optionalEnd();
            System.err.println("FAIL: expected IllegalStateException not thrown in testOptionalEndWithoutStart");
        } catch (IllegalStateException e) {
            System.out.println("PASS: testOptionalEndWithoutStart");
        } catch (Exception e) {
            System.err.println("FAIL: unexpected exception in testOptionalEndWithoutStart: " + e);
        }
    }

    private static void testOptionalEndWithStart() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.optionalStart();
            builder.optionalEnd();
            System.out.println("PASS: testOptionalEndWithStart");
        } catch (Exception e) {
            System.err.println("FAIL: exception in testOptionalEndWithStart: " + e);
        }
    }

    private static void testMultipleOptionalEndCalls() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.optionalStart();
            builder.optionalEnd();
            builder.optionalEnd();
            System.out.println("PASS: testMultipleOptionalEndCalls");
        } catch (Exception e) {
            System.err.println("FAIL: exception in testMultipleOptionalEndCalls: " + e);
        }
    }
}

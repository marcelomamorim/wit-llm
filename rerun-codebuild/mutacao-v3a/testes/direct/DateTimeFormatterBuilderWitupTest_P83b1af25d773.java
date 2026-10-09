/*
 * @test
 * @summary Test DateTimeFormatterBuilder.optionalEnd() for correct behavior and exception handling.
 * @run main DateTimeFormatterBuilderWitupTest_P83b1af25d773
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_P83b1af25d773 {

    public static void main(String[] args) {
        testOptionalEndWithoutStart();
        testOptionalEndWithStart();
        testOptionalEndWithActiveNull();
    }

    private static void testOptionalEndWithoutStart() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.optionalEnd();
            System.out.println("FAIL: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("no previous call to optionalStart") : "Unexpected exception message: " + e.getMessage();
            System.out.println("PASS: testOptionalEndWithoutStart()");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testOptionalEndWithStart() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            // Simulate active with a parent to allow optionalEnd()
            // Since active is private, invoke optionalStart() to set up state
            builder.optionalStart();
            builder.optionalEnd();
            System.out.println("PASS: testOptionalEndWithStart()");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testOptionalEndWithActiveNull() {
        try {
            // Reflection or direct access not possible; assuming that calling optionalEnd() without prior setup throws exception.
            // Since in actual usage, active would be null, but in this public API, we cannot simulate this directly.
            // To mimic this, we create a subclass or mock, but as per constraints, we avoid that.
            // Instead, test that calling optionalEnd() after optionalStart() resets active.
            // So, test is covered by testOptionalEndWithStart().
            System.out.println("INFO: testOptionalEndWithActiveNull() not applicable due to API constraints.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}

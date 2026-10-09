/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.optionalEnd method behavior
 * @run main DateTimeFormatterBuilderWitupTest_P83b1af25d773
 */

import static java.time.temporal.ChronoField.*;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_P83b1af25d773 {

    public static void main(String[] args) {
        testOptionalEndWhenActiveParentIsNull();
        testOptionalEndWhenActiveParentIsNotNull();
    }

    private static void testOptionalEndWhenActiveParentIsNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            // Manipulate internal state to simulate active.parent == null
            // A direct access isn't possible; assume default construction has parent == null
            // Call optionalEnd() without prior optionalStart() should throw
            builder.optionalEnd();
            System.err.println("Fail: Expected IllegalStateException not thrown in null parent case.");
            System.exit(1);
        } catch (IllegalStateException e) {
            // Expected exception
            System.out.println("Pass: Exception thrown as expected when active.parent is null.");
        } catch (Exception e) {
            System.err.println("Fail: Unexpected exception: " + e);
            System.exit(1);
        }
    }

    private static void testOptionalEndWhenActiveParentIsNotNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Start an optional segment to set active.parent
        builder.optionalStart();
        // Add a parser or do something to modify state if needed (skipped here for simplicity)
        // End the optional segment
        try {
            builder.optionalEnd();
            System.out.println("Pass: optionalEnd() executed successfully after optionalStart().");
        } catch (Exception e) {
            System.err.println("Fail: Unexpected exception during optionalEnd(): " + e);
            System.exit(1);
        }
    }
}

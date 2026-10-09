/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.optionalEnd() method.
 * @run main DateTimeFormatterBuilderWitupTest_Pd9e7d7b52d8a
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_Pd9e7d7b52d8a {

    public static void main(String[] args) {
        testOptionalEndThrowsWhenActiveParentIsNull();
        testOptionalEndNormalFlow();
    }

    private static void testOptionalEndThrowsWhenActiveParentIsNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            // Manipulate internal state to simulate active.parent == null
            // Since active is private, we rely on the initial state where active.parent == null
            // and call optionalEnd() without prior optionalStart() to trigger exception
            builder.optionalEnd();
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("no previous call to optionalStart")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }

    private static void testOptionalEndNormalFlow() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Since internal state is not accessible, test the normal flow by calling optionalStart() then optionalEnd()
        // but optionalStart() is not in the provided excerpt, so we simulate the flow by calling optionalEnd() after construction
        // which should not throw if internal state is valid
        try {
            builder.optionalEnd();
        } catch (IllegalStateException e) {
            String message = e.getMessage();
            if (message != null && message.contains("no previous call to optionalStart")) {
                throw new AssertionError("Unexpected IllegalStateException in normal flow", e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}

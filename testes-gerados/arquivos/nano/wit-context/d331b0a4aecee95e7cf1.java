/*
 * @test
 * @summary Test for Calendar.setCalendarType method behavior.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetCalendarTypeWithValidType();
        testSetCalendarTypeWithInvalidType();
        testSetCalendarTypeWithOverride();
    }

    private static void testSetCalendarTypeWithValidType() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setCalendarType("gregorian");
            assert builder.build() != null : "Builder should build Calendar object";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid type", e);
        }
    }

    private static void testSetCalendarTypeWithInvalidType() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setCalendarType("invalidType");
            assert false : "Expected IllegalArgumentException for invalid type";
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testSetCalendarTypeWithOverride() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setCalendarType("gregorian");
            builder.setCalendarType("gregorian"); // should succeed
            builder.setCalendarType("gregorian"); // override, should throw
            builder.setCalendarType("gregorian");
            assert false : "Expected IllegalStateException for override";
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}

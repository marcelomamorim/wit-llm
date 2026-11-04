/*
 * @test
 * @summary Tests for Calendar.setCalendarType method behavior.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetCalendarTypeWithValidValues();
        testSetCalendarTypeWithNull();
        testSetCalendarTypeWithInvalidValue();
        testSetCalendarTypeWithOverride();
        testSetCalendarTypeWithDifferentTypeThrows();
    }

    private static void testSetCalendarTypeWithValidValues() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setCalendarType("gregorian");
        Calendar cal = builder.build();
        assert cal != null : "Calendar should be created with 'gregorian' type";
        // Changing to 'iso8601' which is not in available types, expect exception
        try {
            builder.setCalendarType("iso8601");
            Calendar cal2 = builder.build();
            assert false : "Expected IllegalArgumentException for 'iso8601'";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetCalendarTypeWithNull() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setCalendarType(null);
            assert false : "Expected NullPointerException or IllegalArgumentException for null";
        } catch (NullPointerException | IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetCalendarTypeWithInvalidValue() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setCalendarType("invalidType");
            Calendar cal = builder.build();
            assert false : "Expected IllegalArgumentException for invalidType";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetCalendarTypeWithOverride() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setCalendarType("gregorian");
        try {
            builder.setCalendarType("gregorian"); // same type, should succeed
            Calendar cal = builder.build();
            assert cal != null : "Calendar should be built with same type";
        } catch (Exception e) {
            assert false : "No exception expected when setting same type";
        }
        // Now test override attempt
        builder.setCalendarType("gregorian");
        try {
            builder.setCalendarType("gregorian"); // same again
            Calendar cal2 = builder.build();
            assert cal2 != null : "Calendar should be built with same type";
        } catch (Exception e) {
            assert false : "No exception expected when setting same type";
        }
    }

    private static void testSetCalendarTypeWithDifferentTypeThrows() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setCalendarType("gregorian");
        // Attempt to set a different type after initial set
        try {
            builder.setCalendarType("anotherType");
            assert false : "Expected IllegalStateException when changing type";
        } catch (IllegalStateException e) {
            // expected
        }
    }
}

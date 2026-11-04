/*
 * @test
 * @summary Test java.util.Calendar.Builder.setCalendarType(String) method
 * @run main java.util.CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Set;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testSetCalendarTypeWithGregorianAlias();
        testSetCalendarTypeWithValidType();
        testSetCalendarTypeWithIso8601();
        testSetCalendarTypeWithUnknownType();
        testSetCalendarTypeOverrideSameType();
        testSetCalendarTypeOverrideDifferentType();
    }

    static void testSetCalendarTypeWithGregorianAlias() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setCalendarType("gregorian");
        // "gregorian" is converted to "gregory"
        // The type field is set to "gregory"
        // No exception expected
    }

    static void testSetCalendarTypeWithValidType() {
        Calendar.Builder builder = new Calendar.Builder();
        Set<String> types = Calendar.getAvailableCalendarTypes();
        for (String type : types) {
            Calendar.Builder b = new Calendar.Builder();
            b.setCalendarType(type);
        }
    }

    static void testSetCalendarTypeWithIso8601() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setCalendarType("iso8601");
        // "iso8601" is accepted even if not in available types
    }

    static void testSetCalendarTypeWithUnknownType() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setCalendarType("unknown-type");
            throw new RuntimeException("Expected IllegalArgumentException for unknown type");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testSetCalendarTypeOverrideSameType() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setCalendarType("gregory");
        builder.setCalendarType("gregory");
        // no exception expected when overriding with same type
    }

    static void testSetCalendarTypeOverrideDifferentType() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setCalendarType("gregory");
        try {
            builder.setCalendarType("iso8601");
            throw new RuntimeException("Expected IllegalStateException for type override");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
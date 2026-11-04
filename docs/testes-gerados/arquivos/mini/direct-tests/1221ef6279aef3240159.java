/*
 * @test
 * @summary Test java.util.Calendar.Builder.set(int field, int value) method
 * @run main java.util.CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testSetValidField();
        testSetInvalidFieldNegative();
        testSetInvalidFieldTooLarge();
        testSetAfterInstantSet();
    }

    private static void testSetValidField() {
        Calendar.Builder builder = new Calendar.Builder();
        // Set a valid field (e.g., YEAR = 1)
        Calendar.Builder result = builder.set(Calendar.YEAR, 2020);
        if (result != builder) {
            throw new AssertionError("set should return the same Builder instance");
        }
    }

    private static void testSetInvalidFieldNegative() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.set(-1, 10);
            throw new AssertionError("Expected IllegalArgumentException for negative field");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetInvalidFieldTooLarge() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.set(Calendar.FIELD_COUNT, 10); // FIELD_COUNT is out of valid range
            throw new AssertionError("Expected IllegalArgumentException for field >= FIELD_COUNT");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetAfterInstantSet() {
        Calendar.Builder builder = new Calendar.Builder();
        // Use reflection to set instantSet to true since no public API to do so
        try {
            java.lang.reflect.Field instantSetField = Calendar.Builder.class.getDeclaredField("instantSet");
            instantSetField.setAccessible(true);
            instantSetField.setBoolean(builder, true);
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed", e);
        }

        try {
            builder.set(Calendar.YEAR, 2020);
            throw new AssertionError("Expected IllegalStateException when instant is set");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test java.util.Calendar.Builder.set(int, int) for argument validation and state checks
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testSetFieldInvalid();
        testSetAfterInstantSet();
        testSetNormal();
    }

    private static void testSetFieldInvalid() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.set(-1, 10);
            throw new RuntimeException("Expected IllegalArgumentException for field < 0");
        } catch (IllegalArgumentException expected) {
        }
        try {
            builder.set(Calendar.FIELD_COUNT, 10); // FIELD_COUNT is out of range
            throw new RuntimeException("Expected IllegalArgumentException for field >= FIELD_COUNT");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testSetAfterInstantSet() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setInstant(0L); // sets instant
        try {
            builder.set(Calendar.YEAR, 2020);
            throw new RuntimeException("Expected IllegalStateException after instant has been set");
        } catch (IllegalStateException expected) {
        }
    }

    private static void testSetNormal() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.set(Calendar.YEAR, 2020);
        builder.set(Calendar.MONTH, Calendar.JANUARY);
        // No exception expected, verify chaining
        Calendar.Builder returned = builder.set(Calendar.DAY_OF_MONTH, 15);
        if (returned != builder) {
            throw new RuntimeException("set() should return the same Builder instance");
        }
    }
}

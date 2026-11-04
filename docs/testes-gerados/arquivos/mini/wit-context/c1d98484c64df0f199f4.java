/*
 * @test
 * @summary Test java.util.Calendar.Builder.setInstant(Date) for expected behavior and exception
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Date;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testSetInstantWithValidDate();
        testSetInstantWithNullDate();
        testSetInstantThrowsWhenFieldsNotNull();
    }

    private static void testSetInstantWithValidDate() {
        Calendar.Builder builder = new Calendar.Builder();
        Date now = new Date();
        Calendar.Builder result = builder.setInstant(now);
        if (result == null) {
            throw new AssertionError("setInstant returned null for valid Date");
        }
    }

    private static void testSetInstantWithNullDate() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setInstant(null);
            throw new AssertionError("Expected NullPointerException for null Date argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSetInstantThrowsWhenFieldsNotNull() {
        // The WIT expath indicates IllegalStateException if fields != null
        // We simulate this by calling setInstant twice on the same builder
        Calendar.Builder builder = new Calendar.Builder();
        Date now = new Date();
        builder.setInstant(now); // first call sets fields
        try {
            builder.setInstant(now); // second call should throw IllegalStateException
            throw new AssertionError("Expected IllegalStateException when fields already set");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}

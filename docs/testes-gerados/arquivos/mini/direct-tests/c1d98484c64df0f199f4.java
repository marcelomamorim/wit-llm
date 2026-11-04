/*
 * @test
 * @summary Test java.util.Calendar.Builder.setInstant(Date) method behavior
 * @run main java.util.CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Date;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testSetInstantWithValidDate();
        testSetInstantWithNullDate();
    }

    private static void testSetInstantWithValidDate() {
        Calendar.Builder builder = new Calendar.Builder();
        Date date = new Date(123456789L);
        Calendar.Builder result = builder.setInstant(date);
        if (result != builder) {
            throw new RuntimeException("setInstant(Date) should return the same Builder instance");
        }
    }

    private static void testSetInstantWithNullDate() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setInstant(null);
            throw new RuntimeException("Expected NullPointerException when passing null to setInstant(Date)");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }
}

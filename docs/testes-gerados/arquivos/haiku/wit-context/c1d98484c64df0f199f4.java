/*
 * @test
 * @summary Witup generated checks for Calendar.Builder.setInstant(Date)
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Date;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testNullInstantThrowsNPE();
        testValidInstantRoundTrip();
    }

    static void testNullInstantThrowsNPE() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setInstant((Date) null);
            throw new RuntimeException("expected NullPointerException for null instant");
        } catch (NullPointerException expected) {
            // expected behavior
        }
    }

    static void testValidInstantRoundTrip() {
        Date instant = new Date(1500000000000L);
        Date result = new Calendar.Builder().setInstant(instant).build().getTime();
        if (result.getTime() != instant.getTime()) {
            throw new RuntimeException("instant not preserved: " + result.getTime());
        }
    }
}

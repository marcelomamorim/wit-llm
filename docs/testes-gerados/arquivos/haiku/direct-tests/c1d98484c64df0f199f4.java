/*
 * @test
 * @summary Verify Calendar.Builder.setInstant(Date) delegates to the millisecond value and rejects null
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Date;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetInstantUsesDateMillis();
        testSetInstantReturnsSameBuilder();
        testSetInstantNullThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetInstantUsesDateMillis() {
        long millis = 1234567890123L;
        Calendar cal = new Calendar.Builder()
                .setInstant(new Date(millis))
                .build();
        check(cal.getTimeInMillis() == millis,
              "expected millis " + millis + " but was " + cal.getTimeInMillis());
    }

    static void testSetInstantReturnsSameBuilder() {
        Calendar.Builder builder = new Calendar.Builder();
        Calendar.Builder result = builder.setInstant(new Date(0L));
        check(result == builder, "setInstant(Date) should return the same Builder");
        check(builder.build().getTimeInMillis() == 0L,
              "epoch instant should yield time 0");
    }

    static void testSetInstantNullThrowsNPE() {
        boolean thrown = false;
        try {
            new Calendar.Builder().setInstant((Date) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "setInstant((Date) null) should throw NullPointerException");
    }
}

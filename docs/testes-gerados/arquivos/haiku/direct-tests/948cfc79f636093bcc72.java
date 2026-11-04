/*
 * @test
 * @summary Verifies Calendar.Builder.setTimeZone null handling, chaining, and applied zone
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testNullThrowsNPE();
        testReturnsSameBuilder();
        testZoneIsApplied();
        testLastZoneWins();
    }

    static void testNullThrowsNPE() {
        Calendar.Builder b = new Calendar.Builder();
        try {
            b.setTimeZone(null);
            throw new RuntimeException("Expected NullPointerException for null zone");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReturnsSameBuilder() {
        Calendar.Builder b = new Calendar.Builder();
        Calendar.Builder r = b.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (r != b) {
            throw new RuntimeException("setTimeZone must return this builder");
        }
    }

    static void testZoneIsApplied() {
        TimeZone tokyo = TimeZone.getTimeZone("Asia/Tokyo");
        Calendar cal = new Calendar.Builder().setTimeZone(tokyo).build();
        String id = cal.getTimeZone().getID();
        if (!"Asia/Tokyo".equals(id)) {
            throw new RuntimeException("Expected Asia/Tokyo but got " + id);
        }
    }

    static void testLastZoneWins() {
        Calendar.Builder b = new Calendar.Builder()
                .setTimeZone(TimeZone.getTimeZone("UTC"))
                .setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        Calendar cal = b.build();
        String id = cal.getTimeZone().getID();
        if (!"Asia/Tokyo".equals(id)) {
            throw new RuntimeException("Expected last zone Asia/Tokyo but got " + id);
        }
    }
}

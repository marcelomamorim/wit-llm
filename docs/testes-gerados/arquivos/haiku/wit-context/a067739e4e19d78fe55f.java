/*
 * @test
 * @summary Witup-derived tests for MonthDay.now(Clock)
 * @run main MonthDayWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.MonthDay;
import java.time.ZoneOffset;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        Clock utc = Clock.fixed(Instant.parse("2018-03-15T10:15:30Z"), ZoneOffset.UTC);
        MonthDay md = MonthDay.now(utc);
        if (!md.equals(MonthDay.of(3, 15))) {
            throw new RuntimeException("expected --03-15 but got " + md);
        }

        Clock offset = Clock.fixed(Instant.parse("2018-12-31T23:30:00Z"), ZoneOffset.ofHours(2));
        MonthDay shifted = MonthDay.now(offset);
        if (!shifted.equals(MonthDay.of(1, 1))) {
            throw new RuntimeException("expected --01-01 but got " + shifted);
        }

        try {
            MonthDay.now(null);
            throw new RuntimeException("expected NullPointerException for null clock");
        } catch (NullPointerException expected) {
        }
    }
}

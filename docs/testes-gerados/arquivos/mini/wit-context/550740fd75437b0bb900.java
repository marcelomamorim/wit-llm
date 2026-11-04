/*
 * @test
 * @summary Test OffsetTime.atDate(LocalDate) for normal and exceptional cases
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testAtDateNormal();
        testAtDateNullDate();
    }

    private static void testAtDateNormal() {
        LocalTime lt = LocalTime.of(10, 15, 30, 123456789);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 30);
        OffsetTime ot = OffsetTime.of(lt, offset);
        LocalDate date = LocalDate.of(2020, 5, 20);

        OffsetDateTime odt = ot.atDate(date);

        if (!odt.toLocalDate().equals(date)) {
            throw new AssertionError("atDate returned OffsetDateTime with wrong date");
        }
        if (!odt.toLocalTime().equals(lt)) {
            throw new AssertionError("atDate returned OffsetDateTime with wrong time");
        }
        if (!odt.getOffset().equals(offset)) {
            throw new AssertionError("atDate returned OffsetDateTime with wrong offset");
        }
    }

    private static void testAtDateNullDate() {
        LocalTime lt = LocalTime.of(0, 0);
        ZoneOffset offset = ZoneOffset.UTC;
        OffsetTime ot = OffsetTime.of(lt, offset);

        try {
            ot.atDate(null);
            throw new AssertionError("Expected NullPointerException when date is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

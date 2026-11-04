/*
 * @test
 * @summary Verifies LocalDateTime.minusHours on boundaries, negative, zero and round-trip cases
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2020, 1, 1, 0, 0);

        check(base.minusHours(1).equals(LocalDateTime.of(2019, 12, 31, 23, 0)), "minusHours(1) across year boundary");
        check(base.minusHours(0).equals(base), "minusHours(0) must keep the value");
        check(base.minusHours(-1).equals(LocalDateTime.of(2020, 1, 1, 1, 0)), "minusHours(-1) must add one hour");
        check(base.minusHours(24).equals(LocalDateTime.of(2019, 12, 31, 0, 0)), "minusHours(24) must move to previous day");

        LocalDateTime leap = LocalDateTime.of(2020, 3, 1, 0, 30, 15, 500);
        check(leap.minusHours(1).equals(LocalDateTime.of(2020, 2, 29, 23, 30, 15, 500)), "minusHours across leap day must keep seconds and nanos");
        check(leap.minusHours(1).plusHours(1).equals(leap), "minusHours followed by plusHours must round-trip");

        LocalDateTime t = LocalDateTime.of(2021, 6, 15, 12, 0);
        check(t.minusHours(36).equals(LocalDateTime.of(2021, 6, 14, 0, 0)), "minusHours(36) must be 1.5 days earlier");
        check(t.minusHours(12).getDayOfMonth() == 15 && t.minusHours(12).getHour() == 0, "minusHours(12) must reach midnight of same day");
        check(t.minusHours(12).toLocalDate().equals(LocalDateTime.of(2021, 6, 15, 0, 0).toLocalDate()), "minusHours(12) must keep the same date");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

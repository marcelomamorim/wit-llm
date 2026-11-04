/*
 * @test
 * @summary WITUP-derived tests for LocalTime.of(int hour, int minute, int second) exception paths and boundaries
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        checkValid(0, 0, 0);
        checkValid(23, 0, 0);
        checkValid(23, 59, 59);
        checkValid(0, 0, 59);
        checkValid(0, 59, 0);
        checkValid(12, 30, 45);

        checkThrows(-1, 0, 0);
        checkThrows(24, 0, 0);
        checkThrows(-1, 30, 15);
        checkThrows(24, 30, 15);
        checkThrows(Integer.MIN_VALUE, 0, 0);
        checkThrows(Integer.MAX_VALUE, 0, 0);

        checkThrows(10, 60, 0);
        checkThrows(10, -1, 0);
        checkThrows(10, 0, 60);
        checkThrows(10, 0, -1);
        checkThrows(10, 60, 60);
        checkThrows(0, 0, 60);
        checkThrows(0, 60, 0);
    }

    static void checkValid(int hour, int minute, int second) {
        LocalTime t = LocalTime.of(hour, minute, second);
        if (t.getHour() != hour || t.getMinute() != minute
                || t.getSecond() != second || t.getNano() != 0) {
            throw new RuntimeException("unexpected fields for " + hour + ":" + minute + ":" + second);
        }
        if (!t.equals(LocalTime.of(hour, minute, second, 0))) {
            throw new RuntimeException("inconsistent with four-argument factory for " + hour + ":" + minute + ":" + second);
        }
    }

    static void checkThrows(int hour, int minute, int second) {
        try {
            LocalTime.of(hour, minute, second);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for " + hour + ":" + minute + ":" + second);
    }
}

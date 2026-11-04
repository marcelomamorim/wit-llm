/*
 * @test
 * @summary Witup-derived checks for LocalDateTime.of(int, Month, int, int, int, int)
 * @run main LocalDateTimeWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    interface Action {
        void run();
    }

    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2020, Month.FEBRUARY, 29, 10, 20, 30);
        check(dt.getYear() == 2020 && dt.getMonth() == Month.FEBRUARY && dt.getDayOfMonth() == 29
                && dt.getHour() == 10 && dt.getMinute() == 20 && dt.getSecond() == 30, 1);

        LocalDateTime max = LocalDateTime.of(999999999, Month.DECEMBER, 31, 23, 59, 59);
        check(max.getYear() == 999999999 && max.getDayOfMonth() == 31, 2);

        expectDateTime(() -> LocalDateTime.of(1000000000, Month.JANUARY, 1, 0, 0, 0), 3);
        expectDateTime(() -> LocalDateTime.of(-1000000000, Month.JANUARY, 1, 0, 0, 0), 4);
        expectDateTime(() -> LocalDateTime.of(2021, Month.FEBRUARY, 29, 0, 0, 0), 5);
        expectDateTime(() -> LocalDateTime.of(2021, Month.APRIL, 31, 0, 0, 0), 6);
        expectDateTime(() -> LocalDateTime.of(2021, Month.JANUARY, 0, 0, 0, 0), 7);
        expectDateTime(() -> LocalDateTime.of(2021, Month.JANUARY, 1, 24, 0, 0), 8);
        expectDateTime(() -> LocalDateTime.of(2021, Month.JANUARY, 1, -1, 0, 0), 9);
        expectDateTime(() -> LocalDateTime.of(2021, Month.JANUARY, 1, 0, 60, 0), 10);
        expectDateTime(() -> LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0, 60), 11);

        expectNpe(() -> LocalDateTime.of(2021, null, 1, 0, 0, 0), 12);
    }

    static void expectDateTime(Action action, int id) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new AssertionError(id);
    }

    static void expectNpe(Action action, int id) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError(id);
    }

    static void check(boolean condition, int id) {
        if (!condition) {
            throw new AssertionError(id);
        }
    }
}

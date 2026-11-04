/*
 * @test
 * @summary LocalDateTime.of(LocalDate, LocalTime) valid construction and NullPointerException on null arguments
 * @run main LocalDateTimeWitupTest
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2018, 9, 15);
        LocalTime time = LocalTime.of(10, 30, 15);
        LocalDateTime dt = LocalDateTime.of(date, time);
        if (!dt.toLocalDate().equals(date)) {
            throw new RuntimeException("date part mismatch");
        }
        if (!dt.toLocalTime().equals(time)) {
            throw new RuntimeException("time part mismatch");
        }
        if (dt.getYear() != 2018 || dt.getHour() != 10 || dt.getSecond() != 15) {
            throw new RuntimeException("unexpected field values");
        }
        expectNpe(null, time);
        expectNpe(date, null);
        expectNpe(null, null);
    }

    static void expectNpe(LocalDate d, LocalTime t) {
        try {
            LocalDateTime.of(d, t);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected");
    }
}

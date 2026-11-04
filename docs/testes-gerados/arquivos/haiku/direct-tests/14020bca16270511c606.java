/*
 * @test
 * @summary Verifies LocalDate.getMonth() returns the Month matching the stored month value
 * @run main LocalDateWitupTest
 */
import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        Month[] months = Month.values();
        for (int m = 1; m <= 12; m++) {
            LocalDate d = LocalDate.of(2021, m, 1);
            Month expected = months[m - 1];
            check(d.getMonth() == expected, "month mismatch for month " + m);
            check(d.getMonth().getValue() == d.getMonthValue(), "value mismatch for month " + m);
        }
        check(LocalDate.of(2020, 2, 29).getMonth() == Month.FEBRUARY, "leap day month");
        check(LocalDate.of(2000, 1, 1).getMonth() == Month.JANUARY, "january 1st");
        check(LocalDate.of(1999, 12, 31).getMonth() == Month.DECEMBER, "december 31st");
        check(LocalDate.of(2021, 6, 15).getMonth() == Month.of(6), "june via Month.of");
        check(LocalDate.of(2021, 6, 15).getMonth() == LocalDate.of(2021, 6, 30).getMonth(), "same month different days");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

/*
 * @test
 * @summary Witup-derived checks for Year.adjustInto(Temporal)
 * @run main YearWitupTest
 */
import java.time.LocalDate;
import java.time.Year;

public class YearWitupTest {
    public static void main(String[] args) {
        LocalDate leap = (LocalDate) Year.of(2020).adjustInto(LocalDate.of(2000, 2, 29));
        check(leap.equals(LocalDate.of(2020, 2, 29)), "leap day adjusted into leap year");

        LocalDate clamped = (LocalDate) Year.of(2021).adjustInto(LocalDate.of(2000, 2, 29));
        check(clamped.equals(LocalDate.of(2021, 2, 28)), "Feb 29 clamped to Feb 28 in non-leap year");

        LocalDate same = (LocalDate) Year.of(1999).adjustInto(LocalDate.of(1999, 12, 31));
        check(same.equals(LocalDate.of(1999, 12, 31)), "same year keeps date");

        boolean thrown = false;
        try {
            Year.of(2020).adjustInto(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null temporal must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
